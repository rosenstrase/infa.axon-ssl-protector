package gcs.infa.maven.axonsslprotector;

public class ServiceLocator {
	private static Cache cache = new Cache();

	public static Object getService(String serviceName) {
		return cache.getService(serviceName);
	}

	public static void registerService(String serviceName, Object service) {
		cache.addService(serviceName, service);
	}
}
