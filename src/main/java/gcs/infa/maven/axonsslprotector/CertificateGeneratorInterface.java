package gcs.infa.maven.axonsslprotector;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

import org.bouncycastle.asn1.x500.X500Name;

public interface CertificateGeneratorInterface {

	X509Certificate generateX509Certificate(PrivateKey privateKey, PublicKey publicKey, X500Name issuer,
			int validityTimeout, boolean basicConstraints);

}
