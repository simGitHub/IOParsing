import java.io.*;

public class IOParser{
	public void DefineNetwork(String textFileName) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(textFileName));
			String line = br.readLine();
			while(line != null) {
				System.out.println(line);
				line = br.readLine();
			}
		}
		catch(FileNotFoundException FileReader){
			System.out.print("Text file not found");
		}
		catch(IOException readLine){
			System.out.print("Error reading from file");
		}
		if (br != null) try { br.close(); } catch (IOException e) {}
	}
}
