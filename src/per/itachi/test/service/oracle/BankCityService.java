package per.itachi.test.service.oracle;

import java.util.List;

import per.itachi.test.pojo.oracle.BankCity;

public interface BankCityService {
	
	BankCity getBankCityByID(int id);
	
	List<BankCity> getAllItems(); 
	
	int insertBankCity(BankCity bankCity);
	
	int updBankCityByID(BankCity bankCity);
	
	int delBankCityByID(int id);
}
