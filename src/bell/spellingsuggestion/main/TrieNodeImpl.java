package bell.spellingsuggestion.main;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import bell.spellingsuggestion.main.TrieNode;

/**
 * @author Michael Bell
 * This class will set up a trie structured list
 * given an ArrayList of strings as an input
 */
public class TrieNodeImpl {

	/**
	 * ArrayList<String> to hold strings to be passed into the trie
	 */
	private ArrayList<String> strings = null;
	
	/**
	 * The base node of the trie structure
	 */
	private TrieNode root = null;
	

	/** 
	 * Pass in ArrayList of strings to add to the trie
	 * @param list the list of strings to be added
	 */
	public TrieNodeImpl(ArrayList<String> list){
		root = new TrieNode();
		strings = list;
		
		System.out.println("about to call createTrie");
		//call method to create trie structure
		this.createTrie();
	}
	
	/**
	 * Method to loop over addWord. Uses class var strings
	 */
	private void createTrie (){
		
		//loop over addWord method
		for (int k = 0; k < strings.size(); k++){
			this.addWord(strings.get(k));
		}
	}
	
	/**
	 * add a word into the Trie structure.
	 * The calling method needs to loop this method to create Trie
	 * @param word the String to be added to the Trie
	 * @return returns if the word was succesfully added or not
	 */
	public boolean addWord(String word)
	{

		//error catching
		if (word.length() <= 0){
			throw new NullPointerException("Can't add an empty word to trie");
		}
		
		//make sure our word is lower case
		String lowerWord = word.toLowerCase();
		
		//create the new node
		TrieNode childNode = new TrieNode();
		
		//initialize the first letter of the string
		root.insert(lowerWord.charAt(0));
		
		//returns trieNode if found
		childNode = root.getChild(lowerWord.charAt(0)); 
		
		//create the node structure
	    for (int k = 1; k < lowerWord.length(); k++){
	    	
	    	//look to see if childnode for give char is already created
	    	//if not, then create node
	    	if (childNode.getChild(lowerWord.charAt(k)) == null){
	    		
	    		//create a new child node with given char
	    		childNode.insert(lowerWord.charAt(k));
	    	}
	    	
	    	//sets childNode to next char, should not be null
	    	childNode = childNode.getChild(lowerWord.charAt(k)); 
	    	
	    	//this sequence will trigger if at the end of the word
	    	if (k == (lowerWord.length()-1)){
	    		
	    		//need to make sure childNode isn't already a word
	    		if (!childNode.endsWord()){ 
	    			
	    			//Set the node to complete a word
	    			childNode.setEndsWord(true);
	    			
	    			//set the word text
	    			childNode.setText(lowerWord);
	    			return true;
	    		}
	    	}
	    }
		return false;
	}	
	
	
	/**
	 * Returns if the string is a valid word
	 * @param s the string to search for
	 * @return True if string is valid
	 * @throws NullPointerException
	 */
	public boolean isWord(String s) throws NullPointerException {
		
		
		if (s.length() <= 0){
			return false;
		}
		
		String sLowerCase = s.toLowerCase();
		
		TrieNode childNode = new TrieNode();
		
		childNode = root.getChild(sLowerCase.charAt(0));
		
		if (childNode == null){
			return false;
		}
		
		//search down the trie to see if .getText returns the String
		for (int k = 0; k < (sLowerCase.length()); k++){
			if (childNode.getText().equals(sLowerCase)) {
				return true;
			}
			
			//This method will return null if text is null set at end node
			if (childNode.getChild(sLowerCase.charAt(k+1)) == null){
				return false;
			}
			
			//for starting the next iteration
			childNode = childNode.getChild(sLowerCase.charAt(k+1)); 
		}
		
		if (childNode.getText() == sLowerCase){
			System.out.println("true");
			return true;
		}
		return false;
	}
	
 	/**
 	 * Do a pre-order traversal from this node down
 	 * @param currentNode the node to start at
 	 */
 	private void printNode(TrieNode currentNode)
 	{
 		if (currentNode == null) 
 			return;
 		
 		System.out.println(currentNode.getText());
 		
 		TrieNode next = null;
 		for (Character c : currentNode.getValidNextCharacters()) {
 			next = currentNode.getChild(c);
 			printNode(next);
 		}
 	}
 	
 	/**
 	 * prints the nodes that have isWord flagged as true
 	 * @param currentNode the node to search from
 	 */
 	private void printValidWords(TrieNode currentNode){
 		
 		if (currentNode == null){
 			return;
 		}
 		
 		if (currentNode.endsWord()){
 			System.out.println(currentNode.getText());
 		}
 		
		TrieNode next = null;
 		for (Character c : currentNode.getValidNextCharacters()) {
 			next = currentNode.getChild(c);
 			printValidWords(next);
 		}
 	}
    
 	/**
 	 * Method to return the list of form suggestions. If there are
 	 * more than the numCompletions found the shortest suggestions are 
 	 * prioritized
 	 * @param prefix the first letters of the word or phrase
 	 * @param numCompletions the number of suggestions to attempt to find
 	 * @return The list of valid suggestions
 	 */
    public List<String> predictCompletions(String prefix, int numCompletions) 
    {
    	
   	 //Find the stem in the trie.  If the stem does not appear in the trie, return an
   	 //empty list
   	 List<String> completions = new ArrayList<String>();
   	 
 		if (root == null){ 
			return completions;
 		}
		
		TrieNode next = root;
		
		for (int k = 0; k < prefix.length(); k++) {
			
			if (next.getChild(prefix.charAt(k)) == null){
				return completions;
			}
			
			next = next.getChild(prefix.charAt(k));
		}
		
   	 //Create a LinkedList queue and add the node that completes the stem to the back
   	 //of the list.
		LinkedList<TrieNode> queue = new LinkedList<TrieNode>();

		queue.add(next);

   	//While the queue is not empty and we have not met completions requirement
		while (!queue.isEmpty()){
			
			if (completions.size() >= numCompletions){
				break;
			}
			
			//remove the first Node from the queue
			TrieNode current = queue.remove();
			
			//If it is a word, add it to the completions list
			if (current.endsWord()){
				completions.add(current.getText());
			}
			
			//Add all of its child nodes to the back of the queue
			Set<Character> childrenChars = current.getValidNextCharacters();
			
			//step through each valid child and add to queue
			for (Character c : childrenChars){
				queue.add(current.getChild(c));
			}
			
		}
		
   	 // Return the list of completions
       return completions;
    }
 	
    /**
     * Prints all the valid words in the trie
     */
 	public void printWords(){
 		printValidWords(root);
 	}
 	
 	/**
 	 * Print the entire trie structure
 	 */
 	public void printTree()
 	{
 		printNode(root);
 	}
	
}
