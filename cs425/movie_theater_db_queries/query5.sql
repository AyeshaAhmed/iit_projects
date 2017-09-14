set define off;

create or replace procedure query5 as

username VARCHAR2(255);
comcount NUMBER;

begin
 
select max(a)
into comcount
from (select username, count(username) a
from aahmed31.moviecomments
group by username);

select username
into username
from (select username, count(username) a
  from aahmed31.moviecomments 
  group by username)
where a = comcount;
  
dbms_output.put_line('User with the most comments: ' || username);
  
end query5;