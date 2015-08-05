package at.supp;

import java.util.HashMap;
import java.util.Map;

public class ResultFac {
	public static Map<String, Object> rf(Object data) {
		return rf(true, "NO_ERROR", data);
	}

	public static Map<String, Object> rf(Boolean result, String errorCoe) {
		return rf(false, errorCoe, 0);
	}

	private static Map<String, Object> rf(Boolean result, String errorCoe, Object data) {
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("result", result);
		if (result) {
			resultMap.put("errorCode", "NO_ERROR");
			resultMap.put("data", data);
		} else {
			resultMap.put("errorCode", errorCoe);
			resultMap.put("data", 0);
		}
		return resultMap;
	}
}
