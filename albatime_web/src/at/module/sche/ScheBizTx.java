package at.module.sche;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import at.model.ScheEty;
import at.module.sche.interfaces.IScheBiz;

public class ScheBizTx implements IScheBiz {

	@Autowired
	private IScheBiz scheBiz;

	public void insertScheBiz(ScheEty sche) {
		scheBiz.insertScheBiz(sche);
	}

	public List<ScheEty> retireveScheListBiz(ScheEty sche) {
		return scheBiz.retireveScheListBiz(sche);
	}

	public void updateScheBiz(ScheEty sche) {
		scheBiz.updateScheBiz(sche);
	}

	public void deleteScheBiz(ScheEty sche) {
		scheBiz.deleteScheBiz(sche);
	}
}
