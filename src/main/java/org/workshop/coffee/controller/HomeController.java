package org.workshop.coffee.controller;

import org.workshop.coffee.repository.SearchRepository;
import org.workshop.coffee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private ProductService productService;
    private SearchRepository searchRepository;

    @Autowired
    public HomeController(ProductService productService, SearchRepository searchRepository) {
        this.productService = productService;
        this.searchRepository = searchRepository;
    }

    @GetMapping({"/", "/index", "/home"})
    public String homePage(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "index";
    }

    @PostMapping("/")
    public String searchProducts(Model model, @RequestParam String input) {
        model.addAttribute("products", searchRepository.searchProduct(input));
        return "index";
    }

    @GetMapping("/filter")
    public String filterProducts(Model model,
                                 @RequestParam(required = false) String type,
                                 @RequestParam(required = false) Double minPrice,
                                 @RequestParam(required = false) Double maxPrice) {
        model.addAttribute("products", productService.filterProducts(type, minPrice, maxPrice));
        model.addAttribute("input", "");
        model.addAttribute("selectedType", type);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        return "index";
    }
}
