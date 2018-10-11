import java.util.ArrayList;

import circuitNetwork.*;
import readData.ReadFromTextFile2DArray;

public class SimulateNetwork {
	public SimulateNetwork(Network network, String datasetFileName, double dt, IOParser par, String saveFolder, int SIMULATION_TIME) {
		ReadFromTextFile2DArray textToArrayReader = new ReadFromTextFile2DArray();
		VoltageSourceDictated vg = par.vg;
		
		double[][] dataset = textToArrayReader.readInTheArray(datasetFileName);
		vg.dictate(dataset, 1);
		vg.defineDTtoSuggest(dt);
		network.addsource(vg);
		
		System.out.println(" ** Starting simulation ** ");
		network.operateNetwork(0.0, SIMULATION_TIME);
		System.out.println(" ** Simulation finished ** ");
		
		// exctract all added monitors, memristors to one 2D text file and voltage to one 2D text file. One column corresponds to a value set.
		System.out.println("Extracting gathered data from all memristor and voltage monitors and exporting them to text files");
		ArrayList<Monitor> monitors = network.getMonitors();
		System.out.println("Length of monitor array list: " + monitors.size() );
		String directory = "/users/simon/eclipse-workspace/simulator/src/Data/storedResults/" + saveFolder;
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
		String mStr = directory + "/memristance.txt";
		String vStr = directory + "/voltage.txt";
		new ExportMatrix(mStr, memristanceMatrix);
		new ExportMatrix(vStr, voltageMatrix);
		
	}
}