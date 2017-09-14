set define off;

create or replace procedure edit_tthread (oper in varchar2, iid in number, itheater in number, iuser in varchar2, itext in varchar2) as 
                                     
begin

if oper = 'ins' then
      insert into aahmed31.theaterthreads
      values (iid,itheater,iuser,itext); 
       end if;
            
if oper = 'del' then
      delete from aahmed31.theaterthreads
              where id=iid;
        end if;

if oper = 'upd' then
      update aahmed31.theaterthreads
      set theater_id=itheater,username=iuser,text=itext
       where id=iid;
  end if;
      
end edit_tthread;