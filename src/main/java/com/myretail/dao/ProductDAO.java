package com.myretail.dao;

import java.util.List;

import com.myretail.entity.Product;


public interface ProductDAO {
    /**
     * Used to Create the Product Information
     * @param product
     * @return {@link Product}
     */
    public Product createProduct(Product product);
    
    /**
     * Getting the Product Information using Id
     * @param id
     * @return {@link Product}
     */
    public Product getProduct(long id);
    
    /**
     * Used to Update the Product Information
     * @param product
     * @return {@link Product}
     */
    
    public Product updateProduct(Product product);
    
    /**
     * Deleting the Product Information using Id
     * @param id
     */
    public void deleteProduct(long id);
    
    /**
     * Getting the All Products information
     * @return
     */
    public List<Product> getAllProducts();
}