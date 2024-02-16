package com.example.javaspringhomework.mapper;

import com.example.javaspringhomework.dto.ProductDTO;
import com.example.javaspringhomework.entities.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDTO convertToDTO(Product product){
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())

                .build();
    }

    public Product convertToProduct(ProductDTO dto){
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());

        return product;
    }
}
