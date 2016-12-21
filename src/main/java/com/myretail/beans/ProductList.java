package com.myretail.beans;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.hateoas.ResourceSupport;

import com.myretail.controller.ProductController;

@XmlRootElement
public class ProductList {
	private List<Product> products = new ArrayList<>();
	
	public ProductList(List<com.myretail.entity.Product> allProducts) {
		
		for (com.myretail.entity.Product product : allProducts ) {
			Product p = new Product();
			p.setProdId(product.getId());
			p.setName(product.getName());
			
			Price price = new Price();
			price.setCurrency_code(product.getCurrent_price().getCurrency_code());
			price.setValue(product.getCurrent_price().getValue());
			
			p.setCurrent_price(price);
			p.add(linkTo(ProductController.class).slash(p.getProdId()).withSelfRel());
			products.add(p);
		}
	}

	
	
	public List<Product> getCategories() {
		return products;
	}



	public void setCategories(List<Product> products) {
		this.products = products;
	}



	public class Product extends ResourceSupport {
		

	    private long prodId;
	    private String name;
	    private Price current_price;
	    public Product() {
	    }
	    public Product(long id, BigDecimal value, String currency_code) {
	        this.prodId = id;
	        this.current_price = new Price(value, currency_code);
	    }

	    public long getProdId() {
	        return prodId;
	    }

	    public void setProdId(long id) {
	        this.prodId = id;
	    }

	    public Price getCurrent_price() {
	        return this.current_price;
	    }

	    public void setCurrent_price(Price price) {
	        this.current_price = price;
	    }
	 
	    public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
	}
	
	public class Price {
	    private BigDecimal value;
	    private String currency_code;

	    public Price() {
	    }

	    public Price(BigDecimal value, String currency_code) {
	        this.value = value;
	        this.currency_code = currency_code;
	    }

	    public BigDecimal getValue() {
	        return value;
	    }

	    public void setValue(BigDecimal value) {
	        this.value = value;
	    }

	    public String getCurrency_code() {
	        return currency_code;
	    }

	    public void setCurrency_code(String currency_code) {
	        this.currency_code = currency_code;
	    }

	}

}
