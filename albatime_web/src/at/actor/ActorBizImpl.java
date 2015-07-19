package at.actor;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.actor.interfaces.IActorBiz;
import at.actor.interfaces.IActorDao;
import at.com.interfaces.IComDao;
import at.model.ActorEty;
import at.supp.CC;

public class ActorBizImpl implements IActorBiz {
	private static final Logger lgr = LoggerFactory.getLogger(ActorBizImpl.class);

	/*
	 * DI codes
	 */
	private IActorDao actorDao;

	public void setActorDao(IActorDao actorDao) {
		this.actorDao = actorDao;
	}

	private IComDao comDao;

	public void setComDao(IComDao comDao) {
		this.comDao = comDao;
	}

	/*
	 * functional methods
	 */

	public int insertActorBiz(ActorEty actor) {
		lgr.debug(CC.GETTING_INTO_4 + "insertActorBiz");
		actor.setAsNormalStus();
		lgr.debug(CC.GETTING_OUT_4 + "insertActorBiz");
		return actorDao.insertActor(actor);
	}

	public List<ActorEty> retireveActorListBiz(ActorEty actor) {
		lgr.debug(CC.GETTING_INTO_4 + "retireveActorListBiz");
		actor.setAsNormalStus();
		List<ActorEty> actorList = actorDao.retireveActorListDao(actor);
		lgr.debug(CC.GETTING_OUT_4 + "retireveActorListBiz");
		return actorList;
	}

	public int updateActorBiz(ActorEty actor) {
		lgr.debug(CC.GETTING_INTO_4 + "updateActorBiz");
		int updateActorResult = actorDao.updateActorDao(actor);
		lgr.debug(CC.GETTING_OUT_4 + "updateActorBiz");
		return updateActorResult;
	}

	public int deleteActorBiz(ActorEty actor) {
		lgr.debug(CC.GETTING_INTO_4 + "deleteActorBiz");
		lgr.debug("deleting User [{}]'s Actor [{}]", actor.getUserId(), actor.getSeq());
		int deleteActorResult = actorDao.deleteActorDao(actor);
		lgr.debug(CC.GETTING_OUT_4 + "deleteActorBiz");
		return deleteActorResult;
	}

	/*
	 * supporting methods
	 */
}
