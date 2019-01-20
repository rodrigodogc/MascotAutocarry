package scrapingforlife;

import com.diogonunes.jcdp.color.api.Ansi;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.apache.commons.codec.binary.Base64;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import static scrapingforlife.TestThread.config;

import scrapingforlife.configuration.DriverFactory;
import static scrapingforlife.DebugControl.debug;
import static scrapingforlife.DebugControl.printSeparator;
import static scrapingforlife.QueryParameters.*;
import static scrapingforlife.WriteResults.*;

public class TestScrap implements Runnable {

        public static String PeakListFileName;
        public static String Significance;
        public static int SignificScore;
        
        
        
	public static SearchModel searchModel;
	public static Path peakListPath;
	private DriverFactory driverFactory;
     
	public static String[][] peptideCombinations = {
			{"Da", "1.2"},
			//{"Da", "1.0"},
			//{"Da", "0.8"},
			//{"Da", "0.6"},
			//{"Da", "0.4"},
			//{"Da", "0.2"},
			{"ppm", "100"},
			//{"ppm", "200"}
	};
        	public static String[][] msmsCombinations = {
			{"Da", "0.6"},
			{"Da", "0.4"},
			{"Da", "0.2"}			
	};
        
                      
	public TestScrap(SearchModel searchModel, Path peakListPath) throws IOException {
		this.driverFactory =  new DriverFactory();
		this.searchModel = searchModel;
		this.peakListPath = peakListPath;
	}
        
        public static String decodificPassword(String str){
            return new String(new Base64().decode(str));
        }

	@Override
	public void run() {
            
            try {
                if("PMF Standard".equals(config("mascot.search.type"))){
                    PMFSearchOpenDB();
                }
                else if("PMF Private DB".equals(config("mascot.search.type"))){
                    PMFSearchPrivateDB();
                }
                else if("MS/MS Standard".equals(config("mascot.search.type"))){
                    MSMSSearchOpenDB();
                }
                else if("MS/MS Private DB".equals(config("mascot.search.type"))){
                    MSMSSearchPrivateDB();
                }
                
                else{
                    debug("Invalid SearchType. Exiting...", Ansi.Attribute.BOLD, Ansi.FColor.RED, Ansi.BColor.BLACK);
                    try { Thread.sleep(2000); } catch (InterruptedException ex) {}
                    System.exit(0);
                }
                
                
            } catch (IOException ex) {
                debug("Not was possible start search", Ansi.Attribute.BOLD, Ansi.FColor.RED, Ansi.BColor.BLACK);
                System.exit(0);      
            }
	
        }
        
        public void PMFSearchOpenDB(){
            final RemoteWebDriver webDriver = driverFactory.getDriver();
                
            for (int i = 0; i < this.peptideCombinations.length; i++) {
                debug("Acessing Mascot PMF page...", Ansi.Attribute.BOLD, Ansi.FColor.CYAN, Ansi.BColor.BLACK);
                webDriver.get("http://www.matrixscience.com/cgi/search_form.pl?FORMVER=2&SEARCH=PMF");

                debug("Completing Search Fields...", Ansi.Attribute.LIGHT, Ansi.FColor.CYAN, Ansi.BColor.BLACK);

                try {

                    debug("Search Parameters:\n"
                            + "................... Peaklist: " + PeakListFileName + "\n"
                            + "................... Taxonomy: " + config("browse.taxonomy").replace(".", "").substring(6) + "\n"
                            + "................... Database: " + config("browse.database") + "\n"
                            + "................... PepTol  : " + this.peptideCombinations[i][1] + " " + this.peptideCombinations[i][0],
                            Ansi.Attribute.BOLD, Ansi.FColor.MAGENTA, Ansi.BColor.BLACK);
                } catch (IOException e) {}
                
                completeQueryParametersPMFStandard(webDriver, i);

                // Waiting some seconds to get result page correctly instead processing page
                try {
                    int waitingTime = Integer.parseInt(config("loading.wait.time"));
                    WebDriverWait wait = new WebDriverWait(webDriver, waitingTime);
                    debug("Performing Search (Max. " + waitingTime + " seconds)...",
                            Ansi.Attribute.BOLD, Ansi.FColor.YELLOW, Ansi.BColor.BLACK);
                    wait.until(ExpectedConditions.urlContains("http://www.matrixscience.com/cgi/master_results.pl"));
                } catch (IOException e) {
                }
                // Getting fields to be save	    
                
                writeResultsPMFStandard(webDriver, i);
                webDriver.manage().deleteAllCookies();
                
            }
            debug("All searchs are done for " + PeakListFileName,
                    Ansi.Attribute.LIGHT, Ansi.FColor.GREEN, Ansi.BColor.BLACK);
            webDriver.quit();
            
            debug("Starting new search...",
                    Ansi.Attribute.LIGHT, Ansi.FColor.MAGENTA, Ansi.BColor.BLACK);
        } //end
        
        public void PMFSearchPrivateDB() throws IOException{
            
            final RemoteWebDriver webDriver = driverFactory.getDriver();

            for (int i = 0; i < this.peptideCombinations.length; i++) {
                debug("Acessing Mascot PMF Private page...", Ansi.Attribute.BOLD, Ansi.FColor.CYAN, Ansi.BColor.BLACK);
                webDriver.get("https://proteomicsresource.washington.edu/mascot/cgi/search_form.pl?FORMVER=2;SEARCH=PMF");
                
                if(webDriver.findElement(By.xpath("//*[@id=\"plainContent\"]/table/tbody/tr/td/h2")).isDisplayed()){
                    debug("Logging in private database...", Ansi.Attribute.LIGHT, Ansi.FColor.YELLOW, Ansi.BColor.BLACK);
                    webDriver.findElement(By.id("username")).sendKeys(config("private.db.username"));
                    webDriver.findElement(By.name("password")).sendKeys(decodificPassword(config("private.db.password")));
                    webDriver.findElement(By.name("submit")).submit();                    
                }
               
                debug("Completing Search Fields...", Ansi.Attribute.LIGHT, Ansi.FColor.CYAN, Ansi.BColor.BLACK);
                try {

                    debug("Search Parameters:\n"
                            + "................... Peaklist: " + PeakListFileName + "\n"
                            + "................... Taxonomy: " + config("browse.taxonomy").replace(".", "")+ "\n"
                            + "................... Database: " + config("browse.database") + "\n"
                            + "................... PepTol  : " + this.peptideCombinations[i][1] + " " + this.peptideCombinations[i][0],
                            Ansi.Attribute.BOLD, Ansi.FColor.MAGENTA, Ansi.BColor.BLACK);
                } catch (IOException e) {}
                //wait until PMF Private Page is loaded
                new WebDriverWait(webDriver, 5).until(ExpectedConditions.urlToBe("https://proteomicsresource.washington.edu/mascot/cgi/search_form.pl?FORMVER=2;SEARCH=PMF"));
                
                completeQueryParametersPMFPrivate(webDriver, i);
                
                try { // Waiting some seconds to get result page correctly instead processing page
                    int waitingTime = Integer.parseInt(config("loading.wait.time"));
                    WebDriverWait wait = new WebDriverWait(webDriver, waitingTime);
                    debug("Performing Search (Max. " + waitingTime + " seconds)...",
                            Ansi.Attribute.BOLD, Ansi.FColor.YELLOW, Ansi.BColor.BLACK);
                    wait.until(ExpectedConditions.urlContains("https://proteomicsresource.washington.edu/mascot/cgi/master_results.pl"));
                } catch (IOException e) {}
                // Getting fields to be save	    
                writeResultsPMFPrivate(webDriver, i);
                webDriver.manage().deleteAllCookies();
            }
            debug("All searchs are done for " + PeakListFileName,
                    Ansi.Attribute.LIGHT, Ansi.FColor.GREEN, Ansi.BColor.BLACK);
            webDriver.quit();
            debug("Starting new search...",
                    Ansi.Attribute.LIGHT, Ansi.FColor.MAGENTA, Ansi.BColor.BLACK);
            
        } //end
        
        public void MSMSSearchOpenDB(){
          
        }
        
        public void MSMSSearchPrivateDB() throws IOException{
            
            final RemoteWebDriver webDriver = driverFactory.getDriver();

            for (int i = 0; i < this.peptideCombinations.length; i++) {
                debug("Acessing Mascot MS/MS Private page...", Ansi.Attribute.BOLD, Ansi.FColor.CYAN, Ansi.BColor.BLACK);
                webDriver.get("https://proteomicsresource.washington.edu/mascot/cgi/search_form.pl?FORMVER=2&SEARCH=MIS");
                
                if(webDriver.findElement(By.xpath("//*[@id=\"plainContent\"]/table/tbody/tr/td/h2")).isDisplayed()){
                    debug("Logging in private database...", Ansi.Attribute.LIGHT, Ansi.FColor.YELLOW, Ansi.BColor.BLACK);
                    webDriver.findElement(By.id("username")).sendKeys(config("private.db.username"));
                    webDriver.findElement(By.name("password")).sendKeys(decodificPassword(config("private.db.password")));
                    webDriver.findElement(By.name("submit")).submit();                    
                }
               
                debug("Completing Search Fields...", Ansi.Attribute.LIGHT, Ansi.FColor.CYAN, Ansi.BColor.BLACK);
                try {

                    debug("Search Parameters:\n"
                            + "................... Peaklist: " + PeakListFileName + "\n"
                            + "................... Taxonomy: " + config("browse.taxonomy").replace(".", "")+ "\n"
                            + "................... Database: " + config("browse.database") + "\n"
                            + "................... PepTol  : " + this.peptideCombinations[i][1] + " " + this.peptideCombinations[i][0],
                            Ansi.Attribute.BOLD, Ansi.FColor.MAGENTA, Ansi.BColor.BLACK);
                } catch (IOException e) {}
                
                //wait until PMF Private Page is loaded
                new WebDriverWait(webDriver, 5).until(ExpectedConditions.urlContains("https://proteomicsresource.washington.edu/mascot/cgi/search_form.pl?FORMVER"));
                
                completeQueryParametersMSMSPrivate(webDriver, i);
                
                try { // Waiting some seconds to get result page correctly instead processing page
                    int waitingTime = Integer.parseInt(config("loading.wait.time"));
                    WebDriverWait wait = new WebDriverWait(webDriver, waitingTime);
                    debug("Performing Search (Max. " + waitingTime + " seconds)...",
                            Ansi.Attribute.BOLD, Ansi.FColor.YELLOW, Ansi.BColor.BLACK);
                    wait.until(ExpectedConditions.urlContains("https://proteomicsresource.washington.edu/mascot/cgi/master_results.pl"));
                } catch (IOException e) {}
                // Getting fields to be save	    
                writeResultsPMFPrivate(webDriver, i);
                webDriver.manage().deleteAllCookies();
            }
            debug("All searchs are done for " + PeakListFileName,
                    Ansi.Attribute.LIGHT, Ansi.FColor.GREEN, Ansi.BColor.BLACK);
            webDriver.quit();
            debug("Starting new search...",
                    Ansi.Attribute.LIGHT, Ansi.FColor.MAGENTA, Ansi.BColor.BLACK);
                         
            
        }


	public static void buildProteinDetails(final RemoteWebDriver driver, final BufferedWriter writer) {
		final List<WebElement> resultProteinIds = driver.findElementsByXPath("/html/body/form[2]/table/tbody/tr[1]/td[2]/tt/a");
		final List<String> proteinDetailsUrls = resultProteinIds.stream()
				.map(element -> {return element.getAttribute("href");}).collect(Collectors.toList());
                    debug("Writing results...",Ansi.Attribute.BOLD, Ansi.FColor.YELLOW, Ansi.BColor.BLACK);
		for(final String proteinDetailUrl : proteinDetailsUrls) {
			driver.navigate().to(proteinDetailUrl);
			final List<WebElement> proteinDetails = driver.findElementsByXPath("/html/body/form/table[1]/tbody/tr");
			try {
				for(WebElement detail : proteinDetails) {
					final String detailTitle = detail.findElement(By.tagName("th")).getText();
					final String detailValue = detail.findElement(By.tagName("td")).getText();
					writer.write(detailTitle + detailValue);
					writer.newLine();
				}
				writer.newLine();
                                
                        } catch (IOException e) {
                                debug("Fail writing results!",Ansi.Attribute.BOLD, Ansi.FColor.RED, Ansi.BColor.BLACK);
			}
		}
          
            debug("Done.", Ansi.Attribute.LIGHT, Ansi.FColor.GREEN, Ansi.BColor.BLACK);
            
            if("Insignificant".equals(Significance)){
                debug("Top score is [" + Significance + "]",
                Ansi.Attribute.BOLD, Ansi.FColor.RED, Ansi.BColor.BLACK);
            }else{
                debug("Top score is [" + Significance + "]",
                Ansi.Attribute.BOLD, Ansi.FColor.GREEN, Ansi.BColor.BLACK);            
            }
            
            printSeparator("-----------------------------------------------------------------");
	}
}
