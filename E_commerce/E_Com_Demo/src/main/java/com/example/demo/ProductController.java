package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    // ================= PRODUCT APIS =================
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findByIsActiveTrue(); // Sirf active items load honge
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepository.findById(id).map(product -> {
            product.setActive(false); // Soft delete switch trigger
            productRepository.save(product);
            return product;
        }).orElseThrow(() -> new RuntimeException("Product nahi mila!"));
        return "Product soft-deleted successfully!";
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        return productRepository.findById(id).map(product -> {
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            product.setDescription(updatedProduct.getDescription());
            product.setImageUrl(updatedProduct.getImageUrl());
            product.setCategory(updatedProduct.getCategory());
            return productRepository.save(product);
        }).orElseThrow(() -> new RuntimeException("Product nahi mila!"));
    }

    // ================= CART APIS =================
    @GetMapping("/cart")
    public List<CartItem> getCartItems() {
        return cartRepository.findAll();
    }

    @PostMapping("/cart")
    public CartItem addToCart(@RequestBody CartItem cartItem) {
        return cartRepository.save(cartItem);
    }

    @DeleteMapping("/cart/{id}")
    public String removeFromCart(@PathVariable Long id) {
        cartRepository.deleteById(id);
        return "Item removed from cart!";
    }

    @DeleteMapping("/cart/clear")
    public String clearCart() {
        cartRepository.deleteAll();
        return "Cart cleared completely!";
    }

    // ================= ORDER HISTORY APIS =================
    @PostMapping("/orders")
    public Order placeOrder(@RequestBody Order order) {
        return orderRepository.save(order);
    }

    @GetMapping("/orders")
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @DeleteMapping("/orders/{id}")
    public String cancelOrder(@PathVariable Long id) {
        orderRepository.deleteById(id);
        return "Order deleted successfully!";
    }
}