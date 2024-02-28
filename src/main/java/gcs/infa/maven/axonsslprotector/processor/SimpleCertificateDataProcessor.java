package gcs.infa.maven.axonsslprotector.processor;

import java.util.Map;

import org.bouncycastle.asn1.x500.X500Name;

public class SimpleCertificateDataProcessor implements CertificateDataProcessor {

	@Override
	public X500Name generateX500Name(Map<String, String> formData) {
		String dn = String.format("E=%s, CN=%s, OU=%s, O=%s, L=%s, ST=%s, C=%s", formData.get("email"),
				formData.get("commonName"), formData.get("organizationalunit"), formData.get("organization"),
				formData.get("locality"), formData.get("state"), formData.get("country"));
		return new X500Name(dn);
	}

}
