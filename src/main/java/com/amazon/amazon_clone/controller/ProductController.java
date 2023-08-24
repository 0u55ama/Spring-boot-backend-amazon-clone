package com.amazon.amazon_clone.controller;


import com.amazon.amazon_clone.common.ApiResponse;
import com.amazon.amazon_clone.dto.ProductDto;
import com.amazon.amazon_clone.model.Category;
import com.amazon.amazon_clone.model.Product;
import com.amazon.amazon_clone.repository.CategoryRepo;
import com.amazon.amazon_clone.repository.ProductRepo;
import com.amazon.amazon_clone.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    ProductRepo productRepo;


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDto productDto){
        Optional<Category> optionalCategory = categoryRepo.findById(productDto.getCategoryId());
        if (!optionalCategory.isPresent()){
            return new ResponseEntity<>(new ApiResponse(false, "CATEGORY DOES NOT EXISTS"), HttpStatus.BAD_REQUEST);
        }
        else {
            productService.createProduct(productDto, optionalCategory.get());
            return new ResponseEntity<>(new ApiResponse(true, "PRODUCT HAS BEEN ADDED"), HttpStatus.CREATED);

        }

    }

    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> getProducts(){
        List<ProductDto> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId") Integer productId, @RequestBody ProductDto productDto) throws Exception {
        Optional<Category> optionalCategory = categoryRepo.findById(productDto.getCategoryId());
        if (!optionalCategory.isPresent()){
            return new ResponseEntity<>(new ApiResponse(false, "CATEGORY DOES NOT EXISTS"), HttpStatus.BAD_REQUEST);
        }
        else {
            productService.updateProduct(productDto, productId);
            return new ResponseEntity<>(new ApiResponse(true, "PRODUCT HAS BEEN UPDATED"), HttpStatus.OK);

        }

    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable("productId") Integer productId) throws Exception {
        Optional<Product> product = productRepo.findById(productId);
        if (!product.isPresent()){
            return new ResponseEntity<>(new ApiResponse(false, "PRODUCT DOES NOT EXISTS"), HttpStatus.BAD_REQUEST);
        }
        else {
            productService.deleteProduct( productId);
            return new ResponseEntity<>(new ApiResponse(true, "PRODUCT HAS BEEN DELETED"), HttpStatus.OK);

        }


    }

}
