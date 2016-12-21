package com.myretail.controller;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.myretail.beans.MessageRestBean;
import com.myretail.entity.Product;
import com.myretail.exception.DataNotFoundException;
import com.myretail.service.ProductService;


@RestController
@RequestMapping("/v1/products")
public class ProductController {
    
	private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);
	
	/**TODO 
	 * 1) implementing pagination for findAll request	 *
	 * 2) validations 
	 */
	
    @Autowired
    private ProductService productService;
    
    public ProductController() {
    	LOG.info("ProductController()");
    }
         
    @RequestMapping(method = RequestMethod.POST)   
    @ResponseStatus(value=HttpStatus.CREATED)
    public Product create(@RequestBody Product product) {        
        return productService.createProduct(product);
    }
 
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") long id) {
        productService.deleteProduct(id);
        return "Product " + id + " deleted";
    }
 
    @RequestMapping(method = RequestMethod.GET)
    public List<Product> findAll() {
        return productService.getAllProducts();
    }
 
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Product findById(@PathVariable("id") long id) {        
        return productService.getProduct(id);
    }
 
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseStatus(value=HttpStatus.OK)
    public Product update(@RequestBody Product product, @PathVariable("id") long id) {
    	product.setId(id);
        return productService.updateProduct(product);
    }
    
	@ExceptionHandler({SQLException.class,DataAccessException.class,DataNotFoundException.class})
	public ResponseEntity<MessageRestBean> handleDatabaseErrors(Exception ex) {	   
	  return new ResponseEntity<MessageRestBean>(new MessageRestBean(ex.getMessage(), "Known Error"),  (ex instanceof DataNotFoundException) ? HttpStatus.NOT_FOUND : HttpStatus.INTERNAL_SERVER_ERROR);
	  
	}
}