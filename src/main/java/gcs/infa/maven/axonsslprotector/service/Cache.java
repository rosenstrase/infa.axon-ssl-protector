package gcs.infa.maven.axonsslprotector.service;

import java.util.HashMap;
import java.util.Map;

public class Cache {
	private Map<String, Object> services = new HashMap<>();

	public Object getService(String serviceName) {
		return services.get(serviceName);
	}

	public void addService(String serviceName, Object newService) {
		services.put(serviceName, newService);
	}
}
