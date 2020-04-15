CREATE TABLE GOODS
(
    ID          BIGINT PRIMARY KEY AUTO_INCREMENT,
    SHOP_ID     BIGINT,
    NAME        VARCHAR(100),
    DESCRIPTION VARCHAR(1024),
    DETAILS     TEXT,
    IMG_URL     VARCHAR(1024),
    PRICE       DECIMAL,
    STOCK       INT       NOT NULL DEFAULT 0,
    STATUS      VARCHAR(16), -- 'ok' 正常 'deleted' 已经删除
    CREATED_AT  TIMESTAMP NOT NULL DEFAULT NOW(),
    UPDATED_AT  TIMESTAMP NOT NULL DEFAULT NOW()
);

insert into GOODS
    (id, shop_id, name, description, price, stock, status)
values (1, 1, '肥皂1', '一块好肥皂1', 10, 100, 'ok');

insert into GOODS
    (id, shop_id, name, description, price, stock, status)
values (2, 2, '肥皂2没有权限', '一块没权限肥皂2', 100, 100, 'ok');

insert into GOODS
    (id, shop_id, name, description, price, stock, status)
values (3, 1, '肥皂3', '一块好肥皂3', 10, 100, 'ok');

insert into GOODS
    (id, shop_id, name, description, price, stock, status)
values (4, 2, '肥皂4没有权限', '一块没权限肥皂4', 100, 100, 'deleted');
