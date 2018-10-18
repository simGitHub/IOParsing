import java.util.ArrayList;

import circuitNetwork.*;
import readData.ReadFromTextFile2DArray;

public class SimulateNetwork {
	public SimulateNetwork(Network network, IOParser par, String dataDir, String dataName, double dt, int SIMULATION_TIME) {
		network.resetSources(); // needed network sources
		ReadFromTextFile2DArray textToArrayReader = new ReadFromTextFile2DArray();
		VoltageSourceDictated vg = par.vg;
		String dataFileDir = dataDir + dataName + "/" + dataName + ".txt";
		String saveFileDir = dataDir + dataName + "/";
		
		//System.out.println("Reading dataset: " + dataFileDir);
		double[][] dataset = textToArrayReader.readInTheArray(dataFileDir);
		vg.dictate(dataset, 1);
		vg.defineDTtoSuggest(dt);
		network.addsource(vg);
		
		//System.out.println(" ** Starting simulation ** ");
		network.operateNetwork(0.0, SIMULATION_TIME);
		//System.out.println(" ** Simulation finished ** ");
		
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
		String mStr = saveFileDir + "memristance.txt";
		String vStr = saveFileDir + "voltage.txt";
		new ExportMatrix(mStr, memristanceMatrix);
		new ExportMatrix(vStr, voltageMatrix);
		System.out.println();
		
	}
}