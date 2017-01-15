package bell.spellingsuggestion.parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.apache.commons.io.IOUtils;

/**
 * @author Michael Bell
 * This class can be used to download a web url into a single string
 */
public class DownloadWebpage {

	private URL url;
	
	private String dataDump = null;
	
	/**
	 * Uses default URL for sports stats, need to create a second constructor to pass in URL
	 * for using in a more universal manner
	 * @throws MalformedURLException
	 */
	public DownloadWebpage() throws MalformedURLException{
		url = new URL("http://widgets.sports-reference.com/wg.fcgi?css=1&site=cbb&url=%2Fcbb%2Fseasons%2F2017-school-stats.html&div=div_basic_school_stats");
	}
	
	/**
	 * Opens URL and returns the page as  a single string
	 * @return String the unformatted webpage represented as a string
	 */
	public String getDataDump(){
		
		URLConnection conn;
		try {
			conn = url.openConnection();
			
		InputStream in = conn.getInputStream();
		String encoding = conn.getContentEncoding();
		
		//write to variable
		dataDump = IOUtils.toString(in, encoding);
		
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		return dataDump;
	}
	
}
