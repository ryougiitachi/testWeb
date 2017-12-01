package per.itachi.test.dao.oracle;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import per.itachi.test.pojo.oracle.BankCity;

public interface BankCityDao {
	
	@Cacheable
	BankCity getBankCityByID(int id);
	
	@Cacheable
	List<BankCity> getAllItems(); 
	
	@CachePut
	int insertBankCity(BankCity bankCity);
	
	@CachePut
	int updBankCityByID(BankCity bankCity);
	
	@CacheEvict
	int delBankCityByID(int id);
}
