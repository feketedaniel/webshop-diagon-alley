set time zone 'Europe/Budapest';

drop table if exists public.order cascade;
create table "order"
(
    id                 serial
        constraint order_pk primary key,
    user_id            integer,
    payment_details_id integer,
    is_checked         boolean,
    is_payed           boolean
);

drop table if exists public.product cascade;
drop table if exists public.product_category;

create table product_category
(
    id          serial
        constraint product_category_pk primary key,
    name        varchar,
    description varchar,
    department  varchar
);
create table product
(
    id                  serial
        constraint product_pk primary key,
    name                varchar,
    default_price       numeric,
    default_currency    varchar,
    description         varchar,
    product_category_id integer,
    supplier_id         integer,
    img_url             varchar
);

drop table if exists public.supplier;
create table supplier
(
    id          serial
        constraint supplier_pk primary key,
    name        varchar,
    description varchar
);

drop table if exists public.order_item;
create table order_item
(
    id         serial
        constraint order_item_pk primary key,
    product_id integer,
    order_id   integer,
    amount     integer
);

drop table if exists public.payment_details;
create table payment_details
(
    id               serial
        constraint payment_details_pk primary key,
    order_id         integer,
    user_id          integer,
    shipping_country varchar,
    shipping_city    varchar,
    shipping_zip     integer,
    shipping_address varchar,
    billing_country  varchar,
    billing_city     varchar,
    billing_zip      integer,
    billing_address  varchar,
    name             varchar,
    email            varchar,
    phone            varchar
);

drop table if exists public.users;
create table users
(
    id                serial
        constraint user_pk primary key,
    name              varchar,
    email             varchar,
    password          varchar,
    registration_date timestamp without time zone default now()::timestamp(0)
);

alter table only payment_details
    add constraint fk_order_id foreign key (order_id) references "order" (id);
alter table only payment_details
    add constraint fk_user_id foreign key (user_id) references users (id);
alter table only order_item
    add constraint fk_product_id foreign key (product_id) references product (id);
alter table only order_item
    add constraint fk_order_id foreign key (product_id) references "order" (id);
alter table only product
    add constraint fk_product_category_id foreign key (product_category_id) references product_category (id);
alter table only product
    add constraint fk_supplier_id foreign key (supplier_id) references supplier (id);
alter table only "order"
    add constraint fk_user_id foreign key (user_id) references users (id);
alter table only "order"
    add constraint fk_payment_details_id foreign key (payment_details_id) references payment_details (id);

insert into supplier (id, name, description)
values (1, 'Ollivanders', 'Makers of Fine Wands since 382 B.C.');
insert into supplier (id, name, description)
values (2, 'Nimbus Racing Broom Company',
        'The Nimbus Racing Broom Company was a broomstick company formed in 1967 by Devlin Whitehorn.');
insert into supplier (id, name, description)
values (3, 'Madam Malkin''s Robes for All Occasions',
        'Most of the page was devoted to an advertisement for Madame Malkin''s Robes for All Occations, which was apparently having a sale.');
insert into supplier (id, name, description)
values (4, 'Ellerby and Spudmore',
        'Black Forest broomstick manufacturing company created by Ellerby and Able Spudmore');
insert into supplier (id, name, description)
values (5, 'Eeylops Owl Emporium', 'Eeylops Owl Emporium is a dark shop that sells owls.');
insert into supplier (id, name, description)
values (6, 'Flourish and Blotts',
        'Flourish and Blotts where the shelves were stacked to the ceiling with books as large as paving stones bound in leather; books the size of postage stamps in covers of silk; books full of peculiar symbols and a few books with nothing in them at all.');
insert into supplier (id, name, description)
values (7, 'Sugarplum''s Sweets Shop', 'Magical sweets');
insert into supplier (id, name, description)
values (8, 'Bertie Bott''s', 'Magical sweets');
select pg_catalog.setval('supplier_id_seq', 8, true);

insert into product_category (id, name, description)
values (1, 'Wands', 'magical instrument');
insert into product_category (id, name, description)
values (2, 'Robes', 'clothes');
insert into product_category (id, name, description)
values (3, 'Broomsticks', 'magical instrument');
insert into product_category (id, name, description)
values (4, 'Pets', 'magical creatures');
insert into product_category (id, name, description)
values (5, 'Sweets', 'magical sweets');
select pg_catalog.setval('product_category_id_seq', 5, true);

insert into product(id, name, default_price, default_currency, description, product_category_id, supplier_id, img_url)
VALUES (1, 'Vine wood wand and dragon heartstring core', 49.9, 'GAL',
        'Made with unique vine wood and dragon heartstring core, rigid 10 3/4" long', 1, 1,
        'https://cdn.shopify.com/s/files/1/0514/6332/3817/products/Hermione2_grande.png');
insert into product(id, name, default_price, default_currency, description, product_category_id, supplier_id, img_url)
VALUES (2, 'Holly wood wand with phoenix feather core', 49.9, 'GAL',
        'Made with  unique holly wood with a phoenix feather core, fairly flexible 11" long', 1, 1,
        'https://cdn.shopify.com/s/files/1/0514/6332/3817/products/Harry2_grande.png');
insert into product(id, name, default_price, default_currency, description, product_category_id, supplier_id, img_url)
VALUES (3, 'Nimbus 2000', 49.9, 'GAL',
        'One of the Nimbus Racing Broom Company''s most successful models. Highly reliable with good speed and exceptional handling — not for beginners!',
        3, 2,
        'https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/43b88a73-9b7b-426e-b2a1-a05e6c25a3bc/d4qte7b-60276053-af9c-4b6b-9ad9-0d5dbafd7f5b.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7InBhdGgiOiJcL2ZcLzQzYjg4YTczLTliN2ItNDI2ZS1iMmExLWEwNWU2YzI1YTNiY1wvZDRxdGU3Yi02MDI3NjA1My1hZjljLTRiNmItOWFkOS0wZDVkYmFmZDdmNWIucG5nIn1dXSwiYXVkIjpbInVybjpzZXJ2aWNlOmZpbGUuZG93bmxvYWQiXX0.DPqAZbB5rajXCqF-z-K0kljMqVR0j5i9ZZXTj3TztN4');
insert into product(id, name, default_price, default_currency, description, product_category_id, supplier_id, img_url)
VALUES (4, 'Nimbus 2001', 49.9, 'GAL',
        'The top of the Nimbus Racing Broom Company''s range. Capable of previously unseen speed and control. A world-class broom.',
        3, 2,
        'https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/43b88a73-9b7b-426e-b2a1-a05e6c25a3bc/d4qteb9-3d792244-f793-4a92-8d12-80733b1e88c4.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7InBhdGgiOiJcL2ZcLzQzYjg4YTczLTliN2ItNDI2ZS1iMmExLWEwNWU2YzI1YTNiY1wvZDRxdGViOS0zZDc5MjI0NC1mNzkzLTRhOTItOGQxMi04MDczM2IxZTg4YzQucG5nIn1dXSwiYXVkIjpbInVybjpzZXJ2aWNlOmZpbGUuZG93bmxvYWQiXX0.Dx87bIya3ZhQ1uI9CLtKTMlnIA7Qv6A-JK2ypM-sRkw');
insert into product(id, name, default_price, default_currency, description, product_category_id, supplier_id, img_url)
VALUES (5, 'Chocolate Frog', 49.9, 'GAL', 'Delicious solid milk Chocolate Frog and a lenticular wizard card', 5, 7,
        'https://cdn.shopify.com/s/files/1/0514/6332/3817/products/1338923_1_grande.png?v=1610552119');
insert into product(id, name, default_price, default_currency, description, product_category_id, supplier_id, img_url)
VALUES (6, 'Bertie Bott''s Every Flavour Beans', 49.9, 'GAL',
        'Take home these tasty gourmet jellybeans which come in up to 20 flavours. The flavours range from delicious to disgusting.',
        5, 8, 'https://cdn.shopify.com/s/files/1/0514/6332/3817/products/1338924_1_grande.png?v=1610551920');
insert into product(id, name, default_price, default_currency, description, product_category_id, supplier_id, img_url)
VALUES (7, 'Slytherin Robe', 49.9, 'GAL', 'Hogwarts school uniform', 2, 3,
        'https://cdn.shopify.com/s/files/1/0514/6332/3817/products/1296484_1296653_0_grande.png?v=1613579670');
insert into product(id, name, default_price, default_currency, description, product_category_id, supplier_id, img_url)
VALUES (8, 'Aurora', 49.9, 'GAL', 'Snowy Owl', 4, 5, 'https://www.seekpng.com/png/full/46-465733_snowy-owl-png.png');
select pg_catalog.setval('product_id_seq', 8, true)
