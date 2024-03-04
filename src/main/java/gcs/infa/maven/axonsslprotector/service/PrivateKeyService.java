package gcs.infa.maven.axonsslprotector.service;

import java.io.IOException;
import java.security.PrivateKey;

public interface PrivateKeyService {
	void savePrivateKey(KeyGenerator keyGenerator, String filename) throws IOException;

	void savePrivateKeyToFile(PrivateKey privateKey, String filename) throws IOException;
}
