package per.itachi.test.dao.oracle;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import per.itachi.test.pojo.oracle.BankCity;

/**
 * spring-cache在类头加注解不是必须的
 * */
//@CacheConfig
//@Cacheable
public interface BankCityDao {
	
	/**
	 * cacheNames必须被指定一个，否则运行时会报异常，每个缓存名应该都对应一个RedisCache实例；<br/>
	 * key可选，如果不指定会以一个SimpleKey []作为key，括号中内容与入参有关；<br/>
	 * spring似乎只以参数名和返回值为键值对<br/>
	 * 标签@Cacheable与@CachePut的区别是前者执行前查缓存，后者不查直接改<br/>
	 * 默认情况下spring会把缓存名加上key作为redis键值存入缓存<br/>
	 * target与targetClass两者的区别，前者返回对象，后者返回类<br/>
	 * #root对应org.springframework.cache.interceptor.CacheExpressionRootObject的实例化对象<br/>
	 * */
	@Cacheable(cacheNames={"bankCity"}, sync=true)//
	BankCity getBankCityByID(int id);
	
	@Cacheable(cacheNames={"bankCities"}, key="#root.method.name")//字符串是用单引号 
	List<BankCity> getAllItems(); 
	
	@Cacheable(cacheNames={"bankCities"}, key="methodName")
	@MapKey("id")
	Map<Integer, BankCity> mapAllItems(); 
	
	@CachePut(cacheNames={"bankCity"}, key="#p0.id")
	int insertBankCity(BankCity bankCity);
	
	@CachePut(cacheNames={"bankCity"}, key="#{a0.id}")
	int updBankCityByID(BankCity bankCity);
	
	/**
	 * 直接用参数名称有时候不太好用，单个参数还是不要加花括号比较好
	 * */
	@Caching(evict={@CacheEvict(cacheNames={"bankCity"}, key="#p0"), //allEntries=true这个参数指定时将忽略key
					@CacheEvict(cacheNames={"bankCities"}, key="target")}) 
	int delBankCityByID(int id);
}
