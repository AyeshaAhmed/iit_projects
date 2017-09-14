CS 425 Final Project
Project Name: TCBT

Feature Ownership:
Andrew Caron: made the GUI interface for users, checked table structure, 
connected backend with frontend, wrote queries, wrote insert-update-delete 
queries, wrote the Test Document.

Ayesha Ahmed: (EDITOR) restructured and created tables, inserted test data, 
wrote backend Java code for GUI information parsing, integrated some backend 
functions with the frontend GUI, wrote the Design Document, Demo Slides and README.

Emily Warman: wrote backend Java code for GUI information parsing, 
integrated backend functions with the frontend GUI, made github project, 
wrote queries, checked table structure.

Database Version Used: Oracle SQL Developer 4.1.1

Download Libraries:
jdatepicker: java swing date picker
ojdbc.jar

Files Submitted: 
CS425 Final Presentation, CS425DesignDocument, cs425_final_creat.sql, 
cs425_final_test_data.sql, README.txt, CS 425 Test Document

CS425_final_java_project\src (folder): Admin.java, CreditCard.java, 
CreditCardCo.java, EmployeeList.java, Employees.java, EmployeeTrain.java, 
Guest.java, GUI.java, Location.java, LocationList.java, MovieForum.java, 
MovieList.java, Movies.java, MovieThread.java, Showing.java, ShowingList.java, 
TheaterForum.java, TheaterThread.java, User.java

queries (folder): query1.sql, query2.sql, query3.sql, query4.sql, query5.sql, 
query6.sql, query7.sql, query8.sql, query9.sql, runQueries.sql

edit-sql (folder): edit_CC.sql, edit_CCC.sql, edit_emp_sch.sql, edit_empl.sql, 
edit_genre.sql, edit_info.sql, edit_jobtype.sql, edit_loc.sql, edit_manage.sql, 
edit_mcom.sql, edit_movies.sql, edit_mthread.sql, edit_point_lvl.sql, 
edit_points.sql, edit_rewards.sql, edit_sched.sql, edit_stars.sql, edit_tcom.sql, 
edit_tickets.sql, edit_training.sql, edit_ttheard.sql, edit_user.sql.

How to Compile:
SQL: Open the sql files in Oracle SQL Developer, and click the run script button. 
Compile create.sql, then insert.sql.
JAVA:The plugin "Oracle Database Tools"ù must be installed into Eclipse under 
the Eclipse Marketplace. Open the java files in Eclipse, and click on the 
green play button to compile the code.

How to Run:
SQL: Open all the sql files in Oracle, and click the run script button. 
Run create.sql, then insert.sql, then runQueries.sql last.
JAVA: Import the Java folder then clicking the green play button above will 
run the GUI program. Use the test document information to go throught the GUI 
program to test and see all of its features

Other Information: The following variables must be set as such: 
DBURL =  "jdbc: oracle:thin:@fourier.cs.iit.edu:1521:orcl"ù, 
DBUSER = (your oracle username), DBPASS = (your oracle password)