package bell.spellingsuggestion.parser;

import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * @author Michael Bell
 * This class can be used to parse a string for key search terms.
 */
public class ParseUniversalDocument {

	private String leftDelimiter;
	private String rightDelimiter;
	private String unformattedDocument;
	
	/**
	 * default constructor
	 */
	public ParseUniversalDocument() {
	}
	
	/**
	 * Uses the DownloadWebpage class to get an unformatted string of a web url
	 */
	private void readWebpage() {
	
		//create webPage object
		DownloadWebpage webPage;
		try {
			webPage = new DownloadWebpage();
		
			//Connect to webpage and save in current object
			unformattedDocument = webPage.getDataDump();
		
		} catch (MalformedURLException e) {
		e.printStackTrace();
		}

	}
	

	/**
	 * method to parse document and add each entry to an arrayList
	 * @param leftD the left side delimiter
	 * @param rightD the right side delimiter
	 * @return the strings that were found by the search algorithm
	 */
	public ArrayList<String> documentParser (String leftD, String rightD) {
		
		//beginning and ending search keys
		leftDelimiter = leftD;
		rightDelimiter = rightD;
		
		int startIndex = 0;
		int endIndex = 0;
		
		//read in the webpage
		this.readWebpage();
		
		//make sure in lowercase to help with case errors
		unformattedDocument = unformattedDocument.toLowerCase();
		
		ArrayList<String> parsedDocument = new ArrayList<String>();
		
		//to ensure the while loop doesn't infinitely loop
		int counter = 0;
		
		while (endIndex < unformattedDocument.length()){
			
			//tally the cycle counter
			counter++;
			
			//Search for the starting and ending point of the substring
			startIndex = unformattedDocument.indexOf(leftDelimiter, (startIndex + 1));
			
			//if delimiter not found
			if (startIndex == -1){
				break;
			}
			
			//this line accounts for the length of the delimiter
			startIndex = startIndex + leftDelimiter.length();
			endIndex = unformattedDocument.indexOf(rightDelimiter, startIndex);
			
			//This sequence to catch end Sof document
			if (endIndex == (-1) | counter > 10000){
				break;
			}
			
			//Get the substring of the document to add to the arrayList
			String match = unformattedDocument.substring(startIndex, endIndex);
			
			//add the match to the ArrayList
			parsedDocument.add(match);
			
			startIndex++;
		
		}
		System.out.println(counter);
		
		return parsedDocument;
	}
}
