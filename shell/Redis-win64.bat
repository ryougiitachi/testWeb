rem redis-server用于在服务端直接操作配置redis服务器
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
rem 这样写应该是错的
#redis-server --service-install --service-name RedisService01 --port 10001 redis.windows-service.conf
#redis-server --service-install --service-name RedisService02 --port 10002 redis.windows-service.conf
rem 这样应该是对的
#redis-server --service-install redis.windows-service.conf --service-name RedisService03 --port 10003
rem 启动指定的windows服务
#redis-server --service-start --service-name RedisService01
rem 停止指定的windows服务
#redis-server --service-stop --service-name RedisService01
rem 卸载指定的windows服务
#redis-server --service-uninstall --service-name RedisService01


rem redis-cli主要用于登陆redis服务器，默认没有密码
rem 客户端命令下可以执行加值减值查值等命令
#redis-cli -h 127.0.0.1 -p 10001 -a mypass
rem 登陆之后可以执行以下命令
rem 具体可参考http://www.runoob.com/redis/redis-keys.html
rem 注意：redis里面的值是可以互相覆盖的，一个键值a原来为hash的话是可以被覆盖成string的，反过来却不行，到时候再访问就要用相对应的命令访问了，否则会报错，不知道是不是windows版本实现的问题
rem 查看当前所有的键，可以指定键值
#key *
rem 查看某键值的类型，不能用星号
#type archer
rem 新建哈希键，若有重复键值按照后面的为准
#hmset mapServant archer emiya saber Arthur archer gilgamesh assassin sasaki Rider Alexander
rem 查看哈希映射
#hgetall mapServant
