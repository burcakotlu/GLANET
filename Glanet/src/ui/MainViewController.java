package ui;

import javax.swing.JPanel;

import common.Commons;
import ui.MainView;
import ui.MainView.MainViewDelegate;
import processinputdata.InputDataProcess;
import processinputdata.prepare.*;

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

	public void startRunActionsWithOptions(String inputFolder, 
			   String inputFormat, 
			   String dataMode, 
			   String numberOfPermutations,
			   String enrichmentType,
			   String outputFolder) {
		
		//PreparationofOCDSnps.java
		String[] args = { inputFolder, outputFolder, inputFormat };
		
		InputDataProcess.run(args);
	}
}