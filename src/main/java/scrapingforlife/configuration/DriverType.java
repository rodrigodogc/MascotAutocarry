package scrapingforlife.configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import scrapingforlife.TestThread;
  

public enum DriverType implements DriverSetup {
	

	FIREFOX {
        public RemoteWebDriver getWebDriverObject(DesiredCapabilities capabilities) {
        	if(System.getProperty("webdriver.firefox.driver") == null) {
                    try{
    			System.setProperty("webdriver.firefox.driver", ".\\webdrivers\\geckodriver.exe");
                    }
                        catch(Exception e){
                            JOptionPane.showMessageDialog(null, "firefoxdriver.exe not found!");
                        }
                }
            final FirefoxOptions options = new FirefoxOptions();
            options.merge(capabilities);
            options.setHeadless(HEADLESS);
            
                try{
                    if(Boolean.valueOf(TestThread.config("invisible.mode")) == true){
                        options.addArguments("--headless");
                    }
                }catch(IOException e){
                    System.out.println(e.getMessage());         
                }            

            return new FirefoxDriver(options);
        }
    },
    CHROME {
        public RemoteWebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            
        	if(System.getProperty("webdriver.chrome.driver") == null) {
                    try{
                        System.setProperty("webdriver.chrome.driver", ".\\webdrivers\\chromedriver.exe");
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(null, "chromedriver.exe not found!","Chromedriver not Found",JOptionPane.ERROR_MESSAGE);
                    }
                }            
           
            final HashMap<String, Object> chromePreferences = new HashMap<>();
            chromePreferences.put("profile.password_manager_enabled", false);
            
            final ChromeOptions options = new ChromeOptions();
            options.merge(capabilities);
            options.setHeadless(HEADLESS);
            options.addArguments("--no-default-browser-check");
            options.setExperimentalOption("prefs", chromePreferences);
            
                try{
                    if(Boolean.valueOf(TestThread.config("invisible.mode")) == true){
                        options.addArguments("--headless");
                    }
                }catch(IOException e){
                    System.out.println(e.getMessage());         
                }

            return new ChromeDriver(options);
        }
    },
    IE {
        public RemoteWebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            final InternetExplorerOptions options = new InternetExplorerOptions();
            options.merge(capabilities);
            options.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
            options.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
            options.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);

            return new InternetExplorerDriver(options);
        }

        @Override
        public String toString() {
            return "internet explorer";
        }
    },
    EDGE {
        public RemoteWebDriver getWebDriverObject(DesiredCapabilities capabilities) {
        	if(System.getProperty("webdriver.edge.driver") == null) {
    			System.setProperty("webdriver.edge.driver", "F:\\edgedriver_win64\\edgedriver.exe");
    		}
            final EdgeOptions options = new EdgeOptions();
            options.merge(capabilities);

            return new EdgeDriver(options);
        }
    },
    SAFARI {
        public RemoteWebDriver getWebDriverObject(DesiredCapabilities capabilities) {
        	if(System.getProperty("webdriver.safari.driver") == null) {
    			System.setProperty("webdriver.safari.driver", "F:\\safaridriver_win64\\safaridriver.exe");
    		}
            final SafariOptions options = new SafariOptions();
            options.merge(capabilities);

            return new SafariDriver(options);
        }
    },
    OPERA {
        public RemoteWebDriver getWebDriverObject(DesiredCapabilities capabilities) {
        	if(System.getProperty("webdriver.opera.driver") == null) {
                    try{
                        System.setProperty("webdriver.opera.driver", ".\\webdrivers\\operadriver.exe");
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(null, "operadriver.exe not found!");
                    }
                }
            final OperaOptions options = new OperaOptions();
            
            try {
                options.setBinary(TestThread.config("browser.binary.path"));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Browser not found");
            }
            
            options.merge(capabilities);
            
            
            return new OperaDriver(options);
        }
    };

    public final static boolean HEADLESS = Boolean.getBoolean("headless");

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}