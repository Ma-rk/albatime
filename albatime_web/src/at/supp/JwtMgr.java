package at.supp;

import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.util.Calendar;
import java.util.TimeZone;

import com.google.gson.JsonObject;

import net.oauth.jsontoken.JsonToken;
import net.oauth.jsontoken.crypto.HmacSHA256Signer;

public class JwtMgr {
	private static final String AUDIENCE = "NotReallyImportant";

	private static final String ISSUER = "YourCompanyOrAppNameHere";

	private static final String SIGNING_KEY = "xxfhi84g4g#$g234r@!#rd3!@r@#f$gvj676I8z";
	
	public String createJsonWebToken(String userId, Long durationDays) {
		// Current time and signing algorithm
		Calendar calender = Calendar.getInstance(TimeZone.getTimeZone("GMT+9"));
		HmacSHA256Signer signer;
		try {
			signer = new HmacSHA256Signer(ISSUER, null, SIGNING_KEY.getBytes());
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		}

		// Configure JSON token
		JsonToken token = new net.oauth.jsontoken.JsonToken(signer);
		token.setAudience(AUDIENCE);
		token.setIssuedAt(new org.joda.time.Instant(calender.getTimeInMillis()));
		token.setExpiration(new org.joda.time.Instant(calender.getTimeInMillis() + 1000L * 60L * 60L * 24L * durationDays));

		// Configure request object, which provides information of the item
		JsonObject request = new JsonObject();
		request.addProperty("userId", userId);
		request.addProperty("userBirth", userId);
		request.addProperty("userGender", userId);

		JsonObject payload = token.getPayloadAsJsonObject();
		payload.add("info", request);

		try {
			return token.serializeAndSign();
		} catch (SignatureException e) {
			throw new RuntimeException(e);
		}
	}
	
	
}
