/* 
 * Andrew Caron, Emily Warman, Ayesha Ahmed
 * CS 425
 * HW 2
*/

SET DEFINE OFF;

CREATE OR REPLACE PROCEDURE Question2 (TheaterLocation in VARCHAR2) AS

TYPE TextTable IS TABLE OF VARCHAR2(255) INDEX BY PLS_INTEGER;
TYPE NumberTable IS TABLE OF NUMBER INDEX BY PLS_INTEGER;
TYPE DateTable IS TABLE OF DATE INDEX BY PLS_INTEGER;

NumRows NUMBER;
EmpDate DateTable;
EmpID NumberTable;
EmpJob TextTable;

BEGIN
 
  SELECT job_date
    BULK COLLECT INTO EmpDate
    FROM aahmed31.empschedule
    WHERE theater_id = (SELECT theater_id FROM aahmed31.locations WHERE address = TheaterLocation);
  SELECT emp_id
    BULK COLLECT INTO EmpID
    FROM aahmed31.empschedule
    WHERE theater_id = (SELECT theater_id FROM aahmed31.locations WHERE address = TheaterLocation);
  SELECT job_type
    BULK COLLECT INTO EmpJob
    FROM aahmed31.empschedule
    WHERE theater_id = (SELECT theater_id FROM aahmed31.locations WHERE address = TheaterLocation);

  NumRows := EmpJob.COUNT;

  DBMS_OUTPUT.PUT_LINE('Schedule for location: ' || TheaterLocation);
    
  FOR i IN 1..NumRows
  LOOP
    DBMS_OUTPUT.PUT_LINE(' Date and Time: ' || EmpDate(i) || ' Employee: ' ||
    EmpID(i) || ' Job: ' || EmpJob(i) );
  END LOOP;
END Question2;