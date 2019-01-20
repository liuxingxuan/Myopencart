package io.lxx.opencartadminwebapi.admin.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import io.lxx.opencartadminwebapi.admin.exception.BackendClientException;
import io.lxx.opencartservice.dto.ProductAddDTO;
import io.lxx.opencartservice.dto.ProductListDTO;
import io.lxx.opencartservice.dto.ProductUpdateDTO;
import io.lxx.opencartservice.po.Product;
import io.lxx.opencartservice.service.impl.ProductServiceImpl;
import io.lxx.opencartservice.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
@Transactional
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @GetMapping("/getById")
    public Product getById(@RequestParam Long productId){
        Product product = productService.getById(productId);
        return product;
    }
    @GetMapping("/getWithPage")
    public PageInfo getWithPage(@RequestParam(required = false,defaultValue = "1")Integer pageNum){
        Page<Product> products = productService.getWithPage(pageNum);
        PageInfo<Product> productPageInfo = products.toPageInfo();
        return productPageInfo;
    }
    @PostMapping("/add")
    public Long add(@RequestBody ProductAddDTO productAddDTO){
    //引入事务 操作实体表和商品类目表
        Long productId = productService.add(productAddDTO);
        return productId;
    }
    @PostMapping("/update")
    public void update(@RequestBody ProductUpdateDTO productUpdateDTO){
        productService.update(productUpdateDTO);
    }

    @PostMapping("/batchdelete")
    public void batchdelete(@RequestBody List<Long> productIds){
        productService.batchDelete(productIds);
    }
    @GetMapping("/searchProducts")
    public List <ProductListDTO> searchProductsWithPage(@RequestParam(required = false,defaultValue = "1")String name,
                                                        @RequestParam(required = false,defaultValue = "1")String price,
                                                        @RequestParam(required = false,defaultValue = "1")String pageNum){
        return null;
    }
    @PostMapping("/uploadProductMainPhoto")
    public String uploadAvatar(@RequestParam("file") MultipartFile file) throws Exception {
        String contentType = file.getContentType();
        if (!contentType.equals("image/png") && !contentType.equals("image/jpeg")) {
            throw new BackendClientException("file only support png or jpg");
        }
        String uuid = UUID.randomUUID().toString();
        String type = file.getContentType().split("/")[1];
        String fileName = String.format("%s.%s", uuid, type);
        String url = String.format("productMainPhoto/%s", fileName);
        FileUtil.storeFile(file.getBytes(),url);
        return fileName;
    }
}
