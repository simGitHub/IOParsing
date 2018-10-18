import java.util.ArrayList;
import circuitNetwork.*;

public class main {
	public static void main(String[] args) {
		// Insert temporary code below (used for testing/debugging)

		

		// End of temporary code
		final int SIMULATION_TIME = 19;
		IOParser par = new IOParser();
		if(args.length != 0) {
			String directory = "/users/simon/eclipse-workspace/simulator/src/data/par/";
			String dataName; double dt = 0.01; String dataDir;
			Network network = null; String configFile = args[0];
			network = par.DefineNetwork(configFile, network);
			
			
			dataDir = directory + "/square/";
			dataName = "square_2";
			new SimulateNetwork(network, par, dataDir, dataName, dt, SIMULATION_TIME);
			dataName = "square_4";
			new SimulateNetwork(network, par, dataDir, dataName, dt, SIMULATION_TIME);
			dataName = "square_2_dim";
			new SimulateNetwork(network, par, dataDir, dataName, dt, SIMULATION_TIME);
			
			//
			dataDir = directory + "/triangle/";
			dataName = "triangle_2";
			new SimulateNetwork(network, par, dataDir, dataName, dt, SIMULATION_TIME);
			dataName = "triangle_4";
			new SimulateNetwork(network, par, dataDir, dataName, dt, SIMULATION_TIME);
			dataName = "triangle_2_amped";
			new SimulateNetwork(network, par, dataDir, dataName, dt, SIMULATION_TIME);


		}
		
		else {
			System.out.println("Missing argument for input file name");

		}	
		
		
		
		
	}
}

