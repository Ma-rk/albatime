package at.supp;

import com.google.gson.Gson;

/**
 * this class contains Constant Data (String, number, etc...) class name "CC"
 * means "Constant Container"
 */
public class CC {
	public static final Gson gson = new Gson();
	
	public static final long MS_FOR_ONE_DAY = 1000L * 60L * 60L * 24L;
	public static final long DEFAULT_SESSION_DURATION_DAYS = 21L;

	public static final String JWT_TOKEN = "jwToken";
	public static final String USER_ID_IN_COOKIE = "userIdInCookie";
	public static final String USER_TOKEN_SEQ_IN_COOKIE = "userTokenSeqInCookie";

	public static final String GETTING_INTO_2 = "==>";
	public static final String GETTING_OUT_2 = "<==";
	public static final String GETTING_INTO_4 = "====>";
	public static final String GETTING_OUT_4 = "<====";
	public static final String GETTING_INTO_6 = "======>";
	public static final String GETTING_OUT_6 = "<======";

	public static final String ACTOR_STUS_NORMAL = "ACT_STA_01";
	public static final String ACTOR_STUS_DELETED = "ACT_STA_02";
	
	public static final String ACCOUNT_STUS_NORMAL = "USR_STA_01";
	public static final String ACCOUNT_STUS_DEACTIVATED = "USR_STA_02";
	
	public static final String ACCOUNT_TYPE_ALBA = "USR_TYP_01";
	public static final String ACCOUNT_TYPE_OWNER = "USR_TYP_02";

	public static final String TOKEN_STUS_NORMAL = "TKN_STA_01";
	public static final String TOKEN_STUS_EXPIRED = "TKN_STA_02";
}
