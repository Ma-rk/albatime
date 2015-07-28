package at.sche.interfaces;

import java.util.List;

import at.model.ScheEty;

public interface IScheBiz {
	int createScheBiz(ScheEty sche);

	List<ScheEty> retireveScheListBiz(ScheEty sche);

	int updateScheBiz(ScheEty sche);
}
