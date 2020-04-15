CREATE TABLE SHOP
(
    ID            BIGINT PRIMARY KEY AUTO_INCREMENT,
    NAME          VARCHAR(100),
    DESCRIPTION   VARCHAR(1024),
    IMG_URL       VARCHAR(1024),
    OWNER_USER_ID BIGINT,
    STATUS        VARCHAR(16),
    CREATED_AT    TIMESTAMP NOT NULL DEFAULT NOW(),
    UPDATED_AT    TIMESTAMP NOT NULL DEFAULT NOW()
);

insert into SHOP
    (id, name, description, owner_user_id, status)
values (1, 'shop1', '测试店', 1, 'ok');

insert into SHOP
(id, name, description, owner_user_id, status)
values (2, 'shop2', '测试店2', 100, 'ok');
