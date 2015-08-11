package at.module.sche.interfaces;

import java.util.List;

import at.model.ScheEty;

public interface IScheBiz {
	int insertScheBiz(ScheEty sche);

	List<ScheEty> retireveScheListBiz(ScheEty sche);

	int updateScheBiz(ScheEty sche);
}
