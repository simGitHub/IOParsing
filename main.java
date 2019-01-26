import circuitNetwork.*;

public class main {
	public static void main(String[] args) {
		final int SIMULATION_TIME = 20;
		IOParser par = null;
		if(args.length != 0) {
			String directory_standard = "/users/simon/eclipse-workspace/simulator/src/data/results/";
			String dataName; 
			double dt = 0.01; 
			String configFile = args[0];
			int nbrOfSongs = 100;
			Network network;
			String[] structures = {"2x1", "2x2", "2x3", "4x1", "4x2", "4x3", "8x1", "8x2", "8x3"};
			String directory = null;
			int grid_length = 2;
			int grid_depth = 1;
			for(String struct : structures) {
				grid_depth = Character.getNumericValue(struct.charAt(0));
				grid_length = Character.getNumericValue(struct.charAt(2));
				System.out.println("grid_depth: " + grid_depth + ", grid_length: " + grid_length);
				System.out.println("Starting to run structure: " + struct);
				directory = directory_standard + "/" + struct;
				for(int k = 1; k < 11 ; k ++) {
					System.out.println("Simulation number: " + k);
					par = new IOParser();
					par.grid_depth = grid_depth;
					par.grid_length = grid_length;
					network = par.BuldNetworkFromConfigFile(configFile);
					for(int i = 0; i < nbrOfSongs; i++) {
						network.resetSources();
						if(i % 10 == 0) {
							System.out.println("Running blues song " + Integer.toString(i));
						}
						if(i < 10) {
							dataName = "blues.0000" + Integer.toString(i);
						}
						else {
							dataName = "blues.000" + Integer.toString(i);
						}
						new SimulateNetwork(network, par, directory, dataName, dt, SIMULATION_TIME, k);
					}
					for(int i = 0; i < nbrOfSongs; i++) {
						network.resetSources();
						if(i % 10 == 0) {
							System.out.println("Running classical song " + Integer.toString(i));
						}
						if(i < 10) {
							dataName = "classical.0000" + Integer.toString(i);
						}
						else {
							dataName = "classical.000" + Integer.toString(i);
						}
						new SimulateNetwork(network, par, directory, dataName, dt, SIMULATION_TIME, k);
					}
				}	
			}
			System.out.println("Simulation is finished");
				
	
	
				
		}
		else {
			System.out.println("Missing argument for input file name");
		}	
	}
}
