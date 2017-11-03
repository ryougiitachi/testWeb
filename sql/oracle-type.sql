--参考文章链接
--http://www.cnblogs.com/advocate/p/3729998.html
--http://blog.csdn.net/hyman_c/article/details/62893574

--什么是RECORD？由单行多列的标量构成的复合结构。可以看做是一种用户自定义数据类型。组成类似于多维数组。可以用来表示表中的一行数据，通常在用游标查询结果抽取数据的时候会用到。
--记录可以直接赋值。RECORD1 :=RECORD2；不能整体比较和判空。
--新建type类型有两种方式，一个是在plsql语句块中用type is record()/is table of;，一个是独立创建create type as object()/create type as table of;
--应该是先定义record/object，再定义table of，要不后面的table of会报找不到的异常
--table of新建collection可以是oracle基本类型(?)，但是不能加括号指定大小
--这样新建出来的是嵌套表
--新建plsql语句块中新建
DECLARE 
	Type T_USER_RECORD IS RECORD(
		USER_ID		NUMBER(20),
		USERNAME	VARCHAR2(100)
	);
	Type T_USER_COLLECTION IS TABLE OF T_USER_RECORD;
BEGIN
END;
/
--在sql环境中独立创建
CREATE TYPE T_USER_ROWTYPE AS OBJECT(
	USER_ID		NUMBER(20),
	USERNAME	VARCHAR2(100)
);
CREATE TYPE T_USER_COLLECTION AS TABLE OF T_USER_ROWTYPE;

--%rowtype在plsql环境中使用，可以用来返回某个表的行类型列定义，算是type is record的简化版，使用起来比较方便。但是每次使用都会查询行类型，所以速度相对较慢(?)。根据实际需求使用。
DECLARE 
	TYPE T_COLLECTION IS TABLE OF T_USER %ROWTYPE;
BEGIN
END
/

--%type在plsql环境中使用，可以用来返回某表某一列的字段类型，使用比较方便，不怕表字段被更改，与%rowtype类似，也是每次运行时检查一遍类型，相对于指定类型速度比较慢(?)。
DECLARE
	TYPE T_USER_RECORD IS RECORD(
		USER_ID		T_USER.USER_ID %TYPE,
		USERNAME	T_USER.USERNAME %TYPE
	);
BEGIN
END;
/

--Oracle中index by binary_integer的作用
--这种新建出来之后是index-by表，类似于程序语言中的哈希散列表，可以用来存储不连续的松散的数值
--一般的table of出来的collection在使用的时候都需要先初始化，并且每次使用时都要.EXTEND。加上这句话之后该collection不用初始化，索引随便用，直接下标1000也行。
--.COUNT返回当前元素数量，.FIRST第一个元素下标，.NEXT下一个元素的下标
--没加的情况
DECLARE
	TYPE T_NUMBER IS TABLE OF NUMBER;		---可以是基本类型
	V_COL T_NUMBER := T_NUMBER();
BEGIN
	--V_COL := T_NUMBER();		--valid?
	V_COL.EXTEND;	--V_COL.EXTEND()
	V_COL(1) := 10;	--下标从一开始
	V_COL.EXTEND;
	V_COL(2) := 100;
END;
/
--加了的情况
DECLARE 
	--前略
	TYPE T_COLLECTION IS TABLE OF T_USER %ROWTYPE INDEX BY BINARY_INTEGER;
	vN	NUMBER;
BEGIN
	T_COLLECTION(1000).USER_ID := 1000;
	T_COLLECTION(1000).USERNAME := 'MEDUSA';
	vN := MyTab.First;
	For varR In vN..MyTab.count					-----vN..MyTab.count ?
	Loop
		DBMS_OUTPUT.PUT_LINE(vN ||'   '||MyTab(vN).rno||'   '||MyTab(vN).rname||'   '||MyTab(vN).rsal);
		vN := MyTab.Next(vN);
	End Loop;
END;
/

--ORACLE中VARRAY可变数组的使用
--参考文章:：http://blog.sina.com.cn/s/blog_14deb4ba00102vh2k.html
--PLSQL语句块中定义
DECLARE 
	TYPE VARRAY_TEST IS VARRAY(10) OF VARCHAR2(100);
	TYPE VARRAY_OBJ IS VARRAY(10) OF T_USER%TYPE;
BEGIN
END;
/
--SQL环境中定义
CREATE TYPE VARRAY_PRIMITIVE AS VARRAY(10) OF NUMBER(38);
CREATE TYPE VARRAY_OBJ AS VARRAY(10) OF OBJECT_NAME;
--在SQL环境中可以改变变长数组类型和嵌套表类型元素大小
ALTER TYPE VARRAY_PRIMITIVE MODIFY ELEMENT TYPE VARCHAR2(1) CASCADE;
--更改变长数组数量
ALTER TYPE VARRAY_OBJ MODIFY LIMIT 5 CASCADE;
--CASCADE选项把更改传播到数据库中的依赖对象。也可以用 INVALIDATE 选项使依赖对象无效
--对于变长数组执行insert的情况，需要整体插入
INSERT INTO table_name VALUES( value, VARRAY_PRIMITIVE( 'xxxx', 'xx', 'x'));
--同理，对于变长数组执行update的情况，需要整体更新
UPDATE table_name SET var_col_name = VARRAY_PRIMITIVE('xxx','xxxxxx') WHERE expr1;


--Oracle的数据集合类型，分三种：
--Varray：固定长度（其他方法可扩展），连续的数组。对应其他程序中的数组。
--Nested table：长度不固定，不连续。对应其他程序中的set，list等。
--Associative Arrays（Known as index-by table）：有索引的集合。对应其他程序中的hashMap。
--这三种都可以在sql环境或者plsql中定义通过TYPE/CREATE TYPE定义。
