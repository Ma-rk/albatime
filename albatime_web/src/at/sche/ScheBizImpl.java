package at.sche;

import java.util.List;

import at.model.ScheEty;
import at.sche.interfaces.IScheBiz;
import at.sche.interfaces.IScheDao;

public class ScheBizImpl implements IScheBiz {
	/*
	 * DI codes
	 */
	private IScheDao scheDao;

	public void setScheDao(IScheDao scheDao) {
		this.scheDao = scheDao;
	}

	public int insertScheBiz(ScheEty sche) {
		sche.setAsNormalStus();
		return scheDao.insertScheDao(sche);
	}

	public List<ScheEty> retireveScheListBiz(ScheEty sche) {
		return scheDao.retrieveScheListDao(sche);
	}

	public int updateScheBiz(ScheEty sche) {
		sche.setAsUpdatedStus();
		return scheDao.updateScheDao(sche);
	}

}
