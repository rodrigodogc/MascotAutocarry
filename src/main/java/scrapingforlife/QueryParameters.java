
package scrapingforlife;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import static scrapingforlife.TestScrap.*;


public class QueryParameters {
    
    public static void completeQueryParametersPMFStandard(final RemoteWebDriver driver, int i) {
        final Select dataBaseSelect = new Select(driver.findElement(By.name("DB")));
        dataBaseSelect.selectByValue(searchModel.getDatabase());
        dataBaseSelect.deselectByValue("contaminants");

        if (driver.findElement(By.id("USERNAME")).getText().isEmpty()) {
            driver.findElement(By.id("USERNAME")).sendKeys(searchModel.getName());
        }

        if (driver.findElement(By.id("USEREMAIL")).getText().isEmpty()) {
            driver.findElement(By.id("USEREMAIL")).sendKeys(searchModel.getEmail());
        }

        final Select taxonomySelect = new Select(driver.findElement(By.name("TAXONOMY")));
        taxonomySelect.selectByValue(TestScrap.searchModel.getTaxonomy());

        final Select ModsSelect = new Select(driver.findElementByName("MASTER_MODS"));

        ModsSelect.selectByValue("Carbamidomethyl (C)");
        driver.findElement(By.name("add_MODS")).click();

        ModsSelect.selectByValue("Oxidation (M)");
        driver.findElement(By.name("add_IT_MODS")).click();

        final Select unitySelect = new Select(driver.findElement(By.name("TOLU")));
        unitySelect.selectByValue(peptideCombinations[i][0]);

        driver.findElement(By.name("TOL")).sendKeys(Keys.CONTROL + "a");
        driver.findElement(By.name("TOL")).sendKeys(Keys.DELETE);
        driver.findElement(By.name("TOL")).sendKeys(peptideCombinations[i][1]);

        driver.findElement(By.id("InputRadio-DATAFILE")).click();
        driver.findElement(By.id("InputSource-DATAFILE")).sendKeys(TestScrap.peakListPath.toAbsolutePath().toString());

        driver.findElement(By.id("Start_Search_Button")).submit();
        
	}

    public static void completeQueryParametersPMFPrivate(final RemoteWebDriver driver, int i) {
        final Select dataBaseSelect = new Select(driver.findElement(By.name("DB")));
        dataBaseSelect.selectByValue(searchModel.getDatabase());
        dataBaseSelect.deselectByValue("Gossypium_hirsutum");

        final Select taxonomySelect = new Select(driver.findElement(By.name("TAXONOMY")));
        taxonomySelect.selectByValue(TestScrap.searchModel.getTaxonomy());

        final Select ModsSelect = new Select(driver.findElementByName("MASTER_MODS"));

        ModsSelect.selectByValue("Carbamidomethyl (C)");
        driver.findElement(By.name("add_MODS")).click();

        ModsSelect.selectByValue("Oxidation (M)");
        driver.findElement(By.name("add_IT_MODS")).click();

        final Select unitySelect = new Select(driver.findElement(By.name("TOLU")));
        unitySelect.selectByValue(peptideCombinations[i][0]);

        driver.findElement(By.name("TOL")).sendKeys(Keys.CONTROL + "a");
        driver.findElement(By.name("TOL")).sendKeys(Keys.DELETE);
        driver.findElement(By.name("TOL")).sendKeys(peptideCombinations[i][1]);

        driver.findElement(By.id("InputRadio-DATAFILE")).click();
        driver.findElement(By.id("InputSource-DATAFILE")).sendKeys(TestScrap.peakListPath.toAbsolutePath().toString());

        driver.findElement(By.xpath("//*[@id=\"plainContent\"]/form/table/tbody/tr[16]/td[2]/b/input")).submit();
        
	}
    public static void completeQueryParametersMSMSStandard(final RemoteWebDriver driver, int i) {
        final Select dataBaseSelect = new Select(driver.findElement(By.name("DB")));
        dataBaseSelect.selectByValue(searchModel.getDatabase());
        dataBaseSelect.deselectByValue("contaminants");

        if (driver.findElement(By.id("USERNAME")).getText().isEmpty()) {
            driver.findElement(By.id("USERNAME")).sendKeys(searchModel.getName());
        }

        if (driver.findElement(By.id("USEREMAIL")).getText().isEmpty()) {
            driver.findElement(By.id("USEREMAIL")).sendKeys(searchModel.getEmail());
        }

        final Select taxonomySelect = new Select(driver.findElement(By.name("TAXONOMY")));
        taxonomySelect.selectByValue(TestScrap.searchModel.getTaxonomy());

        final Select ModsSelect = new Select(driver.findElementByName("MASTER_MODS"));

        ModsSelect.selectByValue("Carbamidomethyl (C)");
        driver.findElement(By.name("add_MODS")).click();

        ModsSelect.selectByValue("Oxidation (M)");
        driver.findElement(By.name("add_IT_MODS")).click();

        final Select unitySelect = new Select(driver.findElement(By.name("TOLU")));
        unitySelect.selectByValue(peptideCombinations[i][0]);

        driver.findElement(By.name("TOL")).sendKeys(Keys.CONTROL + "a");
        driver.findElement(By.name("TOL")).sendKeys(Keys.DELETE);
        driver.findElement(By.name("TOL")).sendKeys(peptideCombinations[i][1]);

        driver.findElement(By.id("InputRadio-DATAFILE")).click();
        driver.findElement(By.id("InputSource-DATAFILE")).sendKeys(TestScrap.peakListPath.toAbsolutePath().toString());

        driver.findElement(By.id("Start_Search_Button")).submit();
        
	}
    public static void completeQueryParametersMSMSPrivate(final RemoteWebDriver driver, int i) {
        
        final Select dataBaseSelect = new Select(driver.findElement(By.name("DB")));
        dataBaseSelect.selectByVisibleText("Gossypium_hirsutum (AA)");  
        driver.findElement(By.name("remove_DBs")).click();

        final Select taxonomyDBSelect = new Select(driver.findElement(By.name("MASTER_DB")));
        taxonomyDBSelect.selectByValue(TestScrap.searchModel.getDatabase());
        driver.findElement(By.name("add_DBs")).click();

        final Select ModsSelect = new Select(driver.findElementByName("MASTER_MODS"));
        ModsSelect.selectByValue("Carbamidomethyl (C)");
        driver.findElement(By.name("add_MODS")).click();
        ModsSelect.selectByValue("Oxidation (M)");
        driver.findElement(By.name("add_IT_MODS")).click();

        final Select unitySelect = new Select(driver.findElement(By.name("TOLU")));
        unitySelect.selectByValue(peptideCombinations[i][0]);

        driver.findElement(By.name("TOL")).sendKeys(Keys.CONTROL + "a");
        driver.findElement(By.name("TOL")).sendKeys(Keys.DELETE);
        driver.findElement(By.name("TOL")).sendKeys(peptideCombinations[i][1]);
        
        driver.findElement(By.name("ITOL")).sendKeys(Keys.CONTROL + "a");
        driver.findElement(By.name("ITOL")).sendKeys(Keys.DELETE);
        driver.findElement(By.name("ITOL")).sendKeys(msmsCombinations[i][1]);
        
        final Select peptideChargeSelect = new Select(driver.findElement(By.name("CHARGE")));
        peptideChargeSelect.selectByVisibleText("1+");         
       
        driver.findElement(By.id("InputSource-DATAFILE")).sendKeys(TestScrap.peakListPath.toAbsolutePath().toString());
        
        final Select dataFormatSelect = new Select(driver.findElement(By.name("FORMAT")));
        dataFormatSelect.selectByVisibleText("Bruker (.XML)");
        
        final Select instrumentSelect = new Select(driver.findElement(By.name("INSTRUMENT")));
        instrumentSelect.selectByVisibleText("MALDI-TOF-TOF");
        
        String[] precursorMass = peakListPath.getFileName().toString().replaceAll(".xml", "").split("msms");
        
        driver.findElement(By.name("PRECURSOR")).sendKeys(precursorMass[1]);

        driver.findElement(By.xpath("//*[@id=\"plainContent\"]/form/table/tbody/tr[18]/td[2]/b/input")).submit();
        
	}    
    
}
