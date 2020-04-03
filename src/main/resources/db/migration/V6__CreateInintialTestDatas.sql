insert into `USER`
    (id, name, tel)
values (1000, 'test', '13127755898');

insert into GOODS
    (shop_id, name, description, price, stock, status)
values (100, '肥皂', '一块好肥皂', 10, 100, 'ok');

insert into SHOP
    (id, name, description, owner_user_id, status)
values (100, 'testshop', '测试店', 1000, 'ok');

insert into SHOP
(id, name, description, owner_user_id, status)
values (200, 'testshop', '测试店2', 1234, 'ok');
