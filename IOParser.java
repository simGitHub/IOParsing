import java.io.*;
import java.util.*;

public class IOParser{
	public void DefineNetwork(String textFileName) {
		BufferedReader br = null;
		Network n = new Network();
		try {
			br = new BufferedReader(new FileReader(textFileName));
			boolean END_NETWORK = false;
			boolean numberOfNodesSpecified = false;
			String line = br.readLine();
			
			if(line.equals("BEGIN_NETWORK")) {
				while(END_NETWORK != true) {
					// System.out.println(line);
					line = br.readLine();
					
					if(!line.isEmpty()) {
						StringTokenizer st = new StringTokenizer(line);
						if(st.nextToken().equals("#NODES")) {
							int numberOfNodes= Integer.parseInt(st.nextToken());
							if(numberOfNodes > 0) {
								n.setNumberOfNodes(numberOfNodes);
								System.out.println("Number of nodes set to " + numberOfNodes);
								numberOfNodesSpecified = true;
							}
							else {
								System.out.print("Number of nodes must be greater than zero.");
							}
						}
					} else {
						System.out.println("Empty line detected.");
					}
					
					if(line == null) {
						System.out.println("Missing or incorrect END_NETWORK line.");
						END_NETWORK = true; // use break instead?
					}
					else if(line.equals("END_NETWORK")){
						END_NETWORK = true;
					}
				}
			}
			
			else {
				System.out.println("Missing or incorrect BEGIN_NETWORK line.");
			}
		}
		
		catch(FileNotFoundException FileReader){
			System.out.print("Text file not found.");
		}
		catch(IOException readLine){
			System.out.print("Error reading from file.");
		}
		if (br != null) try { br.close(); } catch (IOException e) {}
	}
}
