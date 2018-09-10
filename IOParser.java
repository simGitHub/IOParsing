/*
 * Reads IOParsing text file. For incorrect input tokens an error should be displayed.
 * See end of code for a template of IOParsing text file.
 * @author Simon Karlsson
 * @email simkarls@student.chalmers.se
 */


/*
 * Temporarily thoughts: 
 * 		add some lines to check if necessary parameters have been set before e.g. adding memristors to the network.
 * 		E.g. check that the number of nodes have been when adding memristors.
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
			//boolean numberOfNodesSpecified = false;
			String line = br.readLine();
			
			if(line.equals("BEGIN_NETWORK")) {
				System.out.println("Starting to read lines.");
				while(END_NETWORK != true) {
					line = br.readLine();
					
					if(line == null) {
						System.out.println("Null line detected: missing or incorrect END_NETWORK line.");
						break;
					}
					
					else if(line.equals("END_NETWORK")){
						System.out.println("End of line.");
						END_NETWORK = true;
					}
					
					// Reads tokens from lines and adds parameters to the network. Also checks if line is empty or not
					int groundNode;
					int extNode1; int extNode2;
					int driveNode1; int driveNode2;
					int memNode1; int memNode2;
					double R; double Rmin; double Rmax;
					if(!line.isEmpty()) { 
						StringTokenizer st = new StringTokenizer(line);
						String firstToken = st.nextToken();
						// Detects node token
						if(firstToken.equals("#NODES")) {
							int numberOfNodes= Integer.parseInt(st.nextToken());
							if(numberOfNodes > 0) {
								n.setNumberOfNodes(numberOfNodes);
								System.out.println("Number of nodes is set to " + numberOfNodes);
								//numberOfNodesSpecified = true;
							}
							else {
								System.out.println("Number of nodes must be greater than zero.");
							}
						}
						// Detects EXT token
						else if(firstToken.equals("EXT")) {
							extNode1 = Integer.parseInt(st.nextToken(" ,"));
							extNode2 = Integer.parseInt(st.nextToken());
							n.setExtNodes(extNode1, extNode2);
							System.out.println("EXT set at node " + extNode1 + " and " + extNode2);
						}
						
						// Detects Ground signal token
						else if(firstToken.equals("GROUND")) {
							groundNode = Integer.parseInt(st.nextToken());
							n.setGround(groundNode);
							System.out.println("Ground set at node " + groundNode);	
						}
						
						// Detects Drive token
						else if(firstToken.equals("DRIVE_V")){
							driveNode1 = Integer.parseInt(st.nextToken(" ,"));
							driveNode2 = Integer.parseInt(st.nextToken());
							n.setDriveNodes(driveNode1, driveNode2);
							System.out.println("Drive set between node " + driveNode1 + " and " + driveNode2);	
						}
								
						// Detects memristor tokens
						else if(firstToken.equals("MEM")) {
							memNode1 = Integer.parseInt(st.nextToken(" ,"));
							memNode2 = Integer.parseInt(st.nextToken(",|"));
							R = Double.parseDouble(st.nextToken("|,"));
							Rmin = Double.parseDouble(st.nextToken());
							Rmax = Double.parseDouble(st.nextToken());
							n.addMemristor(memNode1, memNode2,R,Rmin,Rmax);
							System.out.println("Memristor added between node " + memNode1 + " and " + memNode2 + " with R = " + R + 
									", Rmin = " + Rmin + " and Rmax = " + Rmax + ".");
							
						}

						
					} else {
						System.out.println("Empty line detected.");
					}
					
					

				}
			}
			
			else {
				System.out.println("Incorrect first line, should be BEGIN_NETWORK.");
			}
		}
		catch(NoSuchElementException stringTokenizer) {
			System.out.println("Error with delimiter/tokenizer.");
			System.out.println("Error message: " + stringTokenizer.getLocalizedMessage() );
		}
		
		catch(NumberFormatException stringToIntegerConvertion) {
			System.out.println("Error in converting from string to integer/double, parameter must be an integer/double. ");
			System.out.println("Error message: " + stringToIntegerConvertion.getLocalizedMessage() );
		}
		
		catch(FileNotFoundException fileReader){
			System.out.println("Text file not found.");
		}
		catch(IOException readLine){
			System.out.println("Error reading from file.");
		}
		
		if (br != null) try { br.close(); } catch (IOException e) {}
	}
}

// ** Below is a template for text file for IOParsing (this line is not included in the template).**
//BEGIN_NETWORK
//#NODES 4
//GROUND 2
//DRIVE_V 1,2
//EXT 3,4
//MEM 1, 2|2.0, 3.0, 4.5
//MEM 2, 3|3.0, 4.0, 5.0
//MEM 1, 4|2.3, 3.0, 5.0
//END_NETWORK

// ** Below is a generic version.**
//BEGIN_NETWORK
//#NODES #nodes
//GROUND groundNode
//EXT node1,node2
//DRIVE_V node1,node2
//MEM node1,node2|R, Rmin, Rmax
//MEM node1,node2|R, Rmin, Rmax
//MEM node1,node2|R, Rmin, Rmax
//END_NETWORK

