package at.module.sche;

import java.util.List;

import at.model.ScheEty;
import at.module.sche.interfaces.IScheBiz;

public class ScheBizTx implements IScheBiz {
	/*
	 * DI codes
	 */
	private IScheBiz scheBiz;

	public void setScheBiz(IScheBiz scheBiz) {
		this.scheBiz = scheBiz;
	}

	public void insertScheBiz(ScheEty sche) {
		scheBiz.insertScheBiz(sche);
	}

	public List<ScheEty> retireveScheListBiz(ScheEty sche) {
		return scheBiz.retireveScheListBiz(sche);
	}

	public void updateScheBiz(ScheEty sche) {
		scheBiz.updateScheBiz(sche);
	}

}
