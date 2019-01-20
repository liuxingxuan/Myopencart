package io.lxx.opencartservice.service;

import com.github.pagehelper.Page;
import io.lxx.opencartservice.dto.ProductAddDTO;
import io.lxx.opencartservice.dto.ProductUpdateDTO;
import io.lxx.opencartservice.po.Product;

import java.util.List;

public interface ProductService {
    Product getById(Long productId);

    Page<Product> getWithPage(Integer pageNum);

    Long add(ProductAddDTO productAddDTO);

    void update(ProductUpdateDTO productUpdateDTO);

    int batchDelete(List<Long> productIds);
}
