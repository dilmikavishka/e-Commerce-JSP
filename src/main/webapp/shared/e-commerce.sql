-- Create the database
CREATE DATABASE e_commerce;

-- Use the database
USE e_commerce;
create table if not exists categories
(
    category_id   varchar(36)                         not null
    primary key,
    category_name varchar(100)                        not null,
    description   text                                null,
    created_at    timestamp default CURRENT_TIMESTAMP null
    );

create table if not exists products
(
    product_id   varchar(36)                         not null
    primary key,
    product_name varchar(100)                        not null,
    category_id  varchar(36)                         null,
    price        decimal(10, 2)                      not null,
    stock        int                                 not null,
    description  text                                null,
    created_at   timestamp default CURRENT_TIMESTAMP null,
    base64_image longtext                            null,
    constraint products_ibfk_1
    foreign key (category_id) references categories (category_id)
    );

create index category_id
    on products (category_id);

create table if not exists users
(
    user_id    varchar(36)                         not null
    primary key,
    username   varchar(50)                         not null,
    email      varchar(100)                        not null,
    password   varchar(255)                        not null,
    role       varchar(50)                         null,
    created_at timestamp default CURRENT_TIMESTAMP null,
    constraint email
    unique (email),
    constraint username
    unique (username)
    );

create table if not exists cart
(
    cart_id     varchar(36)                         not null
    primary key,
    user_id     varchar(36)                         not null,
    added_at    timestamp default CURRENT_TIMESTAMP null,
    total_Price varchar(100)                        null,
    constraint cart_ibfk_1
    foreign key (user_id) references users (user_id)
    );

create index user_id
    on cart (user_id);

create table if not exists cart_items
(
    cart_item_id varchar(36) not null
    primary key,
    cart_id      varchar(36) not null,
    product_id   varchar(36) not null,
    quantity     int         not null,
    constraint cart_items_ibfk_1
    foreign key (cart_id) references cart (cart_id)
    on update cascade on delete cascade,
    constraint cart_items_ibfk_2
    foreign key (product_id) references products (product_id)
    on update cascade on delete cascade
    );

create index cart_id
    on cart_items (cart_id);

create index product_id
    on cart_items (product_id);

create table if not exists orders
(
    order_id    varchar(36)                           not null
    primary key,
    user_id     varchar(36)                           not null,
    order_date  timestamp   default CURRENT_TIMESTAMP null,
    total_price decimal(10, 2)                        null,
    status      varchar(50) default 'Pending'         null,
    constraint orders_ibfk_1
    foreign key (user_id) references users (user_id)
    );

create table if not exists order_details
(
    order_detail_id varchar(36)    not null
    primary key,
    order_id        varchar(36)    not null,
    product_id      varchar(36)    not null,
    quantity        int            not null,
    price           decimal(10, 2) not null,
    constraint order_details_ibfk_1
    foreign key (order_id) references orders (order_id)
    on update cascade on delete cascade,
    constraint order_details_ibfk_2
    foreign key (product_id) references products (product_id)
    on update cascade on delete cascade
    );

create index order_id
    on order_details (order_id);

create index product_id
    on order_details (product_id);

create index user_id
    on orders (user_id);