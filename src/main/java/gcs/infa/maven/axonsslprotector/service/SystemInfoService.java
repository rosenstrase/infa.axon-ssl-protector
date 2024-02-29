package gcs.infa.maven.axonsslprotector.service;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SystemInfoService {
	public String getHostname() {
		try {
			InetAddress localHost = InetAddress.getLocalHost();
			String hostname = localHost.getHostName();
			return hostname;
		} catch (UnknownHostException e) {
			System.err.println("Unable to get the hostname of the local machine.");
			e.printStackTrace();
		}
		return "unknown-host";
	}

}

