import java.util.ArrayList;
import circuitNetwork.*;

public class main {
	public static void main(String[] args) {
		// Insert temporary code below (used for testing/debugging)

		

		// End of temporary code
		final int SIMULATION_TIME = 19;
		IOParser par = new IOParser();
		String saveFolder; String datasetFileName; double dt; String dataType;
		if(args.length != 0) {
			String datasetDirectory = "/users/simon/eclipse-workspace/simulator/src/data/datasets/";
			Network network = null;
			
			String configFile = args[0];
			network = par.DefineNetwork(configFile, network);
			
			// simulate with triangle signal
			dataType = "triangle";
			saveFolder = dataType;
			datasetFileName = dataType + ".txt";
			datasetFileName = datasetDirectory + datasetFileName;
			dt = 0.01;
			new SimulateNetwork(network, datasetFileName, dt, par, saveFolder, SIMULATION_TIME);
			
			// simulate with square signal
			network.resetSources();
			dataType = "square";
			saveFolder = dataType;
			datasetFileName = dataType + ".txt";
			datasetFileName = datasetDirectory + datasetFileName;
			dt = 0.01;
			new SimulateNetwork(network, datasetFileName, dt, par, saveFolder, SIMULATION_TIME);
		
			
		}
		
		else {
			System.out.println("Missing argument for input file name");

		}	
		
		
		
		
	}
}

