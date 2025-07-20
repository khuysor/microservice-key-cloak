create table tb_products
(
    id             bigint auto_increment primary key,
    sku            varchar(255) unique not null,
    barcode        varchar(255)        not null,
    quantity       integer,
    price          decimal(10, 2),
    seo_url        varchar(255),
    image_url      varchar(255),
    status         tinyint(1)                   default 0,
    available_date timestamp           not null,
    create_date    timestamp           not null default current_timestamp,
    update_date    timestamp           not null default current_timestamp on update current_timestamp

);
create table tb_product_lang
(
    lang_id     bigint       not null,
    product_id  bigint       not null,
    name        varchar(100) not null,
    description varchar(255),
    primary key (lang_id, product_id)
)