INSERT INTO products (pid, name, price, image)
SELECT 'PD1', 'HUAWEI Pocket 2', 7499, '1.png'
WHERE NOT EXISTS (SELECT * FROM products WHERE pid = 'PD1');

INSERT INTO products (pid, name, price, image)
SELECT 'PD2', 'HUAWEI P60 Art', 8988, '2.png'
WHERE NOT EXISTS (SELECT * FROM products WHERE pid = 'PD2');

INSERT INTO products (pid, name, price, image)
SELECT 'PD3', 'HUAWEI Mate 60 Pro+', 8999, '3.png'
WHERE NOT EXISTS (SELECT * FROM products WHERE pid = 'PD3');

INSERT INTO products (pid, name, price, image)
SELECT 'PD4', 'HUAWEI Mate 60 RS 非凡大师', 11999, '4.png'
WHERE NOT EXISTS (SELECT * FROM products WHERE pid = 'PD4');

INSERT INTO products (pid, name, price, image)
SELECT 'PD5', 'HUAWEI Mate X5', 12999, '5.png'
WHERE NOT EXISTS (SELECT * FROM products WHERE pid = 'PD5');

INSERT INTO products (pid, name, price, image)
SELECT 'PD6', 'HUAWEI MateBook X Pro 2023 微绒典藏版', 13199, '6.png'
WHERE NOT EXISTS (SELECT * FROM products WHERE pid = 'PD6');

INSERT INTO products (pid, name, price, image)
SELECT 'PD7', 'HUAWEI MatePad Pro 13.2英寸', 13199, '7.png'
WHERE NOT EXISTS (SELECT * FROM products WHERE pid = 'PD7');

INSERT INTO products (pid, name, price, image)
SELECT 'PD8', 'MacBook Pro 256GB内存特别版', 99999, '8.png'
WHERE NOT EXISTS (SELECT * FROM products WHERE pid = 'PD8');