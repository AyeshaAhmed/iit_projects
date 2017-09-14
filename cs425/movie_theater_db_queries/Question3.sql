/* 
 * Andrew Caron, Emily Warman, Ayesha Ahmed
 * CS 425
 * HW 2
*/

SET DEFINE OFF;

CREATE OR REPLACE PROCEDURE Question3 (GuestName in VARCHAR2, Status out VARCHAR2, PointNum out NUMBER) AS

BEGIN
  SELECT currentpoints
    INTO PointNum
    FROM points
    WHERE user_id = (SELECT user_id FROM registeredusers WHERE name = GuestName);
  SELECT rewardslevel
    INTO Status
    FROM registeredusers
    WHERE name = GuestName;
  DBMS_OUTPUT.PUT_LINE('Guest: ' || GuestName || ' Status: ' || Status || ' Points: ' || PointNum);
END Question1;