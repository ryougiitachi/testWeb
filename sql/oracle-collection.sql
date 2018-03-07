--参考文章链接
--http://www.cnblogs.com/gkl0818/archive/2009/02/25/1397769.html

--Oracle中主要存在三种集合类型：
--Associative arrays, Nested tables, varrays，即关联数组、嵌套表、变量数组。
--三种集合类型，除了关联数组只能在PL/SQL中使用以外，其他两种类型在Database和PL/SQL中都可以使用。

--关联数组
--在PL/SQL环境下定义形如(不能在SQL环境用)：
TYPE T_ASSOCIATIVE_ARRAYS IS TABLE OF CHAR(10) INDEX BY PLS_INTEGER;
--该类型集合是一维、无边界、稀疏的，只能用于PL/SQL环境。
--该类型集合无类型构造函数，在使用时不用初始化，直接为某一个下标赋值即可，也不用使用EXTEND扩展空间；
--由于下表不连续，是要使用FIRST/LAST/NEXT/PRIOR来确定遍历元素。
--其中index-by参数后面接的类型有
--BINARY_INTEGER（9i R2 之前只能使用该类型）
--PLS_INTEGER
--POSITIVE
--NATURAL
--SIGNTYPE
--VARCHAR2(32767);
--table.column%TYPE;
--cursor.column%TYPE;
--package.variable%TYPE;
--package.subtype;
--PL/SQL执行块的实例
DECLARE 
	TYPE T_ASSOCIATIVE_ARRAYS IS TABLE OF VARCHAR2(10) INDEX BY PLS_INTEGER;	--定义
	V_AA T_ASSOCIATIVE_ARRAYS;
	V_INDEX PLS_INTEGER;														--作为行数变量，需要和定义的关联数组的index-by后面的类型一样。
BEGIN 
	V_AA(202020):='aaa';
	V_AA(-125):='bbb';					--行数可以为负数
	V_AA(88):='ccc';					--赋值，row number 可以为任何整数，且可以跳跃（稀疏的），也不用按照顺序赋值，其内部最终按照 row number 排序
	V_INDEX := V_AA.FIRST;
	V_INDEX := V_AA.NEXT(V_INDEX);
	DBMS_OUTPUT.PUT_LINE(V_AA(V_INDEX));
END;
/

--嵌套表
--在SQL环境下定义形如：
CREATE OR REPLACE TYPE T_NESTED_TABLE AS TABLE OF CHAR(10);
--该类型集合是一维、无边界的，起初是紧凑的，在使用过程中经过删除操作可能变得稀疏，可用于PL/SQL环境与SQL环境；
--该类型集合有类型构造函数，在使用时必须初始化，并且赋值之前分配空间，使用EXTEND扩展集合空间；
--PL/SQL执行块的实例
DECLARE 
	TYPE T_NESTED_TABLE IS TABLE OF VARCHAR2(10);
	V_NT1 T_NESTED_TABLE;
BEGIN 
	V_NT1 := T_NESTED_TABLE();		--创建集合实例，需要使用构造函数
	V_NT1.EXTEND(5);				--使用前分配空间
	V_NT1(1) := 'ccc';				--行数最小值为1
	V_NT1(2) := 'ccc';
	FOR V_ROWNUM IN V_NT1.FIRST .. V_NT1.LAST 
	LOOP 
		DBMS_OUTPUT.PUT_LINE(V_NT1(V_ROWNUM));
	END LOOP;
END;
/

--变量数组(Variable-sized arrays)
--在SQL环境下定义形如：
CREATE OR REPLACE TYPE T_VARRAY AS VARRAY(10) OF CHAR(10);
--该类型集合是一维的、有边界的、紧凑的，可用于PL/SQL环境与SQL环境；
--该类型集合有构造函数，在使用时必须初始化，并且在赋值前分配空降，但最大不能超过限定的长度，使用EXTEND扩展集合空间，且元素是有顺序的；
--PL/SQL执行块的实例
DECLARE 
	TYPE T_VARRAY IS VARRAY(10) OF VARCHAR2(10);
	V_VARRAY T_VARRAY;
BEGIN 
	V_VARRAY := T_VARRAY();
	V_VARRAY.EXTEND(5);						--使用前先分配空间最大值不能超过最大范围
	V_VARRAY(1) := 'eee';					--行数最小值为1
END;
/

--集合涉及的方法
--常用的集合方法有COUNT/DELETE/EXISTS/EXTEND/FIRST/LAST/LIMIT/PRIOR/NEXT/TRIM
--异常的情况可参考文章中的介绍，再次介绍函数的意思
--COUNT——无参数，返回集合元素个数
--DELETE——可有参可无参，
--EXISTS——有参数，判断指定行是否存在
--EXTEND——可有参可无参，
--FIRST——无参数，
--LAST——无参数，
--LIMIT——无参数，返回变量数组最大范围
--PRIOR——有参数，
--NEXT——有参数，
--TRIM——可有参可无参，删除末为指定数量的行
