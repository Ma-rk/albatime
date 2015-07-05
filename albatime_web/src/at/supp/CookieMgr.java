package at.supp;

import javax.servlet.http.Cookie;

public class CookieMgr {
	public static String getCookie(Cookie[] cookies, String name) {
		if (cookies == null || cookies.length == 0)
			return null;

		String jwtokenValueFromCookie = null;

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(name)) {
				jwtokenValueFromCookie = cookie.getValue();
				break;
			}
		}

		if (jwtokenValueFromCookie == null || jwtokenValueFromCookie.isEmpty())
			return null;

		return jwtokenValueFromCookie;
	}
}
