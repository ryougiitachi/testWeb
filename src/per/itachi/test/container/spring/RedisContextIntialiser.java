package per.itachi.test.container.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * Spring的Bean中id与name的区别：https://www.cnblogs.com/flying607/p/5132293.html<br/> 
 * 注解@EnableCaching的作用相当于cache:annotation-driven，若要启用cache应该是必须的<br/> 
 * */
@Configuration("spring-context-redis")
@EnableCaching
public class RedisContextIntialiser extends CachingConfigurerSupport {
	
	@Autowired
	private JedisConnectionFactory redisConnectionFactory;

	@Override
	@Bean("redisCacheManager")
	public CacheManager cacheManager() {
		RedisCacheManager cacheManager = RedisCacheManager.create(redisConnectionFactory);
		return cacheManager;
	}

	@Override
	public KeyGenerator keyGenerator() {
		return super.keyGenerator();
	}

	@Override
	public CacheResolver cacheResolver() {
		return super.cacheResolver();
	}

	@Override
	public CacheErrorHandler errorHandler() {
		return super.errorHandler();
	}
	
//	@Bean("void")
//	public void getVoid() {}

}
