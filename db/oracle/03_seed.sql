-- 03_seed.sql
BEGIN EXECUTE IMMEDIATE 'DROP SEQUENCE SEQ_LIB'; EXCEPTION WHEN OTHERS THEN NULL; END; /
CREATE SEQUENCE SEQ_LIB START WITH 1 INCREMENT BY 1;

INSERT INTO LIBRARIES (lib_id, lib_name, address, phone, open_hours, lon, lat, location)
VALUES (SEQ_LIB.NEXTVAL, '四川大学望江校区图书馆', '成都市武侯区一环路南一段24号', '028-8540xxxx', '08:00-22:00',
        104.084000, 30.642000,
        MDSYS.SDO_GEOMETRY(2001,4326, MDSYS.SDO_POINT_TYPE(104.084000,30.642000,NULL), NULL, NULL));

INSERT INTO LIBRARIES (lib_id, lib_name, address, phone, open_hours, lon, lat, location)
VALUES (SEQ_LIB.NEXTVAL, '四川大学华西校区图书馆', '成都市武侯区人民南路三段17号', '028-8550xxxx', '08:00-22:00',
        104.080000, 30.650000,
        MDSYS.SDO_GEOMETRY(2001,4326, MDSYS.SDO_POINT_TYPE(104.080000,30.650000,NULL), NULL, NULL));

INSERT INTO LIBRARIES (lib_id, lib_name, address, phone, open_hours, lon, lat, location)
VALUES (SEQ_LIB.NEXTVAL, '电子科技大学清水河校区图书馆', '成都市高新区（西区）电子科技大学', '028-6183xxxx', '08:00-22:00',
        103.933000, 30.753000,
        MDSYS.SDO_GEOMETRY(2001,4326, MDSYS.SDO_POINT_TYPE(103.933000,30.753000,NULL), NULL, NULL));

INSERT INTO LIBRARIES (lib_id, lib_name, address, phone, open_hours, lon, lat, location)
VALUES (SEQ_LIB.NEXTVAL, '电子科技大学沙河校区图书馆', '成都市成华区建设北路二段四号', '028-8320xxxx', '08:00-22:00',
        104.154000, 30.678000,
        MDSYS.SDO_GEOMETRY(2001,4326, MDSYS.SDO_POINT_TYPE(104.154000,30.678000,NULL), NULL, NULL));

INSERT INTO LIBRARIES (lib_id, lib_name, address, phone, open_hours, lon, lat, location)
VALUES (SEQ_LIB.NEXTVAL, '西南交通大学犀浦校区图书馆', '成都市郫都区犀安路999号', '028-6636xxxx', '08:00-22:00',
        103.982000, 30.822000,
        MDSYS.SDO_GEOMETRY(2001,4326, MDSYS.SDO_POINT_TYPE(103.982000,30.822000,NULL), NULL, NULL));

INSERT INTO LIBRARIES (lib_id, lib_name, address, phone, open_hours, lon, lat, location)
VALUES (SEQ_LIB.NEXTVAL, '四川师范大学成龙校区图书馆', '成都市龙泉驿区成龙大道二段1819号', '028-8476xxxx', '08:00-22:00',
        104.162000, 30.634000,
        MDSYS.SDO_GEOMETRY(2001,4326, MDSYS.SDO_POINT_TYPE(104.162000,30.634000,NULL), NULL, NULL));

INSERT INTO BOOKS (book_id, isbn, title, author, publisher) VALUES (1, '9787111128069', '数据库系统概论', '王珊', '高等教育出版社');
INSERT INTO BOOKS (book_id, isbn, title, author, publisher) VALUES (2, '9787111213826', 'GIS 原理与应用', '某作者', '某出版社');

INSERT INTO BOOK_INVENTORY (inventory_id, book_id, lib_id, status, call_number) VALUES (1001, 1, 1, 'AVAILABLE', 'TP311 W25');
INSERT INTO BOOK_INVENTORY (inventory_id, book_id, lib_id, status, call_number) VALUES (1002, 1, 2, 'BORROWED',  'TP311 W25');
INSERT INTO BOOK_INVENTORY (inventory_id, book_id, lib_id, status, call_number) VALUES (1003, 2, 3, 'READING_ROOM','P203 G10');
INSERT INTO BOOK_INVENTORY (inventory_id, book_id, lib_id, status, call_number) VALUES (1004, 2, 4, 'ON_HOLD',     'P203 G10');

COMMIT;
