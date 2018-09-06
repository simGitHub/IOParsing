
public class main {
	public static void main(String[] args) {
		// Insert temporary code below (used for testing/debugging)

		// End of temporary code
		
		IOParser par = new IOParser();
		if(args.length != 0) {
			String textFileName = args[0];
			par.DefineNetwork(textFileName);
		}
		else {
			System.out.print("Missing input file name");
			  
		}	
	}
}

