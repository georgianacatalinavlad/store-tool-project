package org.example.storetoolproject.models.responses;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.storetoolproject.enums.CategoryTypes;
import org.example.storetoolproject.enums.ProductStatus;

import java.time.LocalDate;

@Data
@Builder
@Getter
@Setter
public class ProductResponse {
    private String name;
    private Integer pricePerUnit;
    private Integer quantity;
    private CategoryTypes category;
    private String brand;
    private ProductStatus status;
    private String createdBy;
    private LocalDate created_on;
    private int deleted;
    private String deletedBy;
    private LocalDate deleted_on;
    private String modifiedBy;
    private LocalDate modified_on;
}