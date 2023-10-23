insert into products (id, price, title, author, product_info_id)
values (1, 9.44, 'She Was the Quiet One', 'Lee Child', 1),
       (2, 8.87, 'The Modern Prometheus: A Novelette', 'Hastings', 2),
       (3, 6.45, 'Inca-Gold', 'Harris', 3),
       (4, 7.43, 'Into the Water: The Number One Bestseller', 'Homer', 4),
       (5, 15.70, 'Courtney''s War (Courtneys 15)', 'James', 5),
       (6, 9.99, 'Plants vs. Zombies: Bully For You', 'Paul Tobin', 6),
       (7, 3.99, 'Frost At Christmas', 'Wingfield', 7),
       (8, 9.14, 'The Ghost', 'Brian Zan', 8),
       (9, 9.94, 'Tilly Trotter''s Legacy', 'Rosie Goodwin', 9),
       (10, 8.74, 'Red Moon', 'Kim Stanley', 10),
       (11, 88.88, 'The Lord of the Rings', 'J.R.R.Tolkien', 11),
       (12, 8.88, 'The Last Wish: Introducing the Witcher', 'Sapkowski', 12),
       (13, 8.88, 'The Atlas Paradox (Atlas series, 2)', 'Olivie Blake', 13),
       (14, 13.22, 'The Curse of Oak Island: The Story of the World’s Longest Treasure Hunt', 'Sullivan, Randall', 14),
       (15, 35.87, 'Java: The Complete Reference, Twelfth Edition', 'Schildt', 15),
       (16, 13.19, 'Lonely Planet South India & Kerala (Travel Guide)', 'Kevin,Harding', 16),
       (17, 16.66, 'Anatomy Coloring Book', 'McCann', 17),
       (18, 7.45, 'Country Boy: A Biography of Albert Lee', 'Derek Watts', 18),
       (19, 6.47, 'Minecraft Young Readers: Survival Mode', 'Mojang', 19),
       (20, 5.34, 'The Sewing Machine', 'Natalie Fergie', 20);

update product_seq set next_val=21 where next_val=1;

insert into products_genres (product_id, genre_id)
values
    (1, 1),
    (2, 5),
    (3, 1),
    (4, 4),
    (5, 15),
    (6, 12),
    (7, 14),
    (8, 15),
    (9, 6),
    (10, 16),
    (11, 14),
    (12, 14),
    (13, 16),
    (14, 6),
    (15, 11),
    (16, 7),
    (17, 10),
    (18, 3),
    (19, 13),
    (20, 6);