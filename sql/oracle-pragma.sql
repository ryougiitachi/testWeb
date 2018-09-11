--ORACLE的编译指示/命令，pragmatics的简写，在编译时处理这些指令，而非运行时。属于伪指令，类似于sql的hint，不会改变程序意义。一般在声明区使用(?)

--常用PRAGMA指令
--EXCEPTION_INIT
--格式：PRAGMA EXCEPTION_INIT(exception_name, error_number);
--将一个特定的错误号与程序中所声明的异常标示符关联起来
--有许多oracle错误码系统没有给定义异常名称EXCEPTION_NAME，若想在PLSQL中捕获这个异常，则需要提前声明这种异常名。
DECLARE 
	DEADLOCK_DETECTED	EXCEPTION;						--先声明异常
	PRAGMA EXCEPTION_INIT(DEADLOCK_DETECTED, -60);		--再绑定异常
BEGIN
EXCEPTION
	WHEN DEADLOCK_DETECTED THEN
		ROLLBACK;
END;
/
--AUTONOMOUS_TRANSACTION
--格式：PRAGMA AUTONOMOUS_TRANSACTION
--自治事务，加上该指令表示是一个独立的事务，由于调用主事务main transaction而产生。
--在一个自治事务的执行中，主事务不会影响到自治事务的执行，主事务如果提交或回滚，自治事务不受影响，自治事务回滚，主事务也不会变化。
--自治事务执行块中必须包含rollback/commit，否则编译就报错(或者运行时？)
--该指令没有任何参数
--参考文章：http://blog.itpub.net/26110315/viewspace-729971/
create table test_at(id number,name varchar2(20));
insert into test_at values(1000,'oracle');
insert into test_at values(1001,'db2');
declare
	pragma autonomous_transaction;
begin
	for i in 1002 .. 1010 loop
		insert into test_at values(i,'redis');
	end loop;
	commit;
end;
/
--SERIALLY_REUSABLE
--格式：PRAGMA SERIALLY_REUSABLE
--作用于PACKAGE的声明，可以声明在规范，或者规范和实现体中，不能单独声明在实现体中
--oracle默认将package运行状态放在用户全局区PGA，加上这个选项之后将放到系统全局区SGA，这样每次调用后包的运行状态将被释放，可以连续调用而不受之前的影响。简言之，告诉PL/SQL 的运行时引擎，在数据引用之时不要保持包级数据。
--该指令没有任何参数
--参考文章：https://docs.oracle.com/cd/B28359_01/appdev.111/b28370/seriallyreusable_pragma.htm#LNPLS01346
--参考文章：http://www.cnblogs.com/linbo3168/p/6038311.html

--SQLERRM
