package scrapingforlife;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.swing.JOptionPane;


public class TestThread {
    
          
    public static Properties getProp() throws IOException {  //Gets external properties configs
        Properties props = new Properties();
        FileInputStream file = new FileInputStream(".\\config.properties");
        //"C:\\Users\\Rodrigo\\Desktop\\MascotAutoProject\\src\\main\\java\\scrapingforlife\\config.properties");
        props.load(file);
        return props;
    }

    public static String config(String param) throws IOException {
        String prp = getProp().getProperty(param);
        return prp;
    }
    
    
    public static void kill() throws IOException {
        
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, "Would you like to stop?",
                "Mascot Search Running...", dialogButton);
        if (dialogResult == JOptionPane.YES_OPTION) {
            if (Boolean.valueOf(config("close.browser.after.exit")) == true) {              
                Runtime.getRuntime().exec("cmd /c taskkill /F /IM opera.exe");
                Runtime.getRuntime().exec("cmd /c taskkill /F /IM firefox.exe");
                Runtime.getRuntime().exec("cmd /c taskkill /F /IM chrome.exe");
            }
            Runtime.getRuntime().exec("cmd /c taskkill /F /IM operadriver.exe");
            Runtime.getRuntime().exec("cmd /c taskkill /F /IM chromedriver.exe");
            Runtime.getRuntime().exec("cmd /c taskkill /F /IM firefoxdriver.exe");
            System.exit(0);
        }

    }
    public static void theBegin() throws IOException {
                 

    }
	public static void main(String args[]) throws IOException {
                 
            final SearchModel searchModel = buildSearchModel();
            final List<Path> peakListPaths = buildPeakListPaths(searchModel.getPeakListFolderPath());

            final ExecutorService service = Executors.newFixedThreadPool(
                    Integer.parseInt(config("threads.simultaneous")));// Number of simultaneous threads

            IntStream.range(0, peakListPaths.size())
                    .forEach(i -> {
                        try {
                            service.submit(new TestScrap(searchModel, peakListPaths.get(i)));
                        } catch (IOException ex) {

                        }
                    });

            service.shutdown();

            if (Boolean.valueOf(config("invisible.mode")) == false) {
                //kill();
                RunningWindow exit = new RunningWindow();
                exit.setVisible(true);
            }
            
        }

	private static List<Path> buildPeakListPaths(final String peakListFolderPath) {
		final List<Path> peakListPaths = new ArrayList<>();
		try (Stream<Path> paths = Files.walk(Paths.get(peakListFolderPath))) {
			peakListPaths.addAll(paths.filter(Files::isRegularFile).collect(Collectors.toList()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return peakListPaths;
	}

	private static SearchModel buildSearchModel() throws IOException {
		final SearchModel searchModel = new SearchModel();
		searchModel.setName(config("browse.name"));
		searchModel.setEmail(config("browse.email"));
		searchModel.setDatabase(config("browse.database"));
                searchModel.setTaxonomy(config("browse.taxonomy"));
		searchModel.setPeakListFolderPath(config("input.peaklists.path"));
		searchModel.setPeakListResultPath(config("output.results.path") + "\\");
		return searchModel;
	}
}