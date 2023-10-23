insert into categories (id, name)
values (1, 'Fiction'),
       (2, 'Non Fiction'),
       (3, 'Children\'s');

update category_seq set next_val=4 where next_val=1