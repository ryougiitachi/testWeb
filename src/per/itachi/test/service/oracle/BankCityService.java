package per.itachi.test.service.oracle;

import java.util.List;
import java.util.Map;

import per.itachi.test.pojo.oracle.BankCity;

public interface BankCityService {
	
	BankCity getBankCityByID(int id);
	
	List<BankCity> getAllItems();
	
	Map<Integer, BankCity> mapAllItems(); 
	
	int insertBankCity(BankCity bankCity);
	
	int updBankCityByID(BankCity bankCity);
	
	int delBankCityByID(int id);
}
