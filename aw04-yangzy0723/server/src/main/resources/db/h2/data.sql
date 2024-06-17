INSERT INTO products (pid, name, price, category, img, stock, quantity)
SELECT 'PD1', 'HUAWEI Pocket 2', 7499, 'phone', '1.png', 1, 10
WHERE NOT EXISTS (SELECT * FROM products WHERE pid = 'PD1');

INSERT INTO products (pid, name, price, category, img, stock, quantity)
SELECT 'PD2', 'HUAWEI P60 Art', 8988, 'phone', '2.png', 1, 5
WHERE NOT EXISTS (SELECT * FROM products WHERE pid = 'PD2');

INSERT INTO products (pid, name, price, category, img, stock, quantity)
SELECT 'PD3', 'HUAWEI Mate 60 Pro+', 8999, 'phone', '3.png', 1, 5
WHERE NOT EXISTS (SELECT * FROM products WHERE pid = 'PD3');

INSERT INTO products (pid, name, price, category, img, stock, quantity)
SELECT 'PD4', 'HUAWEI Mate 60 RS 非凡大师', 11999, 'phone', '4.png', 1, 2
WHERE NOT EXISTS (SELECT * FROM products WHERE pid = 'PD4');

INSERT INTO products (pid, name, price, category, img, stock, quantity)
SELECT 'PD5', 'HUAWEI Mate X5', 12999, 'phone', '5.png', 1, 5
WHERE NOT EXISTS (SELECT * FROM products WHERE pid = 'PD5');

INSERT INTO products (pid, name, price, category, img, stock, quantity)
SELECT 'PD6', 'HUAWEI MateBook X Pro 2023 微绒典藏版', 13199, 'laptop', '6.png', 1, 5
WHERE NOT EXISTS (SELECT * FROM products WHERE pid = 'PD6');

INSERT INTO products (pid, name, price, category, img, stock, quantity)
SELECT 'PD7', 'HUAWEI MatePad Pro 13.2英寸', 8999, 'tablet', '7.png', 1, 10
WHERE NOT EXISTS (SELECT * FROM products WHERE pid = 'PD7');

INSERT INTO products (pid, name, price, category, img, stock, quantity)
SELECT 'PD8', 'MacBook Pro 256GB内存特别版', 99999, 'laptop', '8.png', 1, 1
WHERE NOT EXISTS (SELECT * FROM products WHERE pid = 'PD8');

INSERT INTO products (pid, name, price, category, img, stock, quantity)
SELECT 'PD9', 'HUAWEI P70 Ultra', 10999, 'phone', '9.png', 1, 5
    WHERE NOT EXISTS (SELECT * FROM products WHERE pid = 'PD9');