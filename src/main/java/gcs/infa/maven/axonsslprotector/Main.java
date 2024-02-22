package gcs.infa.maven.axonsslprotector;

import javax.swing.SwingUtilities;

public class Main 
{
	public static void main(String[] args) {
		try {
			SwingUtilities.invokeLater(() -> {
				MainFrame mainFrame = new MainFrame();
				mainFrame.setVisible(true);
			});

		} catch (Exception e) {
			e.printStackTrace();// test
		}
    }
}
