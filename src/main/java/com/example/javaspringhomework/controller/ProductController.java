package com.example.javaspringhomework.controller;

import com.example.javaspringhomework.dto.ProductDTO;
import com.example.javaspringhomework.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getProducts(){
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping("/products")
    public ResponseEntity<ProductDTO> saveProduct(@RequestBody ProductDTO product){
        return ResponseEntity.ok(service.save(product));
    }
}
