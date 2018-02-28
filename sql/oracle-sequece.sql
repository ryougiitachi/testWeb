--关于cache选项的调试过程：http://blog.itpub.net/17203031/viewspace-717042/
--cache参数会影响IO等性能，数据库每次在用完缓存数据之后会从序列中重新取值，这伴随着写入redo并更新序列等操作，如果不添加cache参数这种相关操作将增加很多倍，而且增加并发锁问题风险。
--可以把序列想象成某种特殊的表，这种表用户不能手动操作，每次数据库取序列数都会读取并更新这个表的操作。cache参数不保证主键连续，若有强制逐渐连续的需求要慎用了。

--在不删的情况下重置序列
--参考文章：https://www.cnblogs.com/programsky/p/4207828.html
SELECT SEQ_TEST.NEXTVAL FROM DUAL; --6
ALTER SEQUENCE SEQ_TEST INCREMENT BY -5; --应该是n-1?
SELECT SEQ_TEST.NEXTVAL FROM DUAL;
ALTER SEQUENCE SEQ_TEST INCREMENT BY 1;

