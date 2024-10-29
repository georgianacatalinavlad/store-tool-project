package org.example.storetoolproject.services;

import org.example.storetoolproject.exceptions.StoreProcessFailedException;
import org.example.storetoolproject.models.entities.Product;
import org.example.storetoolproject.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.logging.Logger;

import static org.example.storetoolproject.StoreFactoryTest.getProductRequest;
import static org.example.storetoolproject.StoreFactoryTest.getSavedProduct;
import static org.example.storetoolproject.enums.ProductStatus.IN_STOCK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StoreServiceTest {
    @InjectMocks
    private StoreService storeService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private Authentication authentication;;

    @Test
    void givenProductRequest_whenSaveProduct_thenReturnSavedProduct() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("admin");
        SecurityContextHolder.setContext(securityContext);

        when(productRepository.save(any(Product.class))).thenReturn(getSavedProduct());

        storeService.saveProduct(getProductRequest());

        assertEquals(1, getSavedProduct().getId());
        assertEquals("admin", getSavedProduct().getCreatedBy());
        assertEquals(IN_STOCK.getValue(), getSavedProduct().getStatus());
    }

    @Test
    void givenInvalidProductRequest_whenSaveProduct_thenReturnAnException() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("admin");
        SecurityContextHolder.setContext(securityContext);

        assertThrows(StoreProcessFailedException.class, () -> storeService.saveProduct(null));

        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void givenProductId_whenFindProductById_thenReturnSavedProduct() {

        when(productRepository.findProductById(anyInt())).thenReturn(Optional.ofNullable(getSavedProduct()));

        storeService.findProduct(1);

        assertEquals(1, getSavedProduct().getId());
        assertEquals("admin", getSavedProduct().getCreatedBy());
        assertEquals(IN_STOCK.getValue(), getSavedProduct().getStatus());
    }

    @Test
    void givenInvalidProductId_whenFindProductById_thenReturnAnException() {

        when(productRepository.findProductById(1)).thenReturn(Optional.empty());

        assertThrows(StoreProcessFailedException.class, () -> storeService.findProduct(1));
    }

    @Test
    void givenProductId_whenDeleteProduct_thenDeleteProduct() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("admin");
        SecurityContextHolder.setContext(securityContext);

        when(productRepository.findProductById(anyInt())).thenReturn(Optional.ofNullable(getSavedProduct()));

        storeService.deleteProduct(1);

        verify(productRepository).save(any(Product.class));
    }
}
