/*John Ha
CS 1501
Assignment 1
2/11/2016
*/

public class LinkedList{
	
	//fields for a linked list
	public Node head;
	public LinkedList list = this.list;
	public int size;
	
	//constructor for each linked list
	public LinkedList(char c){
		Node newNode = new Node(c);
		this.head = newNode;
		newNode.list = this.list;
		this.size++;
	}
	public LinkedList(){
		Node newNode = new Node();
		this.head = newNode;
		newNode.list = this.list;
		this.size++;//empty constructor
	}
	
	/*method to insert a node into the linked list
	*@param in is the node inserted into the linked list
	*/
	public void insert(Node in){
		if(this.size == 0){
			this.head = in;
		}
		else{
			in.sibling = this.head;
			this.head = in;
		}
		size++;
	}
	/*method to insert a character by creating a new node that holds the character in the linked list.
	*@param c is the character to be inputted as the data of the new node
	*/
	public void insert(char c){
		Node newNode = new Node(c);
		
		if(this.size==0){
			this.head = newNode;
		}
		else{
			newNode.sibling = this.head;
			this.head = newNode;
		}
		size++;
	}
	public boolean has(char c){
		boolean truth = false;
		Node check = this.head;
		
		while(check!=null){
			if(check.data==c){
				truth = true;
				break;
			}
			else{
				check = check.sibling;
			}
		}
		return truth;
	}

}