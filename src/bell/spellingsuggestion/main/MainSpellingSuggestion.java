package bell.spellingsuggestion.main;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import bell.spellingsuggestion.parser.ParseUniversalDocument;

/**
 * @author Michael Bell
 *Class designed to parse valid key words out of a source, add 
 * them to a Trie data structure, and then return a list of valid
 * search suggestions for a given prefix.
 * 
 * This class was designed to operate through the main method,
 * and to be easily implementable for a variety of situations.
 */
public class MainSpellingSuggestion {

	private static TrieNodeImpl trieNode = null;
	private static List<String> suggestionResults = null;
	
	/**
	 * @param prefix the prefix that will have suggestions generated for it
	 */
	public MainSpellingSuggestion(String prefix){
		
		suggestionResults = new ArrayList<String>();
		MainSpellingSuggestion.main(prefix);
	}
	
	/**
	 * @return the list of suggestion results
	 */
	public List<String> getSuggestionResults(){
		return suggestionResults;
	}
	
	/**
	 * "Controller" to run through the creation of the trie 
	 * @param prefix the string prefix to generate valid suggestions for
	 */
	public static void main(String prefix) {
		
		//call ParseUniversalDocument; in future should pass in URL (xml config?)
		ParseUniversalDocument parser = new ParseUniversalDocument();
		
		//This is set up to parse the sports team database
		ArrayList<String> searchMatches = parser.documentParser("2017.html\">", "</a>");
		
		//save comma delimited list somewhere for quicker access later
		writeToFile(searchMatches);
		
		//create TrieNode list
		trieNode = new TrieNodeImpl(searchMatches);
		//trieNode.printWords();
		
		//Query for user string
		//String startOfSuggestion = JOptionPane.showInputDialog("Please input start of name");
		String startOfSuggestion = prefix;
		
		//Search for valid entries and print 6 closest suggestions
		suggestionResults = trieNode.predictCompletions(startOfSuggestion, 6);
		
		System.out.println("End of main method");

	}
	
	/**
	 * Given a list of Strings, this will call writeToCSV and then
	 * write the CSV string to a local file
	 * @param terms the strings to write to a file
	 */
	private static void writeToFile(ArrayList<String> terms){
		
		String csvList = writeToCsv(terms);
		
		try {
			PrintWriter writer = new PrintWriter("SearchMatches.txt");
			
			//write the csv list to a text document
			writer.print(csvList);
			writer.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Text file not found");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Given a list of strings, this will turn them into a single CSV String.
	 * @param terms the list of strings to add to the csv string
	 * @return String the single string containing all the terms
	 */
	private static String writeToCsv(ArrayList<String> terms){
		
		StringBuffer buf = new StringBuffer();
		
		for (String str : terms){
			buf.append(str);
			buf.append(",");
		}

		return buf.toString();
	}
}
