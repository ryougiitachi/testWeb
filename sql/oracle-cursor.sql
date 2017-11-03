--REF CURSOR
--属于游标变量，与游标不同的是可以不用绑定指定的查询，相比游标更加灵活。
--游标变量分为强类型弱类型，同普通游标一样具有四属性：rowcount/found/notfound/isopen
DECLARE 
	TYPE CURSOR_STRONG IS REF CURSOR RETURN T_USER%ROWTYPE;		--强类型
	TYPE CURSOR_WEAK IS REF CURSOR;								--弱类型
	V_C_STRONG			CURSOR_STRONG;
	V_C_WEAK			CURSOR_WEAK;
BEGIN
END;
/
--SYS_REFCURSOR为系统预定义游标变量，可以直接定义游标变量
DECLARE
	V_CURSOR	SYS_REFCURSOR;
BEGIN
END;
/
--使用过程步骤
DECLARE
	V_CURSOR	SYS_REFCURSOR;
	V_USER_ID	NUMBER;
BEGIN
	V_USER_ID := 1;
	OPEN V_CURSOR FOR SELECT USER_ID, USERNAME FROM T_USER WHERE USER_ID = V_USER_ID;
	WHILE (V_CURSOR %FOUND) LOOP
		FETCH V_CURSOR INTO PV_USER_ID, PV_USERNAME;
	END LOOP;
	CLOSE V_CURSOR;
END;
/
--参考文章：http://blog.csdn.net/indexman/article/details/17118975
--参考文章：http://www.cnblogs.com/heshan664754022/archive/2013/05/22/3092437.html
