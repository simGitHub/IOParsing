import java.util.ArrayList;
import testing.savaToFile;
import circuitNetwork.*;

public class main {
	public static void main(String[] args) {
		// Insert temporary code below (used for testing/debugging)

		

		// End of temporary code
		IOParser par = new IOParser();
		final int SIMULATION_TIME = 19;
		if(args.length != 0) {
			
			Network network = null;
			String textFileName = args[0];
			network = par.DefineNetwork(textFileName, network);
			System.out.println(" ** Starting simulation ** ");
			network.operateNetwork(0.0, SIMULATION_TIME);
			System.out.println(" ** Simulation finished ** ");
			
			
			// exctract all added monitors, memristors to one 2D text file and voltage to one 2D text file. One column corresponds to a value set.
			System.out.println("Extracting gathered data from all memristor and voltage monitors and exporting them to text files");
			ArrayList<Monitor> monitors = network.getMonitors();
			System.out.println("Length of monitor array list: " + monitors.size() );
			String directory = "/users/simon/eclipse-workspace/simulator/src/data/";
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
			String mStr = directory + "/memristanceValues.txt";
			String vStr = directory + "/voltageValues.txt";
			new ExportMatrix(mStr, memristanceMatrix);
			new ExportMatrix(vStr, voltageMatrix);	
		}
		
		else {
			System.out.println("Missing argument for input file name");

		}	
		
		
		
		
	}
}

