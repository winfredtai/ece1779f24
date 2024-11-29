package com.team9.ece1779f24.controller;

import com.team9.ece1779f24.model.Cart;
import com.team9.ece1779f24.payload.CartDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CartController {

/*    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private AuthUtil authUtil;

    @Autowired
    private CartService cartService;*/

    @PostMapping("/carts/ticket/{ticketId}/quantity")
    public ResponseEntity<CartDTO> addTicketToCart(@PathVariable Long ticketId){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/carts")
    public ResponseEntity<List<CartDTO>> getCarts() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/carts/users/cart")
    public ResponseEntity<CartDTO> getCartById(){
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/carts/{cartId}/ticket/{ticketId}")
    public ResponseEntity<String> deleteTicketFromCart(@PathVariable Long cartId,
                                                        @PathVariable Long ticketId) {
        return ResponseEntity.ok().build();
    }
}
