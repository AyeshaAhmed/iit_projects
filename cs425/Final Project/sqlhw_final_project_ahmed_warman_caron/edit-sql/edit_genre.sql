set define off;

create or replace procedure edit_genre (oper in varchar2, imovie_id in number, 
                                        igenretype in varchar2) as 
                                     
begin

if oper = 'ins' then
      insert into aahmed31.genre
      values (imovie_id, 
            igenretype); 
       end if;
            
if oper = 'del' then
      delete from aahmed31.genre
              where movie_id = imovie_id;
        end if;

if oper = 'upd' then
      update aahmed31.genre
      set genretype = igenretype
       where movie_id = imovie_id;
  end if;
      
end edit_genre;