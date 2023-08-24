package com.amazon.amazon_clone.service;

import com.amazon.amazon_clone.dto.ProductDto;
import com.amazon.amazon_clone.exceptions.ProductNotExistsExeption;
import com.amazon.amazon_clone.model.Category;
import com.amazon.amazon_clone.model.Product;
import com.amazon.amazon_clone.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepo productRepository;

    public void createProduct(ProductDto productDto, Category category) {
        Product product = new Product();

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageURL(productDto.getImageURL());
        product.setCategory(category);

        productRepository.save(product);
    }

    public ProductDto getProductDto(Product product){
        ProductDto productDto = new ProductDto();

        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageURL(product.getImageURL());
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setId(product.getId());
        
        return productDto;
    }

    public List<ProductDto> getAllProducts() {
        List<Product> allProducts = productRepository.findAll();

        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product: allProducts) {
            productDtos.add(getProductDto(product));
        }

        return productDtos;
    }

    public void updateProduct(ProductDto productDto, Integer productId) throws Exception {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        //throw an exeption id product does not exists

        if (!optionalProduct.isPresent()){
            throw new Exception("PRODUCT NOT PRESENT");
        }

        Product product = optionalProduct.get();

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageURL(productDto.getImageURL());

        productRepository.save(product);

    }

    public Product findById(Integer productId) throws ProductNotExistsExeption {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()){
            throw new ProductNotExistsExeption("PRODUCT ID IS INVALID: " + productId);
        }
        return optionalProduct.get();
    }

    public void deleteProduct(Integer productId) throws Exception {

        Optional<Product> optionalProduct = productRepository.findById(productId);
        //throw an exeption id product does not exists

        if (!optionalProduct.isPresent()){
            throw new Exception("PRODUCT NOT PRESENT");
        }
        productRepository.deleteById(productId);
    }
}

