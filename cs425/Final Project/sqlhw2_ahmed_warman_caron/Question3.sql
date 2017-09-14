/* 
 * Andrew Caron, Emily Warman, Ayesha Ahmed
 * CS 425
 * HW 2
*/

SET DEFINE OFF;

CREATE OR REPLACE PROCEDURE Question3 (GuestName in VARCHAR2) AS

  total INT;
  PointNum NUMBER;
  Status VARCHAR2(255);
BEGIN

  SELECT current_points
    INTO PointNum
    FROM aahmed31.points
    WHERE username = (SELECT username FROM aahmed31.theaterusers WHERE name = GuestName);

  SELECT total_points
    INTO total
    FROM aahmed31.points
    WHERE username = (SELECT username FROM aahmed31.theaterusers WHERE name = GuestName);

  IF total < 100 THEN
	Status := 'Beginner';
  ELSIF total < 200 THEN
	Status := 'Silver';
  ELSIF total < 300 THEN
	Status := 'Gold';
  ELSE Status := 'Platinum';
  END IF;

  DBMS_OUTPUT.PUT_LINE('Guest: ' || GuestName || ' Status: ' || Status || ' Points: ' || PointNum);

END Question3;

