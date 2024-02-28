package gcs.infa.maven.axonsslprotector.ui;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gcs.infa.maven.axonsslprotector.generator.CertificateGenerator;
import gcs.infa.maven.axonsslprotector.processor.CertificateDataProcessor;
import gcs.infa.maven.axonsslprotector.processor.GenerateCertificateAction;
import gcs.infa.maven.axonsslprotector.processor.SimpleCertificateDataProcessor;
import gcs.infa.maven.axonsslprotector.service.ServiceLocator;
import gcs.infa.maven.axonsslprotector.writer.CertificateWriter;

public class CertificateGeneratorGUI extends JFrame {

	private static final long serialVersionUID = 1147658359L;

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
		generateButton.addActionListener(createGenerateCertificateAction());

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

	private ActionListener createGenerateCertificateAction() {
		CertificateDataProcessor dataProcessor = new SimpleCertificateDataProcessor();
		CertificateGenerator certGenerator = (CertificateGenerator) ServiceLocator
				.getService("X509CertificateGenerator");
		CertificateWriter certWriter = (CertificateWriter) ServiceLocator.getService("CertificateWriter");

		return new GenerateCertificateAction(this, dataProcessor, certGenerator, certWriter);
	}

	public String getCountryField() {
		return cField.getText();
	}

	public String getStateField() {
		return stField.getText();
	}

	public String getLocalityField() {
		return lField.getText();
	}

	public String getOrganizationField() {
		return oField.getText();
	}

	public String getOrganizationalUnitField() {
		return ouField.getText();
	}

	public String getCommonNameField() {
		return cnField.getText();
	}

	public String getEmailField() {
		return eField.getText();
	}
}