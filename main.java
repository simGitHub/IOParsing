import circuitNetwork.*;

public class main {
	public static void main(String[] args) {
		final int SIMULATION_TIME = 20;
		IOParser par = new IOParser();
		if(args.length != 0) {
			String directory = "/users/simon/eclipse-workspace/simulator/src/data/music/n_mfcc/4";
			String dataName; 
			double dt = 0.01; 
			String configFile = args[0];
			int nbrOfSongs = 100;
			Network network = par.BuldNetworkFromConfigFile(configFile);
			
			for(int i = 0; i < nbrOfSongs; i++) {
				if(i % 10 == 0) {
					System.out.println("Running blues song " + Integer.toString(i));
				}
				network.resetSources();
				if(i < 10) {
					dataName = "blues.0000" + Integer.toString(i);
				}
				else {
					dataName = "blues.000" + Integer.toString(i);
				}
				new SimulateNetwork(network, par, directory, dataName, dt, SIMULATION_TIME);
			}
			for(int i = 0; i < nbrOfSongs; i++) {
				if(i % 10 == 0) {
					System.out.println("Running classical song " + Integer.toString(i));
				}
				network.resetSources();
				if(i < 10) {
					dataName = "classical.0000" + Integer.toString(i);
				}
				else {
					dataName = "classical.000" + Integer.toString(i);
				}
				new SimulateNetwork(network, par, directory, dataName, dt, SIMULATION_TIME);
			}
			System.out.println("Simulation is finished");


			
		}
		else {
			System.out.println("Missing argument for input file name");
		}	
	}
}
