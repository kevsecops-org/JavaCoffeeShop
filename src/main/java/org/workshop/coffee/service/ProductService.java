package org.workshop.coffee.service;

import org.workshop.coffee.domain.Product;
import org.workshop.coffee.repository.ProductRepository;
import org.workshop.coffee.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SearchRepository searchRepository;

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void delete(Long productId) {
        productRepository.deleteById(productId);
    }

    public Product getProduct(Long productId) {
        return productRepository.findById(productId).get();
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductByName(String name) { return productRepository.findProductByProductName(name); }

    public List<Product> searchProducts(String search) {
        return searchRepository.searchProduct(search);
    }

    public List<Product> filterProducts(String type, Double minPrice, Double maxPrice) {
        return searchRepository.filterProducts(type, minPrice, maxPrice);
    }
}
