/*Ayesha Ahmed aahmed31 A20344142 cs442 HW1
--Question 1 Part a
--create tables*/

CREATE TABLE Type(
type VARCHAR(20) NOT NULL,
description VARCHAR(255) NOT NULL,
PRIMARY KEY (type)
);

CREATE TABLE FamilyPackage(
id INT NOT NULL,
address VARCHAR(255) NOT NULL,
phone VARCHAR(10) NOT NULL,
PRIMARY KEY (id)
);

CREATE TABLE RecCenterMember(
id INT NOT NULL,
f_name VARCHAR(20) NOT NULL,
l_name VARCHAR(20) NOT NULL,
dob DATE NOT NULL,
family_id int NULL,
PRIMARY KEY(id),
FOREIGN KEY (family_id) REFERENCES FamilyPackage(id)
);

CREATE TABLE Instructor(
id INT NOT NULL,
f_name VARCHAR(20) NOT NULL,
l_name VARCHAR(20) NOT NULL,
member_id INT,
PRIMARY KEY (id),
FOREIGN KEY (member_id) REFERENCES RecCenterMember(id)
);

CREATE TABLE Class(
id INT NOT NULL,
title VARCHAR(20) NOT NULL,
type VARCHAR(20) NOT NULL,
instructor INT NOT NULL,
season VARCHAR(20) NOT NULL,
year INT NOT NULL,
CHECK (season = 'Fall' OR season='Spring' or season='Winter' or season = 'Summer'),
PRIMARY KEY (id),
FOREIGN KEY (type) REFERENCES Type(type),
FOREIGN KEY (instructor) REFERENCES Instructor(id)
);

CREATE TABLE Enrollment(
class_id INT NOT NULL,
member_id INT NOT NULL,
cost INT NOT NULL,
FOREIGN KEY (class_id) REFERENCES Class(id),
FOREIGN KEY (member_id) REFERENCES RecCenterMember(id),
PRIMARY KEY (class_id,member_id)
);