package gcs.infa.maven.axonsslprotector.processor;

import java.util.Map;

import org.bouncycastle.asn1.x500.X500Name;

public interface CertificateDataProcessor {
	X500Name generateX500Name(Map<String, String> formData);
}
