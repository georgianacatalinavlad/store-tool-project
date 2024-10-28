package org.example.storetoolproject.exceptions;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException() {
        super("No products found.");
    }

    public ProductNotFoundException(final int productId) {
        super(String.format("Product with id %s not found.", productId));
    }
}
