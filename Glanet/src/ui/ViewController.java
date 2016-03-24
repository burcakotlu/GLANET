package ui;

import javax.swing.JComponent;
import javax.swing.JPanel;

public abstract class ViewController {

	public JPanel contentPanel;
	public JComponent view;
	public ViewController presentingViewController; // the controller that
													// presents this
													// viewController
	public ViewController presentedViewController; // the controller that is
													// presented by this
													// viewController

	public ViewController( JPanel contentPanel) {

		this.contentPanel = contentPanel;
		if( contentPanel == null)
			throw new NullPointerException( "contentPanel cannot be NULL");
	}

	public abstract void loadView();

	public void presentViewController( ViewController viewController) {

		contentPanel.removeAll();
		contentPanel.invalidate();
		viewController.loadView();
		contentPanel.revalidate();
	}

	public abstract void dismissViewController();
	
	public abstract void frameSizeChanged(int width, int height);
}