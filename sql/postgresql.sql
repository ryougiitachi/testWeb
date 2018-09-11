psql命令前都需要加反斜杠\
\echo	 显示字符串，显示环境变量前加:，结尾不用加分号;
\set	 设置环境变量，结尾不用加分号

POSTGRESQL中的设定信息是以环境变量的形式存在的，并且区分大小写
\echo :AUTOCOMMIT 
也可以用show来查看环境变量(不知为什么会报错)
show AUTOCOMMIT;
自动提交的环境变量——AUTOCOMMIT
\set AUTOCOMMIT off;

start with / connect by 
