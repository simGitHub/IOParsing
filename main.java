
public class main {
	public static void main(String[] args) {
//		System.out.print(args[0]);
		IOParser par = new IOParser();
		if(args[0] != null) {
			String textFileName = args[0];
			par.DefineNetwork(textFileName);
		}
		else {
			System.out.print("Missing input file name");
		}	
	}
}

