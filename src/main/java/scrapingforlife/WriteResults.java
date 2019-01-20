
package scrapingforlife;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import static scrapingforlife.TestThread.config;
import static scrapingforlife.TestScrap.*;

public class WriteResults {
    
    	public static void writeResultsPMFStandard(final RemoteWebDriver driver, final Integer i) {
		final List<WebElement> resultHeader = driver.findElementsByXPath("/html/body/font[1]/pre//b");
                
		final Object SignificScoreDetail = driver.executeScript("return document.querySelector(\"body > br:nth-child(4)\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).nextSibling.nodeValue");
                final int SignificScore = Integer.parseInt(SignificScoreDetail.toString().substring(0,30).replaceAll("[^0-9]", ""));
                
                final WebElement Score = driver.findElementByXPath("/html/body/font[1]/pre/b[8]");
		final int TopScore = Integer.parseInt(Score.getText().substring(0, 21).replaceAll("[^0-9]", ""));                                           
                
                final String ProteinNameDetail = Score.getText().substring(37);
                final String[] ProteinName = ProteinNameDetail.split("OS");
                
                final WebElement TopScoreID = driver.findElementByXPath("/html/body/font[1]/pre/b[8]/font"); //catch Top score Mascor ID
                String MascotID = TopScoreID.getText();
               
                
                
                    if(TopScore >= SignificScore){
                        Significance = "SIGNIFICANT!";
                    }else{
                        Significance = "Insignificant";
                    }
                                   
                PeakListFileName = peakListPath.getFileName().toString();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(searchModel.getPeakListResultPath() + //file title
				PeakListFileName + 
				"_" + peptideCombinations[i][1] +
				"_" + peptideCombinations[i][0] +
				"_" + "Score" + "_" + Significance + ".txt"))){
			for(final WebElement header : resultHeader) {
				writer.write(header.getText());
				writer.newLine();
			}
                        writer.write((String) "Siginificance minimum Score: " + String.valueOf(SignificScore));
                        writer.newLine();
                        
			writer.newLine();
			buildProteinDetails(driver, writer);                                                            
                        
                    String DatabaseName = config("browse.database");
                    String SpotID       = PeakListFileName;
                    String MinScore     = String.valueOf(SignificScore);
                    String ScorePeakLi  = String.valueOf(TopScore);
                    String Signific     = Significance.replaceAll("[^a-zA-Z]", ""); 
                    String SearchType   = "PMF";                    
                  //MascotID
                  //Protein name
                    String PeptideTol   = peptideCombinations[i][1];
                    String Organism     = config("browse.taxonomy").replaceAll("[^a-zA-Z() ]+", "").replaceAll("[^a-zA-Z()]", " ");
                    String ProteinMass  = "10";
                    String ProteinPi    = "7.1";
                    String HortologusSp = "Granja spp";
                
                    if(Boolean.valueOf(config("save.result.table")) == true){
                        FileWriter csv = new FileWriter(config("results.table.path"),true);
                        BufferedWriter bw = new BufferedWriter(csv);
                        PrintWriter pw = new PrintWriter(bw);
            
                        pw.println(DatabaseName + ";" + SpotID + ";" + MinScore + ";" + ScorePeakLi + ";" +
                                   Signific + ";" + SearchType + ";" + MascotID + ";;" + ProteinName[0] +";"+  
                                   PeptideTol + ";" + Organism + ";" + ProteinMass + ";" + ProteinPi +";" +
                                   HortologusSp);
                        pw.flush();
                    }        
		} 
                catch (IOException e) {
			e.printStackTrace();
		}
		
	}
    	public static void writeResultsPMFPrivate(final RemoteWebDriver driver, final Integer i) {
		final List<WebElement> resultHeader = driver.findElementsByXPath("/html/body/font[1]/pre");
                
		final Object SignificScoreDetail = driver.executeScript("return document.querySelector(\"body > br:nth-child(4)\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).nextSibling.nodeValue");
                final int SignificScore = Integer.parseInt(SignificScoreDetail.toString().substring(0,30).replaceAll("[^0-9]", ""));
                
                final WebElement Score = driver.findElementByXPath("/html/body/font[1]/pre/b[7]");
		final int TopScore = Integer.parseInt(Score.getText().substring(0, 21).replaceAll("[^0-9]", ""));                                           
                
                final String ProteinNameDetail = Score.getText().substring(25);
                final String[] ProteinName = ProteinNameDetail.split(",");
                     
                String MascotID = "N/A";
               
                
                
                    if(TopScore >= SignificScore){
                        Significance = "SIGNIFICANT!";
                    }else{
                        Significance = "Insignificant";
                    }
                                   
                PeakListFileName = peakListPath.getFileName().toString();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(searchModel.getPeakListResultPath() + //file title
				PeakListFileName + 
				"_" + peptideCombinations[i][1] +
				"_" + peptideCombinations[i][0] +
				"_" + "Score" + "_" + Significance + ".txt"))){
			for(final WebElement header : resultHeader) {
				writer.write(header.getText());
				writer.newLine();
			}
                        writer.write((String) "Siginificance minimum Score: " + String.valueOf(SignificScore));
                        writer.newLine();
                        
			writer.newLine();
			buildProteinDetails(driver, writer);                                                            
                        
                    String DatabaseName = config("browse.database");
                    String SpotID       = PeakListFileName;
                    String MinScore     = String.valueOf(SignificScore);
                    String ScorePeakLi  = String.valueOf(TopScore);
                    String Signific     = Significance.replaceAll("[^a-zA-Z]", ""); 
                    String SearchType   = "PMF";                    
                  //MascotID
                  //Protein name
                    String PeptideTol   = peptideCombinations[i][1];
                    String Organism     = config("browse.taxonomy").replaceAll("[^a-zA-Z() ]+", "").replaceAll("[^a-zA-Z()]", " ");
                    String ProteinMass  = "10";
                    String ProteinPi    = "7.1";
                    String HortologusSp = "Granja spp";
                
                    if(Boolean.valueOf(config("save.result.table")) == true){
                        FileWriter csv = new FileWriter(config("results.table.path"),true);
                        BufferedWriter bw = new BufferedWriter(csv);
                        PrintWriter pw = new PrintWriter(bw);
            
                        pw.println(DatabaseName + ";" + SpotID + ";" + MinScore + ";" + ScorePeakLi + ";" +
                                   Signific + ";" + SearchType + ";" + MascotID + ";;" + ProteinName[0] +";"+  
                                   PeptideTol + ";" + Organism + ";" + ProteinMass + ";" + ProteinPi +";" +
                                   HortologusSp);
                        pw.flush();
                    }        
		} 
                catch (IOException e) {
			e.printStackTrace();
		}
		
	}
    	public static void writeResultsMSMSStandard(final RemoteWebDriver driver, final Integer i) {
		final List<WebElement> resultHeader = driver.findElementsByXPath("/html/body/font[1]/pre//b");
                
		final Object SignificScoreDetail = driver.executeScript("return document.querySelector(\"body > br:nth-child(4)\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).nextSibling.nodeValue");
                final int SignificScore = Integer.parseInt(SignificScoreDetail.toString().substring(0,30).replaceAll("[^0-9]", ""));
                
                final WebElement Score = driver.findElementByXPath("/html/body/font[1]/pre/b[8]");
		final int TopScore = Integer.parseInt(Score.getText().substring(0, 21).replaceAll("[^0-9]", ""));                                           
                
                final String ProteinNameDetail = Score.getText().substring(37);
                final String[] ProteinName = ProteinNameDetail.split("OS");
                
                final WebElement TopScoreID = driver.findElementByXPath("/html/body/font[1]/pre/b[8]/font"); //catch Top score Mascor ID
                String MascotID = TopScoreID.getText();
               
                
                
                    if(TopScore >= SignificScore){
                        Significance = "SIGNIFICANT!";
                    }else{
                        Significance = "Insignificant";
                    }
                                   
                PeakListFileName = peakListPath.getFileName().toString();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(searchModel.getPeakListResultPath() + //file title
				PeakListFileName + 
				"_" + peptideCombinations[i][1] +
				"_" + peptideCombinations[i][0] +
				"_" + "Score" + "_" + Significance + ".txt"))){
			for(final WebElement header : resultHeader) {
				writer.write(header.getText());
				writer.newLine();
			}
                        writer.write((String) "Siginificance minimum Score: " + String.valueOf(SignificScore));
                        writer.newLine();
                        
			writer.newLine();
			buildProteinDetails(driver, writer);                                                            
                        
                    String DatabaseName = config("browse.database");
                    String SpotID       = PeakListFileName;
                    String MinScore     = String.valueOf(SignificScore);
                    String ScorePeakLi  = String.valueOf(TopScore);
                    String Signific     = Significance.replaceAll("[^a-zA-Z]", ""); 
                    String SearchType   = "PMF";                    
                  //MascotID
                  //Protein name
                    String PeptideTol   = peptideCombinations[i][1];
                    String Organism     = config("browse.taxonomy").replaceAll("[^a-zA-Z() ]+", "").replaceAll("[^a-zA-Z()]", " ");
                    String ProteinMass  = "10";
                    String ProteinPi    = "7.1";
                    String HortologusSp = "Granja spp";
                
                    if(Boolean.valueOf(config("save.result.table")) == true){
                        FileWriter csv = new FileWriter(config("results.table.path"),true);
                        BufferedWriter bw = new BufferedWriter(csv);
                        PrintWriter pw = new PrintWriter(bw);
            
                        pw.println(DatabaseName + ";" + SpotID + ";" + MinScore + ";" + ScorePeakLi + ";" +
                                   Signific + ";" + SearchType + ";" + MascotID + ";;" + ProteinName[0] +";"+  
                                   PeptideTol + ";" + Organism + ";" + ProteinMass + ";" + ProteinPi +";" +
                                   HortologusSp);
                        pw.flush();
                    }        
		} 
                catch (IOException e) {
			e.printStackTrace();
		}
		
	}
    	public static void writeResultsMSMSPrivate(final RemoteWebDriver driver, final Integer i) {
		final List<WebElement> resultHeader = driver.findElementsByXPath("/html/body/font[1]/pre");
                /**
		final Object SignificScoreDetail = driver.executeScript("return document.querySelector(\"body > br:nth-child(4)\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).nextSibling.nodeValue");
                final int SignificScore = Integer.parseInt(SignificScoreDetail.toString().substring(0,30).replaceAll("[^0-9]", ""));
                
                final WebElement Score = driver.findElementByXPath("/html/body/font[1]/pre/b[7]");
		final int TopScore = Integer.parseInt(Score.getText().substring(0, 21).replaceAll("[^0-9]", ""));                                           
                
                final String ProteinNameDetail = Score.getText().substring(25);
                final String[] ProteinName = ProteinNameDetail.split(",");
                **/
                //final WebElement TopScoreID = driver.findElementByXPath("/html/body/font[1]/pre/b[8]/font"); //catch Top score Mascor ID
                //String MascotID = TopScoreID.getText();
               
                
                    
                    //if(TopScore >= SignificScore){
                    //   Significance = "SIGNIFICANT!";
                    //}else{
                    //    Significance = "Insignificant";
                    //}
                                   
                //PeakListFileName = peakListPath.getFileName().toString();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(searchModel.getPeakListResultPath() + //file title
				PeakListFileName + 
				"_" + peptideCombinations[i][1] +
				"_" + peptideCombinations[i][0] +
                                ".txt"))){
			for(final WebElement header : resultHeader) {
				writer.write(header.getText());
				writer.newLine();
			}
                        //writer.write((String) "Siginificance minimum Score: " + String.valueOf(SignificScore));
                        //writer.newLine();
                        
			writer.newLine();
			//buildProteinDetails(driver, writer);                                                            
                    /**     
                    String DatabaseName = config("browse.database");
                    String SpotID       = PeakListFileName;
                    String MinScore     = String.valueOf(SignificScore);
                  //String ScorePeakLi  = String.valueOf(TopScore);
                    String Signific     = Significance.replaceAll("[^a-zA-Z]", ""); 
                    String SearchType   = "PMF";                    
                  //MascotID
                  //Protein name
                    String PeptideTol   = peptideCombinations[i][1];
                    String Organism     = config("browse.taxonomy").replaceAll("[^a-zA-Z() ]+", "").replaceAll("[^a-zA-Z()]", " ");
                    String ProteinMass  = "10";
                    String ProteinPi    = "7.1";
                    String HortologusSp = "Granja spp";
                    
                    if(Boolean.valueOf(config("save.result.table")) == true){
                        FileWriter csv = new FileWriter(config("results.table.path"),true);
                        BufferedWriter bw = new BufferedWriter(csv);
                        PrintWriter pw = new PrintWriter(bw);
            
                        pw.println(DatabaseName + ";" + SpotID + ";" + MinScore + ";" + ScorePeakLi + ";" +
                                   Signific + ";" + SearchType + ";" + MascotID + ";;" + ProteinName[0] +";"+  
                                   PeptideTol + ";" + Organism + ";" + ProteinMass + ";" + ProteinPi +";" +
                                   HortologusSp);
                        pw.flush();
                    }
                    **/
		} 
                catch (IOException e) {
			e.printStackTrace();
		}
		
	}
    
    
}
