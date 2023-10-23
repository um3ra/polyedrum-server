insert into genres (id, name, category_id)
values (1, 'Romance', 1),
       (3, 'Art', 1),
       (4, 'Adventure', 1),
       (5, 'Horror', 1),
       (6, 'History', 2),
       (7, 'Travel', 2),
       (10, 'Medicine', 2),
       (11, 'Computing & I.T', 2),
       (12, 'Comics', 3),
       (13, 'Beginner Readers', 3),
       (14, 'Fantasy', 1),
       (15, 'Classics', 1),
       (16, 'Sci-Fi', 2);

update genre_seq set next_val=17 where next_val=1