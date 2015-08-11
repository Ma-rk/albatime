package at.supp;

import java.util.HashMap;
import java.util.Map;

public class ResultFac {
	public static Map<String, Object> rf() {
		return rf(true, null, null);
	}

	public static Map<String, Object> rf(Object data) {
		return rf(true, null, data);
	}

	public static Map<String, Object> rf(Boolean result, String errorCoe) {
		return rf(false, errorCoe, null);
	}

	private static Map<String, Object> rf(Boolean result, String errorCoe, Object data) {
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("result", result);
		if (!result) {
			resultMap.put("errorCode", errorCoe);
		}
		if (data != null) {
			resultMap.put("data", data);
		}
		return resultMap;
	}
}
