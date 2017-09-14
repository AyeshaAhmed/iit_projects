set define off;

create or replace procedure edit_training (oper in varchar2, iemp_id in number, ijanitor in number, 
                                      isalesrep in number, iticketmaster in number) as
begin

if oper = 'ins' then
      insert into aahmed31.jobtraining
      values (iemp_id, 
            ijanitor, 
            isalesrep, 
            iticketmaster); 
            
       end if;
            
if oper = 'del' then
      delete from aahmed31.jobtraining
              where emp_id = iemp_id;
        end if;

if oper = 'upd' then
      update aahmed31.jobtraining
      set janitor = ijanitor, 
          salesrep = isalesrep, 
          ticketmaster = iticketmaster 
       where emp_id = iemp_id;
  end if;
      
end edit_training;