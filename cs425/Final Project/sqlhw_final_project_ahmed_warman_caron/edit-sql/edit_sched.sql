set define off;

create or replace procedure edit_sched (oper in varchar2, iid in number, imovid in number, irmid in number, idate in date,iprice in number, isold in number) as

                                     
begin

if oper = 'ins' then
      insert into aahmed31.schedule
      values (iid,imovid,irmid,idate,iprice,isold); 
       end if;
            
if oper = 'del' then
      delete from aahmed31.schedule
              where showing_id=iid;
        end if;

if oper = 'upd' then
      update aahmed31.schedule
      set movie_id=imovid,room_id=irmid,show_date=idate,ticket_price=iprice,tickets_sold=isold
       where showing_id=iid;
  end if;
      
end edit_sched;