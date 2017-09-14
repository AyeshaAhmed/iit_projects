/* 
 * Andrew Caron, Emily Warman, Ayesha Ahmed
 * CS 425
 * HW 2
*/

SET DEFINE OFF;

CREATE OR REPLACE PROCEDURE Question1 (ReviewMovie in VARCHAR2, ReviewText out VARCHAR2) AS

BEGIN
  SELECT text
  INTO ReviewText
  FROM (SELECT * FROM moviethreads ORDER BY id)
  WHERE rownum <= 2 AND movie = ReviewMovie;
  DBMS_OUTPUT.PUT_LINE('2 Most Recent Reviews: ' || ReviewText);
  DBMS_OUTPUT.PUT_LINE(ReviewText);
END Question1;