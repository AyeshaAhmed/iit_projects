set define off;

create or replace procedure edit_tcom (oper in varchar2, iid in number, inum in number, iuser in varchar2, itext in varchar2) as

                                     
begin

if oper = 'ins' then
      insert into aahmed31.theatercomments
      values (iid,inum,iuser,itext); 
       end if;
            
if oper = 'del' then
      delete from aahmed31.theatercomments
              where thread_id=iid
              and comment_number=inum;
        end if;

if oper = 'upd' then
      update aahmed31.theatercomments
      set username=iuser,text=itext
       where thread_id=iid
       and comment_number=inum;
  end if;
      
end edit_tcom;