CREATE TABLE PRICES (
                        BRAND_ID INT,
                        START_DATE TIMESTAMP,
                        END_DATE TIMESTAMP,
                        PRICE_LIST INT,
                        PRODUCT_ID INT,
                        PRIORITY INT,
                        PRICE DECIMAL(10, 2),
                        CURR VARCHAR(3),
                        PRIMARY KEY (BRAND_ID, START_DATE, END_DATE, PRICE_LIST, PRODUCT_ID),
                        FOREIGN KEY (BRAND_ID) REFERENCES BRAND(BRAND_ID) -- Asegúrate de tener una tabla "BRAND" con su clave primaria
);
INSERT INTO PRICES (BRAND_ID, START_DATE, END_DATE, PRICE_LIST, PRODUCT_ID, PRIORITY, PRICE, CURR)
VALUES
    (1, '2020-06-14 00:00:00', '2020-12-31 23:59:59', 1, 35455, 0, 35.50, 'EUR'),
    (1, '2020-06-14 15:00:00', '2020-06-14 18:30:00', 2, 35455, 1, 25.45, 'EUR'),
    (1, '2020-06-15 00:00:00', '2020-06-15 11:00:00', 3, 35455, 1, 30.50, 'EUR'),
    (1, '2020-06-15 16:00:00', '2020-12-31 23:59:59', 4, 35455, 1, 38.95, 'EUR');
