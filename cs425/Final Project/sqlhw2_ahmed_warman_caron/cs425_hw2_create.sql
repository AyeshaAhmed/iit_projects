/* TCBT
 * Andrew Caron, Emily Warman, Ayesha Ahmed
 * CS 425
 * HW 2
*/

create table Locations(
theater_id int not null,
address varchar(255) not null,
city varchar(30) not null,
state varchar(30) not null,
zip varchar(10) not null,
theater_name VARCHAR(50) not null,
primary key (theater_id)
);

create table TheaterInfo(
room_id int not null,
theater_id int not null,
room_num int not null,
capacity int not null,
unique (theater_id, room_num),
foreign key (theater_id) REFERENCES Locations(theater_id),
primary key (room_id)
);

CREATE TABLE Movies(
movie_id NUMBER NOT NULL, 
title VARCHAR(20) NOT NULL unique, 
DirectorName VARCHAR(20) NOT NULL, 
Description VARCHAR(100) NOT NULL,
unique (title, DirectorName),
Primary Key (movie_id)
);

CREATE TABLE Stars(
movie_id number,
StarName VARCHAR(30) NOT NULL, 
FOREIGN KEY (movie_id) REFERENCES Movies(movie_id),
PRIMARY KEY(StarName, movie_id)
);

CREATE TABLE Genre(
movie_id NUMBER NOT NULL,
GenreType VARCHAR(20) NOT NULL,
FOREIGN KEY (movie_id) REFERENCES Movies(movie_id),
primary key (GenreType, movie_id)
);

create table Schedule(
showing_id INT NOT NULL,
movie_id int not null,
room_id INT,
show_date date not null,
ticket_price number not null,
tickets_sold int not null,
foreign key (movie_id) references Movies(movie_id),
FOREIGN KEY (room_id) REFERENCES TheaterInfo(room_id),
PRIMARY KEY (showing_id)
);

create table CC(
ccn varchar(16) not null,
ccn_code varchar(10) not null,
cc_name varchar(20) not null,
card_type varchar(20) not null,
exp_date date not null,
street1 varchar(255) not null,
street2 varchar(255) null,
city varchar(30) not null,
state varchar(30) not null,
zip varchar(10) not null,
primary key (ccn)
);

create table CreditCardCompany(
ccn varchar(16) not null,
cc_balanace int not null,
foreign key (ccn) references CC(ccn),
primary key (ccn)
);

create table TheaterUsers(
username varchar(20) null unique,
password varchar(20) null,
name varchar(20) not null,
ccn varchar(16),
phone varchar(15),
email varchar(50),
foreign key (ccn) references CC(ccn),
primary key (ccn)
);

create table Tickets(
ccn varchar(16) not null,
showing_id int not null,
ticket_no int not null,
date_purchased date not null,
foreign key (ccn) references TheaterUsers(ccn),
foreign key (showing_id) references Schedule(showing_id),
primary key (ccn, showing_id, date_purchased)
);

create table Points(
username varchar (20) not null,
current_points int not null,
total_points int not null,
foreign key (username) references TheaterUsers(username),
primary key (username)
);

create table PointLevel(
level_name varchar(20) not null,
level_boundary int not null,
primary key (level_name)
);

create table Rewards(
theater_id int not null,
movie_points int not null,
review_points int not null,
level_name varchar (20) not null,
offers varchar(255) null,
deals varchar(255) null,
foreign key (level_name) references PointLevel(level_name),
foreign key (theater_id) REFERENCES Locations(theater_id),
primary key (theater_id, level_name)
);

create table TheaterThreads(
id int not null,
theater_id int not null,
username varchar (20) not null,
text varchar(255),
foreign key (username) references TheaterUsers(username),
foreign key (theater_id) REFERENCES Locations(theater_id),
primary key (id)
);

create table TheaterComments(
thread_id int not null,
comment_number int not null, 
username varchar (20),
text varchar(255),
foreign key (username) references TheaterUsers(username),
foreign key (thread_id) references TheaterThreads(id),
primary key (thread_id, comment_number)
);

create table MovieThreads(
id int not null,
username varchar(20) not null,
movie_id int not null,
movie varchar(20) not null,
star_name varchar(50) null,
director varchar(50) null,
text varchar(255) null,
foreign key (username) references TheaterUsers(username),
foreign key (movie, director) references Movies(title, DirectorName),
foreign key (star_name,movie_id) references Stars(StarName,movie_id),
primary key (id)
);

create table MovieComments(
thread_id int not null,
comment_number int not null, 
username varchar(20) not null,
text varchar(255),
foreign key (username) references TheaterUsers(username),
foreign key (thread_id) references MovieThreads(id),
primary key (thread_id, comment_number)
);

create table JobTypes(
job_type varchar(100) not null,
description varchar(255) not null,
primary key (job_type)
);

create table Management(
manager_id int not null,
theater_id int null,
man_type varchar(100) not null,
username varchar(20) not null unique,
sched_password varchar(20) not null,
man_name varchar(50) not null,
address VARCHAR(255) NOT NULL,
phone VARCHAR(15) NOT NULL,
ssn varchar(15) not null,
foreign key (theater_id) REFERENCES Locations(theater_id),
foreign key (man_type) REFERENCES JobTypes(job_type),
primary key (manager_id)
);

create table Employees(
emp_id int not null,
emp_name VARCHAR(50) NOT NULL,
address VARCHAR(255) NOT NULL,
phone VARCHAR(15) NOT NULL,
ssn varchar(15) not null,
hiredby_id int null,
foreign key (hiredby_id) references Management(manager_id),
primary key (emp_id)
);

create table JobTraining(
emp_id int not null,
janitor number(1),
salesRep number(1),
ticketMaster number(1),
foreign key (emp_id) references Employees(emp_id),
primary key (emp_id)
);

create table EmpSchedule(
emp_id int not null,
job_date date not null,
theater_id int not null,
job_type varchar(100),
unique(emp_id, job_date),
foreign key (emp_id) references Employees(emp_id),
foreign key (job_type) references JobTypes(job_type),
foreign key (theater_id) REFERENCES Locations(theater_id),
primary key(job_date, emp_id)
);
