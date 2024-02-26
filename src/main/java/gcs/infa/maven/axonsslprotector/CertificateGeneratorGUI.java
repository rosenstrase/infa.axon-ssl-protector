package gcs.infa.maven.axonsslprotector;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URLDecoder;
import java.security.cert.X509Certificate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.bouncycastle.asn1.x500.X500Name;

public class CertificateGeneratorGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextField eField;
	private JTextField cnField;
	private JTextField ouField;
	private JTextField oField;
	private JTextField lField;
	private JTextField stField;
	private JTextField cField;

	public CertificateGeneratorGUI() {

		setTitle("Certificate Generator");
		setSize(400, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 2));

		eField = new JTextField(20);
		cnField = new JTextField(20);
		ouField = new JTextField(20);
		oField = new JTextField(20);
		lField = new JTextField(20);
		stField = new JTextField(20);
		cField = new JTextField(20);

		JButton generateButton = new JButton("Generate Certificate");
		generateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				generateCertificate();
			}
		});

		panel.add(new JLabel("Email:"));
		panel.add(eField);
		panel.add(new JLabel("Common Name:"));
		panel.add(cnField);
		panel.add(new JLabel("Org Unit:"));
		panel.add(ouField);
		panel.add(new JLabel("Org:"));
		panel.add(oField);
		panel.add(new JLabel("Locality:"));
		panel.add(lField);
		panel.add(new JLabel("State:"));
		panel.add(stField);
		panel.add(new JLabel("Country:"));
		panel.add(cField);
		panel.add(new JLabel(""));
		panel.add(generateButton);

		add(panel);
		setVisible(true);
	}

	private void generateCertificate() {

		String country = cField.getText();
		String state = stField.getText();
		String locality = lField.getText();
		String organization = oField.getText();
		String organizationalunit = ouField.getText();
		String commonName = cnField.getText();
		String email = eField.getText();

		String dn = "E=" + email + ", CN=" + commonName + ", OU= " + organizationalunit + ", O=" + organization
				+ ", L= " + locality + ", ST= " + state + ", C=" + country;

		X500Name issuer = new X500Name(dn);

		try {

			KeyGenerator keyGen = (KeyGenerator) ServiceLocator.getService("KeyGenerator");

			X509CertificateGenerator certGen = (X509CertificateGenerator) ServiceLocator
					.getService("X509CertificateGenerator");

			String jarPath = Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			String decodedPath = URLDecoder.decode(jarPath, "UTF-8");
			String directory = new File(decodedPath).getParent();
			String fileName = directory + File.separator + "private";

			X509Certificate cert = certGen.generateX509Certificate(keyGen.getPrivateKey(), keyGen.getPublicKey(),
					issuer, 0, true);

			CertificateWriter certificateWriter = (CertificateWriter) ServiceLocator.getService("CertificateWriter");
			certificateWriter.writeCertificateToFile(cert, fileName, ".crt");

			JOptionPane.showMessageDialog(this, "Certificate generated successfully");
			dispose();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error generating certificate: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			}
	}
}