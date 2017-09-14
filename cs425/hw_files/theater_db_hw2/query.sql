/*Ayesha Ahmed aahmed31 A20344142 cs442 HW1*/
/*query/ search data*/

/* Question 2*/
SELECT DISTINCT phone
FROM RecCenterMember, FamilyPackage
WHERE RecCenterMember.l_name = 'OShea' AND RecCenterMember.family_id = FamilyPackage.id;

/*Question 3*/
SELECT f_name, l_name
FROM RecCenterMember, Enrollment
WHERE RecCenterMember.id = Enrollment.member_id AND 
  Enrollment.cost = (SELECT MAX(cost) FROM Enrollment);

/*Question 4*/
SELECT f_name, l_name
FROM Instructor
WHERE Instructor.member_id IS NULL;

/*Question 5*/
SELECT RecCenterMember.F_NAME, RecCenterMember.L_NAME
FROM Type RIGHT OUTER JOIN Class ON Type.type = Class.type RIGHT OUTER JOIN 
          Enrollment ON Class.id = Enrollment.class_id LEFT OUTER JOIN 
          RecCenterMember ON Enrollment.Member_id = RecCenterMember.id
GROUP BY RecCenterMember.f_name, RecCenterMember.l_name
HAVING COUNT(*) > 2;

/*Question 6*/
SELECT f_name, l_name
FROM RecCenterMember, Enrollment, Class
WHERE RecCenterMember.id = Enrollment.member_id AND Class.id = Enrollment.class_id AND Class.type = 'Craft';

/*Question 7*/
SELECT f_name, l_name
FROM RecCenterMember
WHERE RecCenterMember.dob >= DATE '1950-01-01' AND RecCenterMember.dob < DATE '1980-01-01';

/*Question 8*/
SELECT DISTINCT type, description
FROM type NATURAL JOIN class
WHERE class.year = 2008 OR class.year = 2009;


