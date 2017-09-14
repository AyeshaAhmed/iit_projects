set define off;

create or replace procedure edit_stars (oper in varchar2, iid in number, istar in varchar2) as

                                     
begin

if oper = 'ins' then
      insert into aahmed31.stars
      values (iid,istar); 
       end if;
            
if oper = 'del' then
      delete from aahmed31.stars
              where movie_id=iid
              and starname=istar;
        end if;
 
end edit_stars;