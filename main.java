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
			
			System.out.println("Starting simulation");
			network.operateNetwork(0.0, SIMULATION_TIME);
			System.out.println("Simulation finished");
			
			
			// get monitors
			System.out.println("Extract gathered data from monitors");
			ArrayList<Monitor> monitors = network.getMonitors();
			System.out.println("Length of array list is: " + monitors.size() );
			String directory = "/users/simon/eclipse-workspace/simulator/src/data/";
			
			Monitor vMonitor1 = monitors.get(0);
			Monitor vMonitor2 = monitors.get(1);
			Monitor mMonitor1 = monitors.get(2);
			Monitor mMonitor2 = monitors.get(3);
			ArrayList<Double> v1 = vMonitor1.getValues();
			ArrayList<Double> v2 = vMonitor2.getValues();
			ArrayList<Double> m1 = mMonitor1.getValues();
			ArrayList<Double> m2 = mMonitor2.getValues();
			String vStr1 = directory + "/v1.txt";
			String vStr2 = directory + "/v2.txt";
			String mStr1 = directory + "/m1.txt";
			String mStr2 = directory + "/m2.txt";
			new savaToFile(v1, vStr1);
			new savaToFile(v2, vStr2);
			new savaToFile(m1, mStr1);
			new savaToFile(m2, mStr2);
			
		}
		
		// runs if no textile input is given (for testing purpose)
		else {
			System.out.println("Missing input file name");
			// test of simulator

			
			  
		}	
		
		
		
		
	}
}

