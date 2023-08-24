package com.amazon.amazon_clone.controller;

import com.amazon.amazon_clone.common.ApiResponse;
import com.amazon.amazon_clone.model.Category;
import com.amazon.amazon_clone.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCategory(@RequestBody Category category){
        categoryService.createCategory(category);
        return new ResponseEntity<>(new ApiResponse(true, "A NEW CATEGORY CREATED"), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public List<Category> listCategory(){
        return categoryService.listCategory();

    }

    @PostMapping("/update/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryId") int categoryId, @RequestBody Category category ){
        if (!categoryService.findById(categoryId)){
            return new ResponseEntity<>(new ApiResponse(false, "CATEGORY DOES NOT EXISTS"), HttpStatus.NOT_FOUND);

        }
        categoryService.editCategory(categoryId, category);
        return new ResponseEntity<>(new ApiResponse(true, "CATEGORY HAS BEEN UPDATED"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") int categoryId){
        if (!categoryService.findById(categoryId)){
            return new ResponseEntity<>(new ApiResponse(false, "CATEGORY DOES NOT EXISTS"), HttpStatus.NOT_FOUND);

        }
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(new ApiResponse(true, "CATEGORY HAS BEEN DELETED"), HttpStatus.OK);
    }
}
