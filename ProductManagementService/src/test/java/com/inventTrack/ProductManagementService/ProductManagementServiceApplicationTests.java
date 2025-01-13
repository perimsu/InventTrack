package com.inventTrack.ProductManagementService;

import com.inventTrack.ProductManagementService.Model.Product;
import com.inventTrack.ProductManagementService.Repository.ProductRepository;
import com.inventTrack.ProductManagementService.Service.ProductService;
import com.inventTrack.ProductManagementService.Controller.ProductController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
class ProductManagementServiceApplicationTests {

	private MockMvc mockMvc;

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductController productController;

	@Autowired
	private ProductRepository productRepository;

	private Product product;

	@BeforeEach
	public void setUp() {

		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();


		product = new Product();
		product.setName("Test Product");
		product.setCategory("Electronics");
		product.setDescription("Test product description");
		product.setPrice(100.0);
		product.setStockQuantity(50);


		productRepository.save(product);
	}


	@Test
	void testGetAllProducts() throws Exception {

		List<Product> products = productRepository.findAll();


		mockMvc.perform(get("/products"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(products.size()))
				.andExpect(jsonPath("$[0].name").value("Test Product"));
	}


	@Test
	void testGetProductById() throws Exception {

		Product foundProduct = productRepository.findById(product.getId()).orElse(null);


		mockMvc.perform(get("/products/{id}", foundProduct.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Test Product"));
	}


	@Test
	void testSaveProduct() throws Exception {
		Product newProduct = new Product();
		newProduct.setName("New Product");
		newProduct.setCategory("Books");
		newProduct.setDescription("A new product for testing");
		newProduct.setPrice(200.0);
		newProduct.setStockQuantity(100);


		mockMvc.perform(post("/products")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(newProduct)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("New Product"));
	}


	@Test
	void testUpdateProduct() throws Exception {

		Product updatedProduct = productRepository.findById(product.getId()).orElse(null);
		updatedProduct.setName("Updated Test Product");


		mockMvc.perform(put("/products/{id}", updatedProduct.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(updatedProduct)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Updated Test Product"));
	}


	@Test
	void testDeleteProduct() throws Exception {

		Product productToDelete = productRepository.findById(product.getId()).orElse(null);

		mockMvc.perform(delete("/products/{id}", productToDelete.getId()))
				.andExpect(status().isNoContent());

		assert (!productRepository.findById(productToDelete.getId()).isPresent());
	}
}
