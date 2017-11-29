rem 启动redis
rem windows版本有两个配置文件redis.windows.conf和redis.windows-service.conf，默认情况下两者只在log设置上有区别。
#redis-server redis.windows-service.conf
rem 指定启动时最大内存
#redis-server redis.windows.conf --maxmemory 200m
rem 安装redis服务，默认名称为Redis
#redis-server --service-install redis.windows.conf
rem 卸载redis服务
#redis-server --service-uninstall
rem 启动默认的windows服务Redis
#redis-server --service-start
rem 停止默认的windows服务Redis
#redis-server --service-stop
rem 指定参数安装redis服务，默认端口是6379
#redis-server --service-install --service-name RedisService01 --port 10001 redis.windows-service.conf
#redis-server --service-install --service-name RedisService02 --port 10002 redis.windows-service.conf
#redis-server --service-install --service-name RedisService03 --port 10003 redis.windows-service.conf
rem 启动指定的windows服务
#redis-server --service-start --service-name RedisService01
rem 停止指定的windows服务
#redis-server --service-start --service-name RedisService01
rem 关闭指定的windows服务
#redis-server --service-stop --service-name RedisService01

