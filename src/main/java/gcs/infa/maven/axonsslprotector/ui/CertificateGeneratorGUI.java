package gcs.infa.maven.axonsslprotector.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
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

		setTitle("Certificate Form");
		setSize(400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		eField = new JTextField(20);
		cnField = new JTextField(20);
		ouField = new JTextField(20);
		oField = new JTextField(20);
		lField = new JTextField(20);
		stField = new JTextField(20);
		cField = new JTextField(20);

		panel.add(new JLabel("Email:"), gbc);
		panel.add(eField, gbc);
		panel.add(new JLabel("Common Name:"), gbc);
		panel.add(cnField, gbc);
		panel.add(new JLabel("Org Unit:"), gbc);
		panel.add(ouField, gbc);
		panel.add(new JLabel("Org:"), gbc);
		panel.add(oField, gbc);
		panel.add(new JLabel("Locality:"), gbc);
		panel.add(lField, gbc);
		panel.add(new JLabel("State:"), gbc);
		panel.add(stField, gbc);
		panel.add(new JLabel("Country:"), gbc);
		panel.add(cField, gbc);

		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(createGenerateCertificateAction());
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				closeForm();
			}
		});

		gbc.weightx = 0.5;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;

		panel.add(submitButton, gbc);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(cancelButton, gbc);

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

	private void closeForm() {
		this.dispose();
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