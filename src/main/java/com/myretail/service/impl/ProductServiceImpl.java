package com.myretail.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myretail.dao.ProductDAO;
import com.myretail.entity.Product;
import com.myretail.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDAO productDAO;

	/**
	 * Default Constructor
	 */
	public ProductServiceImpl() {
		super();
	}

	@Override
	public Product createProduct(Product product) {
		return productDAO.createProduct(product);
	}

	@Override
	public Product getProduct(long id) {
		return productDAO.getProduct(id);
	}

	@Override
	public Product updateProduct(Product product) {
		return productDAO.updateProduct(product);
	}

	@Override
	public void deleteProduct(long id) {
		productDAO.deleteProduct(id);
	}

	@Override
	public List<Product> getAllProducts() {
		return productDAO.getAllProducts();
	}

}