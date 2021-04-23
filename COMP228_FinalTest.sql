CREATE TABLE Student (
   stID char(9) NOT NULL,
   stName varchar (20),
   stFees numeric(8,2),
   PRIMARY KEY (stID)
);
insert into Student values('301101411','Mark Louis', 5500.96);
insert into Student values('301101412','Hitin Sarin', 6000.05);
insert into Student values('301101413','Sam Bethovan', 5320.73);
insert into Student values('301101414','Benjamin Moore', 7000.25);
insert into Student values('301101415','Baljeet Sidhu', 6700.86);
insert into Student values('301101416','Suzanne Miller', 4900.5);
insert into Student values('301101417','Mary May', 6200.89);
insert into Student values('301101418','Ken Qalid', 5100.25);
insert into Student values('301101419','Shanaya Khanna', 7200.14);
insert into Student values('301101420','Heena Kausar', 5000.54);

commit;

