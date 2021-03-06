package com.example.famousventureempire.services;

import com.example.famousventureempire.data.model.Product;
import com.example.famousventureempire.data.model.ProductDto;
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

    ModelMapper modelMapper= new ModelMapper();

    @Override
    public void deleteProductsById(Integer productsId) throws ProductException {
        Product product =productRepository.findById(productsId).orElse(null);
        if(product ==null){
           throw new ProductException("Product does not exist");
        }
        productRepository.deleteById(productsId);

    }

    @Override
    public void addProduct(ProductDto productDto) throws ProductException {
       Product product = new Product();
        if (productDto ==null){
            throw new ProductException("Post cannot be null");
        }
       if(productRepository.findProductsByName(productDto.getName()).isPresent()){
           throw new ProductException("Product already exists");
       }
        if(productDto.getImage()!=null && !productDto.getImage().isEmpty()){
            log.info("the image name is -->{}",productDto.getImage().getName());
            Map<Object,Object> params=new HashMap<>();
            params.put("public_id","Famous/"+productDto.getImage().getName()+productDto.getName());
            log.info("Image parameters-->{}",params);
            try{
                Map<?,?> uploadResult = cloudStorageService.uploadImage(productDto.getImage(),params);
                product.setImage(String.valueOf(uploadResult.get("url")));
                productDto.setImageReturned(product.getImage());
            }catch (IOException e){
                e.printStackTrace();
            }

        }
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setCategory(productDto.getCategory());
        product.setName(productDto.getName());
        productRepository.save(product);
    }

    @Override
    public ProductDto findProductById(Integer productsId) throws ProductException {
        Optional<Product> products=productRepository.findById(productsId);
        ProductDto productDto =new ProductDto();
        if(products.isEmpty()){
            throw new ProductException("Product Not Found");
        }
            modelMapper.map(products.get(), productDto);
            productDto.setImageReturned(products.get().getImage());
            return productDto;
    }

    @Override
    public List<ProductDto> findProductsByNameContaining(String Name) throws ProductException {
        List<Product> products=productRepository.findProductsByNameContaining(Name);
        if(products.isEmpty()){
            throw new ProductException("Product Not Found");
        }
        log.info("List of products containing the name are-->{}",products);
        ModelMapper modelMapper= new ModelMapper();
        List<ProductDto> productDto =products
                .stream().map(product ->modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
        log.info("List of productsDto containing the name are-->{}", productDto);
        return productDto;
    }

    @Override
    public List<Product> findProductsByDescription(String name) {
        List<Product> products=productRepository.findProductsByDescription(name);
        return products;
    }
}
