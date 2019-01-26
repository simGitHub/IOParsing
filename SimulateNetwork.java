import java.util.ArrayList;
import java.util.List;

import circuitNetwork.*;
import readData.ReadFromTextFile2DArray;
// Reads data from directory, simulate the network with it, then export memristance (voltage) monitors to text file in same directory.
// make so it does not export voltage if only zero voltage monitors is declared

public class SimulateNetwork {
	List<VoltageSourceDictated> vgList;
	Network network;
	IOParser par;
	int SIMULATION_TIME;
	double[][] dataset;
	double dt;
	String dataDir;
	String dataName;
	String simulationNumber;
	
	public SimulateNetwork(Network network, IOParser par, String dataDir, String dataName, double dt, int SIMULATION_TIME, int k) {
		this.vgList = par.vgList;
		this.par = par;
		this.network = network;
		this.SIMULATION_TIME = SIMULATION_TIME; 
		this.dt = dt;
		this.dataName = dataName;
		this.dataDir = dataDir;
		ReadFromTextFile2DArray textToArrayReader = new ReadFromTextFile2DArray();
		String dataFile = dataDir + "/" + dataName + ".txt";
		this.dataset = textToArrayReader.readInTheArray(dataFile);
		simulationNumber = "/" + Integer.toString(k) + "/";
		
		
		if(par.amplifierValue != 1) {
			this.changeAmplitudeOfSignal();
		}
		
		this.addDataToVoltageSources();
		this.runSimulation();
		this.extractData();
		
		}
	
	
	public void addDataToVoltageSources(){
		VoltageSourceDictated vgTemp;
		int nbrDataColumns = dataset[0].length - 1;
		int nbrVoltageSources = vgList.size();
		if (nbrDataColumns != nbrVoltageSources){
			System.out.println("Caution: number of voltage sources and data columns is not the same, some voltage sources will get the same data input (data from column first data column)");
		}
		for(int i=0;i<nbrVoltageSources;i++) {
			vgTemp = vgList.get(i);
			if(i < nbrDataColumns) {
				vgTemp.dictate(dataset, i + 1); 
			}
			else {
				System.out.println("Adding same data column value to rest of the voltage sources");
				vgTemp.dictate(dataset, 1);
			}
			vgTemp.defineDTtoSuggest(dt);
			network.addsource(vgTemp);
		}
	}
	
	public void changeAmplitudeOfSignal() {
		for(int iRow = 0; iRow < dataset.length;iRow++) {
			for (int iCol = 1; iCol < dataset[0].length; iCol++) {
				dataset[iRow][iCol] = par.amplifierValue * dataset[iRow][iCol];
			}
		}
	}
	
	public void runSimulation() {
		//System.out.println(" ** Starting simulation ** ");
		network.operateNetwork(0.0, SIMULATION_TIME);
		//System.out.println(" ** Simulation finished ** ");
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
			String mStr = dataDir + simulationNumber + "/memristance_" + dataName + ".txt";
			new ExportMatrix(mStr, memristanceMatrix);
		}
		else {
			String mStr = dataDir + simulationNumber + "/memristance_" + dataName + ".txt";
			String vStr = dataDir + simulationNumber + "/voltage_" + dataName + ".txt";
			new ExportMatrix(mStr, memristanceMatrix);
			new ExportMatrix(vStr, voltageMatrix);
		}
		
	}
}