package infa.axon_ssl_protector;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v1CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequest;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;

public class X509CertificateGenerator implements CertificateGeneratorInterface {

	private final KeyGenerator keyGenerator;
	private static final String signatureRsa = "SHA256withRSA";

	public X509CertificateGenerator(KeyGenerator keyGenerator) {
		this.keyGenerator = keyGenerator;
	}

	private JcaPKCS10CertificationRequest generateCSR(PublicKey publicKey) throws OperatorCreationException {
		PKCS10CertificationRequestBuilder p10Builder = new JcaPKCS10CertificationRequestBuilder(
				new X500Principal("CN=Test"), publicKey);
		ContentSigner signer = new JcaContentSignerBuilder(X509CertificateGenerator.signatureRsa)
				.build(keyGenerator.getPrivateKey());
		return new JcaPKCS10CertificationRequest(p10Builder.build(signer));
	}

	@Override
	public X509Certificate generateX509Certificate(PrivateKey privateKey, PublicKey publicKey, X500Name issuer,
			int validityTimeout, boolean basicConstraints) {
		try {
			java.math.BigInteger serial = generateSerialNumber();
			Date startDate = DateTime.START_DATE.getCurrentDateTime();
			Date endDate = DateTime.END_DATE.calculateEndDate(startDate, validityTimeout);

			JcaPKCS10CertificationRequest certReq = generateCSR(publicKey);

			JcaX509v1CertificateBuilder caBuilder = buildCertificateBuilder(issuer, serial, startDate, endDate,
					certReq.getPublicKey());

			ContentSigner caSigner = signCertificate(privateKey);
			X509Certificate cert = signX509Certificate(caBuilder, caSigner);

			return cert;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static java.math.BigInteger generateSerialNumber() {
		byte[] randomBytes = new byte[10];
		new SecureRandom().nextBytes(randomBytes);
		return new java.math.BigInteger(1, randomBytes);
	}

	private static JcaX509v1CertificateBuilder buildCertificateBuilder(X500Name issuer, java.math.BigInteger serial,
			Date startDate, Date endDate, PublicKey publicKey) {
		return new JcaX509v1CertificateBuilder(issuer, serial, startDate, endDate, issuer, publicKey);
	}

	private static ContentSigner signCertificate(PrivateKey privateKey) throws OperatorCreationException {
		return new JcaContentSignerBuilder(signatureRsa).setProvider(new BouncyCastleProvider()).build(privateKey);
	}

	private static X509Certificate signX509Certificate(JcaX509v1CertificateBuilder caBuilder, ContentSigner caSigner)
			throws CertificateException {
		JcaX509CertificateConverter converter = new JcaX509CertificateConverter()
				.setProvider(new BouncyCastleProvider());
		return converter.getCertificate(caBuilder.build(caSigner));
	}

}