/* 
 * Andrew Caron, Emily Warman, Ayesha Ahmed
 * CS 425
 * HW 2
*/

SET DEFINE OFF;

CREATE OR REPLACE PROCEDURE Question1 (ReviewMovie in VARCHAR2) AS

Review1 VARCHAR2(255);
Review2 VARCHAR2(255);

BEGIN

  SELECT text
    INTO Review1
    FROM aahmed31.moviethreads
    WHERE id = (SELECT MAX(id) FROM aahmed31.moviethreads WHERE movie = ReviewMovie);
  
  SELECT text
    INTO Review2
    FROM aahmed31.moviethreads
    WHERE id = (SELECT MAX(id) FROM aahmed31.moviethreads WHERE movie = ReviewMovie 
                AND id NOT IN (SELECT MAX(id) FROM aahmed31.moviethreads WHERE movie = ReviewMovie));
  
  DBMS_OUTPUT.PUT_LINE('2 Most Recent Reviews for ' || ReviewMovie || ': ');
  DBMS_OUTPUT.PUT_LINE(Review1);
  DBMS_OUTPUT.PUT_LINE(Review2);
  
END Question1;