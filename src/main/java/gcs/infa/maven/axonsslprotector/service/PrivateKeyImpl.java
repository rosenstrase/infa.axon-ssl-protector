package gcs.infa.maven.axonsslprotector.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.util.Base64;

public class PrivateKeyImpl implements PrivateKeyService {

	@Override
	public void savePrivateKeyToFile(PrivateKey privateKey, String filename) throws IOException {
		// TODO Auto-generated method stub
		String base64EncodedPrivateKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());

		Files.createDirectories(Paths.get(filename).getParent());

		try (PrintWriter writer = new PrintWriter(
				new OutputStreamWriter(new FileOutputStream(filename), StandardCharsets.UTF_8))) {
			writer.println("-----BEGIN PRIVATE KEY-----");
			writer.println(base64EncodedPrivateKey);
			writer.println("-----END PRIVATE KEY-----");
		}
	}

	@Override
	public void savePrivateKey(KeyGenerator keyGenerator, String filename) throws IOException {
		savePrivateKeyToFile(keyGenerator.getPrivateKey(), filename);
	}

}
