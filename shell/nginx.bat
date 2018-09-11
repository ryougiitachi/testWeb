REM Nginx常用命令与配置简介
REM 参考文章：http://zyjustin9.iteye.com/blog/2017394
REM 参考文章：https://www.cnblogs.com/Chiler/p/8027167.html
REM 参考文章：https://www.zhihu.com/question/19571087/answer/133244938 nginx与apache的比较
REM 参考文章：http://www.ha97.com/5646.html Nginx/LVS/HAProxy负载均衡软件的优缺点详解
REM 参考文章：https://www.cnblogs.com/demon89/p/proxy.html ngnix命令
REM nginx的定位是一个高性能http反向代理服务器，也是一个IMAP/POP3/SMTP服务器。设计上使用了epoll和kqueue
REM 相比APACHE，内存占用小，更适合高并发，apache为同步多进程模型，单进程对应但连接，nginx为异步模型，单进程可对应多连接(万级别)，apache适合处理动态请求，nginx适合处理静态请求，
REM 如果需要apache与nginx公用，一般用nginx在前端抗住高并发请求，后端apache负责转发动态请求。
@echo off
rem 启动nginx，如果是在windows环境最好加一个start，因为该命令会一直占用当前命令行，linux可以用nohup&
#start ngnix 
rem 停止ngnix，stop是快速停止，quit是完整有序地退出
#ngnix -s stop
#ngnix -s quit
rem 检查配置文件格式是否正确，但不加载，不启动也能用
#ngnix -t
rem 检查指定配置文件格式是否正确，但不加载，不启动也能用
#nginx -t -c /path/to/nginx.conf
rem 重新打开日志文件
#nginx -s reopen
rem 启动时候配置文件热加载
#ngnix -s reload

rem nginx.conf结构
rem events
rem http
rem 	upstream *
rem 	server */+
rem 		location */+
