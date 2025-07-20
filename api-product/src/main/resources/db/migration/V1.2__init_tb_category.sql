create table tb_category
(
    id          bigint auto_increment primary key,
    image       varchar(255),
    seo_url     varchar(255) not null,
    status      tinyint(1) default 0,
    parent_id   bigint                default null,
    create_date timestamp    not null default current_timestamp,
    update_date timestamp    not null default current_timestamp on update current_timestamp,
    FOREIGN KEY (parent_id) REFERENCES tb_category (id)
);
create table tb_category_lang
(
    id          bigint auto_increment primary key,
    lang_id     bigint       not null,
    category_id bigint       not null,
    name        varchar(255) not null,
    description varchar(255)
);