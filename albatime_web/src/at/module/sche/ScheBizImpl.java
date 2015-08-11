package at.module.sche;

import java.util.List;

import at.model.ScheEty;
import at.module.sche.interfaces.IScheBiz;
import at.module.sche.interfaces.IScheDao;

public class ScheBizImpl implements IScheBiz {
	/*
	 * DI codes
	 */
	private IScheDao scheDao;

	public void setScheDao(IScheDao scheDao) {
		this.scheDao = scheDao;
	}

	public void insertScheBiz(ScheEty sche) {
		sche.setAsNormalStus();
		scheDao.insertScheDao(sche);
	}

	public List<ScheEty> retireveScheListBiz(ScheEty sche) {
		sche.setAsNormalStus();
		return scheDao.retrieveScheListDao(sche);
	}

	public void updateScheBiz(ScheEty sche) {
		scheDao.updateScheDao(sche);
	}

	public void deleteScheBiz(ScheEty sche) {
		sche.setAsDeletedStus();
		scheDao.deleteScheDao(sche);
	}
}
