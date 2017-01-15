package bell.spellingsuggestion.main;

import java.util.HashMap;
import java.util.Set;

/** 
 * Represents a node in a Trie
 */
class TrieNode {
	
	private HashMap<Character, TrieNode> children; 
	private String text;  //in a large data structure this can be omitted to save space
	private boolean isWord; //sets if this node makes a word
	
	/**
	 * Create a new TrieNode
	 */
	public TrieNode()
	{
		children = new HashMap<Character, TrieNode>();
		text = "";
		isWord = false;
	}
	
	/**
	 * Create a new trieNode given a text string to store in it
	 * @param text the string to be stored
	 */
	public TrieNode(String text)
	{
		this();
		//can be recalled for ease of use in implementing
		this.text = text;
	}
	
	/**
	 * Set the text value at a given node
	 * @param s the string to be stored
	 */
	public void setText (String s){
		this.text = s;
	}
	
	/** Returns the TrieNode that is the child when you follow the 
	 * link from the given Character 
	 * @param c The next character in the key
	 * @return The TrieNode that character links to, or null if that link
	 *   is not in the trie.
	 */
	public TrieNode getChild(Character c)
	{
		return children.get(c);
	}
	
	/** Inserts this character at this node.
	 * Returns the newly created node, if c wasn't already
	 * in the trie.  If it was, it does not modify the trie
	 * and will return null.
	 * @param c The character that will connect to the new node
	 * @return The newly created TrieNode, or returns null if already
	 * there
	 */
	public TrieNode insert(Character c)
	{
		if (children.containsKey(c)) {
			return null;
		}
		
		TrieNode next = new TrieNode();
		
		//Update the TrieNode to contain the child Character
		children.put(c, next);
		
		//return new TrieNode
		return next;
	}
	
	/**
	 * return the text string at this node
	 * @return the text string stored at the node
	 */
    public String getText()
	{
		return text;
	}
	
    /**
     * Set whether or not this node ends a word in the trie. 
     * @param b the setting for isWord
     */
	public void setEndsWord(boolean b)
	{
		isWord = b;
	}
	
	/**
	 *Return whether or not this node ends a word in the trie. 
	 * @return True/False depending on if the node ends a word
	 */

	public boolean endsWord()
	{
		return isWord;
	}
	
	/**
	 * Return the set of characters that have links from this node 
	 * @return the valid next characters connected from the node
	 */
	public Set<Character> getValidNextCharacters()
	{
		return children.keySet();
	}

}

