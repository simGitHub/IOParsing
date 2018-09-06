/*
 * Reads IOParsing text file. For incorrect input tokens an error should be displayed.
 * See end of code for a template of IOParsing text file.
 * @author Simon Karlsson
 * @email simkarls@student.chalmers.se
 */

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
				System.out.println("Starting to read lines.");
				while(END_NETWORK != true) {
					line = br.readLine();
					
					if(line == null) {
						System.out.println("Null line detected: missing or incorrect END_NETWORK line.");
						break;
					}
					
					// *
					// Reads tokens from line and checks if line is empty or not
					if(!line.isEmpty()) { 
						StringTokenizer st = new StringTokenizer(line);
						// Detects node token
						if(st.nextToken().equals("#NODES")) {
							int numberOfNodes= Integer.parseInt(st.nextToken());
							if(numberOfNodes > 0) {
								n.setNumberOfNodes(numberOfNodes);
								System.out.println("Number of nodes is set to " + numberOfNodes + ".");
								numberOfNodesSpecified = true;
							}
							else {
								System.out.println("Number of nodes must be greater than zero.");
							}
						}
						// Detects Drive token
						
						// Detects Input signal token
						
						// Detects memristor tokens
					} else {
						System.out.println("Empty line detected.");
					}
					// *
					
					if(line.equals("END_NETWORK")){
						System.out.println("End of line.");
						END_NETWORK = true;
					}
				}
			}
			
			else {
				System.out.println("Incorrect first line, should be BEGIN_NETWORK.");
			}
		}
		catch(NumberFormatException StringToIntegerConvertion) {
			System.out.println("Error in converting from string to integer, parameter must be an integer.");
		}
		
		catch(FileNotFoundException FileReader){
			System.out.println("Text file not found.");
		}
		catch(IOException readLine){
			System.out.println("Error reading from file.");
		}
		
		if (br != null) try { br.close(); } catch (IOException e) {}
	}
}

// ** Below is a template for text file for IOParsing (this line is not included in the template) **
// BEGIN_NETWORK
// #NODES 4
// DRIVE_SIGNAL 1,2
// INPUT_SIGNAL 3,4
// MEM 1,2|2,3,4
// MEM 2,3|3,4,5
// MEM 1,4|2,3,5
// END_NETWORK

