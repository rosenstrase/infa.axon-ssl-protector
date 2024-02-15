package infa.axon_ssl_protector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import infa.axon_ssl_protector.CertificateGeneratorGUI;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -1668300803666889008L;

	public MainFrame() {
		setTitle("AxonSSLProtector");
		setSize(400, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		JMenu settingsMenu = new JMenu("Settings");
		JMenuItem menuItem = new JMenuItem("Menu Item");
		settingsMenu.add(menuItem);
		menuBar.add(settingsMenu);

		JButton generateButton = new JButton("Generate Certificate");
		generateButton.addActionListener(e -> {
			new CertificateGeneratorGUI();
		});

		JPanel panel = new JPanel();
		panel.add(generateButton);

		setJMenuBar(menuBar);
		add(panel);
		setVisible(true);
	}

}