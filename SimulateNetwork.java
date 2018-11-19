import java.util.ArrayList;
import java.util.List;

import circuitNetwork.*;
import readData.ReadFromTextFile2DArray;
// Reads data from file and runs networks with this data. Later ExportMatrix is called to export the monitors to txt files.

public class SimulateNetwork {
	public SimulateNetwork(Network network, IOParser par, String dataDir, String dataName, double dt, int SIMULATION_TIME) {
		network.resetSources(); // need to reset for different datasets from main
		ReadFromTextFile2DArray textToArrayReader = new ReadFromTextFile2DArray();
		List<VoltageSourceDictated> vgList = par.vgList;
		String dataFile = dataDir + "/" + dataName + ".txt";
		
		// Read data from file and add it to voltageSource (vg)
		double[][] dataset = textToArrayReader.readInTheArray(dataFile);
		
		// amplify the data signal
		for(int i = 0; i < dataset.length;i++) {
			dataset[i][1] = par.amplifierValue * dataset[i][1];
		}
		
		// add source(s) and data to it (them). Maybe add expetion handling, e.g. try and catch, for one may miss to add data to each source.
		vgList.get(0).dictate(dataset, 1); 
		vgList.get(0).defineDTtoSuggest(dt);
		network.addsource(vgList.get(0));
		
		vgList.get(1).dictate(dataset, 1); 
		vgList.get(1).defineDTtoSuggest(dt);
		network.addsource(vgList.get(1));
		
		// run simulation
		System.out.println(" ** Starting simulation ** ");
		network.operateNetwork(0.0, SIMULATION_TIME);
		System.out.println(" ** Simulation finished ** ");
		
		// exctract all added monitors, memristors to one 2D text file and voltage to one 2D text file. One column corresponds to a value set.
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
		String mStr = dataDir + "/memristance_" + dataName + ".txt";
		String vStr = dataDir + "/voltage_" + dataName + ".txt";
		new ExportMatrix(mStr, memristanceMatrix);
		new ExportMatrix(vStr, voltageMatrix);
		System.out.println();
		
	}
}