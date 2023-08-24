package com.amazon.amazon_clone.service;


import com.amazon.amazon_clone.exceptions.CustomException;
import com.amazon.amazon_clone.model.Category;
import com.amazon.amazon_clone.model.Product;
import com.amazon.amazon_clone.repository.CategoryRepo;
import com.amazon.amazon_clone.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    private ProductRepo productRepo;

    public void createCategory(Category category){
        categoryRepo.save(category);

    }

    public List<Category>listCategory(){
        return categoryRepo.findAll();
    }

    public void editCategory(int categoryId, Category updateCategory) {
        Category category = categoryRepo.getById(categoryId);
        category.setCategoryName(updateCategory.getCategoryName());
        category.setDescription(updateCategory.getDescription());
        category.setImageUrl(updateCategory.getImageUrl());
        categoryRepo.save(category);
    }

    public boolean findById(int categoryId) {
        return categoryRepo.findById(categoryId).isPresent();
    }

    public boolean hasProducts(int categoryId) {
        Category category = categoryRepo.getById(categoryId);
        List<Product> products = productRepo.findByCategory(category);
        return !products.isEmpty();
    }

    public void deleteCategory(int categoryId) {
        if (!categoryRepo.findById(categoryId).isPresent()) {
            // Category not found
            return;
        }

        if (hasProducts(categoryId)) {
            // Category has associated products, prevent deletion
            throw new CustomException("YOU CANT DELETE CATEGORY WITH PRODUCT IN IT");
        }

        // Delete the category if it has no associated products
        categoryRepo.deleteById(categoryId);
    }
}
