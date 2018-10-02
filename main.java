import java.util.ArrayList;
import testing.savaToFile;
import circuitNetwork.*;

public class main {
	public static void main(String[] args) {
		// Insert temporary code below (used for testing/debugging)

		

		// End of temporary code
		IOParser par = new IOParser();
		final int SIMULATION_TIME = 50;
		if(args.length != 0) {
			Network network = null;
			String textFileName = args[0];
			network = par.DefineNetwork(textFileName, network);
			
			// memristor (beUsedForState()) is utilized in IOParser class
			network.addmonitor(1); //v1
			network.addmonitor(2); //v2
			System.out.println("Starting simulation");
			network.operateNetwork(0.0, SIMULATION_TIME);
			System.out.println("Simulation finished");
			
			
			// get monitors
			System.out.println("Extract gathered data from monitors");
			ArrayList<Monitor> monitors = network.getMonitors(); //length should be 3+3=6
			System.out.println("Length of array list is: " + monitors.size() );
			String directory = "C:/Users/simon/eclipse-workspace/Simulator/Data";
			
			Monitor vMonitor1 = monitors.get(0);
			Monitor mMonitor1 = monitors.get(2);
			ArrayList<Double> v1 = vMonitor1.getValues();
			ArrayList<Double> m1 = mMonitor1.getValues();
			String vStr1 = directory + "/v1.txt";
			String mStr1 = directory + "/m1.txt";
			new savaToFile(v1, vStr1);
			new savaToFile(m1, mStr1);
			
		}
		
		// runs if no textile input is given (for testing purpose)
		else {
			System.out.println("Missing input file name");
			// test of simulator

			
			  
		}	
		
		
		
		
	}
}

