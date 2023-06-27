-- left join, right join
SELECT c.*,p.*
FROM categories c left join products as p on  p.category_id = c.id ; 

-- join 3 bang
SELECT * 
FROM orders as o join orderitems as ot on o.id = ot.id_order
	join products as p on p.id = ot.id_product
    left join product_infos as pi on p.id = pi.id_product
where o.id = 1;

-- dung toan tu in: de tim ra san pham co bán được và ko bán được
SELECT * FROM shopping_management.products
WHERE products.id in (select orderitems.id_product from orderitems);
SELECT * FROM shopping_management.products
WHERE products.id not in (select orderitems.id_product from orderitems);


-- dùng toán từ all
SELECT * FROM shopping_management.products
where products.price >= all (select price from products);

