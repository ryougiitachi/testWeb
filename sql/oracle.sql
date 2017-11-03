windowing子句
窗口偏移从句，一般用于分析函数(analytic function)，包括last_value/first_value/sum等，可以指定多行函数over分组中的范围，对于上述例子，如果不加这些从句的话，默认将只显示当前行。
关键词——
current row, unbound, preceding, following, between and,
示例——
LAST_VALUE(SALARY) OVER (PARTITION BY DEPNO ORDER BY SALARY ROW BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING) AS MAX_SALARY
LAST_VALUE(SALARY) OVER (PARTITION BY DEPNO ORDER BY SALARY ROW BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) AS MAX_SALARY
LAST_VALUE(SALARY) OVER (PARTITION BY DEPNO ORDER BY SALARY ROW BETWEEN CURRENT ROW AND UNBOUNDED FOLLOWING) AS MAX_SALARY
LAST_VALUE(SALARY) OVER (PARTITION BY DEPNO ORDER BY SALARY ROW BETWEEN 1 PRECEDING AND UNBOUNDED FOLLOWING) AS MAX_SALARY	--
LAST_VALUE(SALARY) OVER (PARTITION BY DEPNO ORDER BY SALARY ROW BETWEEN 1 PRECEDING AND 2 FOLLOWING) AS MAX_SALARY			--不会超出分组范围
涉及相关函数——
AVG *, CORR *, COVAR_POP *, COVAR_SAMP *, COUNT *, CUME_DIST, DENSE_RANK, FIRST, FIRST_VALUE *, LAG, LAST, LAST_VALUE *, LEAD, MAX *, MIN *, NTILE, PERCENT_RANK, PERCENTILE_CONT, PERCENTILE_DISC, RANK, RATIO_TO_REPORT, REGR_ (Linear Regression) Functions *, ROW_NUMBER, STDDEV *, STDDEV_POP *, STDDEV_SAMP *, SUM *, VAR_POP *, VAR_SAMP *, VARIANCE *


SQL * LOADER简介
用来快速导入数据，只允许导入纯文本。
执行命令与sqlplus类似——sqlldr [userid=]dbuser/dbpass@dbservice control=users.ctl，在NT下，SQL*LOADER的命令为SQLLDR，在UNIX下一般为sqlldr/sqlload。
执行命令可以使用control参数来指定控制文件*.ctl
导入的数据可以直接写入到控制文件末尾，也可以与控制文件分离，在控制文件中指定多个*.csv等文件(这种比较常用)。
数据文件可以是 CSV 文件或者以其他分割符分隔的，数据文件可以用 PL/SQL Developer 或者 Toad 导出，也可以用 SQL *Plus 的  spool 格式化产出，或是 UTL_FILE 包生成。另外，用 Toad 还能直接生成包含数据的控制文件。
主要的options参数(命令行参数优先级更高，如果命令行存在参数将覆盖控制参数文件中的参数)
log -- 记录导入时的日志文件，默认为 控制文件(去除扩展名).log
bad -- 坏数据文件，默认为 控制文件(去除扩展名).bad
data -- 数据文件，一般在控制文件中指定。用参数控制文件中不指定数据文件更适于自动操作
errors -- 允许的错误记录数，可以用他来控制一条记录都不能错
rows -- 多少条记录提交一次，默认为 64
skip -- 跳过的行数，比如导出的数据文件前面几行是表头或其他描述
例如，
假设有表如下
create table users(
    user_id number,           --用户 ID
    user_name varchar2(50),   --用户名
    login_times number,       --登陆次数
    last_login date           --最后登录日期
);
1.使用控制文件与数据文件分离法
生成数据文件内容
"   ","USER_ID","USER_NAME","LOGIN_TIMES","LAST_LOGIN"
"1","1","Unmi","3","2009-1-5 20:34:44"
"2","2","","5","2008-10-15"
"3","3","隔叶黄莺","8","2009-1-2"
"4","4","Kypfos","",""
"5","5","不知秋","1","2008-12-23"
建立一个控制文件 users.ctl
OPTIONS (skip=1,rows=128) -- sqlldr 命令显示的选项可以写到这里边来,skip=1 用来跳过数据中的第一行
LOAD DATA
INFILE "users_data.csv" --指定外部数据文件，可以写多个 INFILE "another_data_file.csv" 指定多个数据文件
--这里还可以使用 BADFILE、DISCARDFILE 来指定坏数据和丢弃数据的文件，
truncate --操作类型，用 truncate table 来清除表中原有记录
INTO TABLE users -- 要插入记录的表
Fields terminated by "," -- 数据中每行记录用 "," 分隔
Optionally enclosed by '"' -- 数据中每个字段用 '"' 框起，比如字段中有 "," 分隔符时
trailing nullcols --表的字段没有对应的值时允许为空
(
  virtual_column FILLER, --这是一个虚拟字段，用来跳过由 PL/SQL Developer 生成的第一列序号
  user_id number, --字段可以指定类型，否则认为是 CHARACTER 类型, log 文件中有显示
  user_name,
  login_times,
  last_login DATE "YYYY-MM-DD HH24:MI:SS" -- 指定接受日期的格式，相当用 to_date() 函数转换
)
操作类型
1) insert     --为缺省方式，在数据装载开始时要求表为空
2) append  --在表中追加新记录
3) replace  --删除旧记录(用 delete from table 语句)，替换成新装载的记录
4) truncate --删除旧记录(用 truncate table 语句)，替换成新装载的记录
2.使用控制数据集合法
OPTIONS (skip=1,rows=128) -- sqlldr 命令显示的选项可以写到这里边来,skip=1 用来跳过数据中的第一行
LOAD DATA
INFILE *  -- 因为数据同控制文件在一起，所以用 * 表示
append    -- 这里用了 append 来操作，在表 users 中附加记录 
INTO TABLE users
when LOGIN_TIMES<>'8'  -- 还可以用 when 子句选择导入符合条件的记录
Fields terminated by ","
trailing nullcols
(
  virtual_column FILLER, --跳过由 PL/SQL Developer 生成的第一列序号
  user_id "user_seq.nextval", --这一列直接取序列的下一值，而不用数据中提供的值
  user_name "'Hi '||upper(:user_name)",--,还能用SQL函数或运算对数据进行加工处理
  login_times terminated by ",", NULLIF(login_times='NULL') --可为列单独指定分隔符
  last_login DATE "YYYY-MM-DD HH24:MI:SS" NULLIF (last_login="NULL") -- 当字段为"NULL"时就是 NULL
)
BEGINDATA --数据从这里开始
   ,USER_ID,USER_NAME,LOGIN_TIMES,LAST_LOGIN
1,1,Unmi,3,2009-1-5 20:34
2,2,Fantasia,5,2008-10-15
3,3,隔叶黄莺,8,2009-1-2
4,4,Kypfos,NULL,NULL
5,5,不知秋,1,2008-12-23
参考：http://www.cnblogs.com/flish/archive/2010/05/31/1748221.html
参考：http://www.cnblogs.com/binking/archive/2006/11/21/567587.html
