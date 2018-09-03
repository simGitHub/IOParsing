import java.io.*;

public class IOParser{
	public void DefineNetwork(String textFileName) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(textFileName));
			String line = br.readLine();
		}
		catch(FileNotFoundException e){
			System.out.print("Text file not found");
		}
		catch(IOException e){
			System.out.print("Error reading from file");
		}
		if (br != null) try { br.close(); } catch (IOException e) {}
		
	}

}
