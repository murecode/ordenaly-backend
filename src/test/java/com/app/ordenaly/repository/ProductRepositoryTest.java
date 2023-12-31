package com.app.ordenaly.repository;

import com.app.ordenaly.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class ProductRepositoryTest {

  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private TestEntityManager entityManager;

  @Test
  void testCreateNewProduct() {
    Product product1 = new Product();
    product1.setProductName("Sopa");
    product1.setDescription("De pollo con papita");
    product1.setPrice(8000.);
    product1.setInStock(true);

    Product saveNewProduct = productRepository.save(product1);

    assertTrue(saveNewProduct.getId() > 0);
  }

  @Test
  void testDeleteProductById() {

    Product productBeforeDeleted = entityManager.find(Product.class, 18);
    assertNotNull(productBeforeDeleted);

    productRepository.deleteById(productBeforeDeleted.getId());

    assertNotNull(productRepository.findById(productBeforeDeleted.getId()).isEmpty());

  }



}