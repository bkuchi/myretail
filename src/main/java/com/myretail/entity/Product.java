package com.myretail.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;

import com.datastax.driver.mapping.annotations.Frozen;
import com.datastax.driver.mapping.annotations.Table;

@Table(name = "product")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	@PrimaryKeyColumn(name = "id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private long id;
	
    @Column("name")
    @NotNull
    private String name;
    

	@Frozen
	@NotNull
    private Price current_price;

    public Product() {
    }

    public Product(long id, BigDecimal value, String currency_code) {
        this.id = id;
        this.current_price = new Price(value, currency_code);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
