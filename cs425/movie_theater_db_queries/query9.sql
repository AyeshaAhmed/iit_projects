set define off;

create or replace procedure query9 AS

cnt NUMBER;

begin

  select count(job_type)
  into cnt
  from aahmed31.empschedule
  where TRUNC(job_date) = TRUNC((select CURRENT_DATE + 1 from dual))
  and job_type = 'Ticket Master';
  
  if(cnt > 0) then DBMS_OUTPUT.PUT_LINE('All fine');
  else DBMS_OUTPUT.PUT_LINE('ALERT!!');
  end if;
  
end query9;