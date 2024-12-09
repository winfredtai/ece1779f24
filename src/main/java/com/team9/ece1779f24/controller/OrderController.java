package com.team9.ece1779f24.controller;

import com.team9.ece1779f24.model.User;
import com.team9.ece1779f24.payload.OrderDTO;
import com.team9.ece1779f24.payload.OrderRequestDTO;
import com.team9.ece1779f24.security.jwt.JwtUtils;
import com.team9.ece1779f24.service.OrderService;
import com.team9.ece1779f24.util.AuthUtil;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class OrderController {
    private OrderService orderService;
    private AuthUtil authUtil;
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @PostMapping("/order/users/payments/{paymentMethod}")
    public ResponseEntity<OrderDTO> orderProducts(@PathVariable String paymentMethod,
                                                  @RequestBody OrderRequestDTO orderRequestDTO) {
        User currentUser = authUtil.loggedInUser();
        OrderDTO order = orderService.placeOrder(
                currentUser.getUserId(),
                paymentMethod,
                orderRequestDTO
        );
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/order/test-roles")
    public ResponseEntity<?> testRoles(Authentication authentication) {
        if (authentication != null) {
            logger.debug("Current user roles: {}", authentication.getAuthorities());
            return ResponseEntity.ok(Map.of(
                    "username", authentication.getName(),
                    "authorities", authentication.getAuthorities()
            ));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/test/auth")
    public ResponseEntity<?> testAuth(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        response.put("username", authentication.getName());
        response.put("authorities", authentication.getAuthorities());
        response.put("isAuthenticated", authentication.isAuthenticated());

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('AGENT')")
    @GetMapping("/order/user")
    public ResponseEntity<String> getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.debug("Current user authorities: {}", auth.getAuthorities());
        logger.debug("Has ROLE_AGENT: {}", auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_AGENT")));

        if (!auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_AGENT"))) {
            throw new AccessDeniedException("User does not have ROLE_AGENT");
        }

        String emailId = authUtil.loggedInEmail();
        return new ResponseEntity<>(emailId, HttpStatus.OK);
    }
}