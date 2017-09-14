set define off;

create or replace procedure query3 as

review aahmed31.moviethreads%rowtype;
comcount number;
idNum number;

begin
 
select min(a)
into comcount
from (select thread_id, max(comment_number) a
    from aahmed31.moviecomments group by thread_id);

select thread_id
into idNum
from (select thread_id, max(comment_number) a
    from aahmed31.moviecomments group by thread_id)
where a = comcount;

select *
into review
from aahmed31.moviethreads
where id = idNum;
  
dbms_output.put_line('Least popular thread: ');
dbms_output.put_line('By: ' || review.username);
dbms_output.put_line('Movie: ' || review.movie);
dbms_output.put_line('Star: ' || review.star_name);
dbms_output.put_line('Director: ' || review.director);
dbms_output.put_line('Text: ' || review.text);
  
end query3;