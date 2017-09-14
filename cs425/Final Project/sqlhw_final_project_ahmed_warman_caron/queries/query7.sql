set define off;

create or replace procedure query7 as

theater VARCHAR2(255);
count NUMBER;
id NUMBER;

begin

select max(x)
into count
from(
  select theater_id, SUM(a) x from 
  (select room_id,a from (  
  select showing_id, SUM(ticket_no) a
  from aahmed31.tickets
  group by showing_id) b join aahmed31.schedule c on b.showing_id=c.showing_id) d join aahmed31.theaterinfo e on d.room_id = e.room_id
  group by theater_id);
      
select theater_id
into id
from(
  select theater_id, SUM(a) x from 
  (select room_id,a from (  
  select showing_id, SUM(ticket_no) a
  from aahmed31.tickets
  group by showing_id) b join aahmed31.schedule c on b.showing_id=c.showing_id) d join aahmed31.theaterinfo e on d.room_id = e.room_id
  group by theater_id)
where x = count;

select theater_name
into theater
from aahmed31.locations
where theater_id = id;
  
dbms_output.put_line('Theater with the most tickets bought: ' || theater);
  
end query7;