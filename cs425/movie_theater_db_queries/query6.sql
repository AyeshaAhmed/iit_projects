set define off;

create or replace procedure query6 as

theater VARCHAR2(255);
count NUMBER;
id NUMBER;

begin

select max(c)
into count
from  (select count(a.theater_id) c
      from (aahmed31.theaterinfo a join (SELECT room_id from aahmed31.schedule) b on a.room_id = b.room_id)
      group by a.theater_id);
      
select theater_id
into id
from  (select theater_id, count(a.theater_id) c
     from aahmed31.theaterinfo a join (SELECT room_id from aahmed31.schedule) b on a.room_id = b.room_id
      group by theater_id)
where c = count;

select theater_name
into theater
from aahmed31.locations
where theater_id = id;
  
dbms_output.put_line('Theater with the most showings: ' || theater);
  
end query6;