create table Locations(
theater_id int not null,
street_1 varchar(255) not null,
street_2 varchar(255) null,
city varchar(30) not null,
state varchar(30) not null,
zip varchar(10) not null,
name VARCHAR(50) not null,
primary key (theater_id)
);

create table TheaterInfo(
room_id int not null,
theater_id int,
room_num int not null,
capacity int not null,
room_type varchar(30) not null,
foreign key (theater_id) REFERENCES Locations(theater_id),
primary key (room_id)
);

create table Movies(
title varchar(255) not null,
director varchar(50) not null,
movie_type1 varchar(20) not null,
movie_type2 varchar(20) null,
movie_type3 varchar(20) null,
primary key (title)
);

create table Stars(
movie_id int not null,
movie varchar(255) unique not null,
star1 varchar(50) not null,
star2 varchar(50) not null,
star3 varchar(50) not null,
foreign key (movie) references Movies(title),
primary key (movie_id)
);

create table Schedule(
showing_id INT NOT NULL,
room_id INT,
show_date date not null,
show_time varchar(100) not null,
movie varchar(255),
ticket_price number not null,
tickets_sold int not null,
foreign key (movie) references Movies(title),
FOREIGN KEY (room_id) REFERENCES TheaterInfo(room_id),
PRIMARY KEY (showing_id)
);

create table CC(
ccn varchar(16) not null,
cc_name varchar(20) not null,
card_type varchar(20) not null,
ccn_code varchar(10) not null,
exp_date date not null,
primary key (ccn)
);

create table RegisteredUsers(
user_id int not null,
username varchar(20) null,
password varchar(20) null,
name varchar(20) not null,
ccn varchar(16),
street1 varchar(255) not null,
street2 varchar(255) null,
city varchar(30) not null,
state varchar(30) not null,
zip varchar(10) not null,
phone varchar(15),
email varchar(50),
foreign key (ccn) references CC(ccn),
primary key (user_id)
);

create table Tickets(
user_id int not null,
showing_id int not null,
date_purchased date not null,
ticket_no int not null,
foreign key (user_id) references RegisteredUsers(user_id),
foreign key (showing_id) references Schedule(showing_id),
primary key (user_id, showing_id, date_purchased)
);

create table Points(
user_id int not null,
current_points int not null,
total_points int not null,
foreign key (user_id) references RegisteredUsers(user_id),
primary key (user_id)
);

create table Rewards(
theater_id int not null,
level_name varchar(20) not null,
level_boundary int not null,
offers varchar(255) null,
deals varchar(255) null,
movie_points int not null,
review_points int not null,
foreign key (theater_id) references Locations(theater_id),
primary key (theater_id, level_name)
);

create table TheaterThreads(
id int not null,
theater_id int,
user_id int,
text varchar(255),
foreign key (user_id) references RegisteredUsers(user_id),
foreign key (theater_id) references Locations(theater_id),
primary key (id)
);

create table TheaterComments(
thread_id int not null,
comment_number int not null, 
user_id int not null,
text varchar(255),
foreign key (user_id) references RegisteredUsers(user_id),
foreign key (thread_id) references TheaterThreads(id),
primary key (thread_id, comment_number)
);

create table MovieThreads(
id int not null,
user_id int not null,
movie_id int not null,
movie varchar(255) not null,
star1 varchar(50) not null,
star2 varchar(50) not null,
star3 varchar(50) not null,
director varchar(50) not null,
text varchar(255) null,
foreign key (user_id) references RegisteredUsers(user_id),
foreign key (movie, director) references Movies(title, director),
foreign key (movie_id, star1, star2, star3) references Stars(movie_id, star1, star2, star3),
primary key (id)
);

create table MovieComments(
thread_id int not null,
comment_number int not null, 
user_id int not null,
text varchar(255),
foreign key (user_id) references RegisteredUsers(user_id),
foreign key (thread_id) references MovieThreads(id),
primary key (thread_id, comment_number)
);

create table JobTypes(
job_type varchar(100) not null,
description varchar(255) not null,
primary key (job_type)
);

create table Management(
emp_id int not null,
job_type varchar(100),
theater_id int null,
username varchar(20) not null,
sched_password varchar(20) not null,
f_name VARCHAR(20) NOT NULL,
l_name VARCHAR(20) NOT NULL,
foreign key (job_type) references JobTypes(job_type),
foreign key (theater_id) references Locations(theater_id),
primary key (emp_id)
);

create table Employees(
emp_id int not null,
f_name VARCHAR(20) NOT NULL,
l_name VARCHAR(20) NOT NULL,
address VARCHAR(255) NOT NULL,
phone VARCHAR(15) NOT NULL,
ssn varchar(15) not null,
hiring_emp_id int null,
theater_id int,
foreign key (hiring_emp_id) references Management(emp_id),
foreign key (theater_id) references Locations(theater_id),
primary key (emp_id)
);

create table EmpSchedule(
job_time varchar(50) not null,
job_date varchar(50) not null,
emp_id int not null,
job_type varchar(100),
theater_id int null,
foreign key (emp_id) references Employees(emp_id),
foreign key (job_type) references JobTypes(job_type),
foreign key (theater_id) references Locations(theater_id),
primary key(job_time, job_date, emp_id)
);