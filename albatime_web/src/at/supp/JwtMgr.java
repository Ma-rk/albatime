package at.supp;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import at.model.TokenEty;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;

import net.oauth.jsontoken.JsonToken;
import net.oauth.jsontoken.JsonTokenParser;
import net.oauth.jsontoken.crypto.HmacSHA256Signer;
import net.oauth.jsontoken.crypto.HmacSHA256Verifier;
import net.oauth.jsontoken.crypto.SignatureAlgorithm;
import net.oauth.jsontoken.crypto.Verifier;
import net.oauth.jsontoken.discovery.VerifierProvider;
import net.oauth.jsontoken.discovery.VerifierProviders;

public class JwtMgr {
	private static final String AUDIENCE = "NotReallyImportant";

	private static final String ISSUER = "YourCompanyOrAppNameHere";

	private static final String SIGNING_KEY = "xxfhi84g4g#$g234r@!#rd3!@r@#f$gvj676I8z";

	public static String createJsonWebToken(String userId, Long durationDays) {
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
		token.setExpiration(
				new org.joda.time.Instant(calender.getTimeInMillis() + CC.MS_FOR_ONE_DAY * durationDays));

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

	public static TokenEty verifyToken(String token) {
		try {
			final Verifier hmacVerifier = new HmacSHA256Verifier(SIGNING_KEY.getBytes());

			VerifierProvider hmacLocator = new VerifierProvider() {
				public List<Verifier> findVerifier(String id, String key) {
					return Lists.newArrayList(hmacVerifier);
				}
			};

			VerifierProviders locators = new VerifierProviders();
			locators.setVerifierProvider(SignatureAlgorithm.HS256, hmacLocator);

			net.oauth.jsontoken.Checker checker = new net.oauth.jsontoken.Checker() {
				public void check(JsonObject payload) throws SignatureException {
					// don't throw - allow anything
				}
			};
			// Ignore Audience does not mean that the Signature is ignored
			JsonTokenParser parser = new JsonTokenParser(locators, checker);
			JsonToken jsonToken;
			try {
				jsonToken = parser.verifyAndDeserialize(token);
			} catch (SignatureException e) {
				throw new RuntimeException(e);
			}
			JsonObject payload = jsonToken.getPayloadAsJsonObject();
			TokenEty tokenEty = new TokenEty();
			String issuer = payload.getAsJsonPrimitive("iss").getAsString();
			String userIdString = payload.getAsJsonObject("info").getAsJsonPrimitive("userId").getAsString();
			if (issuer.equals(ISSUER) && !StringUtils.isBlank(userIdString)) {
				tokenEty.setUserId(userIdString);
				tokenEty.setIssued(new DateTime(payload.getAsJsonPrimitive("iat").getAsLong() * 1000));
				tokenEty.setExpires(new DateTime(payload.getAsJsonPrimitive("exp").getAsLong() * 1000));
				return tokenEty;
			} else {
				return null;
			}
		} catch (InvalidKeyException e1) {
			throw new RuntimeException(e1);
		}
	}

	public static String generateJwTokenKey() {
		return new BigInteger(130, new SecureRandom()).toString(32);
	}
}
