set define off;
alter SESSION set NLS_DATE_FORMAT = 'DD-MM-YYYY HH24:MI:SS';

create or replace procedure query8 as

TYPE st IS TABLE OF aahmed31.empschedule%rowtype INDEX BY PLS_INTEGER;
shifts st;
cnt NUMBER;
begin
  select *
  BULK COLLECT INTO shifts
  from aahmed31.empschedule
  where TRUNC(job_date) = TRUNC(to_date('2015-12-14 09:00:00','YYYY-MM-DD HH24:MI:SS'))
  and theater_id = 2;
  
  cnt := shifts.COUNT;
  DBMS_OUTPUT.PUT_LINE('Shifts at Theater 2');
FOR i IN 1..cnt
LOOP
  DBMS_OUTPUT.PUT_LINE('Employee: ' || shifts(i).emp_id || ' Day/Time: ' || shifts(i).job_date || ' Job: ' || shifts(i).job_type);
END LOOP;
end query8;