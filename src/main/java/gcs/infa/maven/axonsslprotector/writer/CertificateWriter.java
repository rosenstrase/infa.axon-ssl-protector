package gcs.infa.maven.axonsslprotector.writer;

import java.security.cert.X509Certificate;

public interface CertificateWriter {

	public void writeCertificateToFile(X509Certificate certificate, String fileName, String fileExtension);

}
