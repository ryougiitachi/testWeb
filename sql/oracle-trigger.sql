--参考文章：http://www.cnblogs.com/liangyihui/p/5886748.html
--参考文章：http://blog.csdn.net/indexman/article/details/8023740/

--在ORACLE中，触发器可以看作是一种特殊的函数存储过程

--触发器语法
/* ***************
 * CREATE [OR REPLACE] TRIGGER trigger_name
 * {BEFORE | AFTER | INSTEAD OF}
 * {INSERT | DELETE | UPDATE [OF column [, column …]]}
 * [OR {INSERT | DELETE | UPDATE [OF column [, column …]]}...]
 * ON [schema.]table_name | [schema.]view_name 
 * [REFERENCING {OLD [AS] old | NEW [AS] new| PARENT as parent}]
 * [FOR EACH ROW ]
 * [WHEN condition]
 * PL/SQL_BLOCK | CALL procedure_name;
 * */

--触发器语句级与行级触发顺序
--BEFORE STATEMENT(ONCE) => BEFORE ROW(FOR EACH ROW) => AFTER ROW(FOR EACH ROW) => AFTER STATEMENT(ONCE)

--替代触发器，INSTEAD OF是ORACLE中特有的一种语法(?)
