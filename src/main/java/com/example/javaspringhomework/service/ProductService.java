package com.example.javaspringhomework.service;

import com.example.javaspringhomework.dto.ProductDTO;
import com.example.javaspringhomework.entities.Product;
import com.example.javaspringhomework.mapper.ProductMapper;
import com.example.javaspringhomework.repository.ProductDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductDAO dao;
    private final ProductMapper mapper;

    public ProductDTO save(ProductDTO dto){
        Product product = mapper.convertToProduct(dto);

        Product save = dao.save(product);

        return mapper.convertToDTO(save);
    }

    public List<ProductDTO> getAll(){
        return dao.findAll().stream().map(mapper::convertToDTO).toList();
    }
}
