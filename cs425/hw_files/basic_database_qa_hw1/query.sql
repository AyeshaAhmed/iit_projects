/*CS425 query from tables*/

/*2*/
SELECT DISTINCT phone
FROM RecCenterMember, FamilyPackage
WHERE RecCenterMember.l_name = 'OShea' AND RecCenterMember.family_id = FamilyPackage.id;

/*3*/
SELECT f_name, l_name
FROM RecCenterMember, Enrollment
WHERE RecCenterMember.id = Enrollment.member_id AND 
  Enrollment.cost = (SELECT MAX(cost) FROM Enrollment);

/*4*/
SELECT f_name, l_name
FROM Instructor
WHERE Instructor.member_id IS NULL;

/*5 INCOMPLETE*/
SELECT f_name, l_name, class_id
FROM RecCenterMember, Enrollment
WHERE Enrollment.member_id = RecCenterMember.id;

/*6*/
SELECT f_name, l_name
FROM RecCenterMember, Enrollment, Class
WHERE RecCenterMember.id = Enrollment.member_id AND Class.id = Enrollment.class_id AND Class.type = 'Craft';

/*7*/
SELECT f_name, l_name
FROM RecCenterMember
WHERE RecCenterMember.dob >= DATE '1950-01-01' AND RecCenterMember.dob < DATE '1980-01-01';

/*8*/
SELECT DISTINCT type, description
FROM type NATURAL JOIN class
WHERE class.year = 2008 OR class.year = 2009;