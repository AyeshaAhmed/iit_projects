set define off;

create or replace procedure edit_emp_sch (oper in varchar2, iemp_id in number, ijob_date in date, 
                                      itheater_id in number, ijob_type in varchar2) as
begin

if oper = 'ins' then
      insert into aahmed31.empschedule
      values (iemp_id, 
            ijob_date, 
            itheater_id, 
            ijob_type); 
            
       end if;
            
if oper = 'del' then
      delete from aahmed31.empschedule
              where emp_id = iemp_id;
        end if;

if oper = 'upd' then
      update aahmed31.empschedule
      set job_date = ijob_date, 
          theater_id = itheater_id, 
          job_type = ijob_type 
       where emp_id = iemp_id;
  end if;
      
end edit_emp_sch;