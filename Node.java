/*John Ha
CS 1501
Assignment 1
2/11/2016
*/

public class Node{
	//fields required for the Node class
	public Node sibling = null;
	public Node child = null;
	public char data;
	public LinkedList list;
	public boolean found;
	
	//constructor for final node of a word
	public Node(char data){
		this.data = data;
		found = false;
	}
	//constructor for regular node
	public Node(Node info){
		this.sibling = info.sibling;
		this.child = info.child;
		this.data = info.data;
		this.list = info.list;
		found = false;
	}
	public Node(){
		
	}
	
	//getters and setters for the nodes
	
	/*
	*@return gets value of the node
	*/
	public char getData(){
		return this.data;
	}
	/*
	*@return gets the current linked list
	*/
	public LinkedList getList(){
		return this.list;
	}
	/*
	*@param the new value for the node
	*/
	public void setData(char c){
		this.data = c;
	}
}