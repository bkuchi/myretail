package com.myretail.entity;


import com.datastax.driver.mapping.annotations.Field;
import com.datastax.driver.mapping.annotations.UDT;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

@UDT (name = "price", keyspace = "productsdb")
public class Price {
	@Field(name = "value")
	@NotNull
    private BigDecimal value;
	@Field(name = "currency_code")
	@NotNull
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
