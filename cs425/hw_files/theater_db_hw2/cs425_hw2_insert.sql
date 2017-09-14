insert all
  into Locations values(1, '5005 Lawler Ave', 'Entrance 3', 'Niles', 'IL', '60714', 'TCBT Niles')
  into Locations values(2, '4444 Devon Ave', null, 'Chicago', 'IL', '60659', 'TCBT Chicago')  
select * from dual;
  
insert all
  into TheaterInfo values(1, 1, 101, 200, 'IMAX 4D')
  into TheaterInfo values(2, 1, 102, 300, 'Regular')
  into TheaterInfo values(3, 1, 103, 400, 'Regular')
  into TheaterInfo values(4, 2, 101, 200, 'IMAX 4D')
  into TheaterInfo values(5, 2, 102, 350, 'IMAX 4D')
  into TheaterInfo values(6, 2, 103, 500, 'Regular')
select * from dual;
  
insert all
  into Movies values('Jurassic Park', 'Steven Spielberg', 'horror', 'adventure', 'sci-fi')
  into Movies values('Harry Potter', 'Chris Columbus', 'fiction', 'adventure', 'fantasy')
select * from dual;

insert all
  into Stars values(1, 'Jurassic Park', 'Jeff Goldblum', 'Sam Neill', 'Laura Dern')
  into Stars values(2, 'Harry Potter', 'Daniel Radcliffe', 'Rupert Grint', 'Emma Watson')
select * from dual;

insert all
  into Schedule values(1, 1, to_date('12/13/2015','mm/dd/yyyy'), '6:00', 'Jurassic Park', 5.50, 150)
  into SCHEDULE values(2, 5, to_date('11/20/2015','mm/dd/yyyy'), '5:30', 'Harry Potter', 4.35, 200)
select * from dual;

insert all
  into CC values('1234567891234567', 'Foo Fighter', 'Visa', '876', to_date('12/13/2020','mm/dd/yyyy'))
  into CC values('9876543219876543', 'Emma Stone', 'Discover', '425', to_date('11/20/2018','mm/dd/yyyy'))
  into CC values('7418529637418529', 'Dob Bole', 'Visa', '876', to_date('12/13/2020','mm/dd/yyyy'))
select * from dual;

insert all
  into REGISTEREDUSERS values(1, null, null, 'Foo Fighter', '1234567891234567', '4745 Laramie Ave', null, 'Morton Grove', 'IL', '60067', null, null)
  into REGISTEREDUSERS values(2, 'FlowerPower', 'Flower234', 'Laura Stone', '9876543219876543', '4253 Knox Blvd', 'Apt 2', 'Skokie', 'IL', '60077', '8475129632', 'LStone@example.com')
  into REGISTEREDUSERS values(3, 'GuyMan', 'NObounds43', 'Dob Bole', '7418529637418529', '3741 Wabash St', 'Apt 210', 'Chicago', 'IL', '60625', '7735814267', 'dobbole@example.com')
select * from dual;

insert all
  into TICKETS values(1, 2, to_date('11/01/2015','mm/dd/yyyy'), 1)
  into TICKETS values(2, 2, to_date('11/02/2015','mm/dd/yyyy'), 4)
  into TICKETS values(3, 1, to_date('11/08/2015','mm/dd/yyyy'), 2)
  into TICKETS values(1, 2, to_date('11/02/2015','mm/dd/yyyy'), 1)
select * from dual;

insert all
  into POINTS values(2, 20, 280)
  into POINTS values(3, 10, 70)
select * from dual;

insert all
  into REWARDS values(1, 'Beginner', 0, 'buy 5 tickets get one free', '10% off first ticket order', 5, 2)
  into REWARDS values(1, 'Silver', 100, 'buy 3 tickets get one free', '10% off popcorn', 5, 2)
  into REWARDS values(1, 'Gold', 200, 'buy 1 ticket get one free', '10% off first week of month ticket order', 5, 2)
  into REWARDS values(1, 'Platinum', 300, '5 free tickets for the year', 'one month free popcorn', 5, 2)
  into REWARDS values(2, 'Beginner', 0, 'buy 5 tickets get one free', '10% off first ticket order', 3, 3)
  into REWARDS values(2, 'Silver', 80, 'buy 2 tickets get one free', '10% off popcorn', 3, 3)
  into REWARDS values(2, 'Gold', 200, 'buy 1 ticket get one free', '5 free popcorns', 3, 3)
  into REWARDS values(2, 'Platinum', 330, '5 free tickets for the year', 'one month free popcorn', 3, 3)
select * from dual;

insert all
  into THEATERTHREADS values(1, 2, 3, 'great place')
  into THEATERTHREADS values(2, 1, 2, 'needs more IMAX')
select * from dual;

insert all
  into THEATERCOMMENTS values(1, 1, 2, 'but the popcorn is not good')
  into THEATERCOMMENTS values(2, 1, 3, 'I agree')
  into THEATERCOMMENTS values(2, 2, 2, 'we should petition')
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
  into MANAGEMENT values(1, 'Owner', null, 'OwnerTCBT', 'TCBTis999', 'Mike', 'Thomson')
  into MANAGEMENT values(2, 'Manager', 1, 'Manage1TCBT', 'ALLmine1432', 'Foo', 'Wang')
  into MANAGEMENT values(3, 'Manager', 2, 'Manage2TCBT', 'myWIFE0786', 'Al', 'Dowson')
  into MANAGEMENT values(4, 'Web Admin', null, 'WebAdminTCBT', 'Admin2345', 'Kate', 'Middleton')
select * from dual;

insert all
  into EMPLOYEES values(1, 'Dale', 'Reed', '2584 Harrison St', '7735486219', '445871236', 1, 1)
  into EMPLOYEES values(2, 'Bob', 'Crown', '3344 Pulaski Ave', '3126547852', '332445879', 1, 1)
  into EMPLOYEES values(3, 'Nancy', 'Dean', '2298 Lowell St', '7732589634', '777882222', 1, 1)
  into EMPLOYEES values(4, 'King', 'Luther', '3695 Harrison St', '3126985555', '555224444', 1, 2)
  into EMPLOYEES values(5, 'Hiedi', 'Big', '3452 Pulaski Ave', '6302147896', '999881111', 1, 2)
  into EMPLOYEES values(6, 'Alpha', 'Gamma', '7721 Lowell St', '8475551269', '666773333', 1, 2)
select * from dual;

insert all
  into EMPSCHEDULE values('8:00', '11/01/2014', 1, 'Janitor', 1)
  into EMPSCHEDULE values('3:00', '10/01/2014', 2, 'Ticket Master', 1)
  into EMPSCHEDULE values('4:00', '01/01/2014', 3, 'Sales Rep', 1)
  into EMPSCHEDULE values('8:00', '05/01/2014', 4, 'Janitor', 2)
  into EMPSCHEDULE values('3:00', '02/01/2014', 5, 'Sales Rep', 2)
  into EMPSCHEDULE values('4:00', '09/01/2014', 6, 'Ticket Master', 2)
select * from dual;








