set define off;

create or replace procedure query2 as

TYPE TextTable IS TABLE OF VARCHAR(255) INDEX BY PLS_INTEGER;

reviews TextTable;
comcount number;

begin

select text
  bulk collect into reviews
  from (SELECT * FROM aahmed31.moviecomments ORDER BY rownum DESC)
  where rownum <= 3;
  
dbms_output.put_line('3 most recent comments overall: ');
dbms_output.put_line(reviews(1));
dbms_output.put_line(reviews(2));
dbms_output.put_line(reviews(3));
  
end query2;