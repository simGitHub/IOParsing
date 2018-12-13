import java.util.ArrayList;
import java.util.List;

import circuitNetwork.*;
import readData.ReadFromTextFile2DArray;
// Reads data from directory, simulate the network with it, then export memristance (voltage) monitors to text file in same directory.
// make so it does not export voltage if only zero voltage monitors is declared

public class SimulateNetwork {
	List<VoltageSourceDictated> vgList;
	double[][] dataset;
	Network network;
	double dt;
	IOParser par;
	int SIMULATION_TIME;
	String dataDir;
	String dataName;
	
	public SimulateNetwork(Network network, IOParser par, String dataDir, String dataName, double dt, int SIMULATION_TIME) {
		network.resetSources(); // need to reset for different datasets from main
		ReadFromTextFile2DArray textToArrayReader = new ReadFromTextFile2DArray();
		String dataFile = dataDir + "/" + dataName + ".txt";
		dataset = textToArrayReader.readInTheArray(dataFile);
		vgList = par.vgList;
		
		this.addDataToVoltageSources();
		// this.amplifySignal(); Fix indices issues before uncommenting.
		this.runSimulation();
		}
	
	
	public void addDataToVoltageSources(){
		//double[][] dataSetForGivenVoltageSource;
		VoltageSourceDictated vgTemp;
		for(int i=0;i<vgList.size();i++) {
			vgList = par.vgList;
			vgTemp = vgList.get(i);
			//dataSetForGivenVoltageSource = exctractDataColumnAndTimeSignalFromMatrix(i);
			System.out.println("value on i: " + i);
			vgTemp.dictate(dataset, 1); 
			vgTemp.defineDTtoSuggest(dt);
			network.addsource(vgTemp);
		}
	}
	
	public double[][] exctractDataColumnAndTimeSignalFromMatrix(int iColumn) {
		return dataset;
	}
	
	public void amplifySignal() {
		for(int i = 0; i < dataset
				.length;i++) {
			dataset[i][1] = par.amplifierValue * dataset[i][1];
		}
	}
	
	public void runSimulation() {
		System.out.println(" ** Starting simulation ** ");
		network.operateNetwork(0.0, SIMULATION_TIME);
		System.out.println(" ** Simulation finished ** ");
	}
	
	public void extractData() {
		ArrayList<Monitor> monitors = network.getMonitors();
		int numberOfVoltageMonitors = par.numberOfVoltageMonitors;
		int numberOfMemristors = par.numberOfMemristors;			
		ArrayList<ArrayList<Double>> memristanceMatrix = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> voltageMatrix = new ArrayList<ArrayList<Double>>();
		for(int i=0;i<numberOfVoltageMonitors;i++) {
			ArrayList<Double> voltageValues = monitors.get(i).getValues();
			voltageMatrix.add(voltageValues);
		}
		for(int i=numberOfVoltageMonitors;i<(numberOfVoltageMonitors + numberOfMemristors);i++) {
			ArrayList<Double> memristanceValues = monitors.get(i).getValues();
			memristanceMatrix.add(memristanceValues);
		}
		if(par.numberOfVoltageMonitors == 0) {
			String mStr = dataDir + "/memristance_" + dataName + ".txt";
			new ExportMatrix(mStr, memristanceMatrix);
			System.out.println();
		}
		else {
			String mStr = dataDir + "/memristance_" + dataName + ".txt";
			String vStr = dataDir + "/voltage_" + dataName + ".txt";
			new ExportMatrix(mStr, memristanceMatrix);
			new ExportMatrix(vStr, voltageMatrix);
			System.out.println();
		}
		
	}
}