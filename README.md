Store Management Tool Project

In this application is implemeneted backend part for management the basic functions from a store.

We have the next endpoints :
 1. Save Product
POST http://localhost:8080/api/store/save-product
Request body {
    "name":"Perfume",
    "brand" : "Chloe",
    "category" : "MAKEUP",
    "pricePerUnit" : 680.99,
    "currency" : "LEU",
    "stock" : 100
}
This request will return product id from database.

 2. Update a existing product
PUT http://localhost:8080/api/store/update-product?productId=17
Request body {
    "pricePerUnit" : 123.99,
    "stock" : 49,
    "currency" : "USD"
}
This request will return updated product.

 3. Get available products (status is not DELETED)
GET http://localhost:8080/api/store/available-products
This request will return products which are not deleted.

 4. Get unavailable products (status is DELETED)
GET http://localhost:8080/api/store/unavailable-products
This request will return products which are deleted.

5. Get a specific product from database
GET http://localhost:8080/api/store/find-product?productId=17
This request will return the product with id received.

 6. Get product depending status
GET http://localhost:8080/api/store/filter-products?status=In Stock
This request will return products with status In Stock.

 7. Delete a product with specific id.
DELETE http://localhost:8080/api/store/delete-product?productId=7
  This request will update status with DELETED and also by who and when was deleted.

The security was done by Basic Auth. 
For testing in Postman need to put on Authorization -> Basic Auth
username : admin
password: admin-pass

In application we have 3 roles, admin, senior and junior.
Admin have access to all endpoints.
Senior have access almost all endpoints, except for the DELETE one.
Junior have access just on GET enpoints.

Database :
We have just a table.
Product table 
CREATE TABLE product
(
    id             int IDENTITY (1, 1) NOT NULL,
    name           varchar(255),
    price_per_unit float(53),
    currency       varchar(255),
    stock          int,
    category       varchar(255),
    brand          varchar(255),
    status         varchar(255),
    created_by     varchar(255),
    created_on     datetime,
    deleted_by     varchar(255),
    deleted_on     datetime,
    modified_by    varchar(255),
    modified_on    datetime,
    CONSTRAINT pk_product PRIMARY KEY (id)
)
    GO

