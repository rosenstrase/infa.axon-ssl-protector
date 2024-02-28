package gcs.infa.maven.axonsslprotector.processor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URLDecoder;
import java.security.KeyPair;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import org.bouncycastle.asn1.x500.X500Name;

import gcs.infa.maven.axonsslprotector.generator.CertificateGenerator;
import gcs.infa.maven.axonsslprotector.main.Main;
import gcs.infa.maven.axonsslprotector.service.GenerateKeypair;
import gcs.infa.maven.axonsslprotector.service.ServiceLocator;
import gcs.infa.maven.axonsslprotector.ui.CertificateGeneratorGUI;
import gcs.infa.maven.axonsslprotector.writer.CertificateWriter;

public class GenerateCertificateAction implements ActionListener {
	private CertificateGeneratorGUI gui;
	private CertificateDataProcessor dataProcessor;
	private CertificateGenerator certGenerator;
	private CertificateWriter certWriter;

	private static final int DEFAULT_VALIDITY_PERIOD = 10950; // 30 years
	private static final boolean DEFAULT_IS_CA = false;

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

		int validityPeriod = DEFAULT_VALIDITY_PERIOD;
		boolean isCA = DEFAULT_IS_CA;

		Map<String, String> formData = new HashMap<>();
		formData.put("country", gui.getCountryField());
		formData.put("state", gui.getStateField());
		formData.put("locality", gui.getLocalityField());
		formData.put("organization", gui.getOrganizationField());
		formData.put("organizationalunit", gui.getOrganizationalUnitField());
		formData.put("commonName", gui.getCommonNameField());
		formData.put("email", gui.getEmailField());

		X500Name issuer = dataProcessor.generateX500Name(formData);

		try {
			GenerateKeypair keyGenService = (GenerateKeypair) ServiceLocator.getService("KeyGenerator");
	        KeyPair keyPair = keyGenService.generateKeyPair();
	        
	        X509Certificate cert = certGenerator.generateX509Certificate(
	                keyPair.getPrivate(),
	                keyPair.getPublic(),
	                issuer,
	                validityPeriod,
	                isCA
	        );
	        
	        String jarPath = Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			String decodedPath = URLDecoder.decode(jarPath, "UTF-8");
			String directory = new File(decodedPath).getParent();
			String filePath = directory + File.separator + "private";
	        String fileExtension = ".crt";
	        
	        certWriter.writeCertificateToFile(cert, filePath, fileExtension);

			JOptionPane.showMessageDialog(gui, "Certificate generated successfully");
			gui.dispose();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(gui, "Error generating Certificate: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
