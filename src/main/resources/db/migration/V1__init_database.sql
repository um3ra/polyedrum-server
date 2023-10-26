create table bucket_seq
(
    next_val bigint
);
insert into bucket_seq
values (1);
create table buckets
(
    id      bigint not null,
    user_id bigint,
    primary key (id)
);
create table buckets_products
(
    bucket_id  bigint not null,
    product_id bigint not null
);
create table categories
(
    id   bigint not null,
    name varchar(255),
    primary key (id)
);
create table category_seq
(
    next_val bigint
);
insert into category_seq
values (1);
create table genre_seq
(
    next_val bigint
);
insert into genre_seq
values (1);
create table genres
(
    category_id bigint,
    id          bigint not null,
    name        varchar(255),
    primary key (id)
);
create table image_seq
(
    next_val bigint
);
insert into image_seq
values (1);
create table images
(
    id   bigint not null,
    name varchar(255),
    type varchar(255),
    primary key (id)
);
create table order_details
(
    amount     decimal(38, 2),
    sum        decimal(38, 2),
    id         bigint not null,
    order_id   bigint,
    product_id bigint,
    primary key (id)
);
create table order_details_seq
(
    next_val bigint
);
insert into order_details_seq
values (1);
create table order_seq
(
    next_val bigint
);
insert into order_seq
values (1);
create table orders
(
    sum         decimal(38, 2),
    change_date datetime(6),
    date        datetime(6),
    id          bigint not null,
    user_id     bigint,
    status      enum ('APPROVED','CANCELED','CLOSED','NEW','PAID'),
    primary key (id)
);
create table product_info_seq
(
    next_val bigint
);
insert into product_info_seq
values (1);
create table product_seq
(
    next_val bigint
);
insert into product_seq
values (1);
create table products
(
    price           decimal(38, 2),
    id              bigint not null,
    image_id        bigint,
    product_info_id bigint,
    author          varchar(255),
    imageurl        varchar(255),
    title           varchar(255),
    primary key (id)
);
create table products_genres
(
    genre_id   bigint not null,
    product_id bigint not null
);
create table products_info
(
    weight          float(53),
    id              bigint not null,
    number_of_pages bigint,
    description     varchar(512),
    publisher       varchar(255),
    primary key (id)
);
create table user_seq
(
    next_val bigint
);
insert into user_seq
values (1);
create table users
(
    id         bigint not null,
    email      varchar(255),
    first_name varchar(255),
    last_name  varchar(255),
    password   varchar(255),
    role       enum ('ADMIN','CLIENT'),
    primary key (id)
);
alter table buckets
    add constraint buckets_uk_user unique (user_id);
alter table products
    add constraint products_uk_image unique (image_id);
alter table products
    add constraint products_uk_product_info unique (product_info_id);
alter table buckets
    add constraint buckets_fk_user foreign key (user_id) references users (id);
alter table buckets_products
    add constraint bucket_products_fk_product foreign key (product_id) references products (id) on delete cascade on update cascade;
alter table buckets_products
    add constraint bucket_products_fk_bucket foreign key (bucket_id) references buckets (id) on delete cascade on update cascade;
alter table genres
    add constraint genres_fk_category foreign key (category_id) references categories (id);
alter table order_details
    add constraint order_details_fk_order foreign key (order_id) references orders (id);
alter table order_details
    add constraint order_details_fk_product foreign key (product_id) references products (id);
alter table orders
    add constraint orders_fk_user foreign key (user_id) references users (id);
alter table products
    add constraint products_fk_image foreign key (image_id) references images (id);
alter table products
    add constraint products_fk_products_info foreign key (product_info_id) references products_info (id);
alter table products_genres
    add constraint products_genres_fk_genre foreign key (genre_id) references genres (id) on delete cascade on update cascade;
alter table products_genres
    add constraint products_genres_fk_products foreign key (product_id) references products (id) on delete cascade on update cascade;