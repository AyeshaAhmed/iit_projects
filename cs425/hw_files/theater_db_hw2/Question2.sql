/* 
 * Andrew Caron, Emily Warman, Ayesha Ahmed
 * CS 425
 * HW 2
*/

SET DEFINE OFF;

CREATE OR REPLACE PROCEDURE Question2 (TheaterLocation in VARCHAR2, EmpDate out VARCHAR2
                                        EmpTime out VARCHAR2, EmpID out NUMBER.
                                        EmpJob out VARCHAR2) AS

BEGIN
  SELECT job_time
    INTO EmpTime
    FROM empschedule
    WHERE theater_id = (SELECT theater_id FROM locations WHERE street1 = TheaterLocation); 
  SELECT job_date
    INTO EmpDate
    FROM empschedule
    WHERE theater_id = (SELECT theater_id FROM locations WHERE street1 = TheaterLocation);
  SELECT emp_id
    INTO EmpID
    FROM empschedule
    WHERE theater_id = (SELECT theater_id FROM locations WHERE street1 = TheaterLocation);
  SELECT job_type
    INTO EmpJob
    FROM empschedule
    WHERE theater_id = (SELECT theater_id FROM locations WHERE street1 = TheaterLocation);

  LOOP
    DBMS_OUTPUT.PUT_LINE('Schedule for location: ' || TheaterLocation
    || ' Date and Time: ' || EmpDate || ' ' || EmpTime || ' Employee: ' ||
    EmpID || ' Job: ' || EmpJob);
  END LOOP;
END Question2;