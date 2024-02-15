package infa.axon_ssl_protector;

import javax.swing.SwingUtilities;

import infa.axon_ssl_protector.MainFrame;

public class Main 
{
	public static void main(String[] args) {
		try {
			SwingUtilities.invokeLater(() -> {
				MainFrame mainFrame = new MainFrame();
				mainFrame.setVisible(true);
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
