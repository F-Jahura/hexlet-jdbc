DROP TABLE IF EXISTS products;

CREATE TABLE products (
    id bigint GENERATED ALWAYS AS IDENTITY,
    title varchar(255),
    description varchar(255),
    price integer
);

INSERT INTO products (title, description, price) VALUES
('banana', 'yellow fruit', 50),
('apple', 'red fruit', 60),
('orange', 'orange citrus fruit', 40),
('strawberry', 'small red fruit', 70),
('pineapple', 'tropical fruit with spiky skin', 80),
('watermelon', 'large green striped fruit', 30),
('kiwi', 'small brown fuzzy fruit', 65),
('grape', 'small round fruit growing in bunches', 55),
('cherry', 'small round red fruit with pit', 75),
('pear', 'pear-shaped fruit with sweet white flesh', 45);