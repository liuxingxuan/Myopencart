package io.lxx.opencartservice.dao;

import com.github.pagehelper.Page;
import io.lxx.opencartservice.dto.ProductAddDTO;
import io.lxx.opencartservice.dto.ProductUpdateDTO;
import io.lxx.opencartservice.po.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Long productId);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(@Param("productId") Long productId);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKeyWithBLOBs(Product record);

    int updateByPrimaryKey(Product record);

    Page<Product> selectWithPage();


    int batchDelete(@Param("productIds") List<Long> productIds);
}