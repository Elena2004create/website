package com.example.shop.services;

import com.example.shop.models.Image;
import com.example.shop.models.Product;
import com.example.shop.models.User;
import com.example.shop.repositories.ProductRepository;
import com.example.shop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public List<Product> listProducts(String title) {
        if (title != null) return productRepository.findByTitle(title);
        return productRepository.findAll(); }

    public void saveProduct(Principal principal, Product product, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException {
        User user = getUserByPrincipal(principal);

        // Если пользователь новый, сохраняем его
        if (user.getId() == null) {
            user = userRepository.save(user);
        }

        product.setUser(user);

        if (file1 != null && file1.getSize() != 0) {
            Image image1 = toImageEntity(file1);
            image1.setPreviewImage(true);
            product.addImageToProduct(image1);
        }

        if (file2 != null && file2.getSize() != 0) {
            Image image2 = toImageEntity(file2);
            product.addImageToProduct(image2);
        }

        if (file3 != null && file3.getSize() != 0) {
            Image image3 = toImageEntity(file3);
            product.addImageToProduct(image3);
        }


        log.info("Saving new Product. Title: {}; Author: {}", product.getTitle(), product.getUser().getEmail());
        Product productFromDb = productRepository.save(product);
        if (!productFromDb.getImages().isEmpty()){
            productFromDb.setPreviewImageId(productFromDb.getImages().get(0).getId());
        }
        productRepository.save(productFromDb);
    }

    //@Transactional
    /*public Product createProduct(Product product) {
        return productRepository.save(product);
    }*/

    // показывает текущего пользователя вошедшего в систему
    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
}
