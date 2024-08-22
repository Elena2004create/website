package com.example.shop.controllers;

import com.example.shop.models.Product;
import com.example.shop.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    /*private final ProductService productService;

    @GetMapping("/")
    public String products(@RequestParam(name = "title", required = false) String title, Principal principal, Model model){
        model.addAttribute("products", productService.listProducts(title));
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        return "products";
    }

    /*@GetMapping("/product/{id}")
    public ResponseEntity<Product> productInfo(@PathVariable Long id, Model model){
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImages());
        return "product-info";
    }/*

    @GetMapping("/product/{id}")
    public Product productInfo(@PathVariable Long id) {


        return productService.getProductById(id);
    }

    @PostMapping("/product/create")
    public String createProduct(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2,
                                @RequestParam("file3") MultipartFile file3, Product product, Principal principal) throws IOException {
        productService.saveProduct(principal, product, file1, file2, file3);
        return "redirect:/";
    }

    @PostMapping("/product/create")
    public ResponseEntity<Product> createProduct(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2,
                                                 @RequestParam("file3") MultipartFile file3, Product product, Principal principal) throws IOException {
        productService.saveProduct(principal, product, file1, file2, file3);
        return ResponseEntity.ok(product);
    }


    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return "redirect:/";
    }*/

    private final ProductService productService;

    @GetMapping("/")
    public ResponseEntity<List<Product>> products(@RequestParam(name = "title", required = false) String title){
        List<Product> products = productService.listProducts(title);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/product/{id}")
    public Product productInfo(@PathVariable Long id) {
        return productService.getProductById(id);
    }


    @PutMapping(value = "/product/create", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    /*@Operation(
            requestBody = @ResponseBody(content = @Content(encoding = @Encoding(name = "product", contentType = "application/json")))
    )*/
    public ResponseEntity<Product> createProduct(
                                                 @RequestPart(value = "file1", required = false) MultipartFile file1,
                                                 @RequestPart(value = "file2", required = false) MultipartFile file2,
                                                 @RequestPart(value = "file3", required = false) MultipartFile file3,
                                                 @Parameter(schema = @Schema(implementation = Product.class))
                                                 @RequestPart(value = "product") @Valid Product product,
                                                 Principal principal)
                                                 throws IOException {
        productService.saveProduct(principal, product, file1, file2, file3);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/product/createTest")
    public ResponseEntity<Product> createProductTest(@RequestBody Product product,
                                                     Principal principal) throws IOException {
        productService.saveProduct(principal, product, null, null, null);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
