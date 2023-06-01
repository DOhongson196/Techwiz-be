package com.nosz.projectsem2be.service;

import com.nosz.projectsem2be.dto.CartDto;
import com.nosz.projectsem2be.dto.CartItemDto;
import com.nosz.projectsem2be.entity.Cart;
import com.nosz.projectsem2be.exception.ProductException;
import com.nosz.projectsem2be.exception.UserLoginException;
import com.nosz.projectsem2be.respository.CartRepository;
import com.nosz.projectsem2be.respository.ProductRepository;
import com.nosz.projectsem2be.respository.UserRepository;
import com.nosz.projectsem2be.security.jwt.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;

    public List<CartItemDto> lisCartItem(){
        String email = JwtTokenFilter.CURRENT_USER;
        if(email == null){
           throw new UserLoginException("You must login before!");
        }
        List<Cart> cartList = cartRepository.findByUser_Email(email);
        List<CartItemDto> cartDtoList = new ArrayList<>();
        cartList.forEach(item -> {
            CartItemDto cartDto = new CartItemDto();
            cartDto.setSubTotal(item.getSubtotal());
            cartDto.setDiscount(item.getProduct().getDiscount());
            cartDto.setImage(item.getProduct().getImage());
            cartDto.setNameProduct(item.getProduct().getName());
            cartDto.setPrice(item.getProduct().getPrice());
            cartDto.setQuantity(item.getQuantity());
            cartDtoList.add(cartDto);
        });
        return cartDtoList;
    }
    public CartDto addToCart(Long productId){
       var foundedProduct = productRepository.findById(productId).orElseThrow(() ->
               new ProductException("Not found product"));
       String email = JwtTokenFilter.CURRENT_USER;
       var foundedUser = userRepository.findByEmail(email).orElseThrow(() ->
               new UserLoginException("You must login before!"));
        Cart cartItem = cartRepository.findByUser_EmailAndProduct_Id(email,productId);
        if(cartItem!=null){
            int addedQuantity = cartItem.getQuantity() + 1;
            cartItem.setQuantity(addedQuantity);
        }else{
            cartItem = new Cart();
            cartItem.setQuantity(1);
            cartItem.setUser(foundedUser);
            cartItem.setProduct(foundedProduct);
        }
        cartRepository.save(cartItem);
        CartDto cartDto = new CartDto();
        cartDto.setProductId(cartItem.getProduct().getId());
        cartDto.setUserId(cartItem.getUser().getId());
        cartDto.setSubTotal(cartItem.getSubtotal());
        return cartDto;
    }

    @Transactional
    public CartDto updateQuantity(Integer quantity,Long productId){
        productRepository.findById(productId).orElseThrow(() ->
                new ProductException("Not found product"));
        String email = JwtTokenFilter.CURRENT_USER;
        var user = userRepository.findByEmail(email).orElseThrow(() ->
                new UserLoginException("You must login before!"));
        cartRepository.updateQuantity(quantity,productId,user.getId());
        Cart cartItem = cartRepository.findByUser_EmailAndProduct_Id(email,productId);
        CartDto cartDto = new CartDto();
        cartDto.setProductId(cartItem.getProduct().getId());
        cartDto.setUserId(cartItem.getUser().getId());
        cartDto.setSubTotal(cartItem.getSubtotal());
        return cartDto;
    }

    @Transactional
    public void deleteCartItem(Long productId){
        productRepository.findById(productId).orElseThrow(() ->
                new ProductException("Not found product"));
        String email = JwtTokenFilter.CURRENT_USER;
        var user = userRepository.findByEmail(email).orElseThrow(() ->
                new UserLoginException("You must login before!"));
        cartRepository.deleteByProductAndUser(productId,user.getId());
    }

}
