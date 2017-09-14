set define off;

create or replace procedure edit_manage (oper in varchar2, iid in varchar2, itheater in varchar2, itype in varchar2, iuser in varchar2, ipass in varchar2, iname in varchar2, iaddr in varchar2, iphone in varchar2, issn in varchar2) as 
                                     
begin

if oper = 'ins' then
      insert into aahmed31.management
      values (iid,itheater,itype,iuser,ipass,iname,iaddr,iphone,issn); 
       end if;
            
if oper = 'del' then
      delete from aahmed31.management
              where manager_id = iid;
        end if;

if oper = 'upd' then
      update aahmed31.management
      set theater_id = itheater, man_type = itype, username=iuser, sched_password = ipass, man_name = iname, address = iaddr, phone=iphone,ssn=issn
       where manager_id = iid;
  end if;
      
end edit_manage;