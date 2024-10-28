package org.example.storetoolproject.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "Product")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "price_per_unit")
    private double pricePerUnit;
    @Column(name = "currency")
    private String currency;
    @Column(name = "stock")
    private int stock;
    @Column(name = "category")
    private String category;
    @Column(name = "brand")
    private String brand;
    @Column(name = "status")
    private String status;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_on")
    private LocalDateTime createdOn;
    @Column(name = "deleted_by")
    private String deletedBy;
    @Column(name = "deleted_on")
    private LocalDateTime deletedOn;
    @Column(name = "modified_by")
    private String modifiedBy;
    @Column(name = "modified_on")
    private LocalDateTime modifiedOn;
}
