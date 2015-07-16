package at.actor;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.actor.interfaces.IActorBiz;
import at.actor.interfaces.IActorDao;
import at.com.interfaces.IComDao;
import at.model.ActorEty;
import at.model.TokenKeyEty;
import at.model.UserEty;
import at.supp.CC;
import at.supp.JwtMgr;
import at.user.interfaces.IUserDao;

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

	public List<ActorEty> retireveActorListBiz(long userId) {
		return actorDao.retireveActorListDao(userId);
	}

	public int updateActorBiz(ActorEty actor) {
		lgr.debug(CC.GETTING_INTO_4 + "updateActorBiz");
		int updateActorResult = actorDao.updateActorDao(actor);
		lgr.debug(CC.GETTING_OUT_4 + "updateActorBiz");
		return updateActorResult;
	}

	public int deleteActorBiz(long actorSeq, long userId) {
		lgr.debug(CC.GETTING_INTO_4 + "deleteActorBiz");
		lgr.debug("deleting User [{}]'s Actor [{}]", userId, actorSeq);
		int deleteActorResult = actorDao.deleteActorDao(actorSeq, userId);
		lgr.debug(CC.GETTING_OUT_4 + "deleteActorBiz");
		return deleteActorResult;
	}

	/*
	 * supporting methods
	 */
}
