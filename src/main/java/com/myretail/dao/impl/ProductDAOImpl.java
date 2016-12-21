package com.myretail.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.UDTMapper;
import com.myretail.dao.ProductDAO;
import com.myretail.entity.Price;
import com.myretail.entity.Product;
import com.myretail.exception.DataNotFoundException;

@Repository
public class ProductDAOImpl implements ProductDAO {
	private static final Logger LOG = LoggerFactory.getLogger(ProductDAOImpl.class);
	
	@Autowired
    public CassandraSessionFactoryBean session;
    private PreparedStatement getStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement deleteStatement;
    private PreparedStatement insertStatement;
    
    final static String SELECT_QUERY ="select * from product where id = ?";
    final static String SELECT_ALL_QUERY ="select * from product";
    final static String INSERT_QUERY = "insert into Product (id, name, current_price) VALUES( ?, ?, { value: ?, currency_code: ? })";
    final static String UPDATE_QUERY = "update product set current_price = { value: ?, currency_code: ? } , name = ? where id = ? if exists";
    final static String DELETE_QUERY = "delete FROM product where id = ?";
    

    
    private UDTMapper<Price> mapper;
    @PostConstruct
    void initialize() {
        this.mapper = new MappingManager(session.getObject()).udtMapper(Price.class);
        this.getStatement = session.getObject().prepare(SELECT_QUERY);
        this.updateStatement = session.getObject().prepare(UPDATE_QUERY);
        this.deleteStatement = session.getObject().prepare(DELETE_QUERY);
        this.insertStatement = session.getObject().prepare(INSERT_QUERY);
        LOG.info("ProductDAOImpl() initialized");
    }
    
    @Override
    public Product createProduct(Product product) {     
    	BoundStatement boundStatement = new BoundStatement(insertStatement);
    	product.setId(genSequenceNumber("product_sequence"));
        ResultSet results = session.getObject().execute(boundStatement.bind(product.getId(),product.getName(), product.getCurrent_price().getValue(), product.getCurrent_price().getCurrency_code()));
        if (results.wasApplied()) {
            return product;
        }

        return null;
    }
    
    @Override
    public Product getProduct(long id) {     
    	 BoundStatement boundStatement = new BoundStatement(getStatement);
         ResultSet results = session.getObject().execute(boundStatement.bind(id));
         List<Row> rows = results.all();
         if (rows.isEmpty()) {
            	 throw new DataNotFoundException("Invalid Product Id");
         }
         return constructProduct(rows.get(0));
    }
    
    @Override
    public Product updateProduct(Product product) {  
    	
        BoundStatement boundStatement = new BoundStatement(updateStatement);
        ResultSet results = session.getObject().execute(boundStatement.bind(product.getCurrent_price().getValue(), product.getCurrent_price().getCurrency_code(), product.getName(), product.getId()));
        if (results.wasApplied()) {
            return product;
        }

        return null;
    }

    @Override
    public void deleteProduct(long id) {        
    	 BoundStatement boundStatement = new BoundStatement(deleteStatement);
         session.getObject().execute(boundStatement.bind(id));
    }

    @Override
    public List<Product> getAllProducts() {       
    	
    	 ResultSet resultSet = session.getObject().execute(SELECT_ALL_QUERY);
    	 if (resultSet == null) {
 			return null;
 		}
    	List<Product> result = new ArrayList<Product>();
 		Iterator<Row> iterator = resultSet.iterator();
 		while (iterator.hasNext()) {
 			Row row = iterator.next();
 			result.add(constructProduct(row));
 		}
 		return result;
    }
    
    private Product constructProduct(Row row) {
        Product product = new Product();
        product.setId(row.getLong("id"));
        product.setName(row.getString("name"));
        product.setCurrent_price(mapper.fromUDT(row.getUDTValue("current_price")));
        return product;
    }
    
    /**
     * Utility Category
     * @param name
     * @return
     */
   public long genSequenceNumber(String name) {
         final PreparedStatement selectSeq = session.getObject().prepare("SELECT num FROM sequence WHERE name = ?;").setConsistencyLevel(ConsistencyLevel.LOCAL_ONE);
         final PreparedStatement updateSeq = session.getObject().prepare("UPDATE sequence SET num = ? WHERE name = ? IF num = ?;").setConsistencyLevel(ConsistencyLevel.ANY);
         ResultSet updateResultSet = null;
         long newSequence;
        do {
          ResultSet selectResultSet = session.getObject().execute(selectSeq.bind(name));
          long currentSequence = selectResultSet.all().get(0).getLong("num");
          newSequence = currentSequence + 1;
          updateResultSet = session.getObject().execute(updateSeq.bind(newSequence, name, currentSequence));
        } while(updateResultSet == null);
        return newSequence;
      }
}