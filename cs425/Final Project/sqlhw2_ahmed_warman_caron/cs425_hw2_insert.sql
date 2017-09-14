/* TCBT
 * Andrew Caron, Emily Warman, Ayesha Ahmed
 * CS 425
 * HW 2
*/

insert all
  into Locations values(1, '5005 Lawler Ave', 'Niles', 'IL', '60714', 'TCBT Niles')
  into Locations values(2, '4444 Devon Ave', 'Chicago', 'IL', '60659', 'TCBT Chicago')
select * from dual;

insert all
  into TheaterInfo values(1, 1, 101, 200)
  into TheaterInfo values(2, 1, 102, 300)
  into TheaterInfo values(3, 1, 103, 400)
  into TheaterInfo values(4, 2, 101, 200)
  into TheaterInfo values(5, 2, 102, 350)
  into TheaterInfo values(6, 2, 103, 500)
select * from dual;
  
insert all
  into Movies values(1, 'Jurassic Park', 'Steven Spielberg', 'Dinosaurs')
  into Movies values(2, 'Harry Potter 1', 'Chris Columbus', 'Magic')
  into Movies values(3, 'Harry Potter 2', 'Chris Columbus', 'Magic')
  into Movies values(4, 'Harry Potter 3', 'Alfonso Cuaron', 'Magic')
select * from dual;

insert all
  into Stars values(1, 'Jeff Goldblum')
  into Stars values(1, 'Sam Neill')
  into Stars values(1, 'Laura Dern')
  into Stars values(2, 'Daniel Radcliffe')
  into Stars values(2, 'Rupert Grint')
  into Stars values(2, 'Emma Watson')
  into Stars values(3, 'Daniel Radcliffe')
  into Stars values(3, 'Rupert Grint')
  into Stars values(3, 'Emma Watson')
  into Stars values(4, 'Daniel Radcliffe')
  into Stars values(4, 'Rupert Grint')
  into Stars values(4, 'Emma Watson')
select * from dual;

insert all
  into Genre values(1, 'horror')
  into Genre values(1, 'adventure')
  into Genre values(1, 'sci-fi')
  into Genre values(2, 'magic')
  into Genre values(2, 'adventure')
  into Genre values(2, 'fantasy')
  into Genre values(3, 'magic')
  into Genre values(3, 'adventure')
  into Genre values(3, 'fantasy')
  into Genre values(4, 'magic')
  into Genre values(4, 'adventure')
  into Genre values(4, 'fantasy')
select * from dual;

insert all
  into Schedule values(1, 1, 1, to_date('12/13/2015','mm/dd/yyyy'), 5.50, 150)
  into SCHEDULE values(2, 2, 5, to_date('11/20/2015','mm/dd/yyyy'), 4.35, 200)
  into Schedule values(3, 3, 3, to_date('12/13/2015','mm/dd/yyyy'), 3.50, 350)
  into SCHEDULE values(4, 4, 2, to_date('11/20/2015','mm/dd/yyyy'), 4.35, 300)
  into SCHEDULE values(5, 1, 6, to_date('11/20/2015','mm/dd/yyyy'), 5.00, 410)
select * from dual;

insert all
  into CC values('1111222233334444', '123', 'Foo Fighter', 'Visa', to_date('12/01/2020','mm/dd/yyyy'), '4745 Laramie Ave', null, 'Morton Grove', 'IL', '60067')
  into CC values('4444333322221111', '321', 'Emma Stone', 'Discover', to_date('11/01/2018','mm/dd/yyyy'), '4253 Knox Blvd', 'Apt 2', 'Skokie', 'IL', '60077')
  into CC values('6666777788889999', '678', 'Dob Bole', 'American Express', to_date('10/01/2020','mm/dd/yyyy'), '3741 Wabash St', 'Apt 210', 'Chicago', 'IL', '60625')
  into CC values('9999888877776666', '987', 'Kit Blum', 'Visa', to_date('01/01/2022','mm/dd/yyyy'), '3030 Devon Ave', null, 'Sauganash', 'IL', '60646')
select * from dual;

insert all
  into CreditCardCompany values('1111222233334444', '123')
  into CreditCardCompany values('4444333322221111', '321')
  into CreditCardCompany values('6666777788889999', '678')
  into CreditCardCompany values('9999888877776666', '0')
select * from dual;

insert all
  into THEATERUSERS values(null, null, 'Foo Fighter', '1111222233334444', null, null)
  into THEATERUSERS values('loveMovies', 'Movies4ever', 'Laura Stone', '4444333322221111', '8475129632', 'LStone@example.com')
  into THEATERUSERS values('GuyMan', 'NObounds43', 'Dob Bole', '6666777788889999', '7735814267', 'dobbole@example.com')
  into THEATERUSERS values(null, null, 'Kit Blum', '9999888877776666', null, null)
select * from dual;

insert all
  into TICKETS values('1111222233334444', 2, 1, to_date('11/01/2015','mm/dd/yyyy'))
  into TICKETS values('4444333322221111', 4, 4, to_date('11/02/2015','mm/dd/yyyy'))
  into TICKETS values('6666777788889999', 1, 2, to_date('11/08/2015','mm/dd/yyyy'))
  into TICKETS values('1111222233334444', 2, 1, to_date('11/02/2015','mm/dd/yyyy'))
  into TICKETS values('9999888877776666', 5, 1, to_date('10/25/2015','mm/dd/yyyy'))
select * from dual;

insert all
  into POINTS values('loveMovies', 25, 260)
  into POINTS values('GuyMan', 10, 70)
select * from dual;

insert all
  into POINTLEVEL values('Beginner', 0)
  into POINTLEVEL values('Silver', 100)
  into POINTLEVEL values('Gold', 200)
  into POINTLEVEL values('Platinum', 300)
select * from dual;

insert all
  into REWARDS values(1, 4, 2, 'Beginner', 'buy 5 tickets get one free', '10% off first ticket order')
  into REWARDS values(1, 4, 2, 'Silver', 'buy 3 tickets get one free', '10% off popcorn')
  into REWARDS values(1, 4, 2, 'Gold', 'buy 1 ticket get one free', '10% off first week of month ticket order')
  into REWARDS values(1, 6, 2, 'Platinum', '5 free tickets for the year', 'one month free popcorn')
  into REWARDS values(2, 3, 1, 'Beginner', 'buy 5 tickets get one free', '10% off first ticket order')
  into REWARDS values(2, 3, 1, 'Silver', 'buy 2 tickets get one free', '10% off popcorn')
  into REWARDS values(2, 3, 1, 'Gold', 'buy 1 ticket get one free', '5 free popcorns')
  into REWARDS values(2, 5, 3, 'Platinum', '5 free tickets for the year', 'one month free popcorn')
select * from dual;

insert all
  into THEATERTHREADS values(1, 2, 'GuyMan', 'great place')
  into THEATERTHREADS values(2, 1, 'loveMovies', 'needs an IMAX 4D room')
select * from dual;

insert all
  into THEATERCOMMENTS values(1, 1, 'loveMovies', 'but the popcorn is not good')
  into THEATERCOMMENTS values(2, 1, 'GuyMan', 'I agree')
  into THEATERCOMMENTS values(2, 2, 'loveMovies', 'we should petition')
select * from dual;

insert all
  into MOVIETHREADS values(1, 'loveMovies', 4, 'Harry Potter 3', 'Emma Watson', null, 'I just love how Emma fits Hermione''s charater so well.')
  into MOVIETHREADS values(2, 'GuyMan', 1, 'Jurassic Park', null, 'Steven Spielberg', 'Spielberg has made a legendary film that we can never forget.')
select * from dual;

insert all
  into MOVIECOMMENTS values(1, 1, 'loveMovies', 'I loved it too! the intro song is unforgetable! Nostolgic!')
  into MOVIECOMMENTS values(2, 1, 'GuyMan', 'I agree. Hermione has the best fit actor.')
  into MOVIECOMMENTS values(2, 2, 'loveMovies', 'IKR! Even the tiniest detail is perfect!')
select * from dual;

insert all
  into JOBTYPES values('Web Admin', 'manages website and database')
  into JOBTYPES values('Owner', 'Owns TCBT')
  into JOBTYPES values('Manager', 'Manages a TCBT theater')
  into JOBTYPES values('Sales Rep', 'Sells food')
  into JOBTYPES values('Ticket Master', 'Sells tickets at booth')
  into JOBTYPES values('Janitor', 'Cleans a TCBT theater')
select * from dual;

insert all
  into MANAGEMENT values(1, null, 'Owner', 'OwnerTCBT', 'TCBTis999', 'Mike Thomson', '1000 Lake Shore Dr', '888777444', '555114444')
  into MANAGEMENT values(2, 1, 'Manager', 'Manage1TCBT', 'ALLmine1432', 'Foo Wang', '5454 Mulberry Ln', '5554441111', '666225555')
  into MANAGEMENT values(3, 2, 'Manager', 'Manage2TCBT', 'myWIFE0786', 'Al Dowson', '8888 Halsted St', '1112223333', '333221111')
  into MANAGEMENT values(4, null, 'Web Admin', 'WebAdminTCBT', 'Admin2345', 'Kate Middleton', '4242 Dearlove Rd', '6664448888', '777553333')
select * from dual;

insert all
  into EMPLOYEES values(1, 'Dale Reed', '2584 Harrison St', '7735486219', '445871236', 1)
  into EMPLOYEES values(2, 'Bob Crown', '3344 Pulaski Ave', '3126547852', '332445879', 2)
  into EMPLOYEES values(3, 'Nancy Dean', '2298 Lowell St', '7732589634', '777882222', 1)
  into EMPLOYEES values(4, 'King Luther', '3695 Harrison St', '3126985555', '555224444', 3)
  into EMPLOYEES values(5, 'Hiedi Big', '3452 Pulaski Ave', '6302147896', '999881111', 1)
  into EMPLOYEES values(6, 'Alpha Gamma', '7721 Lowell St', '8475551269', '666773333', 3)
select * from dual;

insert all
  into JOBTRAINING values(1, 1, 1, 0)
  into JOBTRAINING values(2, 0, 1, 1)
  into JOBTRAINING values(3, 0, 1, 0)
  into JOBTRAINING values(4, 0, 0, 1)
  into JOBTRAINING values(5, 1, 1, 1)
  into JOBTRAINING values(6, 1, 0, 0)
select * from dual;

insert all
  into EMPSCHEDULE values(1, to_date('11/01/2015','mm/dd/yyyy'), 1, 'Janitor')
  into EMPSCHEDULE values(2, to_date('11/01/2015','mm/dd/yyyy'), 1, 'Ticket Master')
  into EMPSCHEDULE values(3, to_date('11/01/2015','mm/dd/yyyy'), 1, 'Sales Rep')
  into EMPSCHEDULE values(4, to_date('11/01/2015','mm/dd/yyyy'), 2, 'Ticket Master')
  into EMPSCHEDULE values(5, to_date('11/01/2015','mm/dd/yyyy'), 2, 'Sales Rep')
  into EMPSCHEDULE values(6, to_date('11/01/2015','mm/dd/yyyy'), 2, 'Janitor')
select * from dual;
