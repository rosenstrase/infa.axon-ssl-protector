package gcs.infa.maven.axonsslprotector.processor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import org.bouncycastle.asn1.x500.X500Name;

import gcs.infa.maven.axonsslprotector.generator.CertificateGenerator;
import gcs.infa.maven.axonsslprotector.main.Main;
import gcs.infa.maven.axonsslprotector.service.GenerateKeypair;
import gcs.infa.maven.axonsslprotector.service.PrivateKeyService;
import gcs.infa.maven.axonsslprotector.service.ServiceLocator;
import gcs.infa.maven.axonsslprotector.service.ValidationService;
import gcs.infa.maven.axonsslprotector.ui.CertificateGeneratorGUI;
import gcs.infa.maven.axonsslprotector.writer.CertificateWriter;

public class GenerateCertificateAction implements ActionListener {
	private CertificateGeneratorGUI gui;
	private CertificateDataProcessor dataProcessor;
	private CertificateGenerator certGenerator;
	private CertificateWriter certWriter;

	public GenerateCertificateAction(CertificateGeneratorGUI gui, CertificateDataProcessor dataProcessor,
			CertificateGenerator certGenerator,
			CertificateWriter certWriter) {
		this.gui = gui;
		this.dataProcessor = dataProcessor;
		this.certGenerator = certGenerator;
		this.certWriter = certWriter;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			String jarPath = Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			String decodedPath = URLDecoder.decode(jarPath, "UTF-8");
			String directory = new File(decodedPath).getParent();

			ValidationService validationService = (ValidationService) ServiceLocator.getService("ValidationService");
			GenerateKeypair keyGenService = (GenerateKeypair) ServiceLocator.getService("KeyGenerator");
			PrivateKeyService privateKeyService = (PrivateKeyService) ServiceLocator.getService("PrivateKeyService");

			Integer validityPeriod = null;
			Boolean isCA = null;

			String country = gui.getCountryField().trim();
			String state = gui.getStateField().trim();
			String locality = gui.getLocalityField().trim();
			String organization = gui.getOrganizationField().trim();
			String organizationalunit = gui.getOrganizationalUnitField().trim();
			String commonName = gui.getCommonNameField().trim();
			String email = gui.getEmailField().trim();

			// VALIDATION PROCESS
			if (!validationService.isValidEmail(email)) {
				JOptionPane.showMessageDialog(gui, "Email is not in the correct format.", "Validation Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			Map<String, String> formData = new HashMap<>();
			formData.put("country", country);
			formData.put("state", state);
			formData.put("locality", locality);
			formData.put("organization", organization);
			formData.put("organizationalunit", organizationalunit);
			formData.put("commonName", commonName);
			formData.put("email", email);

			X500Name issuer = dataProcessor.generateX500Name(formData);

			KeyPair keyPair = keyGenService.generateKeyPair();

			String privateKeyFilename = directory + File.separator + "privateKey.txt";
			privateKeyService.savePrivateKeyToFile(keyPair.getPrivate(), privateKeyFilename);

			X509Certificate cert = certGenerator.generateX509Certificate(keyPair.getPrivate(), keyPair.getPublic(),
					issuer, validityPeriod, isCA);

			String filePath = directory + File.separator + "private";
			String fileExtension = ".crt";

			certWriter.writeCertificateToFile(cert, filePath, fileExtension);

			JOptionPane.showMessageDialog(gui, "Certificate generated successfully");
			gui.dispose();
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(gui, "File operation failed: " + ex.getMessage(), "File Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (NoSuchAlgorithmException | NoSuchProviderException ex) {
			JOptionPane.showMessageDialog(gui, "Key generation error: " + ex.getMessage(), "Key Generation Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (URISyntaxException ex) {
			JOptionPane.showMessageDialog(gui, "URI syntax error: " + ex.getMessage(), "URI Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(gui, "An unexpected error occurred: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
