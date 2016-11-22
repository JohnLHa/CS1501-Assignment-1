/*John Ha
CS 1501
Assignment 1
2/11/2016
*/
import java.io.*;
import java.util.*;

public class pw_check{
	public static void main(String[] args){
		char[] sym = new char[]{'7', '4', '0', '3', '1', '$'};
		int i;
		boolean exists;
		boolean escape = false;
		Scanner kb = new Scanner(System.in);
		
		DLB badList = new DLB();
		String fileName = "dictionary.txt";
		String line = null;
		
		
		if(args.length>0){//checks if -g is run in the command line
			System.out.println("Ideally, would be generating good passwords now and writing to two files.");
			DLB goodList = new DLB();
			//goodList.enumGood(); //Enumerate all good passwords
			//goodList.printing(); //Then writes them all to a .txt file
		}
		else{
			try {//reads dictionary.txt and writes it to the badList DLB
				FileReader file = new FileReader(fileName);
				BufferedReader buffRead = new BufferedReader(file);
			
				while((line = buffRead.readLine()) != null){//adds each word from the .txt file to the DLB
					badList.add(line);
				}
				buffRead.close();
			}
			catch(FileNotFoundException ex){//catches exceptions
				System.out.println("Cannot open " + fileName + " since file was not found.");
			}
			catch(IOException ex){
				System.out.println("Cannot read " + fileName + " due to IO Exception.");
			}	
		
			for(i=0;i<6;i++){//adds all variations of symbols into the bad passwords list
				badList.replace(sym[i]);
			}
		
			do{//prompts the user to print a password, then judges their password
				System.out.println("Please enter in your desired password.");
				String input = kb.nextLine();
				//checks if the word exists in the bad passwords list
				exists = badList.search(input);
				
				if(exists)
					System.out.println("Sorry, that's a terrible password.");
				else{
					System.out.println("Wow. You've finally become wise for once.");
					escape =true;
				}
			}while(!escape);
		}
	}
}