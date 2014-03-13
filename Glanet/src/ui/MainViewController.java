package ui;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

import ui.MainView;
import ui.MainView.MainViewDelegate;

public class MainViewController extends ViewController implements MainViewDelegate {
	
	private MainView mainView;

	public MainViewController( JPanel contentPanel) {
		super(contentPanel);
		
		loadView();
	}

	@Override
	public void loadView() {
		
		if( mainView != null){
			contentPanel.remove(mainView);
		}
		
		mainView = new MainView();
		mainView.setDelegate( this);
		contentPanel.add(mainView);
	}

	@Override
	public void presentViewController(ViewController viewController) {

		contentPanel.removeAll();
		contentPanel.invalidate();
		viewController.loadView();
		contentPanel.revalidate();
		
	}

	@Override
	public void dismissViewController() {
		contentPanel.removeAll();
		contentPanel.add(mainView);
	}

	public void onSetSourceLocationPressed() {
		System.out.println( "will open file");
        
		//presentViewController( new xViewController(contentPanel) );
	}
}