set define off;

create or replace procedure edit_mthread (oper in varchar2, iid in number, iuser in varchar2, imovid in number, imovie in varchar2, istar in varchar2, idir in varchar2, itext in varchar2) as
                                     
begin

if oper = 'ins' then
      insert into aahmed31.moviethreads
      values (iid,iuser,imovid,imovie,istar,idir,itext); 
       end if;
            
if oper = 'del' then
      delete from aahmed31.moviethreads
              where id=iid;
        end if;

if oper = 'upd' then
      update aahmed31.moviethreads
      set username=iuser,movie_id=imovid,movie=imovie,star_name=istar,director=idir,text=itext
       where id=iid;
  end if;
      
end edit_mthread;