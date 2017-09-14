set define off;

create or replace procedure edit_movies (oper in varchar2, iid in number, ititle in varchar2, iname in varchar2, idesc in varchar2) as
                                     
begin

if oper = 'ins' then
      insert into aahmed31.movies
      values (iid,ititle,iname,idesc); 
       end if;
            
if oper = 'del' then
      delete from aahmed31.movies
              where movie_id=iid;
        end if;

if oper = 'upd' then
      update aahmed31.movies
      set title = ititle, directorname=iname,description=idesc
       where movie_id=iid;
  end if;
      
end edit_movies;