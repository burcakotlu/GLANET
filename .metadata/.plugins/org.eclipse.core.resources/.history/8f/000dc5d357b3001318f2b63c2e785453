package simulation;


public class CreateSimulatedData {
	
	int numberofCaseandControl = 2;
	ArrayList referenceSNPs = new ArrayList();
	int[][] caseandcontrolArray = new int[numberofCaseandControl][];
	
	public void writeSimulatedData(){
		 FileWriter fileWriter;
		 BufferedWriter out = null;
			 
		 for(int i= 0; i< numberofCaseandControl; i++){
			 Integer ii= new Integer(i);
			
			try {
//				home computer
				fileWriter = new FileWriter("C:\\eclipse_juno_workspace\\Doktora\\src\\simulation\\casecontrolSimulatedData" + ii.toString() + ".txt");				
				out = new BufferedWriter(fileWriter);
				
				for (int j=0; j<referenceSNPs.size(); j++){
					// decimal zero NUL character
					// decimal one  start of heading character
					// decimal 48 zero character
					// decimal 49 one character
					// therefore you add 48
					  out.write(caseandcontrolArray[i][j]+48);
				}
				out.close();
				  
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }		
	}
	
	public void createSimulatedData(){
		// Use referenceSNPs
		Random r = new Random();
		int numberofReferenceSNPS = referenceSNPs.size();
		int halfNumberofReferenceSNPs = numberofReferenceSNPS/2;
		int subsetSize = 0;
		int index = 0;
		
		for (int i = 0; i< numberofCaseandControl; i++){
			
			while (subsetSize <halfNumberofReferenceSNPs)
				subsetSize = r.nextInt(numberofReferenceSNPS);
			
			caseandcontrolArray[i] = new int[numberofReferenceSNPS];
			
			for (int k=0; k<numberofReferenceSNPS; k++)
				caseandcontrolArray[i][k]=0;
			
			for (int j = 0; j<subsetSize; j++){
				index = r.nextInt(numberofReferenceSNPS);
				caseandcontrolArray[i][index]=1;
			}
		}
		System.out.println("Data Simulation is over");
	}
	
	public void readInputFile(String inputFilename){
		try{
			  // Open the file that is the first 
			  // command line parameter
			  FileInputStream fstream = new FileInputStream(inputFilename);
			  // Get the object of DataInputStream
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  //Read File Line By Line

			  //home computer			  
			  FileWriter fileWriter = new FileWriter("C:\\eclipse_juno_workspace\\Doktora\\src\\simulation\\reference_snps.txt");
			  BufferedWriter out = new BufferedWriter(fileWriter);
			  
			  while ((strLine = br.readLine()) != null)   {
			  // ADD the content to the ArrayList
				  int firstIndexofSpace = strLine.indexOf('\t');
				  String ssnumber = strLine.substring(0, firstIndexofSpace);
				  referenceSNPs.add(ssnumber);
				  out.write(ssnumber+"\n");
			  }
			  //Close the input stream
			  System.out.println("Number of snps in dummy input data: " +  referenceSNPs.size());
			  out.close();
			  in.close();
			  }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }
			}
	
	
	public static void main(String[] args){		
		
		//Get the jvm heap size.
        long heapSize = Runtime.getRuntime().totalMemory();
         
        //Print the jvm heap size.
        System.out.println("Heap Size = " + heapSize);
        
        System.out.println(java.lang.Runtime.getRuntime().maxMemory()); 
		
		CreateSimulatedData createSimulatedData = new CreateSimulatedData();
		createSimulatedData.readInputFile(args[0]);
		createSimulatedData.createSimulatedData();
		createSimulatedData.writeSimulatedData();
		
	}

}
