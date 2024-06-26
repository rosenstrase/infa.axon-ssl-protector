package gcs.infa.maven.axonsslprotector.main;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.swing.SwingUtilities;

import gcs.infa.maven.axonsslprotector.generator.X509CertificateGenerator;
import gcs.infa.maven.axonsslprotector.service.GenerateKeypair;
import gcs.infa.maven.axonsslprotector.service.InputValidator;
import gcs.infa.maven.axonsslprotector.service.PrivateKeyImpl;
import gcs.infa.maven.axonsslprotector.service.PrivateKeyService;
import gcs.infa.maven.axonsslprotector.service.ServiceLocator;
import gcs.infa.maven.axonsslprotector.service.SystemInfoService;
import gcs.infa.maven.axonsslprotector.service.ValidationService;
import gcs.infa.maven.axonsslprotector.util.SignatureAlgorithm;
import gcs.infa.maven.axonsslprotector.writer.FileCertificateWriter;

public class Main 
{
	public static void main(String[] args) {
		try {
			initializeServices();

			SwingUtilities.invokeLater(() -> {
				MainFrame mainFrame = new MainFrame();
				mainFrame.setVisible(true);
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	private static void initializeServices() {
		try {
			SystemInfoService systemInfoService = new SystemInfoService();
			ServiceLocator.registerService("SystemInfoService", systemInfoService);

			ValidationService inputValidator = new InputValidator();
			ServiceLocator.registerService("ValidationService", inputValidator);

			GenerateKeypair keyGenerator = new GenerateKeypair(SignatureAlgorithm.RSA, 4096);
			ServiceLocator.registerService("KeyGenerator", keyGenerator);

			PrivateKeyService privateKeyService = new PrivateKeyImpl();
			ServiceLocator.registerService("PrivateKeyService", privateKeyService);

			FileCertificateWriter certificateWriter = new FileCertificateWriter();
			ServiceLocator.registerService("CertificateWriter", certificateWriter);

			X509CertificateGenerator x509certificateGenerator = new X509CertificateGenerator();
			ServiceLocator.registerService("X509CertificateGenerator", x509certificateGenerator);

		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			throw new RuntimeException("Failed to initialize a required service", e);
		}
	}
}
