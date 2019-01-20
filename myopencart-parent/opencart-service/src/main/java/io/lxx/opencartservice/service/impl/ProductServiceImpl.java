package io.lxx.opencartservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.lxx.opencartservice.dao.ProductMapper;
import io.lxx.opencartservice.dto.ProductAddDTO;
import io.lxx.opencartservice.dto.ProductUpdateDTO;
import io.lxx.opencartservice.po.Product;
import io.lxx.opencartservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;
    @Value("${product.pageSize}")
    private Integer productPageSize;

    @Override
    public Product getById(Long productId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        return product;
    }

    @Override
    public Page<Product> getWithPage(Integer pageNum) {
        PageHelper.startPage(pageNum,productPageSize);
        Page<Product> products = productMapper.selectWithPage();
        return products;
    }

    //封装添加商品的方法
    private Product setProductWithDTO(ProductAddDTO productAddDTO){
        Product product = new Product();
        product.setProductCode(productAddDTO.getProductCode());
        product.setBrand(productAddDTO.getBrand());
        product.setDetail(productAddDTO.getDetail());
        product.setName(productAddDTO.getName());
        product.setVat(productAddDTO.getVat());
        product.setPoint(productAddDTO.getPoint());
        product.setPrice(productAddDTO.getPrice());
        product.setPictureMainUrl(productAddDTO.getPicMainUrl());
        product.setPictureUrls(JSON.toJSONString(productAddDTO.getPicUrls()));
        return product;
    }
    @Override
    public Long add(ProductAddDTO productAddDTO) {
        Product product = setProductWithDTO(productAddDTO);
        productMapper.insert(product);
        return product.getProductId();
    }
    @Override
    public void update(ProductUpdateDTO productUpdateDTO) {
        Product product = setProductWithDTO(productUpdateDTO);
        product.setProductId(productUpdateDTO.getProductId());
        productMapper.updateByPrimaryKey(product);
    }

    @Override
    public int batchDelete(List<Long> productIds) {
        int rows = productMapper.batchDelete(productIds);
        return rows;
    }
}
