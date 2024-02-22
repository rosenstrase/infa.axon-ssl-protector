package gcs.infa.maven.axonsslprotector;

import java.security.cert.X509Certificate;

public interface CertificateWriterInterface {

	public void writeCertificateToFile(X509Certificate certificate, String fileName, String fileExtension);

}
