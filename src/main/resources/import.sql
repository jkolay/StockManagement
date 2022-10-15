insert into price values(11001,200.05,TO_DATE(sysdate, 'yyyy-mm-dd hh24:mi:ss'),null);
insert into price values(11002,250.07,TO_DATE(sysdate, 'yyyy-mm-dd hh24:mi:ss'),null);
insert into price values(11003,120.05,TO_DATE(sysdate, 'yyyy-mm-dd hh24:mi:ss'),null);
insert into price values(11004,150.07,TO_DATE(sysdate, 'yyyy-mm-dd hh24:mi:ss'),null);

insert into stock values(101,'Amazon',50,11001);
insert into stock values(102,'EBay',5,11002);
insert into stock values(103,'Flipkart',100,11003);
insert into stock values(104,'Myntra',45,11004);

update price set stock_stock_id=101 where id=11001;
update price set stock_stock_id=102 where id=11002;
update price set stock_stock_id=103 where id=11003;
update price set stock_stock_id=104 where id=11004;

insert into wallet values(101,12000);
insert into wallet values(102,15000);

insert into users (user_id,email,password,role,username,wallet_id) values (1012,'xyz@gmail.com','password','ROLE_USER','Saheli',101);
insert into users (user_id,email,password,role,username,wallet_id)  values (1014,'abc@gmail.com','password','ROLE_USER','Shree',102);


insert into user_stock_details (id,number_of_stocks,stock_id,user_id)  values(101,11,101,1012);
insert into user_stock_details (id,number_of_stocks,stock_id,user_id) values(102,15,104,1012)

