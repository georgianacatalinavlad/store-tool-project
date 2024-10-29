package org.example.storetoolproject.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ProductStatus {
    IN_STOCK("In Stock"),
    NOT_STOCK("Not Stock"),
    DELETED("Deleted");

    private String value;
}
