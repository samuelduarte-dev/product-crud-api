package com.example.crud.controllers;

import com.example.crud.dtos.ProductsDto;
import com.example.crud.model.Product;
import com.example.crud.repositories.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductRepository repository;

    @GetMapping
    public ResponseEntity getAll() {

        List<Product> listproduct = repository.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(listproduct);

    }

    @GetMapping("/{id}")
    public ResponseEntity getByid(@PathVariable(value = "id") Integer id) {

        Optional product = repository.findById(id);

        if (product.isEmpty()) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        return ResponseEntity.status(HttpStatus.FOUND).body(product.get());

    }

    @PostMapping
    public ResponseEntity save(@RequestBody ProductsDto dto) {

        var product = new Product();

        BeanUtils.copyProperties(dto, product);

        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(product));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") Integer id) {

        Optional<Product> product = repository.findById(id);

        if (product.isEmpty()) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");

        }

        repository.delete(product.get());

        return ResponseEntity.status(HttpStatus.OK).body("Product deleted");


    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable(value = "id") Integer id, @RequestBody ProductsDto dto) {
        Optional<Product> product = repository.findById(id);

        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        var productModel = product.get();

        BeanUtils.copyProperties(dto, productModel);

        return ResponseEntity.status(HttpStatus.OK).body(repository.save(productModel));

    }


}
