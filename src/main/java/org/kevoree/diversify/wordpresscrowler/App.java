package org.kevoree.diversify.wordpresscrowler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;

import com.gargoylesoftware.htmlunit.AlertHandler;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.IncorrectnessListener;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.ScriptException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTableBody;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptErrorListener;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
       new App().submittingForm();
    }
    
     WebClient webClient;
    public void submittingForm() throws Exception {
    	java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
     webClient = new WebClient();
         

        // Get the first page
        final HtmlPage page1 = webClient.getPage("http://localhost:8080/wp-login.php");

        // Get the form that we are dealing with and within that form, 
        // find the submit button and the field that we want to change.
        final HtmlForm form = page1.getFormByName("loginform");

        final HtmlSubmitInput button = form.getInputByName("wp-submit");
        final HtmlTextInput login = form.getInputByName("log");
        final HtmlPasswordInput pass = form.getInputByName("pwd");

        // Change the value of the text field
        login.setValueAttribute("admin");
        pass.setValueAttribute("diversify");
        

        // Now submit the form by clicking the button and get back the second page.
        final HtmlPage page2 = button.click();
        final HtmlPage page3 = webClient.getPage("http://localhost:8080/wp-admin/plugins.php");

        //System.err.println(page3.asText());
        HtmlTableBody e = (HtmlTableBody) page3.getElementById("the-list");
        
        
   //     activateAllPlugins(e);
        desactivateAllPlugins(e);

        
        webClient.closeAllWindows();
    }
    
    public void activateAllPlugins(HtmlTableBody e) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
    	//List<HtmlTableRow> res =  (List<HtmlTableRow>) e.getByXPath("//tr[@class='inactive']");
        //for (HtmlTableRow row : res){
        	List<HtmlSpan> spans = (List<HtmlSpan>) e.getByXPath("//span[@class='activate']");
        	for (HtmlSpan span : spans){
        		HtmlAnchor element = (HtmlAnchor) span.getChildren().iterator().next();
        		webClient.getPage("http://localhost:8080/wp-admin/"+element.getHrefAttribute());
        	}
        	
        //}
    }
    public void desactivateAllPlugins(HtmlTableBody e) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
    	List<HtmlSpan> spans =  (List<HtmlSpan>) e.getByXPath("//span[@class='deactivate']");
        	for (HtmlSpan span : spans){
        		HtmlAnchor element = (HtmlAnchor) span.getChildren().iterator().next();
        		System.err.println(element.getHrefAttribute());
        		webClient.getPage("http://localhost:8080/wp-admin/"+element.getHrefAttribute());
        	}
        	
        
    }

}
