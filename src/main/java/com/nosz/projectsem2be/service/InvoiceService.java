package com.nosz.projectsem2be.service;

import com.nosz.projectsem2be.dto.InvoiceDetailsDto;
import com.nosz.projectsem2be.dto.InvoiceDto;
import com.nosz.projectsem2be.dto.OrderDetailCusDto;
import com.nosz.projectsem2be.entity.Invoice;
import com.nosz.projectsem2be.entity.InvoiceDetails;
import com.nosz.projectsem2be.entity.InvoiceStatus;
import com.nosz.projectsem2be.exception.CartException;
import com.nosz.projectsem2be.exception.CategoryException;
import com.nosz.projectsem2be.exception.InvoiceException;
import com.nosz.projectsem2be.exception.UserLoginException;
import com.nosz.projectsem2be.respository.CartRepository;
import com.nosz.projectsem2be.respository.InvoiceDetailsRepository;
import com.nosz.projectsem2be.respository.InvoiceRepository;
import com.nosz.projectsem2be.respository.UserRepository;
import com.nosz.projectsem2be.security.jwt.JwtTokenFilter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceService {
    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    InvoiceDetailsRepository invoiceDetailsRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void checkOutOrder(InvoiceDto invoiceDto){
        String email = JwtTokenFilter.CURRENT_USER;
        var user = userRepository.findByEmail(email).orElseThrow(() ->
                new UserLoginException("You must login before!"));
        var cartList = cartRepository.findByUser_Email(email);
        if(cartList.size() > 0){
            Invoice invoice = new Invoice();
            BeanUtils.copyProperties(invoiceDto,invoice);
            invoice.setUser(user);
            invoice.setInvoiceStatus(InvoiceStatus.Pending);
            invoiceRepository.save(invoice);
            cartList.forEach(item ->{
                InvoiceDetails invoiceDetails = new InvoiceDetails();
                invoiceDetails.setQuantity(item.getQuantity());
                invoiceDetails.setInvoice(invoice);
                invoiceDetails.setProduct(item.getProduct());
                invoiceDetailsRepository.save(invoiceDetails);
                cartRepository.deleteByUser(user.getId());
            });
        }else {
            throw new CartException("Cart List is empty");
        }

    }
    public Page<?> getAllOrder(Pageable pageable){
        var list = invoiceRepository.findAll(pageable);
        List<InvoiceDto> listDto = new ArrayList<>();
        list.forEach(item -> {
            InvoiceDto invoiceDto = new InvoiceDto();
            BeanUtils.copyProperties(item,invoiceDto);
            invoiceDto.setUserId(item.getUser().getId());
            invoiceDto.setAccountName(item.getUser().getEmail());
            listDto.add(invoiceDto);
        });
        return new PageImpl<>(listDto);
    }

    public Page<?> getAllOrderByStatus(String email,List<InvoiceStatus> invoiceStatus,Pageable pageable){
        var list = invoiceRepository.findByUser_EmailContainsIgnoreCaseOrInvoiceStatus(email,invoiceStatus,pageable);
        List<InvoiceDto> listDto = new ArrayList<>();
        list.forEach(item -> {
            InvoiceDto invoiceDto = new InvoiceDto();
            BeanUtils.copyProperties(item,invoiceDto);
            invoiceDto.setUserId(item.getUser().getId());
            invoiceDto.setAccountName(item.getUser().getEmail());
            listDto.add(invoiceDto);
        });
        return new PageImpl<>(listDto);
    }

    public InvoiceDto findById(Long id) {
        var found  =  invoiceRepository.findById(id).orElseThrow(() -> new CategoryException("Category id " + id + " does not existed"));
        InvoiceDto invoiceDto = new InvoiceDto();
        BeanUtils.copyProperties(found,invoiceDto);

        return invoiceDto;
    }

    public Page<InvoiceDto> getOrderByUserEmail(String email, Pageable pageable){
        var list = invoiceRepository.findByUser_EmailContainsIgnoreCase(email, pageable);

        List<InvoiceDto> listDto = new ArrayList<>();
        list.forEach(item -> {
            InvoiceDto invoiceDto = new InvoiceDto();
            BeanUtils.copyProperties(item,invoiceDto);
            invoiceDto.setUserId(item.getUser().getId());
            invoiceDto.setAccountName(item.getUser().getEmail());
            listDto.add(invoiceDto);
        });
        return new PageImpl<>(listDto);
    }

    public Page<?> getOrderByUser(Pageable pageable){
        String email = JwtTokenFilter.CURRENT_USER;
        userRepository.findByEmail(email).orElseThrow(() ->
                new UserLoginException("You must login before!"));
        var list = invoiceDetailsRepository.findByInvoice_User_Email(email,pageable);
        List<OrderDetailCusDto> listDto = new ArrayList<>();
        list.forEach(item -> {
            OrderDetailCusDto invoiceDetailsDto = new OrderDetailCusDto();
            invoiceDetailsDto.setDiscount(item.getProduct().getDiscount());
            invoiceDetailsDto.setQuantity(item.getQuantity());
            invoiceDetailsDto.setSubTotal(item.getSubtotal());
            invoiceDetailsDto.setNameProduct(item.getProduct().getName());
            invoiceDetailsDto.setPrice(item.getProduct().getPrice());
            invoiceDetailsDto.setInvoiceStatus(item.getInvoice().getInvoiceStatus());
            listDto.add(invoiceDetailsDto);
        });
        return new PageImpl<>(listDto);
    }

    public Page<?> getOrderDetailByOrderId(Pageable pageable,Long orderId){
        var list = invoiceDetailsRepository.findByInvoice_Id(orderId,pageable);
        List<InvoiceDetailsDto> listDto = new ArrayList<>();
        list.forEach(item -> {
            InvoiceDetailsDto invoiceDetailsDto = new InvoiceDetailsDto();
            invoiceDetailsDto.setDiscount(item.getProduct().getDiscount());
            invoiceDetailsDto.setQuantity(item.getQuantity());
            invoiceDetailsDto.setSubTotal(item.getSubtotal());
            invoiceDetailsDto.setNameProduct(item.getProduct().getName());
            invoiceDetailsDto.setPrice(item.getProduct().getPrice());
            listDto.add(invoiceDetailsDto);
        });
        return new PageImpl<>(listDto);
    }

    @Transactional
    public void deleteInvoice(Long id){
        var invoice = invoiceRepository.findOrderById(id);
        if(invoice == null){
            throw new InvoiceException("Invoice do not existed");
        }
        if(invoice.getInvoiceStatus() == InvoiceStatus.Success){
            throw new InvoiceException("Invoice status is successful, you cannot delete");
        }
        invoiceRepository.delete(invoice);
    }

    @Transactional
    public InvoiceDto updateOrder(Long id,InvoiceDto invoiceDto){
        var founded = invoiceRepository.findById(id).orElseThrow(() ->
                new InvoiceException("Not found order"));
        if(founded.getInvoiceStatus() == InvoiceStatus.Success){
            throw new InvoiceException("Invoice status is successful, you cannot update");
        }
        String [] ignoreFields = new String[]{"createDate, user"};
        BeanUtils.copyProperties(invoiceDto,founded,ignoreFields);
        invoiceRepository.save(founded);
        invoiceDto.setId(founded.getId());
        return invoiceDto;
    }
}
