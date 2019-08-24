delete from movies;
delete from users_authorities;
delete from users;
delete from authorities;

insert into authorities(id, name) value(1, 'USER');
insert into authorities(id, name) value(2, 'ADMIN');

insert into users(username, password, enabled, is_vote) values('UsernameA', 
'077d73f714f7a76edc658c29487f96bcdeea47b60e6497a5f81c7707979b45689b9d5821373483c4', true, true);
insert into users(username, password, enabled, is_vote) values('UsernameB', 
'95d2df801d2cb45ff8b08d711d25d1e6a4fb4f536f77371835b33b82e8ad7d08fa4b559b8511899c', true, false);

insert into users_authorities(user_id, authority_id) values('UsernameA', 1);
insert into users_authorities(user_id, authority_id) values('UsernameA', 2);
insert into users_authorities(user_id, authority_id) values('UsernameB', 1);

insert into movies(id, votes, title, genre, year, actors, url, date, user_id)
values(1, 1, 'Title A', 'ACTION', '2017', 'Actors A', 
'https://www.imdb.com/title/tt0497465/?ref_=nv_sr_1', '2017-05-20', 'UsernameA');

insert into movies(id, votes, title, genre, year, actors, url, date, user_id)
values(2, 0, 'Title B', 'ACTION', '2017', 'Actors B', 
'https://www.imdb.com/title/tt7233640/?ref_=fn_al_tt_4', '2017-05-21', 'UsernameB');






