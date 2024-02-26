package gcs.infa.maven.axonsslprotector;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.swing.SwingUtilities;

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
			KeyGenerator keyGenerator = new KeyGenerator(SignatureAlgorithm.RSA, 4096);
			ServiceLocator.registerService("KeyGenerator", keyGenerator);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Failed to initialize KeyGenerator: No Such Algorithm available", e);
		} catch (NoSuchProviderException e) {
			throw new RuntimeException("Failed to initialize KeyGenerator: No Such Provider available", e);
		} catch (Exception e) {
			// Catch-all for any other exceptions that might occur
			throw new RuntimeException("Failed to initialize KeyGenerator", e);
		}
		CertificateWriter certificateWriter = new CertificateWriter();
		ServiceLocator.registerService("CertificateWriter", certificateWriter);
		X509CertificateGenerator x509certificateGenerator = new X509CertificateGenerator();
		ServiceLocator.registerService("X509CertificateGenerator", x509certificateGenerator);
	}
}
