package tr.com.srdc.cda2fhir.testutil;

import java.util.List;
import java.util.Map;

public class JoltUtil {

	@SuppressWarnings("unchecked")
	public static void copyStringArray(Map<String, Object> source, List<String> target, String key) {
		List<Object> sourceArray = (List<Object>) source.get(key);
		if (sourceArray != null) {
			sourceArray.forEach(e -> {
				String value = (String) e;
				if (value != null) {
					target.add(value);
				}
			});
		}
	}

}
