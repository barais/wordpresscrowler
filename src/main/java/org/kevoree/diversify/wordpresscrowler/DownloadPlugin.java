package org.kevoree.diversify.wordpresscrowler;

import java.util.logging.Level;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class DownloadPlugin {

	
	public static void main( String[] args ) throws Exception
    {
       new DownloadPlugin().getAllPlugins("test");
    }
    
     WebClient webClient;
    public void getAllPlugins(String query) throws Exception {
    	java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
    	webClient = new WebClient();
         

        // Get the first page
        final HtmlPage page1 = webClient.getPage("http://wordpress.org/plugins/");
        final HtmlForm form = (HtmlForm) page1.getElementById("plugins-search");
        final HtmlSubmitInput button = form.getInputByValue("Search Plugins");
        final HtmlTextInput q = form.getInputByName("q");
        q.setValueAttribute(query);
        
        

        // Now submit the form by clicking the button and get back the second page.
        final HtmlPage page2 = button.click();

        
        page2.getByXPath("//div[@class='plugin-block']");
        
        
   

    }
	
}
