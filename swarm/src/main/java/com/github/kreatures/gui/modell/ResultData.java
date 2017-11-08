package com.github.kreatures.gui.modell;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.swing.table.TableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.swarm.SwarmConst;

/**
 * This class formats the data for a {@link TableModel}
 * need rows are iteration which corresponding to a iteration in AbstractSwarm
 * @author Cedric Perez Donfack
 *
 */
public class ResultData {
	
	/** reference to the logback logger instance */
	private Logger LOG = LoggerFactory.getLogger(ResultData.class);

	/**
	 * names of columns
	 */
	private List<String> colNames=new ArrayList<>();
	
	private List<List<String>> rowsData=new ArrayList<>();
	
	private List<List<String>> rowsDataSwarm=new ArrayList<>();
	
	
	/**
	 * 
	 * @param simPath the relativ path where the log data of agents are located
	 *
	 */
	public ResultData(Path simPath) {
		
		try {
			loadData(simPath);
		} catch (Exception e) {
			LOG.error("There is some error"+e.getMessage());
			e.printStackTrace();
		}
	}
		
	/**
	 * 
	 * @return the colNames list of columns names.
	 */
	public List<String> getColNames() {
		return Collections.unmodifiableList(colNames);
	}

	/**
	 * @return the rowsData list of all rows
	 */
	public List<List<String>> getRowsData() {
		return Collections.unmodifiableList(rowsData);
	}

	/**
	 * @return the rowsDataSwarm list of row corresponding to needed iteration
	 */
	public List<List<String>> getRowsDataSwarm() {
		return Collections.unmodifiableList(rowsDataSwarm);
	}

	/**
	 * 
	 * @param logDataFolder the folder where the data are located
	 * @return a pair where first is the list of column names and second is the lists of rows.
	 * @throws IOException when the file isn't exist or it can't be opened.
	 */
	@SuppressWarnings({ "unchecked" })
	public void loadData(Path logDataFolder1) throws Exception{
		
			List<Path> allFiles= Files.list(logDataFolder1).collect(ArrayList::new, ArrayList::add,ArrayList::addAll);
			
			if(allFiles.isEmpty()) return;
			List<Exception> exceptionList=new ArrayList<>(1);
			List<List<String>> data=new ArrayList<>();
			colNames.add("Iteration");
			allFiles.stream().forEach(path->{
				String fileName=path.toFile().getName();
				int indexOfDot=fileName.lastIndexOf(".");
				colNames.add(fileName.substring(0, indexOfDot));
				try {
					data.add(Files.readAllLines(path));
				} catch (IOException e) {
					exceptionList.add(e);
					LOG.error("There are some error:"+e.getMessage());
					//e.printStackTrace();
				}
			});
			
			if(!exceptionList.isEmpty()) throw exceptionList.get(0);
			int index=data.size();
			rowsData=(List<List<String>>) getRows(data.toArray(new ArrayList[index])).orElseGet(ArrayList::new);
			rowsDataSwarm=(List<List<String>>) getNeedRows(data.toArray(new ArrayList[index])).orElseGet(ArrayList::new);
			
//		return Optional.ofNullable(new Pair<Collection<String>,List<List<String>>>(colNames,rowsData));
	}
	
	/**
	 * format row for KReatures output result with all output of each agent
	 * @param logData the agent data about its historic behaviours.
	 * @return result data as rows of a table
	 */
	@SuppressWarnings("unchecked")
	private Optional<List<List<String>>> getRows( List<String>... logData){
		if (logData.length==0) return Optional.empty();
		List<List<String>> rowsDataPerAgent=new ArrayList<>();
		boolean stop=true;
		for(int index=0;stop;index++) {
			stop=false;
			List<String> logDataIter=new ArrayList<>();
			for(List<String> log:logData) {
				if(log.size()>index) {
					stop=true;
					logDataIter.add(log.get(index));
				}else {
					logDataIter.add("");
				}
			}
			rowsDataPerAgent.add(getRow(index,logDataIter.toArray(new String[logData.length])).orElseGet(ArrayList::new));
		}
		
		return Optional.ofNullable(rowsDataPerAgent);
	}
	
	/**
	 * format row for KReatures output result. But only the need iterations which also corresponds to
	 * @param logData the agent data about its historic behaviors.
	 * @return result data as rows of a table
	 */
	@SuppressWarnings("unchecked")
	private Optional<List<List<String>>> getNeedRows(List<String>... logData){
		if (logData.length==0) return Optional.empty();
		List<List<String>> rowsDataPerAgent=new ArrayList<>();
		boolean stop=true;
		for(int index=0;stop;index++) {
			stop=false;
			List<String> logDataIter=new ArrayList<>();
			for(List<String> log:logData) {
				if(log.size()>index) {
					stop=true;
					logDataIter.add(log.get(index));
				}else {
					logDataIter.add("");
				}
			}
			rowsDataPerAgent.add(getNeedRow(index,logDataIter.toArray(new String[logData.length])).orElseGet(ArrayList::new));
		}
		
		return Optional.ofNullable(rowsDataPerAgent);
	}
	
	/**
	 * format row for KReatures output result with all output of each agent
	 * @param iteration the iteration number
	 * @param strings the list of informations about each agent at the given iteration number.
	 *  The strings object must has not null elements
	 * @return the row of the result at the given iteration number.
	 */
	private Optional<List<String>> getRow(int iteration,String ...strings){
		if (strings.length==0) return Optional.empty();
		List<String> list=Arrays.asList(strings);
		List<String> row=new ArrayList<>();
		row.add(""+iteration);
		list.stream().forEach(action->{
			if(action.isEmpty()) {
				row.add("");
			}else {
				String[] contents=action.split(",");
				row.add(String.format("%s%n%s", contents[2],contents[1]));
			}
		});
		
		return Optional.ofNullable(row);
	}

	/**
	 * format row for KReatures output result. But only the need iterations which also corresponds to
	 * iteration in AbstractSwarm. 
	 * @param iteration the iteration number
	 * @param strings the list of informations about each agent at the given iteration number. The strings object must has not null elements
	 * @return the row of the result at the given iteration number.
	 */
	private Optional<List<String>> getNeedRow(int iteration,String ...strings){
		if (strings.length==0) return Optional.empty();
		List<String> list=Arrays.asList(strings);
		List<String> row=new ArrayList<>();
		row.add(""+iteration);
		list.stream().forEach(action->{
			if(action.isEmpty()) {
				row.add("");
			}else {
				String[] contents=action.split(",");
				if(Integer.parseInt(contents[3])<SwarmConst.WAIT_TIME.getValue()-1) {
					row.add(String.format("%s%n%s", contents[2],contents[1]));
				}else {
					row.add("");
				}
			}
		});
		
		return Optional.ofNullable(row);
	}
	
	@Override
	public String toString() {
		return String.format("Column names:[%s]%n Row data: [%s]%n",colNames.toString(),rowsData.toString());
	}
}
