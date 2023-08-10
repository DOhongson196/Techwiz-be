package com.nosz.techwiz.service;

import com.nosz.techwiz.dto.CartDto;
import com.nosz.techwiz.dto.CartItemDto;
import com.nosz.techwiz.entity.Cart;
import com.nosz.techwiz.exception.CustomException;
import com.nosz.techwiz.respository.CartRepository;
import com.nosz.techwiz.respository.ProductRepository;
import com.nosz.techwiz.respository.UserRepository;
import com.nosz.techwiz.security.jwt.JwtTokenFilter;
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
            throw new CustomException("You must login before!");
        }
        List<Cart> cartList = cartRepository.findByUser_Email(email);
        List<CartItemDto> cartDtoList = new ArrayList<>();
        cartList.forEach(item -> {
            CartItemDto cartDto = new CartItemDto();
            cartDto.setId(item.getProduct().getId());
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
                new CustomException("Not found product"));
        String email = JwtTokenFilter.CURRENT_USER;
        var foundedUser = userRepository.findByEmail(email).orElseThrow(() ->
                new CustomException("You must login before!"));
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
                new CustomException("Not found product"));
        String email = JwtTokenFilter.CURRENT_USER;
        var user = userRepository.findByEmail(email).orElseThrow(() ->
                new CustomException("You must login before!"));
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
                new CustomException("Not found product"));
        String email = JwtTokenFilter.CURRENT_USER;
        var user = userRepository.findByEmail(email).orElseThrow(() ->
                new CustomException("You must login before!"));
        cartRepository.deleteByProductAndUser(productId,user.getId());
    }

}
