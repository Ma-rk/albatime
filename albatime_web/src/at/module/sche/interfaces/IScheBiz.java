package at.module.sche.interfaces;

import java.util.List;

import at.model.ScheEty;

public interface IScheBiz {
	void insertScheBiz(ScheEty sche);

	List<ScheEty> retireveScheListBiz(ScheEty sche);

	void updateScheBiz(ScheEty sche);
}
