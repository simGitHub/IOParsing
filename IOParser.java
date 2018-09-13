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
import circuitNetwork.*;

public class IOParser{
	public Network DefineNetwork(String textFileName, Network network) {
		BufferedReader br = null;
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
					// int groundNode;
					// int extNode1; int extNode2;
					int nPosSource; int nNegSource;
					double[] ampls = new double[6]; 
					ampls[0] = -1.2; ampls[1] = 0.4; ampls[2] = -0.3; ampls[3] = 0.7; ampls[4] = -0.4; ampls[5] = 6.28;
					int nPos; int nNeg;
					double alpha = 1.0; double beta = 3.0; double vthres = 0.5; // For now all memristors have these parameters
					double initR; double minR; double maxR;
					if(!line.isEmpty()) { 
						StringTokenizer st = new StringTokenizer(line);
						String firstToken = st.nextToken();
						// Detects node token
						if(firstToken.equals("#NODES")) {
							int numberOfNodes= Integer.parseInt(st.nextToken());
							if(numberOfNodes > 0) {
								network = new Network(numberOfNodes);
								System.out.println("Number of nodes is set to " + numberOfNodes);
								//numberOfNodesSpecified = true;
							}
							else {
								System.out.println("Number of nodes must be greater than zero.");
							}
						}
						// Detects EXT token (necessary?)
//						else if(firstToken.equals("EXT")) {
//							extNode1 = Integer.parseInt(st.nextToken(" ,"));
//							extNode2 = Integer.parseInt(st.nextToken());
//							n.setExtNodes(extNode1, extNode2);
//							System.out.println("EXT set at node " + extNode1 + " and " + extNode2);
//						}
						
						// Detects Ground token (necessary?)
//						else if(firstToken.equals("GROUND")) {
//							groundNode = Integer.parseInt(st.nextToken());
//							n.setGround(groundNode);
//							System.out.println("Ground set at node " + groundNode);	
//						}
						
						// Detects Drive token
						else if(firstToken.equals("DRIVE_V")){
							nPosSource = Integer.parseInt(st.nextToken(" ,"));
							nNegSource = Integer.parseInt(st.nextToken());
							VoltageSource voltageSource = new VoltageSource(ampls, nPosSource, nNegSource);
							network.addsource(voltageSource);
							System.out.println("Voltage source set between node " + nPosSource + " and " + nNegSource);	
						}
								
						// Detects memristor tokens
						else if(firstToken.equals("MEM")) {
							nPos = Integer.parseInt(st.nextToken(" ,"));
							nNeg = Integer.parseInt(st.nextToken(",|"));
							initR = Double.parseDouble(st.nextToken("|,"));
							minR = Double.parseDouble(st.nextToken());
							maxR = Double.parseDouble(st.nextToken());
							Memristor memristor = new Memristor(initR, maxR, minR, alpha, beta, vthres, nPos, nNeg);
							memristor.beUsedForState();
							network.addbranch(memristor);
							System.out.println("Memristor added between node " + nPos + " and " + nNeg + " with initR = " + initR + 
									", minR = " + minR + " and maxR = " + maxR + ".");
							
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
		
		// return statement, returns the created network object
		return network;
	}
	
}

// ** Below is a template for text file for IOParsing (this line is not included in the template).**
//BEGIN_NETWORK
//#NODES 4
//(GROUND 2)
//DRIVE_V 1,2
//(EXT 3,4)
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

