package scrapingforlife;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static scrapingforlife.DebugControl.*;
import static scrapingforlife.TestThread.config;
import static scrapingforlife.TestThread.theBegin;

public class SearchValidation {
/**
    public static void validateResults() throws IOException {
        File peakListPath = new File(config("input.peaklists.path"));
        File resultsPath =  new File(config("output.results.path"));

        Arrays.stream(peakListPath.listFiles()).filter(f -> f.isFile()).
                forEach(f -> System.out.println(f.getName()));

        long peakListNumber = Arrays.stream(peakListPath.listFiles()).filter(f -> f.isFile()).count();

        long resultFiles = Arrays.stream(resultsPath.listFiles()).filter(f -> f.isFile()).count();

        System.out.println(peakListNumber + " Peaklists files");

        if (resultFiles != peakListNumber * 8) {
            
            debugErrorMessage("Inconsistent Results!!!!!!");
            debugErrorMessage("Cleaning results and Restarting all searchs!");
                String resultsPathName = resultsPath.getPath();
            resultsPath.delete();
            debugGreenBold("Searchs are being restarted...");
            new File(resultsPathName).createNewFile();
            
            theBegin(); //restarts all searchs
            
        } else {
            debugGreenBold("Results Validated Successfully!");

        }
    }
    **/
}
