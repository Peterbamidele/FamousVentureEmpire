package com.example.famousventureempire.services;

import com.example.famousventureempire.data.model.Products;
import com.example.famousventureempire.data.model.ProductsDto;
import com.example.famousventureempire.data.repository.ProductRepository;
import com.example.famousventureempire.services.cloud.CloudStorageService;
import com.example.famousventureempire.web.exceptions.ProductException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductServicesImpl implements ProductServices {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CloudStorageService cloudStorageService;

    @Override
    public void deleteProductsById(Integer productsId) throws ProductException {
        Products products=productRepository.findById(productsId).orElse(null);
        if(products==null){
           throw new ProductException("Product does not exist");
        }
        productRepository.deleteById(productsId);

    }
    private String extractFileName(String fileName){
        return fileName.split("\\.")[0];
    }

    @Override
    public void addProduct(ProductsDto productsDto) throws ProductException {
       Products products = new Products();
        if (productsDto==null){
            throw new ProductException("Post cannot be null");
        }
       if(productRepository.findProductsByName(productsDto.getName()).isPresent()){
           throw new ProductException("Product already exists");
       }
        if(productsDto.getImage()!=null && !productsDto.getImage().isEmpty()){
            Map<Object,Object> params=new HashMap<>();
            params.put("public_id","blogapp/"+extractFileName(productsDto.getImage().getName()));
            params.put("overwrite",true);
            log.info("Image parameters-->{}",params);
            try{
                Map<?,?> uploadResult = cloudStorageService.uploadImage(productsDto.getImage(),params);
                products.setImage(String.valueOf(uploadResult.get("url")));
            }catch (IOException e){
                e.printStackTrace();
            }

        }
        products.setDescription(productsDto.getDescription());
        products.setPrice(productsDto.getPrice());
        products.setCategory(productsDto.getCategory());
        products.setName(productsDto.getName());
        productRepository.save(products);
    }

    @Override
    public ProductsDto findProductById(Integer productsId) throws ProductException {
        Optional<Products> products=productRepository.findById(productsId);
        ProductsDto productsDto=new ProductsDto();
        ModelMapper modelMapper= new ModelMapper();
        if(products.isEmpty()){
            throw new ProductException("Product Not Found");
        }
            modelMapper.map(products.get(),productsDto);
            return productsDto;
    }

    @Override
    public List<ProductsDto> findProductsByNameContaining(String Name) throws ProductException {
        List<Products> products=productRepository.findProductsByNameContaining(Name);
        if(products.isEmpty()){
            throw new ProductException("Product Not Found");
        }
        log.info("List of products containing the name are-->{}",products);
        ModelMapper modelMapper= new ModelMapper();
        List<ProductsDto>productsDto=products
                .stream().map(product ->modelMapper.map(product,ProductsDto.class)).collect(Collectors.toList());
        log.info("List of productsDto containing the name are-->{}",productsDto);
        //todo mapProducts To dto
        return productsDto;
    }
}
