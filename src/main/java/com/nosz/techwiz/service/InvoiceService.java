package com.nosz.techwiz.service;


import com.nosz.techwiz.dto.InvoiceDetailsDto;
import com.nosz.techwiz.dto.InvoiceDto;
import com.nosz.techwiz.entity.Enum.InvoiceStatus;
import com.nosz.techwiz.entity.Invoice;
import com.nosz.techwiz.entity.InvoiceDetails;
import com.nosz.techwiz.exception.CustomException;
import com.nosz.techwiz.respository.*;
import com.nosz.techwiz.security.jwt.JwtTokenFilter;
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
    @Autowired
    private ProductRepository productRepository;


    @Transactional
    public void checkOutOrder(InvoiceDto invoiceDto){
        String email = JwtTokenFilter.CURRENT_USER;
        var user = userRepository.findByEmail(email).orElseThrow(() ->
                new CustomException("You must login before!"));
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
                invoiceDetails.setPrice(item.getProduct().getPrice());
                invoiceDetails.setDiscount(item.getProduct().getDiscount());
                if(item.getProduct().getQuantity() < item.getQuantity()){
                    throw new CustomException("Product Quantity < Cart Quantity");
                }else{
                    item.getProduct().setQuantity(item.getProduct().getQuantity() - item.getQuantity());
                }
                productRepository.save(item.getProduct());
                invoiceDetailsRepository.save(invoiceDetails);
                cartRepository.deleteByUser(user.getId());
            });
        }else {
            throw new CustomException("Cart List is empty");
        }
    }
    public Page<?> getAllOrder(Pageable pageable){
        var list = invoiceRepository.findAll(pageable);
        List<InvoiceDto> listDto = new ArrayList<>();
        list.forEach(item -> {
            InvoiceDto invoiceDto = new InvoiceDto();
            BeanUtils.copyProperties(item,invoiceDto);
            listDto.add(invoiceDto);
        });
        return new PageImpl<>(listDto,list.getPageable(),list.getTotalPages());
    }

    public Page<?> getAllOrderByStatus(String email, List<InvoiceStatus> invoiceStatus, Pageable pageable){
        var list = invoiceRepository.findByUser_EmailContainsIgnoreCaseAndInvoiceStatusIn(email,invoiceStatus,pageable);
        List<InvoiceDto> listDto = new ArrayList<>();
        list.forEach(item -> {
            InvoiceDto invoiceDto = new InvoiceDto();
            BeanUtils.copyProperties(item,invoiceDto);
            invoiceDto.setUserId(item.getUser().getId());
            invoiceDto.setAccountName(item.getUser().getEmail());
            listDto.add(invoiceDto);
        });
        return new PageImpl<>(listDto,list.getPageable(),list.getTotalPages());
    }

    public InvoiceDto findById(Long id) {
        var found  =  invoiceRepository.findById(id).orElseThrow(() -> new CustomException("Invoice id " + id + " does not existed"));
        InvoiceDto invoiceDto = new InvoiceDto();
        BeanUtils.copyProperties(found,invoiceDto);

        return invoiceDto;
    }


    public Page<?> getOrderByUser(Pageable pageable){
        String email = JwtTokenFilter.CURRENT_USER;
        userRepository.findByEmail(email).orElseThrow(() ->
                new CustomException("You must login before!"));
        var list = invoiceRepository.findByUser_Email(email,pageable);
        List<InvoiceDto> listDto = new ArrayList<>();
        list.forEach(item -> {
            InvoiceDto invoiceDto = new InvoiceDto();
            BeanUtils.copyProperties(item,invoiceDto);
            listDto.add(invoiceDto);
        });
        return new PageImpl<>(listDto,list.getPageable(),list.getTotalPages());
    }

    public Page<?> getOrderDetailByOrderId(Pageable pageable,Long orderId){
        var list = invoiceDetailsRepository.findByInvoice_Id(orderId,pageable);
        List<InvoiceDetailsDto> listDto = new ArrayList<>();
        list.forEach(item -> {
            InvoiceDetailsDto invoiceDetailsDto = new InvoiceDetailsDto();
            invoiceDetailsDto.setDiscount(item.getDiscount());
            invoiceDetailsDto.setQuantity(item.getQuantity());
            invoiceDetailsDto.setSubTotal(item.getSubtotal());
            invoiceDetailsDto.setNameProduct(item.getProduct().getName());
            invoiceDetailsDto.setPrice(item.getPrice());
            listDto.add(invoiceDetailsDto);
        });
        return new PageImpl<>(listDto,list.getPageable(),list.getTotalPages());
    }

    @Transactional
    public void deleteInvoice(Long id){
        var invoice = invoiceRepository.findOrderById(id);
        if(invoice == null){
            throw new CustomException("Invoice do not existed");
        }
        if(invoice.getInvoiceStatus() == InvoiceStatus.Success){
            throw new CustomException("Invoice status is successful, you cannot delete");
        }
        invoiceRepository.delete(invoice);
    }

    @Transactional
    public InvoiceDto updateOrder(Long id,InvoiceDto invoiceDto){
        var founded = invoiceRepository.findById(id).orElseThrow(() ->
                new CustomException("Not found order"));
        if(founded.getInvoiceStatus() == InvoiceStatus.Success){
            throw new CustomException("Invoice status is successful, you cannot update");
        }
        String [] ignoreFields = new String[]{"createDate, user"};
        BeanUtils.copyProperties(invoiceDto,founded,ignoreFields);
        invoiceRepository.save(founded);
        invoiceDto.setId(founded.getId());
        return invoiceDto;
    }

}
