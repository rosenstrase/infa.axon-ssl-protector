package gcs.infa.maven.axonsslprotector;

import java.io.FileWriter;
import java.security.cert.X509Certificate;

import org.bouncycastle.openssl.jcajce.JcaPEMWriter;

public class CertificateWriter implements CertificateWriterInterface {

	@Override
	public void writeCertificateToFile(X509Certificate certificate, String fileName, String fileExtension) {
		try {
			FileWriter fileWriter = new FileWriter(fileName + fileExtension);
			JcaPEMWriter pemWriter = new JcaPEMWriter(fileWriter);
			pemWriter.write("---BEGIN PRIVATE KEY---\n");
			pemWriter.writeObject(certificate);
			pemWriter.write("\n");
			pemWriter.write("---END PRIVATE KEY---");
			pemWriter.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}