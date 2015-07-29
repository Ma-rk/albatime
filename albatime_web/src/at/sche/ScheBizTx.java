package at.sche;

import java.util.List;

import at.model.ScheEty;
import at.sche.interfaces.IScheBiz;

public class ScheBizTx implements IScheBiz {
	/*
	 * DI codes
	 */
	private IScheBiz scheBiz;

	public void setScheBiz(IScheBiz scheBiz) {
		this.scheBiz = scheBiz;
	}

	public int createScheBiz(ScheEty sche) {
		return scheBiz.createScheBiz(sche);
	}

	public List<ScheEty> retireveScheListBiz(ScheEty sche) {
		return scheBiz.retireveScheListBiz(sche);
	}

	public int updateScheBiz(ScheEty sche) {
		return scheBiz.updateScheBiz(sche);
	}

}
