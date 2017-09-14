set define off;

create or replace procedure edit_loc (oper in varchar2, iid in number, iaddr in varchar2, icity in varchar2, ist in varchar2, izip in varchar2, iname in varchar2) as 
                                     
begin

if oper = 'ins' then
      insert into aahmed31.locations
      values (iid,iaddr,icity,ist,izip,iname); 
       end if;
            
if oper = 'del' then
      delete from aahmed31.locations
              where theater_id = iid;
        end if;

if oper = 'upd' then
      update aahmed31.locations
      set address = iaddr, city = icity, state=ist, zip = izip, theater_name = iname
       where theater_id = iid;
  end if;
      
end edit_loc;