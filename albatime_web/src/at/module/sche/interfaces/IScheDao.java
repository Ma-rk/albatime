package at.module.sche.interfaces;

import java.util.List;

import at.model.ScheEty;

public interface IScheDao {
	void insertScheDao(ScheEty sche);

	List<ScheEty> retrieveScheListDao(ScheEty sche);

	void updateScheDao(ScheEty sche);
}
