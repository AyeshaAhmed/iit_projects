set define off;

create or replace procedure edit_tickets (oper in varchar2, iccn in varchar2, iid in number, inum in number, idate in date) as 
                                     
begin

if oper = 'ins' then
      insert into aahmed31.tickets
      values (iccn,iid,inum,idate); 
       end if;
            
if oper = 'del' then
      delete from aahmed31.tickets
              where ccn=iccn
              and showing_id=iid
              and date_purchased=idate;
        end if;

if oper = 'upd' then
      update aahmed31.tickets
      set ticket_no=inum
       where ccn=iccn
       and showing_id=iid
       and date_purchased=idate;
  end if;
      
end edit_tickets;