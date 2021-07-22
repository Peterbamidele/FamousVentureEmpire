package com.example.famousventureempire.services;

import com.example.famousventureempire.data.model.Products;
import com.example.famousventureempire.data.model.ProductsDto;
import com.example.famousventureempire.data.repository.ProductRepository;
import com.example.famousventureempire.web.exceptions.CartException;
import com.example.famousventureempire.web.exceptions.ProductException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductServicesImpl implements ProductServices {
    @Autowired
    ProductRepository productRepository;

    @Override
    public void deleteProductsById(String productsId) throws ProductException {
        Products products=productRepository.findById(productsId).orElse(null);
        if(products==null){
           throw new ProductException("Product does not exist");
        }
        productRepository.deleteById(productsId);

    }

    @Override
    public void addProduct(ProductsDto productsDto) throws ProductException {
       Products products = new Products();

       if(productRepository.findProductsByName(productsDto.getName()).isPresent()){
           throw new ProductException("Product already exists");
       }
       ModelMapper modelMapper= new ModelMapper();
       modelMapper.map(productsDto,products);
       log.info("After Mapping the product");
//        products.setName(productsDto.getName());
//        products.setCategory(productsDto.getCategory());
//        products.setPrice(productsDto.getPrice());
//        products.setImage(productsDto.getImage());
//        products.setDescription(productsDto.getDescription());


        productRepository.save(products);
    }

    @Override
    public Optional<ProductsDto> findProductById(String productsId) throws ProductException {
        Optional<Products> products=productRepository.findById(productsId);
        if(products.isEmpty()){
            throw new ProductException("Product Not Found");
        }
        ModelMapper modelMapper= new ModelMapper();
        Optional<ProductsDto> productsDto =Optional.empty();
        modelMapper.map(products,productsDto);
        log.info("After Mapping the productDto");
        //todo mapProducts To dto
        return productsDto;
    }

    @Override
    public List<ProductsDto> findProductsByNameContaining(String Name) throws ProductException {
        List<Products> products=productRepository.findProductsByNameContaining(Name);
        if(products.isEmpty()){
            throw new ProductException("Product Not Found");
        }
        List<ProductsDto> productsDto=new ArrayList<>();
        ModelMapper modelMapper= new ModelMapper();
        modelMapper.map(products,productsDto);
        //todo mapProducts To dto
        return productsDto;
    }
}
