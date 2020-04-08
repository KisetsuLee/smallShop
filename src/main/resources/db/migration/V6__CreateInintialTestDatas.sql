insert into `USER`
    (id, name, tel)
values (1000, 'test', '13127755898');

insert into GOODS
    (shop_id, name, description, price, stock, status)
values (100, '肥皂1', '一块好肥皂1', 10, 100, 'ok');

insert into GOODS
    (shop_id, name, description, price, stock, status)
values (200, '肥皂2没有权限', '一块没权限肥皂2', 100, 100, 'ok');

insert into GOODS
    (shop_id, name, description, price, stock, status)
values (100, '肥皂3', '一块好肥皂3', 10, 100, 'ok');

insert into GOODS
    (shop_id, name, description, price, stock, status)
values (200, '肥皂4没有权限', '一块没权限肥皂4', 100, 100, 'deleted');

insert into SHOP
    (id, name, description, owner_user_id, status)
values (100, 'testshop', '测试店', 1000, 'ok');

insert into SHOP
(id, name, description, owner_user_id, status)
values (200, 'testshop', '测试店2', 1234, 'ok');

