set define off;

create or replace procedure edit_points (oper in varchar2, iusername in varchar2, icurrent_points in number, itotal_points in number) as 
                                     
begin

if oper = 'ins' then
      insert into aahmed31.points
      values (iusername,icurrent_points,itotal_points); 
       end if;
            
if oper = 'del' then
      delete from aahmed31.points
              where username = username;
        end if;

if oper = 'upd' then
      update aahmed31.points
      set current_points=icurrent_points, total_points=itotal_points
       where username = iusername;
  end if;
      
end edit_points;