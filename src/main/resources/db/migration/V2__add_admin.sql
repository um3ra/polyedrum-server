insert into users (id, email, first_name, last_name, password, role)
values(1, 'polyedrumadmin@gmail.com', 'admin1', 'admin1', '$2y$10$tXpZweCKTUVjITJ6CG6SXOpn2Y4nbnro9qEwvy7FpEvHaGfe5Brqe', 'ADMIN');

update user_seq set next_val=2 where next_val=1