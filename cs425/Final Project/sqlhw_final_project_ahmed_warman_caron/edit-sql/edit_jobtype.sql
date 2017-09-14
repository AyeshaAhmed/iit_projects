set define off;

create or replace procedure edit_jobtype (oper in varchar2, ijob_type in varchar2, 
                                        idescription in varchar2) as 
                                     
begin

if oper = 'ins' then
      insert into aahmed31.jobtypes
      values (ijob_type, 
            idescription); 
       end if;
            
if oper = 'del' then
      delete from aahmed31.jobtypes
              where job_type = ijob_type;
        end if;

if oper = 'upd' then
      update aahmed31.jobtypes
      set description = idescription
       where job_type = ijob_type;
  end if;
      
end edit_jobtype;