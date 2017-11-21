@echo off
rem 最好在节点目录下中新建一个rabbitmq-env-conf.bat配置好该配置的参数，别用默认的，并且把windows目录下的cookie文件覆盖到当前user目录下；
rem 在当前用户环境变量中设置一个RABBITMQ_BASE，这个变量与RABBITMQ_HOME不一样，这个是当前队列节点程序的存储位置，里面有队列db和日志等信息，不设置的话默认会建到APPDATA/ROAMING下面;
REM 在$RABBITMQ_BASE/log下面的日志可以看到启动信息，里面有rabbitmq的home目录，里面放着该主机的cookie文件

rem rabbitmq基本管理命令
rem 参考文章：https://www.cnblogs.com/s648667069/p/6401463.html
rem 启动rabbitmq服务，在windows下默认会启动新建一个名为RabbitMQ的服务
rabbitmq-service.bat start
rabbitmq-service.bat stop
rem 创建虚拟主机vhostRabbit，重复添加会报错Error: vhost_already_exists: vhostRabbit
rabbitmqctl add_vhost vhostRabbit
rem 添加用户，重复添加会报错Error: user_already_exists: testadmin
rabbitmqctl add_user testadmin testadmin123
rem 为用户添加角色(管理员)，重复赋予不报错
rabbitmqctl set_user_tags testadmin administrator
rem 设置用户在虚拟主机上的权限
rabbitmqctl set_permissions -p vhostRabbit testadmin ".*" ".*" ".*"

rem rabbitmq网页管理工具
rem 默认情况下http://localhost:15672是其地址，默认用户名密码皆为guest，该工具会在安装rabbitmq的时候自动安装；
rem 可以使用以下命令启用插件，默认是自动启用的(?)
rabbitmq-plugins enable rabbitmq_management

