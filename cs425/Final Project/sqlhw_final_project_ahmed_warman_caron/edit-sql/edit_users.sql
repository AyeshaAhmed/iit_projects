set define off;

create or replace procedure edit_users (oper in varchar2, iuser in varchar2, ipass in varchar2, iname in varchar2, iccn in varchar2, iphone in varchar2, iemail in varchar2) as 
                                     
begin

if oper = 'ins' then
      insert into aahmed31.theaterusers
      values (iuser,ipass,iname,iccn,iphone,iemail); 
       end if;
            
if oper = 'del' then
      delete from aahmed31.theaterusers
              where username=iuser;
        end if;

if oper = 'upd' then
      update aahmed31.theaterusers
      set password=ipass,name=iname,ccn=iccn,phone=iphone,email=iemail
       where username=iuser;
  end if;
      
end edit_users;