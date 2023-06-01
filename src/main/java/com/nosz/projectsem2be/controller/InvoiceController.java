package com.nosz.projectsem2be.controller;

import com.nosz.projectsem2be.dto.InvoiceDto;
import com.nosz.projectsem2be.entity.InvoiceStatus;
import com.nosz.projectsem2be.service.InvoiceService;
import com.nosz.projectsem2be.service.MapValidationErrorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
@RestController
@RequestMapping("/api/v1/order")
public class InvoiceController {
    @Autowired
    InvoiceService invoiceService;
    @Autowired
    private MapValidationErrorService mapValidationErrorService;
    @PostMapping
    public ResponseEntity<?> placeOrder(@Valid  @RequestBody InvoiceDto invoiceDto, BindingResult bindingResult) {
        ResponseEntity<?> responseEntity = mapValidationErrorService.mapValidationFields(bindingResult);
        if(responseEntity != null){
            return responseEntity;
        }
        invoiceService.checkOutOrder(invoiceDto);
        return new ResponseEntity<>("Place order success!", HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<?> getAllOrder(@PageableDefault(size = 5,sort = "id", direction = Sort.Direction.ASC)
                                              Pageable pageable){
        return new ResponseEntity<>(invoiceService.getAllOrder(pageable),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable("id") Long id){
        return new ResponseEntity<>(invoiceService.findById(id),HttpStatus.OK);
    }

    @GetMapping("/page/user")
    public ResponseEntity<?> getOrderByUser(@PageableDefault(size = 5,sort = "id", direction = Sort.Direction.ASC)
                                         Pageable pageable){
        return new ResponseEntity<>(invoiceService.getOrderByUser(pageable),HttpStatus.OK);
    }

    @GetMapping("/page/order/{id}")
    public ResponseEntity<?> getOrderDetailByOrderId(@PathVariable("id") Long id,
                                                     @PageableDefault(size = 5,sort = "id", direction = Sort.Direction.ASC)
                                            Pageable pageable){
        return new ResponseEntity<>(invoiceService.getOrderDetailByOrderId(pageable,id),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable("id") Long id){
        invoiceService.deleteInvoice(id);
        return new ResponseEntity<>("Order id " + id + " was deleted",HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateInvoice(@Valid @PathVariable Long id,
                                           @RequestBody InvoiceDto invoiceDto,
                                           BindingResult bindingResult){
        ResponseEntity<?> responseEntity = mapValidationErrorService.mapValidationFields(bindingResult);
        if(responseEntity!=null){
            return responseEntity;
        }
        return new ResponseEntity<>(invoiceService.updateOrder(id,invoiceDto),HttpStatus.OK);
    }

    @GetMapping("/find1")
    public ResponseEntity<?> getInvoiceByName(@RequestParam("query") String query,
                                                   @PageableDefault(size = 5, sort = "id",direction = Sort.Direction.ASC)
                                                   Pageable pageable){
        return new ResponseEntity<>(invoiceService.getOrderByUserEmail(query,pageable),HttpStatus.OK);
    }

    @GetMapping("/find2")
    public ResponseEntity<?> getInvoiceByNameAndInvoiceStatus(@RequestParam("query") String query,
                                                              @RequestParam("status") InvoiceStatus[] status,
                                              @PageableDefault(size = 5, sort = "id",direction = Sort.Direction.ASC)
                                              Pageable pageable){
        return new ResponseEntity<>(invoiceService.getAllOrderByStatus(query, List.of(status),pageable),HttpStatus.OK);
    }

}
