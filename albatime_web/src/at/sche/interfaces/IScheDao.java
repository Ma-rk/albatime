package at.sche.interfaces;

import java.util.List;

import at.model.ScheEty;

public interface IScheDao {
	int cleanTbScheDao();

	int insertScheDao(ScheEty sche);

	List<ScheEty> retrieveScheListDao(ScheEty sche);

	int updateScheDao(ScheEty sche);
}
