package com.api.cycleshop.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.cycleshop.binding.MessageResponse;
import com.api.cycleshop.dto.CartDTO;
import com.api.cycleshop.entity.Cart;
import com.api.cycleshop.entity.Cycle;
import com.api.cycleshop.entity.Order;
import com.api.cycleshop.repository.CartRepository;
import com.api.cycleshop.repository.CycleRepository;
import com.api.cycleshop.repository.OrderRepository;
import com.api.cycleshop.service.CartService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/cycles")
public class CycleController {

    @Autowired
    private CycleRepository cycleRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartService cartService;

    @GetMapping
    public List<Cycle> getAllCycleStocks() {
        return cycleRepository.findAll();
    }

    @PostMapping("/{id}/borrow")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @ResponseBody
    List<Cycle> borrowCycle(@PathVariable("id") int id) {
        Optional<Cycle> cycle = cycleRepository.findById(id);
        if (cycle.isPresent()) {
            Cycle c = cycle.get();
            if (c.getNumAvailable() > 0) {
                c.setNumBorrowed(c.getNumBorrowed() + 1);
                cycleRepository.save(c);
            }
            return cycleRepository.findAll();
        }
        return new ArrayList<>();
    }

    @PostMapping("/{id}/return")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @ResponseBody
    List<Cycle> returnCycle(@PathVariable("id") int id) {
        Optional<Cycle> cycle = cycleRepository.findById(id);
        if (cycle.isPresent()) {
            Cycle c = cycle.get();
            if (c.getNumAvailable() > 0) {
                c.setNumBorrowed(c.getNumBorrowed() - 1);
                cycleRepository.save(c);
            }
            return cycleRepository.findAll();
        }
        return new ArrayList<>();
    }

    @PostMapping("/{id}/restock")
    @ResponseBody
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    List<Cycle> restockCycle(@PathVariable("id") int id, @RequestParam("quantity") int quantity) {
        Optional<Cycle> cycle = cycleRepository.findById(id);
        if (cycle.isPresent()) {
            Cycle c = cycle.get();
            c.setStock(c.getStock() + quantity);
            return cycleRepository.findAll();
        } else {
            return new ArrayList<>();
        }
    }

    @PostMapping("cart/{id}/add")
    @ResponseBody
    @Transactional
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    ResponseEntity<MessageResponse> addToCart(@PathVariable("id") int id, @RequestParam("quantity") int quantity) {
        Cart cart = cartService.addToCart(id, quantity);
        cartService.save(cart);
        return ResponseEntity.ok().body(new MessageResponse("Added to cart"));
    }

    @PostMapping("cart/{id}/remove")
    @ResponseBody
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @Transactional
    ResponseEntity<CartDTO> removeFromCart(@PathVariable("id") int id, @RequestParam("quantity") int quantity) {
        Cart cart = cartService.removeFromCart(id, quantity);
        cartRepository.save(cart);
        return ResponseEntity.ok(cartService.getCart());
    }

    @GetMapping("cart")
    @ResponseBody
    @Transactional
    ResponseEntity<CartDTO> getCart() {
        CartDTO cart = cartService.getCart();
        return ResponseEntity.ok(cart);
    }

    @PostMapping("cart/checkout/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @Transactional
    ResponseEntity<CartDTO> checkout(@PathVariable("id") int cartItemId) {
        cartService.checkout(cartItemId);
        return ResponseEntity.ok(cartService.getCart());
    }

    @PostMapping("cart/checkout/all")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @Transactional
    ResponseEntity<CartDTO> checkoutAll() {
        cartService.checkoutAll();
        return ResponseEntity.ok(cartService.getCart());
    }

    @GetMapping("/orders")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    ResponseEntity<List<Order>> getOrders() {
        return ResponseEntity.ok(orderRepository.findAll());
    }

    @GetMapping("/orders/user")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    ResponseEntity<List<Order>> getOrdersByUser() {
        return ResponseEntity.ok(cartService.getOrdersByUser());
    }
}
