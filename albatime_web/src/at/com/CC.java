package at.com;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.google.gson.Gson;

/**
 * this class contains Constant Data (String, number, etc...) class name "CC"
 * means "Constant Container"
 */
public class CC {
	public static final Gson gson = new Gson();
	public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("at_emf");

	public static final int DAYS_FOR_COOKIE_DURATION = 60;
	public static final long SESSION_DURATION_DAYS_FOR_JWT = 21L;
	public static final long MS_FOR_ONE_DAY = 1000L * 60L * 60L * 24L;
	public static final int SECONDS_FOR_ONE_DAY = 60 * 60 * 24;
	public static final int SECONDS_FOR_COOKIE_DURATION = SECONDS_FOR_ONE_DAY * DAYS_FOR_COOKIE_DURATION;

	public static final String JW_TOKEN = "jwToken";
	public static final String USER_ID_IN_COOKIE = "userIdInCookie";
	public static final String USER_TOKEN_SEQ_IN_COOKIE = "userTokenSeqInCookie";

	public static final String GETTING_INTO_2 = "==>";
	public static final String GETTING_OUT_2 = "<==";
	public static final String GETTING_INTO_4 = "====>";
	public static final String GETTING_OUT_4 = "<====";
	public static final String GETTING_INTO_6 = "======>";
	public static final String GETTING_OUT_6 = "<======";

	/*
	 * classification codes
	 */
	public static final String ACCOUNT_STUS_NORMAL = "ACC_STU_01";
	public static final String ACCOUNT_STUS_SLEEP = "ACC_STU_02";
	public static final String ACCOUNT_STUS_DEACTIVATED = "ACC_STU_03";

	public static final String TOKEN_STUS_NORMAL = "TKN_STU_01";
	public static final String TOKEN_STUS_EXPIRED = "TKN_STU_02";

	public static final String ACTOR_STUS_NORMAL = "ACT_STU_01";
	public static final String ACTOR_STUS_DELETED = "ACT_STU_02";

	public static final String CARD_STUS_NORMAL = "CRD_STU_01";
	public static final String CARD_STUS_DELETED = "CRD_STU_02";

	public static final String SCHE_STUS_NORMAL = "SCH_STU_01";
	public static final String SCHE_STUS_DELETED = "SCH_STU_02";
	public static final String SCHE_WORKED_NO = "SCH_WKD_01";
	public static final String SCHE_WORKED_YES = "SCH_WKD_02";
	public static final String SCHE_PAID_NO = "SCH_PAD_01";
	public static final String SCHE_PAID_YES = "SCH_PAD_02";

	public static final String USER_TYPE_ALBA = "USR_TYP_01";
	public static final String USER_TYPE_OWNER = "USR_TYP_02";

	public static final String USER_SEX_MAIL = "USR_SEX_01";
	public static final String USER_SEX_FEMAIL = "USR_SEX_02";
	public static final String USER_SEX_ETC = "USR_SEX_03";

	/*
	 * error codes
	 */
	public static final String ERROR_ACCOUNT_EMAILCHECK_FAIL = "ERR_ACC_01";
	public static final String ERROR_ACCOUNT_REGISTER_FAIL = "ERR_ACC_02";
	public static final String ERROR_ACCOUNT_LOGIN_FAIL = "ERR_USR_03";

	public static final String ERROR_ACTOR_CREATE_FAIL = "ERR_ACT_01";
	public static final String ERROR_ACTOR_RETRIEVE_FAIL = "ERR_ACT_02";
	public static final String ERROR_ACTOR_UPDATE_FAIL = "ERR_ACT_03";
	public static final String ERROR_ACTOR_DELETE_FAIL = "ERR_ACT_04";

	public static final String ERROR_CARD_CREATE_FAIL = "ERR_CRD_01";
	public static final String ERROR_CARD_RETRIEVE_FAIL = "ERR_CRD_02";
	public static final String ERROR_CARD_UPDATE_FAIL = "ERR_CRD_03";
	public static final String ERROR_CARD_DELETE_FAIL = "ERR_CRD_04";

	public static final String ERROR_SCHEDULE_CREATE_FAIL = "ERR_SCH_01";
	public static final String ERROR_SCHEDULE_RETRIEVE_FAIL = "ERR_SCH_02";
	public static final String ERROR_SCHEDULE_UPDATE_FAIL = "ERR_SCH_03";
	public static final String ERROR_SCHEDULE_DELETE_FAIL = "ERR_SCH_04";

	/*
	 * api uri
	 */
	public static final String API_ACCOUNT = "/api/account";
	public static final String API_ACTOR = "/api/actor";
	public static final String API_CARD = "/api/card";
	public static final String API_USER = "/api/user";
	public static final String API_TOKEN = "/api/token";
	public static final String API_SCHE = "/api/sche";
	public static final String PAGE_LOGIN = "/html/login.html";
}
