package com.myretail.validator;

//import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.myretail.entity.Product;


//@Component
public class ProductSearchValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Product.class.isAssignableFrom(clazz);  
	}

	@Override
	public void validate(Object target, Errors errors) {
		Product product = (Product) target;
		String name = product.getName();
        if (!StringUtils.hasLength(name) && product.getId() == 0) {
            errors.rejectValue("name", "required", "name or id is required");
        } 
	}

}