--参考文章
--https://baike.baidu.com/item/PL%2FSQL/8564979
--PL/SQL是Oracle自己开发出来的一套可编程sql，该词的意思是过程化sql语言。可被认为是一种编程语言，具有一般编程语言中的块操作、条件判断、嵌套、循环等特性。
--不同于外部程序调用，因为plsql执行过程中的语句都是在数据库内部进行，所以理论上plsql的执行效率是比外部程序调用多个sql语句要高的。
--在其他的数据库中有一些类似于plsql的数据库编程语言，比如：
--SybaseASE、Microsoft SQL Server的Transact-SQL，PostgreSQL数据库的PL/pgSQL（模仿PL/SQL）和IBM DB2的SQL PL，都符合ISOSQL的SQL/PSM标准。
DECLARE 
BEGIN 
END;
/
--如果与普通sql混用会涉及上下文切换。
