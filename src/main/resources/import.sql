delete from product;
delete from group_product;

insert into group_product(n, name, parent_n, have_childs) values (1, 'IT products', -1, true);

insert into group_product(n, name, parent_n, have_childs) values (2, 'Computers', 1, true);
insert into group_product(n, name, parent_n, have_childs) values (3, 'Desktop Computers', 2, false);
insert into group_product(n, name, parent_n, have_childs) values (4, 'Notebooks', 2, false);
insert into group_product(n, name, parent_n, have_childs) values (5, 'Monitors', 1, false);

insert into group_product(n, name, parent_n, have_childs) values (6, 'Hard drives', 1, false);

insert into group_product(n, name, parent_n, have_childs) values (7, 'FOR TEST DELETE', 1, false);

insert into product(n, name, group_product_n) values (32, 'Desktop2', 3);
insert into product(n, name, group_product_n) values (33, 'Desktop3', 3);
insert into product(n, name, group_product_n) values (31, 'Desktop1', 3);
insert into product(n, name, group_product_n) values (41, 'Notebook1', 4);
insert into product(n, name, group_product_n) values (42, 'Notebook2', 4);
insert into product(n, name, group_product_n) values (51, 'Monitor1', 5);
insert into product(n, name, group_product_n) values (52, 'Monitor2', 5);
insert into product(n, name, group_product_n) values (61, 'HDD1', 6);
insert into product(n, name, group_product_n) values (62, 'HDD2', 6);
COMMIT;

