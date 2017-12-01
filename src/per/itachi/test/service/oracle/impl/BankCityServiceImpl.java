package per.itachi.test.service.oracle.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import per.itachi.test.dao.oracle.BankCityDao;
import per.itachi.test.pojo.oracle.BankCity;
import per.itachi.test.service.oracle.BankCityService;

@Service("oracleBankCityService")
public class BankCityServiceImpl implements BankCityService {
	
	@Autowired(required=false)
	private BankCityDao bankCityDao;

	@Override
	public BankCity getBankCityByID(int id) {
		return bankCityDao.getBankCityByID(id);
	}

	@Override
	public List<BankCity> getAllItems() {
		return bankCityDao.getAllItems();
	}

	@Override
	public int insertBankCity(BankCity bankCity) {
		LocalDateTime now = LocalDateTime.now();
		bankCity.setInsertTime(now);
		bankCity.setEditor(bankCity.getCreator());
		bankCity.setUpdateTime(now);
		return bankCityDao.insertBankCity(bankCity);
	}

	@Override
	public int updBankCityByID(BankCity bankCity) {
		return bankCityDao.updBankCityByID(bankCity);
	}

	@Override
	public int delBankCityByID(int id) {
		return bankCityDao.delBankCityByID(id);
	}
}
