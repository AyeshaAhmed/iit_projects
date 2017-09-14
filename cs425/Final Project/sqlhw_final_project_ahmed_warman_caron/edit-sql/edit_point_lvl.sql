set define off;

create or replace procedure edit_ptlvl (oper in varchar2, ilevel_name in varchar2, ilevel_boundary in number) as 
                                     
begin

if oper = 'ins' then
      insert into aahmed31.pointlevel
      values (ilevel_name,ilevel_boundary); 
       end if;
            
if oper = 'del' then
      delete from aahmed31.pointlevel
              where level_name = ilevel_name;
        end if;

if oper = 'upd' then
      update aahmed31.pointlevel
      set level_boundary = ilevel_boundary
       where level_name = ilevel_name;
  end if;
      
end edit_ptlvl;