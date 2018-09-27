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
			network.addmonitor(3); //v3
			network.addmonitor(4); //i1
			System.out.println("Starting simulation");
			network.operateNetwork(0.0, SIMULATION_TIME);
			System.out.println("Simulation finished");
			
			
			// get monitors
			System.out.println("Extract gathered data from monitors");
			ArrayList<Monitor> monitors = network.getMonitors(); //length should be 3+3=6
			System.out.println("Length of array list is: " + monitors.size() );
			String directory = "C:/Users/simkarls/eclipse-workspace/Simulator/Data";
			
			Monitor vMonitor1 = monitors.get(0);
			Monitor iMonitor1 = monitors.get(3);
			Monitor mMonitor1 = monitors.get(4);
			ArrayList<Double> v1 = vMonitor1.getValues();
			ArrayList<Double> i1 = iMonitor1.getValues();
			ArrayList<Double> m1 = mMonitor1.getValues();
			String vStr1 = directory + "/v1.txt";
			String iStr1 = directory + "/i1.txt";
			String mStr1 = directory + "/m1.txt";
			new savaToFile(v1, vStr1);
			new savaToFile(i1, iStr1);
			new savaToFile(m1, mStr1);
			
		}
		
		// runs if no textile input is given (for testing purpose)
		else {
			System.out.println("Missing input file name");
			// test of simulator
//			
//			int n = 1;
//			Network network = new Network(n);
//			
//			// memristor
//			double initR = 4.0;
//			double minR = 1.0;
//			double maxR = 7.0;
//			double alpha  = 1.0;
//			double beta = 3.0;
//			double vthres = 0.5;
//			int nPos = 1;
//			int nNeg = 0;
//			Memristor memristor = new Memristor(initR, maxR, minR, alpha, beta, vthres, nPos, nNeg);
//			memristor.beUsedForState();
//			network.addbranch(memristor);
//			
//			// voltage
//			double[] ampls = new double[6];
//			ampls[0] = 1.2;
//			ampls[1] = 4;
//			ampls[2] = 3;
//			ampls[3] = 1;
//			ampls[4] = 4;
//			ampls[5] = 6.28;
//			int nPosSource = 1;
//			int nNegSource = 0;
//			VoltageSource voltageSource = new VoltageSource(ampls, nPosSource, nNegSource);
//			network.addsource(voltageSource);
//			
//			// monitor and operate
//			network.addmonitor(1);
//			network.addmonitor(2);
//			network.operateNetwork(0.0, 20.0);
//			
//			// get monitors
//			ArrayList<Monitor> monitors = network.getMonitors();
//			
//			Monitor monitor1 = monitors.get(0);
//			ArrayList<Double> values1 = monitor1.getValues();
//			String directory = "C:/Users/simkarls/eclipse-workspace/Simulator/Data";
//			String str1 = directory + "/v1.txt";
//			savaToFile sv1 = new savaToFile(values1, str1);
//			
//			Monitor monitor2 = monitors.get(1);
//			ArrayList<Double> values2 = monitor2.getValues();
//			String str2 = directory + "/i.txt";
//			savaToFile sv2 = new savaToFile(values2, str2);
//			
//			Monitor monitor3 = monitors.get(2);
//			ArrayList<Double> values3 = monitor3.getValues();
//			String str3 = directory + "/memristance1.txt";
//			savaToFile sv3 = new savaToFile(values3, str3);
			
			  
		}	
		
		
		
		
	}
}

