package com.github.kreatures.gui.controller;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.table.TableModel;

import com.github.kreatures.core.KReaturesConst;
import com.github.kreatures.core.KReaturesPaths;
import com.github.kreatures.gui.modell.ResultData;
import com.github.kreatures.gui.view.ResultView;


/**
 * Use to create a {@link ResultView}, a {@link ResultData} and a {@link ResultDatamodell} in order 
 * to display the result to a screen.  
 * @author Cedric Perez Donfack
 *
 */
public class ShowResult {
	/**
	 * The relativ path of the folder where the agent log data is stored.
	 */
	private Path logDataFolder;

	/**
	 * The Table model 
	 */
	private TableModel tableModel;

	/**
	 * the view 
	 */
	private ResultView jframe;
	/**
	 * The data
	 */
	private ResultData resultData;

	/**
	 * Ctor: for creating all need objects
	 * 
	 * @param simName name of the current simulation.
	 * @see description of {@link ShowResult} for which objects.
	 */
	public ShowResult(String simName){
		logDataFolder=Paths.get(KReaturesPaths.KREATURES_EXAMPLES_DIR.toString()).resolve(simName).resolve(KReaturesConst._KREaturesLogDataFolderName);
		resultData= new ResultData(logDataFolder);
		tableModel=new ResultDatamodell(resultData);
		jframe=new ResultView();
		jframe.setTablemodel(tableModel);		
	}

	public void show() {
		
		Thread thread=new Thread(new Runnable() {

			@Override
			public void run() {
				jframe.setVisible(true);

			}
		});
		thread.start();
	}

}
