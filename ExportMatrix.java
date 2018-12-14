import java.io.*;
import java.util.ArrayList;

public class ExportMatrix {
	BufferedWriter bw;
	public ExportMatrix(String filename, ArrayList<ArrayList<Double>> matrix) {
	    try {
	    	String valueString;
	        bw = new BufferedWriter(new FileWriter(filename));
	        for(int i =0; i<matrix.size();i++) {
	        	ArrayList<Double> valueSet = matrix.get(i);
	        	for(int j=0; j<valueSet.size();j++) {
	        		valueString = Double.toString(valueSet.get(j));
	        		if(j != (valueSet.size() - 1)) {
	        			bw.write(valueString + " ");
	        		}
	        		else {
	        			bw.write(valueString);
	        		}
	        	}
	        	bw.newLine();
	        }
	        bw.flush();
	    } catch (IOException e) {System.out.println("Error in ExportMatrix: probably the directory does not exist");}
	//System.out.println("Data saved in " + filename);	
	}
}
