delete from product;
delete from group_product;
delete from price;
delete from pricetype;

insert into group_product(n, name, parent_n, have_childs) values (1, 'IT products', -1, true);

insert into group_product(n, name, parent_n, have_childs) values (2, 'Computers', 1, true);
insert into group_product(n, name, parent_n, have_childs) values (3, 'Desktop Computers', 2, false);
insert into group_product(n, name, parent_n, have_childs) values (4, 'Notebooks', 2, false);
insert into group_product(n, name, parent_n, have_childs) values (5, 'Monitors', 1, false);

insert into group_product(n, name, parent_n, have_childs) values (6, 'Hard drives', 1, false);

insert into group_product(n, name, parent_n, have_childs) values (7, 'UNKNOWN PRODUCTS', 1, false);

insert into product(n, name, group_product_n) values (31, 'Desktop1', 3);
insert into product(n, name, group_product_n) values (32, 'Desktop2', 3);
insert into product(n, name, group_product_n) values (33, 'Desktop3', 3);
insert into product(n, name, group_product_n) values (41, 'Notebook1', 4);
insert into product(n, name, group_product_n) values (42, 'Notebook2', 4);
insert into product(n, name, group_product_n) values (51, 'Monitor1', 5);
insert into product(n, name, group_product_n) values (52, 'Monitor2', 5);
insert into product(n, name, group_product_n) values (61, 'HDD1', 6);
insert into product(n, name, group_product_n) values (62, 'HDD2', 6);

insert into pricetype(n, name) values (1, 'Normal price');
insert into pricetype(n, name) values (2, 'Discount price');

insert into price(n, product_n, price_type_n, price) values (1, 31, 1, 311);
insert into price(n, product_n, price_type_n, price) values (2, 31, 2, 312);

insert into price(n, product_n, price_type_n, price) values (3, 32, 1, 321);
insert into price(n, product_n, price_type_n, price) values (4, 32, 2, 322);

insert into price(n, product_n, price_type_n, price) values (5, 33, 1, 331);
insert into price(n, product_n, price_type_n, price) values (6, 33, 2, 332);

insert into price(n, product_n, price_type_n, price) values (7, 41, 1, 411);
insert into price(n, product_n, price_type_n, price) values (8, 41, 2, 412);

insert into price(n, product_n, price_type_n, price) values (9, 42, 1, 421);
insert into price(n, product_n, price_type_n, price) values (10, 42, 2, 422);

insert into price(n, product_n, price_type_n, price) values (11, 51, 1, 511);
insert into price(n, product_n, price_type_n, price) values (12, 51, 2, 512);

insert into price(n, product_n, price_type_n, price) values (13, 52, 1, 521);
insert into price(n, product_n, price_type_n, price) values (14, 52, 2, 522);

insert into price(n, product_n, price_type_n, price) values (15, 61, 1, 611);
insert into price(n, product_n, price_type_n, price) values (16, 61, 2, 612);

insert into price(n, product_n, price_type_n, price) values (17, 62, 1, 621);
insert into price(n, product_n, price_type_n, price) values (18, 62, 2, 622);

COMMIT;

