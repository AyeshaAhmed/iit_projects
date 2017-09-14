set define off;

create or replace procedure edit_employees (oper in varchar2, iemp_id in number, iemp_name in varchar2, 
                                      iaddress in varchar2, iphone in varchar2, issn in varchar2, 
                                      ihiredby_id in number) as
begin

if oper = 'ins' then
      insert into aahmed31.employees
      values (iemp_id, 
            iemp_name, 
            iaddress, 
            iphone, 
            issn,
            ihiredby_id); 
            
       end if;
            
if oper = 'del' then
      delete from aahmed31.employees
              where emp_id = iemp_id;
        end if;

if oper = 'upd' then
      update aahmed31.employees
      set emp_name = iemp_name, 
          address = iaddress, 
          phone = iphone, 
          ssn = issn,
          hiredby_id = ihiredby_id
          
      where emp_id = iemp_id;
  end if;
      
end edit_employees;