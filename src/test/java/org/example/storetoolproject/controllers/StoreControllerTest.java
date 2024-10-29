package org.example.storetoolproject.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.storetoolproject.enums.CategoryTypes;
import org.example.storetoolproject.models.entities.Product;
import org.example.storetoolproject.models.requests.ProductRequest;
import org.example.storetoolproject.models.requests.ProductUpdateRequest;
import org.example.storetoolproject.services.StoreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.example.storetoolproject.StoreFactoryTest.getSavedProduct;
import static org.example.storetoolproject.StoreFactoryTest.getUpdatedProduct;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest
@AutoConfigureMockMvc
public class StoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StoreService storeService;

    private static ObjectMapper objectMapper;

    @BeforeAll
    public static void beforeAll() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    @WithUserDetails(value = "admin")
    public void givenProductObject_whenCreateProduct_thenReturnSavedProduct() throws Exception{
        ProductRequest request = ProductRequest.builder().name("Name")
                .brand("brand")
                .currency("currency")
                .pricePerUnit(100.0)
                .category(CategoryTypes.FOOD)
                .stock(21)
                .build();

        when(storeService.saveProduct(any(ProductRequest.class))).thenReturn(1);

        ResultActions response = mockMvc.perform(post("/api/store/save-product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        response.andDo(print()).
                andExpect(status().isCreated());
    }


    @Test
    @WithUserDetails(value = "admin")
    public void givenListOfAvailableProds_whenGetAllAvailableProds_thenReturnProductsList() throws Exception{
        List<Product> productList = List.of(getSavedProduct());
        when(storeService.getAllAvailableProducts()).thenReturn(productList);

        ResultActions response = mockMvc.perform(get("/api/store/available-products"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(productList.size())));

    }

    @Test
    @WithUserDetails(value = "admin")
    public void givenProductId_whenGetProductById_thenReturnProductObject() throws Exception{
        int productId = 1;
        Product savedProduct = getSavedProduct();
        when(storeService.findProduct(productId)).thenReturn(savedProduct);

        ResultActions response = mockMvc.perform(get("/api/store/find-product?productId={productId}", productId));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(savedProduct.getName())))
                .andExpect(jsonPath("$.brand", is(savedProduct.getBrand())))
                .andExpect(jsonPath("$.category", is(savedProduct.getCategory())));

    }

    @Test
    @WithUserDetails(value = "admin")
    public void givenInvalidProductId_whenGetProductById_thenReturnEmpty() throws Exception{
        int productId = 1;
        when(storeService.findProduct(productId)).thenReturn(null);

        ResultActions response = mockMvc.perform(get("/api/store/find-product?productId={productId}", productId));

        response.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithUserDetails(value = "admin")
    public void givenUpdatedProduct_whenUpdateProduct_thenReturnUpdateProductObject() throws Exception{
        int productId = 1;
        Product updatedProduct = getUpdatedProduct();

        when(storeService.updateProduct(anyInt(), any(ProductUpdateRequest.class))).thenReturn(updatedProduct);

        ResultActions response = mockMvc.perform(put("/api/store/update-product?productId={productId}", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ProductUpdateRequest.builder().pricePerUnit(300.00).build())));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.pricePerUnit", is(updatedProduct.getPricePerUnit())))
                .andExpect(jsonPath("$.status", is(updatedProduct.getStatus())))
                .andExpect(jsonPath("$.currency", is(updatedProduct.getCurrency())))
                .andExpect(jsonPath("$.stock", is(updatedProduct.getStock())));
    }
}
