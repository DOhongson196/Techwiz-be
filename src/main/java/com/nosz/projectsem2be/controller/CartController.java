package com.nosz.projectsem2be.controller;

import com.nosz.projectsem2be.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Secured({"ROLE_CUSTOMER"})
    @GetMapping("/{id}")
    public ResponseEntity<?> addToCart(@PathVariable("id") Long id){
        return new ResponseEntity<>(cartService.addToCart(id), HttpStatus.OK);
    }

    @Secured({"ROLE_CUSTOMER"})
    @GetMapping
    public ResponseEntity<?> showShoppingCart(){
        return new ResponseEntity<>(cartService.lisCartItem(), HttpStatus.OK);
    }

    @Secured({"ROLE_CUSTOMER"})
    @GetMapping("/{id}/{quantity}")
    public ResponseEntity<?> updateQuantity(@PathVariable("id") Long id,@PathVariable("quantity") Integer quantity){
        return new ResponseEntity<>(cartService.updateQuantity(quantity,id), HttpStatus.OK);
    }

    @Secured({"ROLE_CUSTOMER"})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long id){
        cartService.deleteCartItem(id);
        return new ResponseEntity<>("Product  id " + id + " has been removed your shopping cart", HttpStatus.OK);
    }

}
