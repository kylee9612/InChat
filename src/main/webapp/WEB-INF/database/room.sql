create table room(
    room_code int (30) primary key,
    user_1 varchar(30),
    user_2 varchar(30),
    foreign key (user_1,user_2)
    references user(id)
)