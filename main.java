import java.util.ArrayList;
import circuitNetwork.*;

public class main {
	public static void main(String[] args) {
		// Insert temporary code below (used for testing/debugging)

		

		// End of temporary code
		final int SIMULATION_TIME = 19;
		IOParser par = new IOParser();
		if(args.length != 0) {
			String directory = "/users/simon/eclipse-workspace/simulator/src/data/";
			String dataDirectory = directory + "datasets/square/modified/frequency/";
			String saveDirectory = directory + "storedResults/square/modified/frequency/";
			
			String saveFileDir; String dataFileDir; String dataType;
			double dt = 0.01;
			Network network = null;
			
			String configFile = args[0];
			network = par.DefineNetwork(configFile, network);
			
			dataType = "square_1";
			dataFileDir = dataDirectory + dataType + ".txt";
			saveFileDir = saveDirectory + dataType + "/";
			new SimulateNetwork(network, dataFileDir, saveFileDir, dt, par, SIMULATION_TIME);
			
			// simulate with square signal
			network.resetSources(); 
			dataType = "square_2";
			dataFileDir = dataDirectory + dataType + ".txt";
			saveFileDir = saveDirectory + dataType + "/";
			new SimulateNetwork(network, dataFileDir, saveFileDir, dt, par, SIMULATION_TIME);
		
			
		}
		
		else {
			System.out.println("Missing argument for input file name");

		}	
		
		
		
		
	}
}

