set define off;

create or replace procedure query1 (reviewmovie in varchar2) as

review1 varchar2(255);
review2 varchar2(255);
review3 varchar2(255);
idNum NUMBER;
comcount number;

begin

select id
INTO idNum
FROM aahmed31.moviethreads
WHERE movie = reviewmovie;

select max(comment_number)
  into comcount
  from aahmed31.moviecomments
  where thread_id = idNum;


select text
  into review1
  from aahmed31.moviecomments
  where comment_number = comcount and thread_id = idNum; 
                           

select text
  into review2
  from aahmed31.moviecomments
  where comment_number = comcount-1 and thread_id = idNum;
    
select text
  into review3
  from aahmed31.moviecomments
  where comment_number = comcount-2 and thread_id = idNum;
  

  
dbms_output.put_line('3 most recent reviews for ' || reviewmovie || ': ');
dbms_output.put_line(review1);
dbms_output.put_line(review2);
dbms_output.put_line(review3);
  
end query1;

