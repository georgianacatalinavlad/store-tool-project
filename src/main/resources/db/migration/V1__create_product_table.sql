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