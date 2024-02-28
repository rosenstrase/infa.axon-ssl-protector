package gcs.infa.maven.axonsslprotector.generator;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v1CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import gcs.infa.maven.axonsslprotector.service.GenerateKeypair;
import gcs.infa.maven.axonsslprotector.service.ServiceLocator;
import gcs.infa.maven.axonsslprotector.util.DateTime;

public class X509CertificateGenerator implements CertificateGenerator {

	private final GenerateKeypair keyGenerator;
	private static final String signatureRsa = "SHA256withRSA";
	private static final int DEFAULT_VALIDITY_PERIOD = 365;
	private static final boolean DEFAULT_IS_CA = false;

	public X509CertificateGenerator() {
		try {
			this.keyGenerator = (GenerateKeypair) ServiceLocator.getService("KeyGenerator");
		} catch (ClassCastException e) {
			throw new RuntimeException("Service Locator returned an object that could not be cast to KeyGenerator", e);
		}
	}

	@Override
	public X509Certificate generateX509Certificate(PrivateKey privateKey, PublicKey publicKey, X500Name issuer,
			Integer validityPeriodOrNull, Boolean isCAOrNull) throws CertificateException, OperatorCreationException {

		final int validityPeriod = (validityPeriodOrNull != null) ? validityPeriodOrNull : DEFAULT_VALIDITY_PERIOD;
		final boolean isCA = (isCAOrNull != null) ? isCAOrNull : DEFAULT_IS_CA;
		Date startDate = new Date();
		Date endDate = DateTime.calculateEndDate(startDate, validityPeriod);

		java.math.BigInteger serial = generateSerialNumber();

		try {
			JcaX509v1CertificateBuilder caBuilder = new JcaX509v1CertificateBuilder(new X500Name(issuer.toString()),
					serial, startDate, endDate, new X500Name(issuer.toString()), publicKey);

			ContentSigner caSigner = new JcaContentSignerBuilder(signatureRsa).build(privateKey);
			X509Certificate cert = new JcaX509CertificateConverter().setProvider(new BouncyCastleProvider())
					.getCertificate(caBuilder.build(caSigner));
			return cert;

		} catch (OperatorCreationException | CertificateException e) {
			throw e;
		}
	}

	private static java.math.BigInteger generateSerialNumber() {
		byte[] randomBytes = new byte[10];
		new SecureRandom().nextBytes(randomBytes);
		return new java.math.BigInteger(1, randomBytes);
	}
}