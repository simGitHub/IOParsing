import java.util.ArrayList;
import circuitNetwork.*;

public class main {
	public static void main(String[] args) {
		// Insert temporary code below (used for testing/debugging)

		

		// End of temporary code
		final int SIMULATION_TIME = 19;
		IOParser par = new IOParser();
		if(args.length != 0) {
			String directory = "/users/simon/eclipse-workspace/simulator/src/data/preset/series/";
			String dataName; double dt = 0.01; 
			Network network = null; String configFile = args[0];
			network = par.DefineNetwork(configFile, network);
			
			
			dataName = "square";
			new SimulateNetwork(network, par, directory, dataName, dt, SIMULATION_TIME);
			
			dataName = "triangle";
			new SimulateNetwork(network, par, directory, dataName, dt, SIMULATION_TIME);


		}
		
		else {
			System.out.println("Missing argument for input file name");

		}	
		
		
		
		
	}
}

