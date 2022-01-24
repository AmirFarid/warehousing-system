SET timezone = 'Asia/Tehran';
CREATE TABLE IF NOT EXISTS capsule.USERS
(
    ID         SERIAL       NOT NULL PRIMARY KEY,
    FIRST_NAME varchar(225) NOT NULL,
    LAST_NAME  varchar(225) NOT NULL,
    USERNAME   varchar(225) NOT NULL UNIQUE,
    PASSWORD   varchar(225),
    CREATED    TIMESTAMPTZ,
    MODIFIED   TIMESTAMPTZ
);

CREATE INDEX USERS_USERNAME_INDEX ON capsule.USERS (USERNAME);

-- -----------------------------------------------------------

CREATE TABLE capsule.MOBILES
(
    ID       SERIAL      NOT NULL PRIMARY KEY,
    NUMBER   VARCHAR(30) NOT NULL,
    USER_ID  INTEGER     NOT NULL,
    FOREIGN KEY (USER_ID) REFERENCES capsule.USERS (ID) ON DELETE RESTRICT,
    CREATED  TIMESTAMPTZ NOT NULL,
    MODIFIED TIMESTAMPTZ NOT NULL

);

CREATE INDEX MOBILES_NUMBER_INDEX ON capsule.MOBILES (NUMBER);
CREATE INDEX MOBILES_USER_ID_INDEX ON capsule.MOBILES (USER_ID);

-- -----------------------------------------------------------------


CREATE TABLE capsule.REGIONS
(
    ID         SERIAL       NOT NULL PRIMARY KEY,
    NAME       VARCHAR(225) NOT NULL,
    SLUG       VARCHAR(225) NOT NULL,
    COORDINATE POINT        NOT NULL,
    TYPE       INTEGER      NOT NULL,
    PARENT_ID  INTEGER,
    FOREIGN KEY (PARENT_ID) REFERENCES capsule.REGIONS (ID) ON DELETE NO ACTION
);

CREATE INDEX REGIONS_PAREN_ID_INDEX ON capsule.REGIONS (PARENT_ID);
COMMENT ON COLUMN capsule.REGIONS.TYPE IS 'country=1 , province=2 , city=3';


--  -------------------------------------------------------------------------------


CREATE TABLE capsule.ADDRESSES
(
    ID           SERIAL       NOT NULL PRIMARY KEY,
    ALIAS        VARCHAR(225) NOT NULL,
    ADDRESS_LINE VARCHAR(225) NOT NULL,
    POSTAL_CODE  VARCHAR(225),
    REGION_ID    INTEGER,
    FOREIGN KEY (REGION_ID) REFERENCES capsule.REGIONS (ID) ON DELETE RESTRICT,
    USER_ID      INTEGER,
    FOREIGN KEY (USER_ID) REFERENCES capsule.USERS (ID) ON DELETE RESTRICT,
    CREATED      TIMESTAMPTZ  NOT NULL,
    MODIFIED     TIMESTAMPTZ  NOT NULL
);

CREATE INDEX ADDRESSES_REGION_ID_INDEX ON capsule.ADDRESSES (REGION_ID);
CREATE INDEX ADDRESSES_USER_ID_INDEX ON capsule.ADDRESSES (USER_ID);
CREATE INDEX ADDRESSES_POSTAL_CODE_INDEX ON capsule.ADDRESSES (POSTAL_CODE);

-- -----------------------------------------------------------------------------------

CREATE TABLE capsule.PHONES
(
    ID         SERIAL       NOT NULL PRIMARY KEY,
    NUMBER     VARCHAR(100) NOT NULL,
    ADDRESS_ID INTEGER      NOT NULL,
    FOREIGN KEY (ADDRESS_ID) REFERENCES capsule.ADDRESSES (ID) ON DELETE NO ACTION,
    CREATED    TIMESTAMPTZ  NOT NULL,
    MODIFIED   TIMESTAMPTZ  NOT NULL
);

CREATE INDEX PHONES_ADDRESS_ID_INDEX ON capsule.PHONES (ADDRESS_ID);

-- -----------------------------------------------------------------------------------

CREATE TABLE capsule.PRODUCT_TYPES
(
    ID          SERIAL      NOT NULL PRIMARY KEY,
    NAME        TEXT        NOT NULL,
    DESCRIPTION TEXT DEFAULT NULL,
    CREATED     TIMESTAMPTZ NOT NULL,
    MODIFIED    TIMESTAMPTZ NOT NULL
);

-- -----------------------------------------------------------------------------------

CREATE TABLE capsule.PRODUCTS
(
    ID              SERIAL       NOT NULL PRIMARY KEY,
    NAME            TEXT         NOT NULL,
    DESCRIPTION     TEXT                  DEFAULT NULL,
    IS_ACTIVE       VARCHAR(10)  NOT NULL DEFAULT 'true',
    UNITS_ON_ORDER  VARCHAR(225) NOT NULL,
    COLOR           VARCHAR(255) NOT NULL,
    QTY             INTEGER      NOT NULL,
    QTY_UNIT        VARCHAR(255) NOT NULL,
    PRICE           VARCHAR(255) NOT NULL,
    PROMOTION_PRICE VARCHAR(255)          DEFAULT NULL,
    PRICE_LEVEL_ONE VARCHAR(255)          DEFAULT NULL,
    PRICE_LEVEL_TWO VARCHAR(255)          DEFAULT NULL,
    PRODUCT_TYPE_ID INTEGER,
    FOREIGN KEY (PRODUCT_TYPE_ID) REFERENCES capsule.PRODUCT_TYPES (ID) ON DELETE NO ACTION,
    CREATED         TIMESTAMPTZ  NOT NULL,
    MODIFIED        TIMESTAMPTZ  NOT NULL
);

CREATE INDEX PRODUCTS_PRODUCT_TYPE_ID_INDEX ON capsule.PRODUCT_TYPES (ID);

-- -----------------------------------------------------------------------------------

CREATE TABLE capsule.CATEGORIES
(
    ID          SERIAL       NOT NULL PRIMARY KEY,
    NAME        VARCHAR(225) NOT NULL,
    SLUG        VARCHAR(225) NOT NULL,
    DESCRIPTION VARCHAR(225) NOT NULL,
    IS_ACTIVE   VARCHAR(10)  NOT NULL DEFAULT 'true',
    PARENT_ID   INTEGER,
    FOREIGN KEY (PARENT_ID) REFERENCES capsule.CATEGORIES (ID) ON DELETE RESTRICT,
    CREATED     TIMESTAMPTZ  NOT NULL,
    MODIFIED    TIMESTAMPTZ  NOT NULL
);


-- -----------------------------------------------------------------------------------

CREATE TABLE capsule.CATEGORY_PRODUCT
(
    ID          SERIAL      NOT NULL PRIMARY KEY,
    CATEGORY_ID INTEGER,
    FOREIGN KEY (CATEGORY_ID) REFERENCES capsule.CATEGORIES (ID) ON DELETE RESTRICT,
    PRODUCT_ID  INTEGER,
    FOREIGN KEY (PRODUCT_ID) REFERENCES capsule.PRODUCTS (ID) ON DELETE RESTRICT
);
CREATE INDEX CATEGORY_PRODUCT_PRODUCT_ID_INDEX ON capsule.CATEGORY_PRODUCT (PRODUCT_ID);
CREATE INDEX CATEGORY_PRODUCT_CATEGORY_ID_INDEX ON capsule.CATEGORY_PRODUCT (CATEGORY_ID);

-- -----------------------------------------------------------------------------------

CREATE TABLE capsule.MEDIA
(
    ID        SERIAL       NOT NULL PRIMARY KEY,
    FILE_NAME VARCHAR(255) NOT NULL,
    MIME_TYPE VARCHAR(255) NOT NULL,
    SIZE      VARCHAR(255) NOT NULL,
    DISK      VARCHAR(255) NOT NULL,
    PATH      VARCHAR(255) DEFAULT NULL,
    ENTITY    VARCHAR(255) NOT NULL,
    ENTITY_ID INTEGER      NOT NULL,
    CREATED   TIMESTAMPTZ  NOT NULL,
    MODIFIED  TIMESTAMPTZ  NOT NULL
);


-- -----------------------------------------------------------------------------------

CREATE TABLE capsule.ATTRIBUTES
(
    ID          SERIAL       NOT NULL PRIMARY KEY,
    NAME        VARCHAR(255) NOT NULL,
    DESCRIPTION VARCHAR(255) DEFAULT NULL
);


CREATE TABLE capsule.ATTRIBUTE_COLLECTIONS
(
    ID           SERIAL       NOT NULL PRIMARY KEY,
    NAME         VARCHAR(255) NOT NULL,
    DESCRIPTION  VARCHAR(255) DEFAULT NULL,
    ATTRIBUTE_ID INTEGER      DEFAULT NULL,
    FOREIGN KEY (ATTRIBUTE_ID) REFERENCES capsule.ATTRIBUTE_COLLECTIONS (ID) ON DELETE NO ACTION
);
CREATE INDEX ATTRIBUTE_COLLECTIONS_ATTRIBUTE_ID_INDEX ON capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID);

CREATE TABLE capsule.ATTRIBUTE_VALUES
(
    ID           SERIAL       NOT NULL PRIMARY KEY,
    VALUE        VARCHAR(255) NOT NULL,
    DESCRIPTION  VARCHAR(255) DEFAULT NULL,
    ATTRIBUTE_ID INTEGER      DEFAULT NULL,
    FOREIGN KEY (ATTRIBUTE_ID) REFERENCES capsule.ATTRIBUTES (ID) ON DELETE NO ACTION
);
CREATE INDEX ATTRIBUTE_VALUES_ATTRIBUTE_ID_INDEX ON capsule.ATTRIBUTE_VALUES (ATTRIBUTE_ID);

-- -----------------------------------------------------------------------------------
CREATE TABLE capsule.ATTRIBUTE_VALUE_PRODUCT_TYPE
(
    ID                 SERIAL  NOT NULL PRIMARY KEY,
    ATTRIBUTE_VALUE_ID INTEGER NOT NULL,
    FOREIGN KEY (ATTRIBUTE_VALUE_ID) REFERENCES capsule.ATTRIBUTE_VALUES (ID) ON DELETE NO ACTION,
    PRODUCT_TYPE_ID    INTEGER NOT NULL,
    FOREIGN KEY (PRODUCT_TYPE_ID) REFERENCES capsule.PRODUCT_TYPES (ID) ON DELETE NO ACTION
);

CREATE INDEX ATTRIBUTE_PRODUCT_PRODUCT_TYPE_ID_INDEX ON capsule.ATTRIBUTE_VALUE_PRODUCT_TYPE (PRODUCT_TYPE_ID);
CREATE INDEX ATTRIBUTE_PRODUCT_ATTRIBUTE_ID_INDEX ON capsule.ATTRIBUTE_VALUE_PRODUCT_TYPE (ATTRIBUTE_VALUE_ID);

-- -----------------------------------------------------------------------------------


-- USER DATA

INSERT INTO capsule.ATTRIBUTES (id, name, description)
VALUES (1, 'material', 'جنس پارچه');
INSERT INTO capsule.ATTRIBUTES (id, NAME, description)
VALUES (2, 'resistance', 'ایستادگی');
INSERT INTO capsule.ATTRIBUTES (id, NAME, description)
VALUES (3, 'usage', 'موارد استفاده');
INSERT INTO capsule.ATTRIBUTES (id, NAME, description)
VALUES (4, 'thickness', 'ضخامت');
INSERT INTO capsule.ATTRIBUTES (id, NAME, description)
VALUES (5, 'width', 'عرض');


--  attribute collection
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'کرپ');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'ژاکارد');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'شانتون');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'کرپ حریر شاین');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'شانتون کنفی بافت');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'شانتون کنفی بافت');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'تور');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'کرپ حریر طرحدار');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'ژاکارد ارگانزا');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'نخ پنبه');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'لینن');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'کرپ حریر');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'پولکی');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'یاخما');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'سوزن دوزی');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'لینن ترک ساده');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'ساتن آمریکایی');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'لینن ساده');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'دانتل');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'دانتل پولکی');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'کتان');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'حریر ساده');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'ژرژت نخ');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'گیپور');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'کرپ جودون');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'فوتر');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'حریر');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'ساتن');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'کرپ نخ');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'وال');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'پلیسه کرپ حریر');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'ارگانزا ساده');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'کتان');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'مخمل ونوس');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'jean');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'ساتن استات');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'تافته');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'سیلک');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'نخی');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'کرپ استات');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'jean');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (1, 'کشمیر');

INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (2, 'متوسط');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (2, 'آهاردار');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (2, 'لخت');

INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (3, 'مجلسی');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (3, 'پیراهن');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (3, 'مانتویی');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (3, 'کت');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (3, 'خرجکار');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (3, 'دامن');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (3, 'دامن-سارافون-سرهمی');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (3, 'پالتویی');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (3, 'مانتویی-پیرهنی-شومیزی');

INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (4, 'نازک');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (4, 'متوسط');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (4, 'ضخیم');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (4, 'بدن نما');
INSERT INTO capsule.ATTRIBUTE_COLLECTIONS (ATTRIBUTE_ID, name)
VALUES (4, 'تابستونه');



insert into capsule.USERS(FIRST_NAME, LAST_NAME, USERNAME, PASSWORD, CREATED, MODIFIED)
values ('nima', 'ebrazi', '09015262679', '123', now(), now());

-- -----------------------------------------------------------------------------------


INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (1, 'کرپ', 'کرپ', '2020-07-14 18:47:37.618000', '2020-07-14 18:47:37.618000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (2, 'ژاکارد', 'ژاکارد', '2020-07-14 18:47:38.654000', '2020-07-14 18:47:38.654000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (3, 'شانتون', 'شانتون', '2020-07-14 18:47:38.657000', '2020-07-14 18:47:38.657000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (4, 'کرپ حریر شاین', 'کرپ حریر شاین', '2020-07-14 18:47:38.661000', '2020-07-14 18:47:38.661000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (5, 'شانتون کنفی بافت', 'شانتون کنفی بافت', '2020-07-14 18:47:38.665000', '2020-07-14 18:47:38.665000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (6, 'شانتون کنفی بافت', 'شانتون کنفی بافت', '2020-07-14 18:47:38.669000', '2020-07-14 18:47:38.669000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (7, 'تور', 'تور', '2020-07-14 18:47:38.672000', '2020-07-14 18:47:38.672000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (8, 'کرپ حریر طرحدار', 'کرپ حریر طرحدار', '2020-07-14 18:47:38.675000', '2020-07-14 18:47:38.675000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (9, 'ژاکارد ارگانزا', 'ژاکارد ارگانزا', '2020-07-14 18:47:38.678000', '2020-07-14 18:47:38.678000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (10, 'نخ پنبه', 'نخ پنبه', '2020-07-14 18:47:38.681000', '2020-07-14 18:47:38.681000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (11, 'لینن', 'لینن', '2020-07-14 18:47:38.684000', '2020-07-14 18:47:38.684000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (12, 'کرپ حریر', 'کرپ حریر', '2020-07-14 18:47:38.687000', '2020-07-14 18:47:38.687000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (13, 'پولکی', 'پولکی', '2020-07-14 18:47:38.690000', '2020-07-14 18:47:38.690000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (14, 'یاخما', 'یاخما', '2020-07-14 18:47:38.694000', '2020-07-14 18:47:38.694000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (15, 'سوزن دوزی', 'سوزن دوزی', '2020-07-14 18:47:38.697000', '2020-07-14 18:47:38.697000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (16, 'لینن ترک ساده', 'لینن ترک ساده', '2020-07-14 18:47:38.700000', '2020-07-14 18:47:38.700000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (17, 'ساتن آمریکایی', 'ساتن آمریکایی', '2020-07-14 18:47:38.704000', '2020-07-14 18:47:38.704000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (18, 'لینن ساده', 'لینن ساده', '2020-07-14 18:47:38.707000', '2020-07-14 18:47:38.707000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (19, 'دانتل', 'دانتل', '2020-07-14 18:47:38.709000', '2020-07-14 18:47:38.709000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (20, 'دانتل پولکی', 'دانتل پولکی', '2020-07-14 18:47:38.715000', '2020-07-14 18:47:38.715000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (21, 'کتان', 'کتان', '2020-07-14 18:47:38.718000', '2020-07-14 18:47:38.718000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (22, 'حریر ساده', 'حریر ساده', '2020-07-14 18:47:38.721000', '2020-07-14 18:47:38.721000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (23, 'ژرژت نخ', 'ژرژت نخ', '2020-07-14 18:47:38.725000', '2020-07-14 18:47:38.725000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (24, 'گیپور', 'گیپور', '2020-07-14 18:47:38.730000', '2020-07-14 18:47:38.730000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (25, 'کرپ جودون', 'کرپ جودون', '2020-07-14 18:47:38.734000', '2020-07-14 18:47:38.734000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (26, 'فوتر', 'فوتر', '2020-07-14 18:47:38.738000', '2020-07-14 18:47:38.738000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (27, 'حریر', 'حریر', '2020-07-14 18:47:38.741000', '2020-07-14 18:47:38.741000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (28, 'ساتن', 'ساتن', '2020-07-14 18:47:38.744000', '2020-07-14 18:47:38.744000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (29, 'کرپ نخ', 'کرپ نخ', '2020-07-14 18:47:38.749000', '2020-07-14 18:47:38.749000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (30, 'وال', 'وال', '2020-07-14 18:47:38.753000', '2020-07-14 18:47:38.753000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (31, 'پلیسه کرپ حریر', 'پلیسه کرپ حریر', '2020-07-14 18:47:38.756000', '2020-07-14 18:47:38.756000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (32, 'ارگانزا ساده', 'ارگانزا ساده', '2020-07-14 18:47:38.761000', '2020-07-14 18:47:38.761000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (33, 'کتان', 'کتان', '2020-07-14 18:47:38.765000', '2020-07-14 18:47:38.765000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (34, 'مخمل ونوس', 'مخمل ونوس', '2020-07-14 18:47:38.768000', '2020-07-14 18:47:38.768000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (35, 'jean', 'jean', '2020-07-14 18:47:38.772000', '2020-07-14 18:47:38.772000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (36, 'ساتن استات', 'ساتن استات', '2020-07-14 18:47:38.775000', '2020-07-14 18:47:38.775000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (37, 'تافته', 'تافته', '2020-07-14 18:47:38.778000', '2020-07-14 18:47:38.778000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (38, 'سیلک', 'سیلک', '2020-07-14 18:47:38.781000', '2020-07-14 18:47:38.781000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (39, 'نخی', 'نخی', '2020-07-14 18:47:38.784000', '2020-07-14 18:47:38.784000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (40, 'کرپ استات', 'کرپ استات', '2020-07-14 18:47:38.787000', '2020-07-14 18:47:38.787000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (41, 'jean', 'jean', '2020-07-14 18:47:38.789000', '2020-07-14 18:47:38.789000');
INSERT INTO capsule.product_types (id, name, description, created, modified)
VALUES (42, 'کشمیر', 'کشمیر', '2020-07-14 18:47:38.792000', '2020-07-14 18:47:38.792000');



insert into capsule.categories(name, slug, description, is_active, parent_id, created, modified)
values ('category-1', 'category-1', 'description-1', 'true', null, now(), now());


DROP TABLE capsule.PHONES CASCADE;
DROP TABLE capsule.ADDRESSES CASCADE;
DROP TABLE capsule.REGIONS CASCADE;
DROP TABLE capsule.MOBILES CASCADE;
drop table capsule.users CASCADE;
drop table capsule.CATEGORY_PRODUCT CASCADE;
drop table capsule.products CASCADE;
drop table capsule.PRODUCT_TYPES CASCADE;
drop table capsule.CATEGORIES CASCADE;
DROP TABLE capsule.ATTRIBUTES cascade;
DROP TABLE capsule.ATTRIBUTE_VALUES cascade;
drop table capsule.MEDIA CASCADE;
drop table capsule.ATTRIBUTE_COLLECTIONS CASCADE;



ALTER TABLE capsule.REGIONS
    DISABLE TRIGGER ALL;
-- now the RI over table b is disabled

INSERT INTO capsule.REGIONS
VALUES (1, 'آذربایجان شرقی', 'آذربایجان شرقی', POINT(37.90357330, 46.26821090), 2, 32);
INSERT INTO capsule.REGIONS
VALUES (2, 'آذربایجان غربی', 'آذربایجان غربی', POINT(37.45500620, 45.00000000), 2, 32);
INSERT INTO capsule.REGIONS
VALUES (3, 'اردبیل', 'اردبیل', POINT(38.48532760, 47.89112090), 2, 32);
INSERT INTO capsule.REGIONS
VALUES (4, 'اصفهان', 'اصفهان', POINT(32.65462750, 51.66798260), 2, 32);
INSERT INTO capsule.REGIONS
VALUES (5, 'البرز', 'البرز', POINT(35.99604670, 50.92892460), 2, 32);
INSERT INTO capsule.REGIONS
VALUES (6, 'ایلام', 'ایلام', POINT(33.29576180, 46.67053400), 2, 32);
INSERT INTO capsule.REGIONS
VALUES (7, 'بوشهر', 'بوشهر', POINT(28.92338370, 50.82031400), 2, 32);
INSERT INTO capsule.REGIONS
VALUES (8, 'تهران', 'تهران', POINT(35.69611100, 51.42305600), 2, 32);
INSERT INTO capsule.REGIONS
VALUES (9, 'چهارمحال و بختیاری', 'چهارمحال و بختیاری', POINT(31.96143480, 50.84563230), 2, 32);
INSERT INTO capsule.REGIONS
VALUES (10, 'خراسان جنوبی', 'خراسان جنوبی', POINT(32.51756430, 59.10417580), 2, 32);
INSERT INTO capsule.REGIONS
VALUES (11, 'خراسان رضوی', 'خراسان رضوی', POINT(35.10202530, 59.10417580), 2, 32);
INSERT INTO capsule.REGIONS
VALUES (12, 'خراسان شمالی', 'خراسان شمالی', POINT(37.47103530, 57.10131880), 2, 32);
INSERT INTO capsule.REGIONS
VALUES (13, 'خوزستان', 'خوزستان', POINT(31.43601490, 49.04131200), 2, 32);
INSERT INTO capsule.REGIONS
VALUES (14, 'زنجان', 'زنجان', POINT(36.50181850, 48.39881860), 2, 32);
INSERT INTO capsule.REGIONS
VALUES (15, 'سمنان', 'سمنان', POINT(35.22555850, 54.43421380), 2, 32);
INSERT INTO capsule.REGIONS
VALUES (16, 'سیستان و بلوچستان', 'سیستان و بلوچستان', POINT(27.52999060, 60.58206760), 2, 32);
INSERT INTO capsule.REGIONS
VALUES (17, 'فارس', 'فارس', POINT(29.10438130, 53.04589300), 2, 32);
INSERT INTO capsule.REGIONS
VALUES (18, 'قزوین', 'قزوین', POINT(36.08813170, 49.85472660), 2, 32);
INSERT INTO capsule.REGIONS
VALUES (19, 'قم', 'قم', POINT(34.63994430, 50.87594190), 2, 32);
INSERT INTO capsule.REGIONS
VALUES (20, 'كردستان', 'كردستان', POINT(35.95535790, 47.13621250), 2, 32);
INSERT INTO capsule.REGIONS
VALUES (21, 'كرمان', 'كرمان', POINT(30.28393790, 57.08336280), 2, 32);
INSERT INTO capsule.REGIONS
VALUES (22, 'كرمانشاه', 'كرمانشاه', POINT(34.31416700, 47.06500000), 2, 32);
INSERT INTO capsule.REGIONS
VALUES (23, 'کهگیلویه و بویراحمد', 'کهگیلویه و بویراحمد', POINT(30.65094790, 51.60525000), 2, 32);
INSERT INTO capsule.REGIONS
VALUES (24, 'گلستان', 'گلستان', POINT(37.28981230, 55.13758340), 2, 32);
INSERT INTO capsule.REGIONS
VALUES (25, 'گیلان', 'گیلان', POINT(37.11716170, 49.52799960), 2, 32);
INSERT INTO capsule.REGIONS
VALUES (26, 'لرستان', 'لرستان', POINT(33.58183940, 48.39881860), 2, 32);
INSERT INTO capsule.REGIONS
VALUES (27, 'مازندران', 'مازندران', POINT(36.22623930, 52.53186040), 2, 32);
INSERT INTO capsule.REGIONS
VALUES (28, 'مركزی', 'مركزی', POINT(33.50932940, -92.39611900), 2, 32);
INSERT INTO capsule.REGIONS
VALUES (29, 'هرمزگان', 'هرمزگان', POINT(27.13872300, 55.13758340), 2, 32);
INSERT INTO capsule.REGIONS
VALUES (30, 'همدان', 'همدان', POINT(34.76079990, 48.39881860), 2, 32);
INSERT INTO capsule.REGIONS
VALUES (31, 'یزد', 'یزد', POINT(32.10063870, 54.43421380), 2, 32);

ALTER TABLE capsule.REGIONS
    ENABLE TRIGGER ALL;

INSERT INTO capsule.REGIONS (ID, NAME, slug, coordinate, type, parent_id)
VALUES (1, 'ایران', 'ایران', 0x00000000010100000092cb0381e3474040c7bc0a00c0334b40, 1, NULL),
       (2, 'آذربایجان شرقی', 'آذربایجان-شرقی', 0x000000000101000000f784364aa8f3424022f719bc54224740, 2, 1),
       (3, 'آذربایجان غربی', 'آذربایجان-غربی', 0x000000000101000000163da6a43dba42400000000000804640, 2, 1),
       (4, 'اردبیل', 'اردبیل', 0x0000000001010000004fecfc361f3e43401b24e93f10f24740, 2, 1),
       (5, 'اصفهان', 'اصفهان', 0x000000000101000000f7c77bd5ca53404006a62e7480d54940, 2, 1),
       (6, 'البرز', 'البرز', 0x000000000101000000f5e450757eff414096b95400e7764940, 2, 1),
       (7, 'ایلام', 'ایلام', 0x000000000101000000fb33cd85dba54040946de00ed4554740, 2, 1),
       (8, 'بوشهر', 'بوشهر', 0x000000000101000000d328c9df62ec3c40b939950c00694940, 2, 1),
       (9, 'تهران', 'تهران', 0x00000000010100000064b14d2a1ad941403430f2b226b64940, 2, 1),
       (10, 'چهارمحال و بختیاری', 'چهارمحال-و-بختیاری', 0x0000000001010000007e3c4f9720f63f407b78e0ad3d6c4940, 2, 1),
       (11, 'خراسان جنوبی', 'خراسان-جنوبی', 0x000000000101000000df09078c3f4240406f04f3a1558d4d40, 2, 1),
       (12, 'خراسان رضوی', 'خراسان-رضوی', 0x000000000101000000ab6e3f2a0f8d41406f04f3a1558d4d40, 2, 1),
       (13, 'خراسان شمالی', 'خراسان-شمالی', 0x0000000001010000007a617ce24abc4240283cb203f88c4c40, 2, 1),
       (14, 'خوزستان', 'خوزستان', 0x000000000101000000971128ac9e6f3f4057772cb649854840, 2, 1),
       (15, 'زنجان', 'زنجان', 0x0000000001010000008e03af963b404240ac04e67c0c334840, 2, 1),
       (16, 'سمنان', 'سمنان', 0x000000000101000000db6ad619df9c4140673c5b5194374b40, 2, 1),
       (17, 'سیستان و بلوچستان', 'سیستان-و-بلوچستان', 0x000000000101000000fb2fc676ad873b40d607ed30814a4e40, 2, 1),
       (18, 'فارس', 'فارس', 0x00000000010100000060d09dbbb81a3d40c30e63d2df854a40, 2, 1),
       (19, 'قزوین', 'قزوین', 0x000000000101000000d59e48e6470b4240b90265ae67ed4840, 2, 1),
       (20, 'قم', 'قم', 0x0000000001010000007de1dfb1e95141401ad93add1e704940, 2, 1),
       (21, 'كردستان', 'كردستان', 0x000000000101000000d53cec2a49fa4140386744696f914740, 2, 1),
       (22, 'كرمان', 'كرمان', 0x0000000001010000004d987a27b0483e40fcd9d9a1ab8a4c40, 2, 1),
       (23, 'كرمانشاه', 'كرمانشاه', 0x000000000101000000c03dcf9f36284140b81e85eb51884740, 2, 1),
       (24, 'کهگیلویه و بویراحمد', 'کهگیلویه-و-بویراحمد', 0x0000000001010000005ee68585a4a63e40b6f3fdd478cd4940, 2, 1),
       (25, 'گلستان', 'گلستان', 0x000000000101000000413dc79118a542407abc35559c914b40, 2, 1),
       (26, 'گیلان', 'گیلان', 0x00000000010100000000ec9227ff8e42408a26ab7d95c34840, 2, 1),
       (27, 'لرستان', 'لرستان', 0x0000000001010000001b43a5b679ca4040ac04e67c0c334840, 2, 1),
       (28, 'مازندران', 'مازندران', 0x000000000101000000f448cd68f51c4240cc04680014444a40, 2, 1),
       (29, 'مركزی', 'مركزی', 0x00000000010100000016f2adb431c14040c09481035a1957c0, 2, 1),
       (30, 'هرمزگان', 'هرمزگان', 0x000000000101000000f833bc5983233b407abc35559c914b40, 2, 1),
       (31, 'همدان', 'همدان', 0x00000000010100000069a620e461614140ac04e67c0c334840, 2, 1),
       (32, 'یزد', 'یزد', 0x000000000101000000219b9abae10c4040673c5b5194374b40, 2, 1),
       (33, 'آذرشهر', 'آذرشهر', 0x000000000101000000a725564623e142407dcc07043afd4640, 3, 2),
       (34, 'اسکو', 'اسکو', 0x0000000001010000007dcc07043af542408236397cd20f4740, 3, 2),
       (35, 'اهر', 'اهر', 0x0000000001010000001c959ba8a53e4340d97745f0bf884740, 3, 2),
       (36, 'بستان آباد', 'بستان-آباد', 0x000000000101000000cdccccccccec4240bbd6dea7aa6a4740, 3, 2),
       (37, 'بناب', 'بناب', 0x0000000001010000002ec6c03a8eab424012f92ea52e074740, 3, 2),
       (38, 'تبریز', 'تبریز', 0x000000000101000000795c548b880843406666666666264740, 3, 2),
       (39, 'جلفا', 'جلفا', 0x000000000101000000fb928d075b784340691ec022bfd04640, 3, 2),
       (40, 'چار اویماق', 'چار-اویماق', 0x00000000010100000049caccbba090424017c15a1025834740, 3, 2),
       (41, 'سراب', 'سراب', 0x000000000101000000b0ff3a376df84240d5eb1681b1c44740, 3, 2),
       (42, 'شبستر', 'شبستر', 0x0000000001010000001a18795913174340952c27a1f4d94640, 3, 2),
       (43, 'عجبشیر', 'عجبشیر', 0x00000000010100000085eb51b81ebd4240cb14731074f24640, 3, 2),
       (44, 'کلیبر', 'کلیبر', 0x0000000001010000000adae4f0496f43409a9658198d844740, 3, 2),
       (45, 'مراغه', 'مراغه', 0x0000000001010000005ad76839d0b1424066666666661e4740, 3, 2),
       (46, 'مرند', 'مرند', 0x000000000101000000a0fcdd3b6a36434081e9b46e83e24640, 3, 2),
       (47, 'ملکان', 'ملکان', 0x000000000101000000713d0ad7a3924240d3ca733392154740, 3, 2),
       (48, 'میانه', 'میانه', 0x000000000101000000317e1af7e6b54240ec51b81e85db4740, 3, 2),
       (49, 'ورزقان', 'ورزقان', 0x0000000001010000009e060c923e4143401e882cd2c4534740, 3, 2),
       (50, 'هریس', 'هریس', 0x00000000010100000078ee3d5c72c63d4054c4e924dbd357c0, 3, 2),
       (51, 'هشترود', 'هشترود', 0x000000000101000000c85f5ad427bd42405e471cb281864740, 3, 2),
       (52, 'ارومیه', 'ارومیه', 0x0000000001010000001a18795913c74240e17a14ae47894640, 3, 3),
       (53, 'اشنویه', 'اشنویه', 0x0000000001010000004277499c158542400c8ffd2c968c4640, 3, 3),
       (54, 'بوکان', 'بوکان', 0x000000000101000000fd4ae7c3b342424040bfefdfbc1a4740, 3, 3),
       (55, 'پیرانشهر', 'پیرانشهر', 0x000000000101000000a3737e8ae358424012f6ed2422924640, 3, 3),
       (56, 'تکاب', 'تکاب', 0x0000000001010000002b14e97e4e3342405e471cb2818e4740, 3, 3),
       (57, 'چالدران', 'چالدران', 0x000000000101000000f422c962518843404f6e803e36314640, 3, 3),
       (58, 'خوی', 'خوی', 0x000000000101000000a9da6e826f4643400f441669e2794640, 3, 3),
       (59, 'سردشت', 'سردشت', 0x000000000101000000e7e44526e013424003b5183c4cbd4640, 3, 3),
       (60, 'سلماس', 'سلماس', 0x0000000001010000009e060c923e194340952c27a1f4614640, 3, 3),
       (61, 'شاهین دژ', 'شاهین-دژ', 0x000000000101000000dfc2baf1ee564240eb54f99e91484740, 3, 3),
       (62, 'ماکو', 'ماکو', 0x000000000101000000399d64abcba5434012f6ed2422424640, 3, 3),
       (63, 'مهاباد', 'مهاباد', 0x0000000001010000001f82aad1ab614240d2393fc571dc4640, 3, 3),
       (64, 'میاندوآب', 'میاندوآب', 0x000000000101000000d7a6b1bd167c4240c85f5ad4270d4740, 3, 3),
       (65, 'نقده', 'نقده', 0x0000000001010000004d4bac8c467a42401f82aad1abb14640, 3, 3),
       (66, 'اردبیل', 'اردبیل', 0x0000000001010000004fecfc361f3e43401b24e93f10f24740, 3, 4),
       (67, 'بیله سوار', 'بیله-سوار', 0x0000000001010000006d3997e2aaad43407973b8567bf94740, 3, 4),
       (68, 'پارس آباد', 'پارس-آباد', 0x00000000010100000073f56393fcd243403d0ad7a370f54740, 3, 4),
       (69, 'خلخال', 'خلخال', 0x000000000101000000556d37c137cf42402b14e97e4e434840, 3, 4),
       (70, 'کوثر', 'کوثر', 0x0000000001010000006c2d82b520de3f4007d968ef422b4b40, 3, 4),
       (71, 'گرمی', 'گرمی', 0x000000000101000000dd8431d8c38443401bf741f1bef64740, 3, 4),
       (72, 'مشگین', 'مشگین', 0x000000000101000000f8dd74cb0e3343400adae4f049d74740, 3, 4),
       (73, 'نمین', 'نمین', 0x000000000101000000999cda19a636434073f22213f03d4840, 3, 4),
       (74, 'نیر', 'نیر', 0x000000000101000000d2393fc5710443408236397cd2ff4740, 3, 4),
       (75, 'آران و بیدگل', 'آران-و-بیدگل', 0x000000000101000000d236fe4465074140b6662b2ff9bd4940, 3, 5),
       (76, 'اردستان', 'اردستان', 0x0000000001010000003b55be6724b040400adae4f0492f4a40, 3, 5),
       (77, 'اصفهان', 'اصفهان', 0x000000000101000000f7c77bd5ca53404006a62e7480d54940, 3, 5),
       (78, 'برخوار و میمه', 'برخوار-و-میمه', 0x000000000101000000bbd6dea7aa6a40403333333333e34940, 3, 5),
       (79, 'تیران و کرون', 'تیران-و-کرون', 0x00000000010100000018b0e42a165a40409256218a6e914940, 3, 5),
       (80, 'چادگان', 'چادگان', 0x00000000010100000002b859bc58624040f373435376504940, 3, 5),
       (81, 'خمینی شهر', 'خمینی-شهر', 0x000000000101000000dc0da2b5a2594040fd4ae7c3b3c24940, 3, 5),
       (82, 'خوانسار', 'خوانسار', 0x000000000101000000e277d32d3b9c4040b81e85eb51284940, 3, 5),
       (83, 'سمیرم', 'سمیرم', 0x00000000010100000064833b061a663f4022382ee3a6c84940, 3, 5),
       (84, 'شاهین شهر و میمه', 'شاهین-شهر-و-میمه', 0x0000000001010000008244c99a479040401658b6c5ebc14940, 3, 5),
       (85, 'شهر رضا', 'شهر-رضا', 0x000000000101000000a4ef24d86e254240d879c0e1aac84d40, 3, 5),
       (86, 'دهاقان', 'دهاقان', 0x000000000101000000713d0ad7a3f03f40be88b663ead24940, 3, 5),
       (87, 'فریدن', 'فریدن', 0x0000000001010000002d76a0f3bf824040646d9dc948274940, 3, 5),
       (88, 'فریدون شهر', 'فریدون-شهر', 0x000000000101000000f373435376784040ca17b490800f4940, 3, 5),
       (89, 'فلاورجان', 'فلاورجان', 0x0000000001010000001a187959134740409e060c923ec14940, 3, 5),
       (90, 'کاشان', 'کاشان', 0x000000000101000000d13131a715fe40400f0bb5a679b44940, 3, 5),
       (91, 'گلپایگان', 'گلپایگان', 0x0000000001010000008d0dddec0fba4040c5ad8218e8244940, 3, 5),
       (92, 'لنجان', 'لنجان', 0x0000000001010000007986ba59cd3c40409a7f4f070da74940, 3, 5),
       (93, 'مبارکه', 'مبارکه', 0x000000000101000000da588979562c4040eb54f99e91c04940, 3, 5),
       (94, 'نائین', 'نائین', 0x00000000010100000002f04fa9126e4040fe840e153e8b4a40, 3, 5),
       (95, 'نجف آباد', 'نجف-آباد', 0x00000000010100000012de793df3504040c91a9a571aaf4940, 3, 5),
       (96, 'نطنز', 'نطنز', 0x000000000101000000927a4fe5b4c1404003b5183c4cf54940, 3, 5),
       (97, 'ساوجبلاق', 'ساوجبلاق', 0x00000000010100000079b537537c3043409c2dc5faabbf4740, 3, 6),
       (98, 'کرج', 'کرج', 0x00000000010100000039036dbc85eb4140927deb1e34784940, 3, 6),
       (99, 'نظرآباد', 'نظرآباد', 0x0000000001010000000f441669e2f94140f6285c8fc24d4940, 3, 6),
       (100, 'طالقان', 'طالقان', 0x000000000101000000748291f2eea24040531d177a69194a40, 3, 6),
       (101, 'آبدانان', 'آبدانان', 0x000000000101000000d7a3703d0a7f4040b3b45373b9b54740, 3, 7),
       (102, 'ایلام', 'ایلام', 0x000000000101000000fb33cd85dba54040946de00ed4554740, 3, 7),
       (103, 'ایوان', 'ایوان', 0x0000000001010000000f441669e2e94040056d72f8a4274740, 3, 7),
       (104, 'دره شهر', 'دره-شهر', 0x0000000001010000000f441669e29140403b55be6724b04740, 3, 7),
       (105, 'دهلران', 'دهلران', 0x000000000101000000317bd976da5840404d4bac8c46a24740, 3, 7),
       (106, 'شیران و چرداول', 'شیران-و-چرداول', 0x000000000101000000d1692794cfd8404012eb9e86b95f4740, 3, 7),
       (107, 'مهران', 'مهران', 0x000000000101000000056d72f8a48f40404277499c15154740, 3, 7),
       (108, 'بوشهر', 'بوشهر', 0x000000000101000000d328c9df62ec3c40b939950c00694940, 3, 8),
       (109, 'تنگستان', 'تنگستان', 0x0000000001010000004ac91759d7fb3c4054a06010a26a4940, 3, 8),
       (110, 'جم', 'جم', 0x00000000010100000029594e42e9d33b40cccf0d4dd9294a40, 3, 8),
       (111, 'دشتستان', 'دشتستان', 0x00000000010100000025ecdb4944443d40ac8f87bebb9b4940, 3, 8),
       (112, 'دشتی', 'دشتی', 0x000000000101000000705e9cf86ae44140d4f60a66a7b74940, 3, 8),
       (113, 'دیر', 'دیر', 0x000000000101000000d7a3703d0ad73b404374081c09f84940, 3, 8),
       (114, 'دیلم', 'دیلم', 0x0000000001010000000052407f461e3e400db1b096f11c4940, 3, 8),
       (115, 'کنگان', 'کنگان', 0x000000000101000000a5d2f47e48d63b40c645ff1543084a40, 3, 8),
       (116, 'گناوه', 'گناوه', 0x00000000010100000025ecdb4944943d4085ee92382b424940, 3, 8),
       (117, 'اسلام شهر', 'اسلام-شهر', 0x00000000010100000009c556d0b4c54140b6c5ebb0789d4940, 3, 9),
       (118, 'پاکدشت', 'پاکدشت', 0x000000000101000000ba241818c3bb4140894160e5d0d74940, 3, 9),
       (119, 'تهران', 'تهران', 0x00000000010100000064b14d2a1ad941403430f2b226b64940, 3, 9),
       (120, 'دماوند', 'دماوند', 0x000000000101000000fa4097152ff941405e15037f53104a40, 3, 9),
       (121, 'رباط کریم', 'رباط-کریم', 0x0000000001010000006bd3d85e0bbe4140056a3178988a4940, 3, 9),
       (122, 'ری', 'ری', 0x0000000001010000007d89c278172c48409753026292ca59c0, 3, 9),
       (123, 'شمیرانات', 'شمیرانات', 0x00000000010100000079d388f436fa414022b7706ab1cc4940, 3, 9),
       (124, 'شهریار', 'شهریار', 0x000000000101000000d2393fc571d441405000c5c892874940, 3, 9),
       (125, 'فیروزکوه', 'فیروزکوه', 0x000000000101000000105a0f5f26b841405e6743fe99674e40, 3, 9),
       (126, 'ورامین', 'ورامین', 0x00000000010100000001eabc7ca1a94140c1413168d7d24940, 3, 9),
       (127, 'اردل', 'اردل', 0x0000000001010000007a17efc7edff3f40d5eb1681b1544940, 3, 10),
       (128, 'بروجن', 'بروجن', 0x0000000001010000005d8c81751cf73f408a58c4b0c3a44940, 3, 10),
       (129, 'شهرکرد', 'شهرکرد', 0x0000000001010000001f82aad1ab294040999cda19a66e4940, 3, 10),
       (130, 'فارسان', 'فارسان', 0x000000000101000000414ef3e90c214040440aaf6e06494940, 3, 10),
       (131, 'کوهرنگ', 'کوهرنگ', 0x00000000010100000093f6aba5254740404c7ca477e0d64940, 3, 10),
       (132, 'لردگان', 'لردگان', 0x00000000010100000048de3994a1823f4085ee92382b6a4940, 3, 10),
       (133, 'بیرجند', 'بیرجند', 0x0000000001010000006857c62bb56e4040e26712abf59c4d40, 3, 11),
       (134, 'درمیان', 'درمیان', 0x00000000010100000044c18c2958844040b55ec2572a0f4e40, 3, 11),
       (135, 'سرایان', 'سرایان', 0x000000000101000000f1bbe9961dee40408333f8fbc5424d40, 3, 11),
       (136, 'سر بیشه', 'سر-بیشه', 0x0000000001010000001f82aad1ab494040a62897c62fe64d40, 3, 11),
       (137, 'فردوس', 'فردوس', 0x000000000101000000452c62d8610241406bd3d85e0b164d40, 3, 11),
       (138, 'قائن', 'قائن', 0x0000000001010000008d0a9c6c03dd4040c2f869dc9b974d40, 3, 11),
       (139, 'نهبندان', 'نهبندان', 0x00000000010100000070438cd7bc8a3f4092770e65a8044e40, 3, 11),
       (140, 'برد سکن', 'برد-سکن', 0x000000000101000000d95bcaf962a14140191bbad91ffc4c40, 3, 12),
       (141, 'بجستان', 'بجستان', 0x000000000101000000cf81e50819424140c2f869dc9b174d40, 3, 12),
       (142, 'تایباد', 'تایباد', 0x0000000001010000001f85eb51b85e4140b91b446b45634e40, 3, 12),
       (143, 'تحت جلگه', 'تحت-جلگه', 0x000000000101000000556d37c1379f414048e17a14ae4f4e40, 3, 12),
       (144, 'تربت جام', 'تربت-جام', 0x000000000101000000556d37c1379f414048e17a14ae4f4e40, 3, 12),
       (145, 'تربت حیدریه', 'تربت-حیدریه', 0x000000000101000000f8dd74cb0ea34140d7a6b1bd169c4d40, 3, 12),
       (146, 'چناران', 'چناران', 0x00000000010100000048de3994a1524240ca17b490808f4d40, 3, 12),
       (147, 'جغتای', 'جغتای', 0x0000000001010000004dd1dbe9184a4240e0a128d027a04c40, 3, 12),
       (148, 'جوین', 'جوین', 0x000000000101000000ebf70ec86f514240f1530bdb05c14c40, 3, 12),
       (149, 'خلیل آباد', 'خلیل-آباد', 0x000000000101000000691ec022bfa0414092770e65a8244d40, 3, 12),
       (150, 'خواف', 'خواف', 0x0000000001010000001763601dc74941404a99d4d006124e40, 3, 12),
       (151, 'درگز', 'درگز', 0x000000000101000000a3737e8ae3b842407b116dc7d48d4d40, 3, 12),
       (152, 'رشتخوار', 'رشتخوار', 0x0000000001010000008a58c4b0c37c41408236397cd2cf4d40, 3, 12),
       (153, 'زاوه', 'زاوه', 0x0000000001010000003e14bb6c2aa34140163bd0f9dfbb4d40, 3, 12),
       (154, 'سبزوار', 'سبزوار', 0x000000000101000000cb96f6178b1b4240f7c2ae377bd54c40, 3, 12),
       (155, 'سرخس', 'سرخس', 0x000000000101000000f6285c8fc24542409f03cb1132944e40, 3, 12),
       (156, 'فریمان', 'فریمان', 0x0000000001010000003d0d18247dda4140cdccccccccec4d40, 3, 12),
       (157, 'قوچان', 'قوچان', 0x000000000101000000785f950b958d42405c92037635414d40, 3, 12),
       (158, 'طرقبه شاندیز', 'طرقبه-شاندیز', 0x00000000010100000069af99d76c2d4240569bff571db84d40, 3, 12),
       (159, 'کاشمر', 'کاشمر', 0x0000000001010000005e471cb2819e4140713ac956973b4d40, 3, 12),
       (160, 'کلات', 'کلات', 0x000000000101000000d95bcaf96219414070404b57b0454d40, 3, 12),
       (161, 'گناباد', 'گناباد', 0x000000000101000000c85f5ad4272d4140ca17b49080574d40, 3, 12),
       (162, 'مشهد', 'مشهد', 0x000000000101000000a42b22d456214240e99216d3f1ce4d40, 3, 12),
       (163, 'مه ولات', 'مه-ولات', 0x0000000001010000009f002fd8b282414014d10a6712644d40, 3, 12),
       (164, 'نیشابور', 'نیشابور', 0x000000000101000000ef01ba2f671b4240d28f8653e6654d40, 3, 12),
       (165, 'اسفراین', 'اسفراین', 0x0000000001010000001763601dc7894240e17a14ae47c14c40, 3, 13),
       (166, 'بجنورد', 'بجنورد', 0x000000000101000000cdccccccccbc4240bbd6dea7aaaa4c40, 3, 13),
       (167, 'جاجرم', 'جاجرم', 0x0000000001010000009a99999999794240713d0ad7a3304c40, 3, 13),
       (168, 'شیروان', 'شیروان', 0x00000000010100000087edddd561b442407c952133bcf64c40, 3, 13),
       (169, 'فاروج', 'فاروج', 0x000000000101000000785f950b959d4240213a048e041c4d40, 3, 13),
       (170, 'مانه و سملقان', 'مانه-و-سملقان', 0x000000000101000000d4788e6dbed442407c48f8dedf5e4c40, 3, 13),
       (171, 'آبادان', 'آبادان', 0x00000000010100000018080264e8583e401151f1248e254840, 3, 14),
       (172, 'امیدیه', 'امیدیه', 0x0000000001010000000f4757e9eebe3e40fd4ae7c3b3da4840, 3, 14),
       (173, 'اندیمشک', 'اندیمشک', 0x0000000001010000007b14ae47e13a4040b6662b2ff92d4840, 3, 14),
       (174, 'اهواز', 'اهواز', 0x000000000101000000606d31e47d513f40034b64d5d6554840, 3, 14),
       (175, 'ایذه', 'ایذه', 0x0000000001010000000667f0f78bd53f40942f682101ef4840, 3, 14),
       (176, 'باغ ملک', 'باغ-ملک', 0x0000000001010000005c07623486324040e37ed12f5bcc4940, 3, 14),
       (177, 'بندرماهشهر', 'بندرماهشهر', 0x0000000001010000001a187959138f3e40676325e659994840, 3, 14),
       (178, 'بهبهان', 'بهبهان', 0x000000000101000000a8e0f08288983e40dfc2baf1ee1e4940, 3, 14),
       (179, 'خرمشهر', 'خرمشهر', 0x0000000001010000001df68c8ef56c3e40c748f60835184840, 3, 14),
       (180, 'دزفول', 'دزفول', 0x000000000101000000d8a9a8b008314040844df80038364840, 3, 14),
       (181, 'دشت آزادگان', 'دشت-آزادگان', 0x0000000001010000002a560dc2dc8e3f40cf84268925174840, 3, 14),
       (182, 'رامشیر', 'رامشیر', 0x000000000101000000fe17be7449e53e404d18288469b44840, 3, 14),
       (183, 'رامهرمز', 'رامهرمز', 0x00000000010100000048e17a14ae473f40c040102043cd4840, 3, 14),
       (184, 'شادگان', 'شادگان', 0x000000000101000000e17d552e54a63e404277499c15554840, 3, 14),
       (185, 'شوش', 'شوش', 0x000000000101000000317bd976da18404012f92ea52e1f4840, 3, 14),
       (186, 'شوشتر', 'شوشتر', 0x0000000001010000007b116dc7d4054040fe47a643a76d4840, 3, 14),
       (187, 'گتوند', 'گتوند', 0x0000000001010000007ec9c6832d204040f373435376684840, 3, 14),
       (188, 'لالی', 'لالی', 0x000000000101000000cf81e508192a4040dfc5fb71fb8b4840, 3, 14),
       (189, 'مسجد سلیمان', 'مسجد-سلیمان', 0x0000000001010000008a558330b7ef3f409c4eb2d5e5a64840, 3, 14),
       (190, 'هندیجان', 'هندیجان', 0x000000000101000000572250fd833c3e40ae4a22fb20db4840, 3, 14),
       (191, 'ابهر', 'ابهر', 0x0000000001010000008333f8fbc512424029594e42e99b4840, 3, 15),
       (192, 'ایجرود', 'ایجرود', 0x000000000101000000f00c648742354240b708313c9b1f4840, 3, 15),
       (193, 'خدابنده', 'خدابنده', 0x000000000101000000dc10e335af0e424026a77686a94b4840, 3, 15),
       (194, 'خرمدره', 'خرمدره', 0x000000000101000000d7a02fbdfd1942407a17efc7ed974840, 3, 15),
       (195, 'زنجان', 'زنجان', 0x0000000001010000008e03af963b404240ac04e67c0c334840, 3, 15),
       (196, 'طارم', 'طارم', 0x00000000010100000082c24593302e3c401981673167df4b40, 3, 15),
       (197, 'ماه نشان', 'ماه-نشان', 0x0000000001010000000adae4f0495f4240ae47e17a14d64740, 3, 15),
       (198, 'دامغان', 'دامغان', 0x00000000010100000035eb8cef8b1542409a9658198d2c4b40, 3, 16),
       (199, 'سمنان', 'سمنان', 0x000000000101000000db6ad619df9c4140673c5b5194374b40, 3, 16),
       (200, 'شاهرود', 'شاهرود', 0x000000000101000000c3f2e7db823542404a969350fa7c4b40, 3, 16),
       (201, 'گرمسار', 'گرمسار', 0x0000000001010000009c51f355f29b4140e4326e6aa02b4a40, 3, 16),
       (202, 'مهدی شهر', 'مهدی-شهر', 0x0000000001010000009a99999999d94140cdccccccccac4a40, 3, 16),
       (203, 'ایرانشهر', 'ایرانشهر', 0x000000000101000000a4703d0ad7333b40056d72f8a4574e40, 3, 17),
       (204, 'چابهار', 'چابهار', 0x00000000010100000070438cd7bc4a394090bfb4a84f524e40, 3, 17),
       (205, 'خاش', 'خاش', 0x000000000101000000581f0f7d77373c409a99999999994e40, 3, 17),
       (206, 'دلگان', 'دلگان', 0x000000000101000000961c1c91949b3b40b6604e756dbc4d40, 3, 17),
       (207, 'زابل', 'زابل', 0x0000000001010000004d4eed0c53073f403b55be6724c04e40, 3, 17),
       (208, 'زاهدان', 'زاهدان', 0x0000000001010000001a187959137f3d40a9da6e826f6e4e40, 3, 17),
       (209, 'زهک', 'زهک', 0x0000000001010000001041d5e8d5e43e401a18795913d74e40, 3, 17),
       (210, 'سراوان', 'سراوان', 0x0000000001010000000f4757e9ee5e3b408333f8fbc52a4f40, 3, 17),
       (211, 'سرباز', 'سرباز', 0x000000000101000000d13c80457ea13a40ee06d15ad1a04e40, 3, 17),
       (212, 'کنارک', 'کنارک', 0x000000000101000000e277d32d3b5c3940ae4a22fb20334e40, 3, 17),
       (213, 'نیکشهر', 'نیکشهر', 0x00000000010100000084cb74f9206b3a40937f1c83041b4e40, 3, 17),
       (214, 'آباده', 'آباده', 0x000000000101000000191efb592c293f40b91b446b45534a40, 3, 18),
       (215, 'ارسنجان', 'ارسنجان', 0x0000000001010000009a99999999e93d40ca17b49080a74a40, 3, 18),
       (216, 'استهبان', 'استهبان', 0x000000000101000000817b9e3f6d203d40fb95ce8767054b40, 3, 18),
       (217, 'اقلید', 'اقلید', 0x000000000101000000f1bbe9961de63e40081f4ab4e4574a40, 3, 18),
       (218, 'بوانات', 'بوانات', 0x000000000101000000e3bd0fac4f7c3e407edc2340f2cb4a40, 3, 18),
       (219, 'پاسارگاد', 'پاسارگاد', 0x0000000001010000001288d7f50b343e4017d4b7cce9964a40, 3, 18),
       (220, 'جهرم', 'جهرم', 0x0000000001010000000000000000803c40cdc98b4cc0c74a40, 3, 18),
       (221, 'خرم بید', 'خرم-بید', 0x000000000101000000a643a7e7dd554040be0c6bcfd1d24940, 3, 18),
       (222, 'خنج', 'خنج', 0x0000000001010000009f03cb1132e43b40c2f869dc9bb74a40, 3, 18),
       (223, 'داراب', 'داراب', 0x000000000101000000666ce8667fc03c4070404b57b0454b40, 3, 18),
       (224, 'زرین دشت', 'زرین-دشت', 0x0000000001010000003ec7ecd1c05a3c40e59f747d7a354b40, 3, 18),
       (225, 'سپیدان', 'سپیدان', 0x000000000101000000955bff53163e3e4002b9c49107ff4940, 3, 18),
       (226, 'شیراز', 'شیراز', 0x000000000101000000425486167e973d40f6dd639fb64a4a40, 3, 18),
       (227, 'فراشبند', 'فراشبند', 0x0000000001010000001a18795913df3c40ac8f87bebb0b4a40, 3, 18),
       (228, 'فسا', 'فسا', 0x000000000101000000f0c16b9736f03c4073f56393fcd24a40, 3, 18),
       (229, 'فیروزآباد', 'فیروزآباد', 0x0000000001010000004374081c09d83c40213d450e11494a40, 3, 18),
       (230, 'قیر و کارزین', 'قیر-و-کارزین', 0x00000000010100000020ef552b136e3c4095f1ef332e8c4a40, 3, 18),
       (231, 'کازرون', 'کازرون', 0x00000000010100000014b4c9e1939e3d40ac8f87bebbd34940, 3, 18),
       (232, 'لارستان', 'لارستان', 0x00000000010100000024f25d4a5dae3b402ec6c03a8e2b4b40, 3, 18),
       (233, 'لامرد', 'لامرد', 0x0000000001010000003a668f06a6573b406c521be615974a40, 3, 18),
       (234, 'مرودشت', 'مرودشت', 0x000000000101000000103e9468c9df3d401f85eb51b8664a40, 3, 18),
       (235, 'ممسنی', 'ممسنی', 0x0000000001010000001e882cd2c4f53f408070f4e791414940, 3, 18),
       (236, 'مهر', 'مهر', 0x000000000101000000394d5590558e3b40e6cc76853e714a40, 3, 18),
       (237, 'نی ریز', 'نی-ریز', 0x000000000101000000be88b663ea323d40952c27a1f4294b40, 3, 18),
       (238, 'آبیک', 'آبیک', 0x00000000010100000085eb51b81e054240dfc5fb71fb434940, 3, 19),
       (239, 'البرز', 'البرز', 0x000000000101000000f5e450757eff414096b95400e7764940, 3, 19),
       (240, 'بوئین زهرا', 'بوئین-زهرا', 0x00000000010100000085ee92382be24140d236fe4465074940, 3, 19),
       (241, 'تاکستان', 'تاکستان', 0x000000000101000000e6e786a6ec084240213d450e11d94840, 3, 19),
       (242, 'قزوین', 'قزوین', 0x000000000101000000d59e48e6470b4240b90265ae67ed4840, 3, 19),
       (243, 'قم', 'قم', 0x0000000001010000007de1dfb1e95141401ad93add1e704940, 3, 20),
       (244, 'بانه', 'بانه', 0x000000000101000000fa241c1fd2ff414038eede9bf0f04640, 3, 21),
       (245, 'بیجار', 'بیجار', 0x000000000101000000f1bbe9961d5e4040ac8f87bebbbb4d40, 3, 21),
       (246, 'دیواندره', 'دیواندره', 0x0000000001010000004a969350faf44140f8dd74cb0e834740, 3, 21),
       (247, 'سروآباد', 'سروآباد', 0x0000000001010000000000000000a8414051bb5f05f82e4740, 3, 21),
       (248, 'سقز', 'سقز', 0x0000000001010000000d8cbcac891f4240cf81e50819224740, 3, 21),
       (249, 'سنندج', 'سنندج', 0x0000000001010000000ab4853133a941401e7c17a53a7e4740, 3, 21),
       (250, 'قروه', 'قروه', 0x0000000001010000006c1beb877d95414050d147cfe3e64740, 3, 21),
       (251, 'کامیاران', 'کامیاران', 0x0000000001010000007b116dc7d4654140cdc98b4cc0774740, 3, 21),
       (252, 'مریوان', 'مریوان', 0x0000000001010000006669a7e672c34140e42f2dea93164740, 3, 21),
       (253, 'بافت', 'بافت', 0x000000000101000000f722da8ea93b3d404277499c154d4c40, 3, 22),
       (254, 'بردسیر', 'بردسیر', 0x0000000001010000003d0ad7a370ed3d409e060c923e494c40, 3, 22),
       (255, 'بم', 'بم', 0x000000000101000000f0be2a172a1b3d4070404b57b02d4d40, 3, 22),
       (256, 'جیرفت', 'جیرفت', 0x000000000101000000c31f8f2ad4ac3c40a8c244285bde4c40, 3, 22),
       (257, 'راور', 'راور', 0x000000000101000000af415f7afb433f405d8c81751c674c40, 3, 22),
       (258, 'رفسنجان', 'رفسنجان', 0x000000000101000000c85c19541b683e40556d37c137ff4b40, 3, 22),
       (259, 'رودبار جنوب', 'رودبار-جنوب', 0x0000000001010000001f98480e7d694240a5b50eb33cb64840, 3, 22),
       (260, 'زرند', 'زرند', 0x00000000010100000086e8103812d03e407ec9c6832d484c40, 3, 22),
       (261, 'سیرجان', 'سیرجان', 0x0000000001010000001cbc653d6b753d400e6f319af0d54b40, 3, 22),
       (262, 'شهر بابک', 'شهر-بابک', 0x000000000101000000399d64abcb1d3e4012f92ea52e8f4b40, 3, 22),
       (263, 'عنبرآباد', 'عنبرآباد', 0x0000000001010000007716180d747a3c40d45ba8a1b2eb4c40, 3, 22),
       (264, 'قلعه گنج', 'قلعه-گنج', 0x0000000001010000006bd3d85e0b863b40ab92c83ec8f04c40, 3, 22),
       (265, 'کرمان', 'کرمان', 0x000000000101000000d9c4138b297c3d4064def4786bd24c40, 3, 22),
       (266, 'کوهبنان', 'کوهبنان', 0x000000000101000000af44a0fa07693f405c8fc2f528244c40, 3, 22),
       (267, 'کهنوج', 'کهنوج', 0x000000000101000000b2a60de25ef23b404348cca266da4c40, 3, 22),
       (268, 'منوجان', 'منوجان', 0x000000000101000000a9e9697693723b40f798a322a9c04c40, 3, 22),
       (269, 'اسلام آباد غرب', 'اسلام-آباد-غرب', 0x0000000001010000004e8bb0975cdd4040ff4f0f1bf6455240, 3, 23),
       (270, 'پاوه', 'پاوه', 0x00000000010100000035eb8cef8b854140bbd39d279e2d4740, 3, 23),
       (271, 'ثلاث باباجانی', 'ثلاث-باباجانی', 0x0000000001010000007cd7fbe82f5e4140d8cf07701f134740, 3, 23),
       (272, 'جوانرود', 'جوانرود', 0x00000000010100000097e13fdd40674140a1bb24ce8a3e4740, 3, 23),
       (273, 'دالاهو', 'دالاهو', 0x0000000001010000001ccd91955f244140942f6821011f4740, 3, 23),
       (274, 'روانسر', 'روانسر', 0x000000000101000000dbea28bd255b41401772b6ca5d534740, 3, 23),
       (275, 'سرپل ذهاب', 'سرپل-ذهاب', 0x000000000101000000b6696caf053b4140a9da6e826fee4640, 3, 23),
       (276, 'سنقر', 'سنقر', 0x00000000010100000097e4805d4d6441401041d5e8d5cc4740, 3, 23),
       (277, 'صحنه', 'صحنه', 0x000000000101000000bbd39d279e3d4140b0ff3a376dd84740, 3, 23),
       (278, 'قصر شیرین', 'قصر-شیرین', 0x000000000101000000d833df1c094241401fee8f9cf1c94640, 3, 23),
       (279, 'کرمانشاه', 'کرمانشاه', 0x0000000001010000009db17966933a4140946de00ed4554740, 3, 23),
       (280, 'کنگاور', 'کنگاور', 0x000000000101000000795c548b884041402ec6c03a8efb4740, 3, 23),
       (281, 'گیلان غرب', 'گیلان-غرب', 0x000000000101000000c7629b5434124140399d64abcbf54640, 3, 23),
       (282, 'هرسین', 'هرسین', 0x000000000101000000c665811bce2241401488e82164cd4740, 3, 23),
       (283, 'بویر احمد', 'بویر-احمد', 0x000000000101000000b323d5777eb93e407b78e0ad3d6c4940, 3, 24),
       (284, 'بهمئی', 'بهمئی', 0x0000000001010000000db21b6ca0f33e400000000000b84940, 3, 24),
       (285, 'دنا', 'دنا', 0x0000000001010000000db21b6ca0f33e400000000000b84940, 3, 24),
       (286, 'کهگیلویه', 'کهگیلویه', 0x0000000001010000009a99999999593e406666666666664940, 3, 24),
       (287, 'گچساران', 'گچساران', 0x0000000001010000009a99999999593e406666666666664940, 3, 24),
       (288, 'آزادشهر', 'آزادشهر', 0x000000000101000000ae4a22fb208b42402c11a8fe41964b40, 3, 25),
       (289, 'آق قلا', 'آق-قلا', 0x0000000001010000001763601dc78142400ad7a3703d3a4b40, 3, 25),
       (290, 'بندر گز', 'بندر-گز', 0x000000000101000000b745990d32634240e6e555421cf94a40, 3, 25),
       (291, 'بندر ترکمن', 'بندر-ترکمن', 0x000000000101000000f47002d369734240213d450e11094b40, 3, 25),
       (292, 'رامیان', 'رامیان', 0x0000000001010000008d0dddec0f8242408d0dddec0f924b40, 3, 25),
       (293, 'علی آباد', 'علی-آباد', 0x00000000010100000071e316f373274240995423aaa6a75240, 3, 25),
       (294, 'کرد کوی', 'کرد-کوی', 0x00000000010100000022aef776a6654240afcc5b751d0e4b40, 3, 25),
       (295, 'کلاله', 'کلاله', 0x000000000101000000691ec022bfb04240dfc2baf1eebe4b40, 3, 25),
       (296, 'گرگان', 'گرگان', 0x000000000101000000f24c1e053e6c42400b39002c3c384b40, 3, 25),
       (297, 'گنبد کاووس', 'گنبد-کاووس', 0x0000000001010000000000000000a04240fb95ce8767954b40, 3, 25),
       (298, 'مینو دشت', 'مینو-دشت', 0x00000000010100000003b5183c4c9d4240bd8bf7e3f6af4b40, 3, 25),
       (299, 'آستارا', 'آستارا', 0x000000000101000000dfc2baf1ee364340c2f869dc9b6f4840, 3, 26),
       (300, 'آستانه اشرفیه', 'آستانه-اشرفیه', 0x000000000101000000e536d03241a14240c2fc70ebc9f84840, 3, 26),
       (301, 'املش', 'املش', 0x000000000101000000531caca4ba8b4240daf11593ed174940, 3, 26),
       (302, 'بندر انزلی', 'بندر-انزلی', 0x00000000010100000033cd2a2279bc4240d2d336b4b7ba4840, 3, 26),
       (303, 'رشت', 'رشت', 0x0000000001010000009c51f355f2a3424048de3994a1ca4840, 3, 26),
       (304, 'رضوانشهر', 'رضوانشهر', 0x000000000101000000c6dcb5847cc642408371cba20b924840, 3, 26),
       (305, 'رودبار', 'رودبار', 0x0000000001010000001f98480e7d694240a5b50eb33cb64840, 3, 26),
       (306, 'رودسر', 'رودسر', 0x0000000001010000000b444fcaa4914240557f29a84d244940, 3, 26),
       (307, 'سیاهکل', 'سیاهکل', 0x0000000001010000002ec6c03a8e93424087a3ab7477ef4840, 3, 26),
       (308, 'شفت', 'شفت', 0x000000000101000000016c4084b8d043408a90ba9d7dbb53c0, 3, 26),
       (309, 'صومعه سرا', 'صومعه-سرا', 0x000000000101000000081f4ab4e4a742405c92037635a94840, 3, 26),
       (310, 'طوالش', 'طوالش', 0x0000000001010000000000000000804240955286600b364840, 3, 26),
       (311, 'فومن', 'فومن', 0x00000000010100000092770e65a89c42400000000000a84840, 3, 26),
       (312, 'لاهیجان', 'لاهیجان', 0x00000000010100000080812040869a424036e84b6f7f004940, 3, 26),
       (313, 'لنگرود', 'لنگرود', 0x0000000001010000005c9203763599424026a77686a9134940, 3, 26),
       (314, 'ماسال', 'ماسال', 0x000000000101000000676325e659ae4240f9d62c3cd4904840, 3, 26),
       (315, 'ازنا', 'ازنا', 0x00000000010100000002b859bc58ba404090bfb4a84fba4840, 3, 27),
       (316, 'الیگودرز', 'الیگودرز', 0x000000000101000000b91b446b45b34040295c8fc2f5d84840, 3, 27),
       (317, 'بروجرد', 'بروجرد', 0x00000000010100000094cd661f75f24040f4f928232e624840, 3, 27),
       (318, 'پلدختر', 'پلدختر', 0x00000000010100000026a77686a99340406e88f19a57db4740, 3, 27),
       (319, 'خرم آباد', 'خرم-آباد', 0x000000000101000000a9da6e826fbe404035eb8cef8b2d4840, 3, 27),
       (320, 'دورود', 'دورود', 0x000000000101000000c889c0a26cbf40404e39701816884840, 3, 27),
       (321, 'دلفان', 'دلفان', 0x0000000001010000005ce102756fc0404000cba54cc52d4840, 3, 27),
       (322, 'سلسله', 'سلسله', 0x0000000001010000008978ebfcdb054040f6b2edb435604140, 3, 27),
       (323, 'کوهدشت', 'کوهدشت', 0x00000000010100000014ae47e17ac44040785f950b95cd4740, 3, 27),
       (324, 'الشتر', 'الشتر', 0x0000000001010000001e0e5c2f97ee4040356bdf92d2214840, 3, 27),
       (325, 'نورآباد', 'نورآباد', 0x0000000001010000004d486b0c3a1d3e408333f8fbc5c24940, 3, 27),
       (326, 'آمل', 'آمل', 0x000000000101000000191bbad91f3c4240c5ad8218e82c4a40, 3, 28),
       (327, 'بابل', 'بابل', 0x00000000010100000009a4c4aeed3b4040637c98bd6c464640, 3, 28),
       (328, 'بابلسر', 'بابلسر', 0x00000000010100000052b81e85eb5942405c8fc2f528544a40, 3, 28),
       (329, 'بهشهر', 'بهشهر', 0x0000000001010000002ec901bb9a5842401f85eb51b8c64a40, 3, 28),
       (330, 'تنکابن', 'تنکابن', 0x00000000010100000036e84b6f7f684240c5aa4198db6f4940, 3, 28),
       (331, 'جویبار', 'جویبار', 0x0000000001010000008d0dddec0f524240cdcccccccc744a40, 3, 28),
       (332, 'چالوس', 'چالوس', 0x0000000001010000006c75de6bad52424053944be317b44940, 3, 28),
       (333, 'رامسر', 'رامسر', 0x000000000101000000713ac956977342405470784144544940, 3, 28),
       (334, 'ساری', 'ساری', 0x000000000101000000f8e0b54b1b48424048e17a14ae874a40, 3, 28),
       (335, 'سوادکوه', 'سوادکوه', 0x00000000010100000035d3bd4eea264240f5ccdc8d4f714a40, 3, 28),
       (336, 'قائم شهر', 'قائم-شهر', 0x000000000101000000b91b446b453b4240ae47e17a146e4a40, 3, 28),
       (337, 'گلوگاه', 'گلوگاه', 0x0000000001010000004277499c155d42400d8cbcac89e74a40, 3, 28),
       (338, 'محمود آباد', 'محمود-آباد', 0x000000000101000000a3737e8ae3504240dc0da2b5a2214a40, 3, 28),
       (339, 'نکا', 'نکا', 0x0000000001010000002b14e97e4e5342406e85b01a4ba64a40, 3, 28),
       (340, 'نور', 'نور', 0x0000000001010000004af14cc34b3149401f6ba1bf2b1d0a40, 3, 28),
       (341, 'نوشهر', 'نوشهر', 0x000000000101000000f8dd74cb0e534240ca17b49080bf4940, 3, 28),
       (342, 'فریدونکنار', 'فریدونکنار', 0x000000000101000000c5aa4198db5742407b14ae47e1424a40, 3, 28),
       (343, 'آشتیان', 'آشتیان', 0x000000000101000000f52b9d0fcf424140ab92c83ec8004940, 3, 29),
       (344, 'اراک', 'اراک', 0x000000000101000000ac8f87bebb0b4140c03dcf9f36d84840, 3, 29),
       (345, 'تفرش', 'تفرش', 0x000000000101000000eb54f99e915841401f82aad1ab014940, 3, 29),
       (346, 'خمین', 'خمین', 0x000000000101000000b3aa6faaffd1404070ce88d2de094940, 3, 29),
       (347, 'دلیجان', 'دلیجان', 0x000000000101000000a46dfc89cafe40400d8cbcac89574940, 3, 29),
       (348, 'زرندیه', 'زرندیه', 0x000000000101000000867fc7a64ba74140db3cc4f5de3e4940, 3, 29),
       (349, 'ساوه', 'ساوه', 0x00000000010100000040bfefdfbc824140fe47a643a72d4940, 3, 29),
       (350, 'شازند', 'شازند', 0x0000000001010000001f85eb51b8f64040d5eb1681b1b44840, 3, 29),
       (351, 'کمیجان', 'کمیجان', 0x00000000010100000064ae0caa0d5c41405ad76839d0a94840, 3, 29),
       (352, 'محلات', 'محلات', 0x00000000010100000020fcd52d4cf44040f48f1903463a4940, 3, 29),
       (353, 'بندرعباس', 'بندرعباس', 0x000000000101000000b9eb5b9ce72e3b401310937021224c40, 3, 30),
       (354, 'میناب', 'میناب', 0x0000000001010000000667f0f78b253b400ad7a3703d8a4c40, 3, 30),
       (355, 'بندر لنگه', 'بندر-لنگه', 0x0000000001010000002a560dc2dc8e3a40f6251b0fb6704b40, 3, 30),
       (356, 'رودان-دهبارز', 'رودان-دهبارز', 0x00000000010100000061ff756eda703b40713d0ad7a3984c40, 3, 30),
       (357, 'جاسک', 'جاسک', 0x0000000001010000001041d5e8d5a43940ae4a22fb20e34c40, 3, 30),
       (358, 'قشم', 'قشم', 0x0000000001010000001b310e89d6cf3a40b95ff4cb16f24b40, 3, 30),
       (359, 'حاجی آباد', 'حاجی-آباد', 0x0000000001010000009f008a91254f3c40f47002d369f34b40, 3, 30),
       (360, 'ابوموسی', 'ابوموسی', 0x000000000101000000cdf1c0b634e1394069609ad832844b40, 3, 30),
       (361, 'بستک', 'بستک', 0x0000000001010000004371c79bfc323b40dfc2baf1ee2e4b40, 3, 30),
       (362, 'گاوبندی', 'گاوبندی', 0x00000000010100000075adbd4f55353b404f0306499f844a40, 3, 30),
       (363, 'خمیر', 'خمیر', 0x0000000001010000001e882cd2c4f33a407b14ae47e1ca4b40, 3, 30),
       (364, 'اسدآباد', 'اسدآباد', 0x0000000001010000005c8fc2f52864414012f92ea52e0f4840, 3, 31),
       (365, 'بهار', 'بهار', 0x000000000101000000fb100a004474414071c229183a384840, 3, 31),
       (366, 'تویسرکان', 'تویسرکان', 0x0000000001010000003430f2b2264641405c92037635394840, 3, 31),
       (367, 'رزن', 'رزن', 0x000000000101000000a2b8e34d7eb14140da58897956844840, 3, 31),
       (368, 'کبودر آهنگ', 'کبودر-آهنگ', 0x000000000101000000bbd6dea7aa9a414092770e65a85c4840, 3, 31),
       (369, 'ملایر', 'ملایر', 0x000000000101000000295fd042022641401cd0d2156c694840, 3, 31),
       (370, 'نهاوند', 'نهاوند', 0x0000000001010000003b55be6724184140333674b33f304840, 3, 31),
       (371, 'همدان', 'همدان', 0x00000000010100000069a620e461614140ac04e67c0c334840, 3, 31),
       (372, 'ابرکوه', 'ابرکوه', 0x000000000101000000da475d2162213f4012e4fb3d0ca04a40, 3, 32),
       (373, 'اردکان', 'اردکان', 0x00000000010100000048e17a14ae2740400ad7a3703d024b40, 3, 32),
       (374, 'بافق', 'بافق', 0x00000000010100000052b5dd04df9c3f409a9658198db44b40, 3, 32),
       (375, 'تفت', 'تفت', 0x000000000101000000f9cfe4ac99fa3b404c63c5ba825958c0, 3, 32),
       (376, 'خاتم', 'خاتم', 0x000000000101000000a1fe6959ada24240a1c096b267cc4840, 3, 32),
       (377, 'صدوق', 'صدوق', 0x00000000010100000046843584190340404bc22a830fbd4a40, 3, 32),
       (378, 'طبس', 'طبس', 0x0000000001010000005470784144cc4040e17d552e54764c40, 3, 32),
       (379, 'مهریز', 'مهریز', 0x000000000101000000581f0f7d77973f4097e13fdd40374b40, 3, 32),
       (380, 'میبد', 'میبد', 0x00000000010100000057636424d61f4040f8020efc03014b40, 3, 32),
       (381, 'یزد', 'یزد', 0x000000000101000000219b9abae10c4040673c5b5194374b40, 3, 32);


truncate capsule.product_types restart identity cascade