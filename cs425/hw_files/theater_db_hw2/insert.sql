/*Ayesha Ahmed aahmed31 A20344142 cs442 HW1
--Question 1 Part b
-- insert data*/

INSERT ALL
  INTO type (type, description) VALUES ('Craft', 'Knitting, sewing, etc')
  INTO type (type, description) VALUES ('Art', 'Painting, sculpting, etc')
  INTO type (type, description) VALUES ('Exercise', 'Any courses having to do with physical activity')
  INTO type (type, description) VALUES ('Languages', 'Anything to do with writing, literature, or communication')
  INTO type (type, description) VALUES ('Kids', 'Courses geared towards children 13 and younger')
SELECT * FROM dual;

INSERT ALL
  INTO FamilyPackage (id, address, phone) VALUES (1,'23 Beacon St. Hillside IL', '7085559384')
  INTO FamilyPackage (id, address, phone) VALUES (2,'4930 Dickens Ave Chicago IL', '3125559403')
  INTO FamilyPackage (id, address, phone) VALUES (3,'345 Fullerton St. Chicago IL', '7735550032')
  INTO FamilyPackage (id, address, phone) VALUES (4,'34 Maple Ln. Elmhurst IL', '3125559382')
  INTO FamilyPackage (id, address, phone) VALUES (5,'4563 Harvard Ave. Lisle IL', '6305559321')
SELECT * FROM dual;

INSERT ALL
  INTO RecCenterMember (id, f_name, l_name, dob, family_id) VALUES (1,'Abby','Smith', to_date('1983-05-21', 'yyyy-mm-dd'), 1)
  INTO RecCenterMember (id, f_name, l_name, dob, family_id) VALUES (2,'Mike','OShea',to_date('1968-04-07','yyyy-mm-dd'),2)
  INTO RecCenterMember (id, f_name, l_name, dob, family_id) VALUES (3,'April','OShea',to_date('1954-06-23','yyyy-mm-dd'),2)
  INTO RecCenterMember (id, f_name, l_name, dob, family_id) VALUES (4,'Vijay','Gupta',to_date('1945-08-01','yyyy-mm-dd'),NULL)
  INTO RecCenterMember (id, f_name, l_name, dob, family_id) VALUES (5,'Lisa','Tang',to_date('2000-11-05','yyyy-mm-dd'),3)
  INTO RecCenterMember (id, f_name, l_name, dob, family_id) VALUES (6,'Harry','Smith',to_date('1972-02-03','yyyy-mm-dd'),NULL)
  INTO RecCenterMember (id, f_name, l_name, dob, family_id) VALUES (7,'Justin','Smith',to_date('1972-02-03','yyyy-mm-dd'),1)
  INTO RecCenterMember (id, f_name, l_name, dob, family_id) VALUES (8,'Lisa','Brown',to_date('1959-12-28','yyyy-mm-dd'),NULL)
  INTO RecCenterMember (id, f_name, l_name, dob, family_id) VALUES (9,'Harry','Tang',to_date('1948-04-03','yyyy-mm-dd'),3)
  INTO RecCenterMember (id, f_name, l_name, dob, family_id) VALUES (10,'Dongmei','Tang',to_date('1942-06-02','yyyy-mm-dd'),3)
  INTO RecCenterMember (id, f_name, l_name, dob, family_id) VALUES (11,'Laura','Dickinson',to_date('1998-11-11','yyyy-mm-dd'),NULL)
  INTO RecCenterMember (id, f_name, l_name, dob, family_id) VALUES (12,'Victor','Garcia',to_date('2006-04-05','yyyy-mm-dd'),5)
  INTO RecCenterMember (id, f_name, l_name, dob, family_id) VALUES (13,'Emily','Citrin',to_date('1993-05-04','yyyy-mm-dd'),NULL)
  INTO RecCenterMember (id, f_name, l_name, dob, family_id) VALUES (14,'Maria','Garcia',to_date('2007-07-07','yyyy-mm-dd'),5)
  INTO RecCenterMember (id, f_name, l_name, dob, family_id) VALUES (15,'Cassie','OShea',to_date('1988-06-02','yyyy-mm-dd'),2)
  INTO RecCenterMember (id, f_name, l_name, dob, family_id) VALUES (16,'Cassandra','McDonald',to_date('1988-06-02','yyyy-mm-dd'),NULL)
  INTO RecCenterMember (id, f_name, l_name, dob, family_id) VALUES (17,'Jessie','Knapp',to_date('1981-09-12','yyyy-mm-dd'),4)
  INTO RecCenterMember (id, f_name, l_name, dob, family_id) VALUES (18,'Monica','Knapp',to_date('1982-09-17','yyyy-mm-dd'),4)
  INTO RecCenterMember (id, f_name, l_name, dob, family_id) VALUES (19,'Leslie','Blackburn',to_date('1986-01-19','yyyy-mm-dd'),NULL)
  INTO RecCenterMember (id, f_name, l_name, dob, family_id) VALUES (20,'Sandra','Svoboda',to_date('1999-09-09','yyyy-mm-dd'),NULL)
SELECT * FROM dual;

INSERT ALL
  INTO Instructor (id, f_name, l_name, member_id) VALUES (1,'Annie','Heard',NULL)
  INTO Instructor (id, f_name, l_name, member_id) VALUES (2,'Monica','Knapp',18)
  INTO Instructor (id, f_name, l_name, member_id) VALUES (3,'James','Robertson',NULL)
  INTO Instructor (id, f_name, l_name, member_id) VALUES (4,'April','OShea',3)
  INTO Instructor (id, f_name, l_name, member_id) VALUES (5,'Harry','Tang', 9)
SELECT * FROM dual;

INSERT ALL
  INTO Class (id, title, type, instructor, season, year) VALUES (1,'Needle Points','Craft',2,'Spring',2010)
  INTO Class (id, title, type, instructor, season, year) VALUES (2,'Photography','Art',1,'Fall',2008)
  INTO Class (id, title, type, instructor, season, year) VALUES (3,'Woodworking','Craft',4,'Spring',2009)
  INTO Class (id, title, type, instructor, season, year) VALUES (4,'Chinese (Intro.)','Languages',1,'Winter',2008)
  INTO Class (id, title, type, instructor, season, year) VALUES (5,'Team Games','Kids',1,'Summer',2008)
  INTO Class (id, title, type, instructor, season, year) VALUES (6,'Yoga (Intro.)','Exercise',2,'Fall',2009)
  INTO Class (id, title, type, instructor, season, year) VALUES (7,'Origam (Adv.)','Craft',4,'Fall',2009)
  INTO Class (id, title, type, instructor, season, year) VALUES (8,'Oil Painting','Art',3,'Spring',2009)
  INTO Class (id, title, type, instructor, season, year) VALUES (9,'Yoga (Adv.)','Exercise',1,'Spring',2008)
  INTO Class (id, title, type, instructor, season, year) VALUES (10,'Chinese (Intro.)','Languages',3,'Spring',2009)
SELECT * FROM dual;

INSERT ALL
  INTO Enrollment (class_id, member_id, cost) VALUES (3,3,20)
  INTO Enrollment (class_id, member_id, cost) VALUES (1,9,15)
  INTO Enrollment (class_id, member_id, cost) VALUES (2,9,20)
  INTO Enrollment (class_id, member_id, cost) VALUES (4,10,30)
  INTO Enrollment (class_id, member_id, cost) VALUES (3,10,10)
  INTO Enrollment (class_id, member_id, cost) VALUES (5,5,10)
  INTO Enrollment (class_id, member_id, cost) VALUES (4,9,30)
  INTO Enrollment (class_id, member_id, cost) VALUES (1,11,25)
  INTO Enrollment (class_id, member_id, cost) VALUES (2,19,40)
  INTO Enrollment (class_id, member_id, cost) VALUES (7,14,10)
  INTO Enrollment (class_id, member_id, cost) VALUES (8,12,5)
  INTO Enrollment (class_id, member_id, cost) VALUES (1,1,30)
  INTO Enrollment (class_id, member_id, cost) VALUES (6,1,15)
  INTO Enrollment (class_id, member_id, cost) VALUES (9,1,20)
  INTO Enrollment (class_id, member_id, cost) VALUES (8,1,25)
  INTO Enrollment (class_id, member_id, cost) VALUES (1,13,18)
  INTO Enrollment (class_id, member_id, cost) VALUES (2,20,9)
  INTO Enrollment (class_id, member_id, cost) VALUES (10,4,15)
  INTO Enrollment (class_id, member_id, cost) VALUES (1,2,3)
SELECT * FROM dual;