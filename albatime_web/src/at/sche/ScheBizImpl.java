package at.sche;

import at.sche.interfaces.IScheBiz;
import at.sche.interfaces.IScheDao;

public class ScheBizImpl implements IScheBiz{
	/*
	 * DI codes
	 */
	private IScheDao scheDao;

	public void setScheDao(IScheDao scheDao) {
		this.scheDao = scheDao;
	}

}
