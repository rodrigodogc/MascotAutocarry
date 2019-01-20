package scrapingforlife.configuration;

import com.diogonunes.jcdp.color.api.Ansi;
import java.io.IOException;
import static org.openqa.selenium.Proxy.ProxyType.MANUAL;
import static org.openqa.selenium.remote.CapabilityType.PROXY;
import static scrapingforlife.configuration.DriverType.CHROME;
import static scrapingforlife.configuration.DriverType.OPERA;
import static scrapingforlife.configuration.DriverType.FIREFOX;

import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JOptionPane;

import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import static scrapingforlife.DebugControl.debug;
import static scrapingforlife.DebugControl.debugGreenBold;
import static scrapingforlife.TestThread.config;


public class DriverFactory {
	
	private final String operatingSystem = System.getProperty("os.name").toUpperCase();
    private final String systemArchitecture = System.getProperty("os.arch");
    private final boolean useRemoteWebDriver = Boolean.getBoolean("remoteDriver");
    private final boolean proxyEnabled = Boolean.getBoolean("proxyEnabled");
    private final String proxyHostname = System.getProperty("proxyHost");
    private final Integer proxyPort = Integer.getInteger("proxyPort");
    private final String proxyDetails = String.format("%s:%d", proxyHostname, proxyPort);
    
    private RemoteWebDriver driver;
    private DriverType selectedDriverType;
    
    public DriverFactory() throws IOException {
        // sets browser for testing
        DriverType driverType = DriverType.valueOf(config("browser.type").toUpperCase());        
            
        String browser = System.getProperty("browser", driverType.toString()).toUpperCase();
        try {
            driverType = DriverType.valueOf(browser);
        } catch (IllegalArgumentException ignored) {
            System.err.println("Unknown driver specified, defaulting to '" + driverType + "'...");
        } catch (NullPointerException ignored) {
            System.err.println("No driver specified, defaulting to '" + driverType + "'...");
        }
        
        selectedDriverType = driverType;
        
        System.out.println(" ");
        debugGreenBold("Local Operating System: " + operatingSystem);
        debugGreenBold("Local Architecture: " + systemArchitecture);
        debugGreenBold("Selected Browser: " + selectedDriverType.toString().toUpperCase());
        debugGreenBold("Connecting to Selenium Grid: " + useRemoteWebDriver);
        System.out.println(" ");
    }

    public RemoteWebDriver getDriver() {
        if (null == driver) {
            try {
				instantiateWebDriver(selectedDriverType);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
        }

        return driver;
    }

    public RemoteWebDriver getStoredDriver() {
        return driver;
    }

    public void quitDriver() {
        if (null != driver) {
            driver.quit();
            driver = null;
        }
    }

    private void instantiateWebDriver(DriverType driverType) throws MalformedURLException {

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        if (proxyEnabled) {
            Proxy proxy = new Proxy();
            proxy.setProxyType(MANUAL);
            proxy.setHttpProxy(proxyDetails);
            proxy.setSslProxy(proxyDetails);
            desiredCapabilities.setCapability(PROXY, proxy);
        }

        if (useRemoteWebDriver) {
            URL seleniumGridURL = new URL(System.getProperty("gridURL"));
            String desiredBrowserVersion = System.getProperty("desiredBrowserVersion");
            String desiredPlatform = System.getProperty("desiredPlatform");

            if (null != desiredPlatform && !desiredPlatform.isEmpty()) {
                desiredCapabilities.setPlatform(Platform.valueOf(desiredPlatform.toUpperCase()));
            }

            if (null != desiredBrowserVersion && !desiredBrowserVersion.isEmpty()) {
                desiredCapabilities.setVersion(desiredBrowserVersion);
            }

            desiredCapabilities.setBrowserName(selectedDriverType.toString());
            driver = new RemoteWebDriver(seleniumGridURL, desiredCapabilities);
        } else {
            driver = driverType.getWebDriverObject(desiredCapabilities);
        }
    }

}
