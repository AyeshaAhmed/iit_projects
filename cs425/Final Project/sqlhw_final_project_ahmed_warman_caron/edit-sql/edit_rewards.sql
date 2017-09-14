set define off;

create or replace procedure edit_rewards (oper in varchar2, itheater_id in number, itheater in varchar2, imovie_points in number, ireview_points in number, ilevel_name in varchar2, ioffers in varchar2, ideals in varchar2) as 
                                     
begin

if oper = 'ins' then
      insert into aahmed31.rewards
      values (itheater_id,imovie_points,ireview_points,ilevel_name,ioffers,ideals); 
       end if;
            
if oper = 'del' then
      delete from aahmed31.rewards
              where theater_id = itheater_id
              and level_name=ilevel_name;
        end if;

if oper = 'upd' then
      update aahmed31.rewards
      set movie_points=imovie_points,review_points=ireview_points,offers=ioffers,deals=ideals
       where theater_id=itheater_id
       and level_name=ilevel_name;
  end if;
      
end edit_rewards;