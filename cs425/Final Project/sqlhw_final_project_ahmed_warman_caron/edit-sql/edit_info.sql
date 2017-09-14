set define off;

create or replace procedure edit_info (oper in varchar2, iid in number, itheater in number, inum in number, icap in number) as 
                                     
begin

if oper = 'ins' then
      insert into aahmed31.theaterinfo
      values (iid,itheater,inum,icap); 
       end if;
            
if oper = 'del' then
      delete from aahmed31.theaterinfo
              where room_id=iid;
        end if;

if oper = 'upd' then
      update aahmed31.theaterinfo
      set theater_id=itheater,room_num=inum,capacity=icap
       where room_id=iid;
  end if;
      
end edit_info;