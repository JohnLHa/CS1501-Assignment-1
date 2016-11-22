/*John Ha
CS 1501
Assignment 1
2/11/2016
*/

import java.util.*;
import java.io.*;


public class DLB{
	public boolean isEmpty;//checks if the DLB is empty
	public int i;
	public int j = 0;
	public LinkedList root;//the root linked list that holds all linked lists
	public int length; //length of the inputted string
	public final char TERM = '#';//terminator to indicate end of word
	
	public DLB(){
		isEmpty = true;
		root = new LinkedList();
	}
	
	public Node getRoot(){
		return root.head;
	}
	
	public boolean add(String input){//adds a new string to the DLB
		length = input.length();
		
		if (isEmpty){//if the DLB is empty, will create the first string
			LinkedList current = new LinkedList(input.charAt(0));
			this.root = current;
			for(i=1; i<length; i++){//creates children nodes/linked lists to the root
				LinkedList leaf = new LinkedList(input.charAt(i));
				current.head.child = leaf.head;
				current = leaf;
				if(i == length-1){ //Puts in terminator value after the whole word is in the DLB
					LinkedList last = new LinkedList(TERM);
					current.head.child = last.head;

				}
			}
			isEmpty = false;
			return true;
		}
		else{//any new string will be added this way
			Node checkNode = root.head;
			
			for(i=0; i<length; i++){//iterate each letter of the string
				boolean charExists = false;//becomes true to break the while loop if the character of the string is placed properly
				
				while(!charExists){				
					if(checkNode.getData()==input.charAt(i)){//if the char already exists
						if(checkNode.child==null && i != (length-1)){//preps a new child if there is none
							LinkedList next = new LinkedList(input.charAt(i+1));
							checkNode.child = next.head;
						}
						if(i == length-1){
							LinkedList last = new LinkedList(TERM);//Puts in terminator value after the whole word is in the DLB
							checkNode.child = last.head;
						}
						checkNode = checkNode.child;//marker oves to child
						charExists = true;
						break;
					}
					if(checkNode.sibling!= null){//moves to sibling
						checkNode = checkNode.sibling;
					}
					else{//makes a new sibling then saves the value there.
						Node sib = new Node(input.charAt(i));
						checkNode.sibling = sib;
						checkNode = checkNode.sibling;

						if(checkNode.child==null && i != (length-1)){//preps a new child if there is none
							LinkedList next = new LinkedList(input.charAt(i+1));
							checkNode.child = next.head;
						}
						if(i == length-1){
							LinkedList last = new LinkedList(TERM);//Puts in terminator value after the whole word is in the DLB
							checkNode.child = last.head;
						}
						checkNode = checkNode.child;//moves to child
						charExists = true;		
					}		
				}
			}
		}	
	return true;		
	}
	
	public boolean search(String word){
		Node main = root.head;
		boolean truth=true;//return value for the search
		int i =0;
		
		return search(main, truth, word, i);//recursive call
	}
	public boolean search(Node main, boolean truth, String word, int i){
		
		if(main.getData() == word.charAt(i)){//checks current node if it has the character
			if(main.child == null){//if yes, checks if there's a child node
				truth = false;;
			}
			else if(main.child.getData() == TERM)//checks if we have reached end of the word 
				truth = true;
			else{//moves to child and searches recursively
				main=main.child;
				truth = search(main, truth, word, i+1);
			}
		}
		else{//if current node is not the right value, checks siblings
			if(main.sibling==null)//checks if sibling node exists. if not, returns false
				truth = false;
			else{//moves to sibling and searches recursively when sibling is found
				main= main.sibling;
				truth = search(main, truth, word, i);
			}
		}
		return truth;
	}
	
	
	public void printing(){//prints out every word in the DLB----- DOES NOT WORK YET
		StringBuilder word= new StringBuilder();
		Node find = root.head;
		
		try{
			PrintWriter write = new PrintWriter("goodpass.txt");
			printing(find, word, write);//calls recursive function
			write.close();
		}
		catch(FileNotFoundException ex){
			System.out.println("Cannot open for some reason.");
		}
	}
	public void printing(Node find, StringBuilder concat, PrintWriter write){
		if(find.getData()!=TERM){//
			find.found=true;
			concat.insert(0, find.getData());//concatenates a string from root to leaf
		}
		else{//if terminator is found, prints out the string, then marks the terminator as found to avoid rereading
			write.println(concat.reverse());
			//System.out.println(concat.reverse());
			find.found=true;
		}
		if(find.child!=null && !find.child.found){
			printing(find.child, concat, write);
		}
		if(find.sibling!=null && !find.sibling.found){
			printing(find.sibling, concat, write);
		}
		else{//deletes letters that a reread going back up the trie
			if(concat.length()>0)
				concat.deleteCharAt(concat.length()-1);
		}
	}
	
	public void reset(Node rootNode)//is supposed to reset the found values for the nodes ----DOES NOT WORK YET
    {
        if (rootNode.child!=null)
        {
			rootNode.found = false;
            reset(rootNode.child);
        }
        if (rootNode.sibling!=null)
        {
			rootNode.found = false;
            reset(rootNode.sibling);
        }
    }
	
	public void replace(char sym){//replaces a letter with a specified character that can be part of a bad password
		Node main = root.head;
		replace(main, sym);//calls recursive function
	}
	public void replace(Node main, char sym){
		if(main.child!=null){
			replace(main.child, sym);
		}
		if(main.sibling!=null){
			replace(main.sibling, sym);
		}
		
		if(sym=='7'){//this if statement will add the value 7 everywhere there is a 't'
			if(main.getData()=='t'){
				Node copy = new Node();//creates a copy node that will be placed under '7'
				copy = main.child;
				
				while(main.sibling!=null){//traverses to end of the linked list containing 't'
					main=main.sibling;
				}
				Node sib = new Node('7');
				main.sibling = sib;//sets new sibling value to '7' as another possible password
				main=main.sibling;
				main.child = copy;//brings the copy of the child under 't' over to '7'
			}
		}
		if(sym=='4'){//this if statement will add the value 4 everywhere there is a 'a'
			if(main.getData()=='a'){
				Node copy = new Node();//creates a copy node that will be placed under '4'
				copy = main.child;
				
				while(main.sibling!=null){//traverses to end of the linked list containing 'a'
					main=main.sibling;
				}
				Node sib = new Node('4');
				main.sibling = sib;//sets new sibling value to '4' as another possible password
				main=main.sibling;
				main.child = copy;//brings the copy of the child under 'a' over to '4'
			}
		}
		if(sym=='0'){//this if statement will add the value 0 everywhere there is a 'o'
			if(main.getData()=='o'){
				Node copy = new Node();//creates a copy node that will be placed under '0'
				copy = main.child;
				
				while(main.sibling!=null){//traverses to end of the linked list containing 'o'
					main=main.sibling;
				}
				Node sib = new Node('0');
				main.sibling = sib;//sets new sibling value to '0' as another possible password
				main=main.sibling;
				main.child = copy;//brings the copy of the child under 'o' over to '0'
			}
		}
		if(sym=='3'){//this if statement will add the value 3 everywhere there is a 'e'
			if(main.getData()=='e'){
				Node copy = new Node();//creates a copy node that will be placed under '3'
				copy = main.child;
				
				while(main.sibling!=null){//traverses to end of the linked list containing 'e'
					main=main.sibling;
				}
				Node sib = new Node('3');
				main.sibling = sib;//sets new sibling value to '3' as another possible password
				main=main.sibling;
				main.child = copy;//brings the copy of the child under 'e' over to '3'
			}
		}
		if(sym=='1'){//this if statement will add the value 1 everywhere there is a 'i'
			if(main.getData()=='i'){
				Node copy = new Node();//creates a copy node that will be placed under '1'
				copy = main.child;
				
				while(main.sibling!=null){//traverses to end of the linked list containing 'i'
					main=main.sibling;
				}
				Node sib = new Node('1');
				main.sibling = sib;//sets new sibling value to '1' as another possible password
				main=main.sibling;
				main.child = copy;//brings the copy of the child under 'i' over to '1'
			}
		}
		if(sym=='1'){//this if statement will add the value 1 everywhere there is a 'l'
			if(main.getData()=='l'){
				Node copy = new Node();//creates a copy node that will be placed under '1'
				copy = main.child;
				
				while(main.sibling!=null){//traverses to end of the linked list containing 'l'
					main=main.sibling;
				}
				Node sib = new Node('1');
				main.sibling = sib;//sets new sibling value to '1' as another possible password
				main=main.sibling;
				main.child = copy;//brings the copy of the child under 'l' over to '1'
			}
		}
		if(sym=='$'){//this if statement will add the value $ everywhere there is a 's'
			if(main.getData()=='s'){
				Node copy = new Node();//creates a copy node that will be placed under '$'
				copy = main.child;
				
				while(main.sibling!=null){//traverses to end of the linked list containing 's'
					main=main.sibling;
				}
				Node sib = new Node('$');
				main.sibling = sib;//sets new sibling value to '$' as another possible password
				main=main.sibling;
				main.child = copy;//brings the copy of the child under 's' over to '$'
			}
		}
	}
	/*
	My attempt at trying to enumerate all good possible passwords with no exhaustive search or pruning
	because I ran out of time. Sorry. The thought is here, but there's definitely a lot that needs
	to be fixed.
	*/
	public void enumGood(){
		int j, k, p;
		boolean num, letter, sym = false;
		int numCount, letterCount, symCount = 0;
		
		char[] array = new char[]{'a','b','c','d','e','f','g',
		'h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w',
		'x','y','z','0','1','2','3','4','5','6','7','8','9','!','@','$',
		'^','_','*'};
		//Alphabet 0-25, Numbers 26-35, Symbols 36-
		/*String[] array = new String[]{"a","b","c","d","e","f","g","h",
		"i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
		"0","1","2","3","4","5","6","7","8","9","!","@","$","^","_","*"};*/
		
		LinkedList main = new LinkedList('a');
		main = root;
		
		for(k =0; k<5;k++){
			for(j=0;j<array.length;j++){
				if(k==0&&j==0){
					LinkedList fill = new LinkedList(array[j]);
					main = fill;
					Node sib = new Node();
					main.head.sibling=sib;
					main.head = main.head.sibling;
					
				}
				else{
					main.head.setData(array[j]);
					Node sib = new Node();
					main.head.sibling=sib;
					main.head = main.head.sibling;
				}
			}
			Node secondMain = new Node();
			secondMain =root.head;
			for(p=0; p<=k; p++){
				if(secondMain.child==null){
					LinkedList child = new LinkedList();
					secondMain.child = child.head;
					secondMain= secondMain.child;
				}
				else{
					secondMain=secondMain.child;
				}
				main.head = secondMain;
				if(p==4){
					LinkedList term = new LinkedList(TERM);
					secondMain.child = term.head;
				}
			}
		}
	}
}