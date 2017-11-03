定义type的格式
package是否外部不能用
oracle的pragma

存储过程参数定义in/out/in out的作用
in相当于将入参引用，在存储过程中使用的时候不能改变其数值，相当于只读，若有改值则会编译报错，不能对其进行NOCOPY修饰
out在引入到执行体内会自动被置成NULL状态，若不对它操作，出来直接是null(?)，可以看做是只写操作，实际上可读写，可以对其进行NOCOPY修饰
in out是in和out的结合，既可读又可写，与out的区别是不会进行NULL初始化
这几种皆属引用操作，操作效率会比较高。

执行存储过程的格式，是否要加()或者execute
oracle可以通过exec[ute]或者call来调用存储过程，其中exec是sqlplus命令，只能在sqlplus中使用(oracle中？)，call是sql命令，没有这方面限制；对于无参数存储过程，exec可以不加括号，call不加括号就会报错。
加不加begin/end块都可以执行存储过程，只不过是为了程序的清晰。

什么事隐式游标
增删改查的DML操作都会用到隐式游标SELECT...INTO.../UPDATE/INSERT/DELETE，也就是SQL这个关键字，不区分大小写。
%found、%notfound、%rowcount在任何DML语句运行之前，这三个游标属性一直是默认值，%isopen对于隐式游标SQL来说一直是false，因为sql总是在执行之后就关闭。

游标属性%rowcount作用，%notfound没开游标是否报错
游标有四个属性——%found(默认为null)、%notfound(默认为null)、%rowcount(默认为0)、%isopen

close油表是否可以在loop内进行

存储过程、事务？

--常用预定义存储过程
--PROCEDURE RAISE_APPLICATION_ERROR(error_number_in IN NUMBER, error_msg_in IN VARCHAR2);
--用于自定义事务异常，错误码-20000到-20999的范围，错误信息最大2k，多于此值系统自截。
--参考文章：http://www.cnblogs.com/caizhanshu/articles/1129642.html
--参考文章：http://blog.csdn.net/daxiang12092205/article/details/19753297
