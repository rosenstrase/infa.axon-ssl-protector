package gcs.infa.maven.axonsslprotector.generator;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.operator.OperatorCreationException;

public interface CertificateGenerator {

	X509Certificate generateX509Certificate(PrivateKey privateKey, PublicKey publicKey, X500Name issuer,
			Integer validityPeriodOrNull, Boolean isCAOrNull) throws CertificateException, OperatorCreationException;

}
