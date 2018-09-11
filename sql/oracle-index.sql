--索引类型与原理机制
--参考文章
--https://www.cnblogs.com/iliuyuet/p/4431464.html
--http://orange5458.iteye.com/blog/1165319
--从原理机制角度可以分为普通B树索引(B-tree)、位图索引(Bitmap)、哈希索引(Hash)、聚簇索引(Cluster)
--B树索引(B-tree)
--虽然包括这篇文章在内的很多文章都说oracle索引属于b-tree索引，但从存储特点来看更加接近于B+-TREE；两者都是方便与外部查询的数据查询存储结构，两者都是多阶平衡树并且有序，叶子节点都在同一层；但是相比B-TREE，后者每个节点元素数和子节点数都是一样的(前者子节点数总是比元素数多一个)，所有节点都存储父节点对应元素信息，由此保证了子节点与元素数一致，而且非叶子节点不存储真正的数据元素，所有数据都要到叶子节点查询，所以每次的查询次数是一样的，保证了每次读取数据块的一致性。
--https://zhuanlan.zhihu.com/p/27700617
--根节点块与分支节点块类似，每个索引条目有两个字段，一个存该分支节点下的最小值，即键值最小值，一个存所链接的索引块地址，有四个字节(?)，一个节点块能存的索引条目数量由键值长度与索引块大小决定。叶子节点存储键值最小值和对应数据行的ROWID，其中rowid，如果是普通索引或者本地索引，占6字节，如果是全局索引占10字节。
--普通索引和唯一索引基本都是这种树模型，默认是升序排列，也可以修改成降序。
--位图索引(Bitmap)
--位图索引也是一个B+-TREE模式，会为每一个数据键值维护一个元素，该元素连接到一个数据块把所有的数据行以bit的的形式0/1标记。
--众所周知，位图索引适合在有限数值的列上创建，例如性别一类，不适合主键一类重复值少的列，因为插入更新删除的时候会对每个键值的位图操作一遍，操作成本比较大。
--如果表有分区怎么办？
--创建位图索引语句
--聚簇索引(Cluster)
--与普通索引不同，聚簇索引会把数据表里面的数据按照指定形式排序，所以每个表只能创建一个聚簇索引。
CREATE BITMAP INDEX IDX_USER_GENDER ON T_USER(GENDER);
--全局分区索引
--创建全局分区索引语句
CREATE INDEX IDX_USER_CDATE ON T_USER(CDATE)
TABLESPACE TBS_MYBATIS_IDX 
GLOBAL 
PARTITION BY RANGE (CDATE) 
(
	PARTITION PI_USER_CDATE_201709 values  less than (to_date('201710','YYYYMM')), 
	PARTITION PI_USER_CDATE_201710 values  less than (to_date('201711','YYYYMM')), 
	PARTITION PI_USER_CDATE_201711 values  less than (to_date('201712','YYYYMM')), 
	PARTITION PI_USER_CDATE_201712 values  less than (to_date('201801','YYYYMM')), 
	PARTITION PI_USER_CDATE_201801 values  less than (to_date('201802','YYYYMM')), 
	PARTITION PI_USER_CDATE_MAX values  less than (maxvalue)
);
--在全局分区索引上做添加合并时只能是哈希类型，若对range类型进行此操作则会提示ORA-14640；
--对于range类型索引可是使用split操作；
ALTER INDEX IDX_USER_CDATE SPLIT PARTITION PI_USER_CDATE_201709 AT (TO_DATE('201709', 'YYYYMM')) INTO (PARTITION PI_USER_CDATE_201708, PARTITION PI_USER_CDATE_201709);
ALTER INDEX IDX_USER_CDATE SPLIT PARTITION PI_USER_CDATE_MAX AT (TO_DATE('201803', 'YYYYMM')) INTO (PARTITION PI_USER_CDATE_201802, PARTITION PI_USER_CDATE_MAX);


--测试：唯一索引和在有唯一性约束的列上创建普通索引是否有区别？
--结果：某个有唯一性约束的列在创建约束的时候会自动创建唯一性索引，所以该列索引和普通索引肯定有区别，问题是创建了唯一性索引之后是否还能在该列创建普通索引(不能)。创建唯一索引之后数据库会把该表rowid存入索引中，精确查询数据时总是会最终引导到rowid。

--测试：在某列创建唯一索引和唯一约束是否有区别
--结果：两个都会建立唯一索引，唯一约束会比唯一索引多一个constraint(在plsqldeveloper10里面没有显示，但keys中却显示了，不知是不是因为类型是U所以显示到了keys页)。在plsqldeveloper的indexes页中两种索引的显示也有所不同，索引的可以随意操作，约束的是黑的，应该是因为约束的索引不能直接操作，只能通过更改约束子句修改。
--过程
--创建惟一主键的
CREATE TABLE T_TST_UQ_IDX(
	TST_ID NUMBER,
	TST_NAME VARCHAR2(20)
);
CREATE UNIQUE INDEX IDX_TST_UQ_IDX_UQ_ID ON T_TST_UQ_IDX(TST_ID);
--有唯一性约束的
CREATE TABLE T_TST_UQ_CON(
	TST_ID NUMBER,
	TST_NAME VARCHAR2(20)
);
ALTER TABLE T_TST_UQ_CON ADD CONSTRAINT UQ_TST_UQ_CON_ID UNIQUE(TST_ID);
--ALTER TABLE T_TST_UQ_CON MODIFY CONSTRAINT UQ_TST_UQ_IDX_ID RENAME UQ_TST_UQ_CON_ID;--不对
--ALTER TABLE T_TST_UQ_CON DROP CONSTRAINT UQ_TST_UQ_IDX_ID;
SELECT A.* FROM T_TST_UQ_IDX A;
SELECT A.* FROM T_TST_UQ_CON A;
--DROP TABLE T_TST_UQ_IDX;
--DROP TABLE T_TST_UQ_CON;

--测试：唯一索引与唯一约束是否都能插入多个null
--结果：两个都可以。插入重复数据后的报错是一样的，都是ORA-00001违反唯一性约束；在用is-not-null条件查询的时候两个都没有走索引，不知是不是因为数据量比较少有没有做表分析。
--过程
--索引的
INSERT INTO T_TST_UQ_IDX(TST_ID, TST_NAME) VALUES(NULL, 'NULL1');
INSERT INTO T_TST_UQ_IDX(TST_ID, TST_NAME) VALUES(NULL, 'NULL2');
INSERT INTO T_TST_UQ_IDX(TST_ID, TST_NAME) VALUES(NULL, 'NULL3');
INSERT INTO T_TST_UQ_IDX(TST_ID, TST_NAME) VALUES(NULL, 'NULL4');
INSERT INTO T_TST_UQ_IDX(TST_ID, TST_NAME) VALUES(NULL, 'NULL5');
INSERT INTO T_TST_UQ_IDX(TST_ID, TST_NAME) VALUES(NULL, 'NULL6');
INSERT INTO T_TST_UQ_IDX(TST_ID, TST_NAME) VALUES(NULL, 'NULL7');
INSERT INTO T_TST_UQ_IDX(TST_ID, TST_NAME) VALUES(NULL, 'NULL8');
INSERT INTO T_TST_UQ_IDX(TST_ID, TST_NAME) VALUES(1, 'NAME1');
INSERT INTO T_TST_UQ_IDX(TST_ID, TST_NAME) VALUES(1, 'NAME2');
--约束的
INSERT INTO T_TST_UQ_CON(TST_ID, TST_NAME) VALUES(NULL, 'NULL1');
INSERT INTO T_TST_UQ_CON(TST_ID, TST_NAME) VALUES(NULL, 'NULL2');
INSERT INTO T_TST_UQ_CON(TST_ID, TST_NAME) VALUES(NULL, 'NULL3');
INSERT INTO T_TST_UQ_CON(TST_ID, TST_NAME) VALUES(NULL, 'NULL4');
INSERT INTO T_TST_UQ_CON(TST_ID, TST_NAME) VALUES(NULL, 'NULL5');
INSERT INTO T_TST_UQ_CON(TST_ID, TST_NAME) VALUES(NULL, 'NULL6');
INSERT INTO T_TST_UQ_CON(TST_ID, TST_NAME) VALUES(NULL, 'NULL7');
INSERT INTO T_TST_UQ_CON(TST_ID, TST_NAME) VALUES(NULL, 'NULL8');
--INSERT INTO T_TST_UQ_CON(TST_ID, TST_NAME) VALUES(NULL, 'NULL9'), (NULL, 'NULL10');--oracle不支持
INSERT INTO T_TST_UQ_CON(TST_ID, TST_NAME) VALUES(1, 'NAME1');
INSERT INTO T_TST_UQ_CON(TST_ID, TST_NAME) VALUES(1, 'NAME2');

--测试：是否可以在唯一索引与唯一约束列创建普通索引
--结果：两个都不行。所有的都是报ORA-01408此列列表已索引，应该是单独创建过索引的列就不能再创建了。
--过程
--索引的
CREATE INDEX IDX_TST_UQ_IDX_ID_01 ON T_TST_UQ_IDX(TST_ID);
CREATE UNIQUE INDEX IDX_TST_UQ_IDX_ID_02 ON T_TST_UQ_IDX(TST_ID);
--约束的
CREATE INDEX IDX_TST_UQ_CON_ID_01 ON T_TST_UQ_CON(TST_ID);
CREATE UNIQUE INDEX IDX_TST_UQ_CON_ID_02 ON T_TST_UQ_CON(TST_ID);

--测试：是否可以在唯一索引与唯一约束列创建函数索引
--结果：普通函数索引可以，唯一函数索引不行，两个都是这样。应该是，因为某一列只能创建一个唯一索引吧。
--过程
--索引的
CREATE UNIQUE INDEX IDX_TST_UQ_IDX_ID_FUN ON T_TST_UQ_IDX(DECODE(TST_ID, NULL, -1, TST_ID));
--约束的
CREATE UNIQUE INDEX IDX_TST_UQ_CON_ID_FUN ON T_TST_UQ_CON(DECODE(TST_ID, NULL, -1, TST_ID));
--DROP INDEX IDX_TST_UQ_IDX_ID_FUN;
--DROP INDEX IDX_TST_UQ_CON_ID_FUN;

--测试：是否可以在已经创建唯一索引列上创建唯一性约束？反之呢？
--结果：有唯一索引的可以在该列上创建唯一性约束，反过来则不行，会提示已经有唯一主键；唯一索引与约束分着创建不同于直接创建约束，分着创建索引在indexes是白的，也就是说可以自行控制，修改约束的时候索引也不受影响；但是直接创建唯一性约束每次启动禁用的时候都会新建删除索引，删除约束之后索引页没了。keys里面的值是跟着约束走的，只有一个唯一索引keys页没有列行。
--过程
--索引的
ALTER TABLE T_TST_UQ_IDX ADD CONSTRAINT UQ_TST_UQ_IDX_ID UNIQUE(TST_ID);
--约束的
CREATE UNIQUE INDEX IDX_TST_UQ_CON_UQ_ID ON T_TST_UQ_CON(TST_ID);
