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
import circuitNetwork.TwoPortElement;

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
					int nPosSource; int nNegSource; int numberOfAmpIndices; double[] ampls;					
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

						// Detects Drive token with the amplitude values
						else if(firstToken.equals("DRIVE_V")){
							nPosSource = Integer.parseInt(st.nextToken(" ,"));
							nNegSource = Integer.parseInt(st.nextToken(",|"));
							numberOfAmpIndices =  Integer.parseInt(st.nextToken("|,"));
							ampls = new double[numberOfAmpIndices];
							int i = 0;
							while(st.hasMoreTokens()) {
								ampls[i] = Double.parseDouble(st.nextToken());
								i = i + 1;
							}
							
							VoltageSource voltageSource = new VoltageSource(ampls, nPosSource, nNegSource);
							network.addsource(voltageSource);
							
							System.out.println("Voltage source set between node " + nPosSource + " and " + nNegSource);	
							System.out.print("Voltage source have DC set to: " + ampls[0]);
							System.out.print(", with frequency amplitudes: ");
							int j = 1;
							while( j < (numberOfAmpIndices - 1) ) {
									System.out.print(ampls[j] + ", ");
									j = j + 1;
								}
							System.out.println("and with frequency: " + ampls[numberOfAmpIndices - 1]);
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
//DRIVE_V 1,2|6|0, 0.4, -0.3, 0.7, -0.4, 6.28
//MEM 1, 2|2.0, 3.0, 4.5
//END_NETWORK

// ** Below is a generic version trying to explain the different parameters. **
//BEGIN_NETWORK
//#NODES #nodes
//DRIVE_V node1, node2|#indices_in_array|DC_value, ampl_1, ampl_2, ampl_3, ampl_4, frequency
//MEM node1,node2|initR, minR, maxR
//END_NETWORK

