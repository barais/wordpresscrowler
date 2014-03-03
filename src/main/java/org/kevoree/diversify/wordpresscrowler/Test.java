package org.kevoree.diversify.wordpresscrowler;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

import de.ailis.pherialize.MixedArray;

public class Test {

	public static void main(String[] args) throws FailingHttpStatusCodeException, IOException {
		MixedArray list;

		//list = Pherialize.unserialize("O:8:\"stdClass\":1:{s:6:\"search\";s:4:\"test\";}").toArray();

		WebClient webClient;
	    	java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
	     webClient = new WebClient();
	    
		
		WebRequest requestSettings = new WebRequest(
				  new URL("http://api.wordpress.org/plugins/info/1.0/"), HttpMethod.POST);

		
			String request = "O:8:\"stdClass\":3:{s:6:\"search\";s:4:\"test\";s:6:\"fields\";a:11:{s:11:\"description\";b:0;s:10:\"downloaded\";b:0;s:6:\"author\";b:0;s:14:\"author_profile\";b:0;s:12:\"contributors\";b:0;s:8:\"requires\";b:0;s:6:\"tested\";b:0;s:13:\"compatibility\";b:0;s:8:\"homepage\";b:0;s:12:\"last_updated\";b:0;s:5:\"added\";b:0;}s:8:\"per_page\";i:100;}";
				// Then we set the request parameters
				requestSettings.setRequestParameters(new ArrayList());
				requestSettings.getRequestParameters().add(new NameValuePair("action", "query_plugins"));
				requestSettings.getRequestParameters().add(new NameValuePair("request", request));

				
				// Finally, we can get the page
				HtmlPage page = webClient.getPage(requestSettings);
		
				String s1 = page.getWebResponse().getContentAsString();
				
				
				//list of slugs
				List<String> slugs = new ArrayList<String>();
				
				for (String s :s1.split("slug\";s:")){
					slugs.add(s.substring(s.indexOf(":")+2 , s.indexOf(";")-1) );
				}

				

				WebRequest requestSettings1 = new WebRequest(
						  new URL("http://api.wordpress.org/plugins/info/1.0/"), HttpMethod.POST);
				String request1  = "O:8:\"stdClass\":2:{s:4:\"slug\";s:16:\"nelio-ab-testing\";s:6:\"fields\";s:30:\"a:1:{s:12:\"downloadlink\";b:1;}\";}";
						// Then we set the request parameters
				requestSettings1.setRequestParameters(new ArrayList());
				requestSettings1.getRequestParameters().add(new NameValuePair("action", "plugin_information"));
				requestSettings1.getRequestParameters().add(new NameValuePair("request", request1));
				// Finally, we can get the page
				HtmlPage page1 = webClient.getPage(requestSettings1);
				String s2 = page1.getWebResponse().getContentAsString();
				
				for (String s :s2.split("download_link\";s:")){
					System.err.println(s.substring(s.indexOf(":")+2 , s.indexOf(";")-1));
				}

				
	}

}
