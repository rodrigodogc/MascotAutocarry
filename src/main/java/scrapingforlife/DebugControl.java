
package scrapingforlife;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import com.diogonunes.jcdp.color.api.Ansi.BColor;
import com.diogonunes.jcdp.color.api.Ansi.FColor;

public class DebugControl {

    public static ColoredPrinter debug(String msg, Ansi.Attribute attribute, Ansi.FColor fcolor, Ansi.BColor bcolor) {
        ColoredPrinter cp = new ColoredPrinter.Builder(1, false)
                .foreground(FColor.WHITE)
                .background(BColor.BLACK)
                .build();
        cp.print(cp.getDateFormatted(), Ansi.Attribute.NONE, FColor.CYAN, BColor.BLACK);    
        cp.debugPrintln(" " + msg, attribute, fcolor, bcolor);
        cp.clear();
        return cp;
    }
    
        public static ColoredPrinter debugGreenBold(String msg) {
            
            Ansi.Attribute attribute = Ansi.Attribute.BOLD;
            Ansi.FColor fcolor       = Ansi.FColor.GREEN;
            Ansi.BColor bcolor       = Ansi.BColor.BLACK;        
            
        ColoredPrinter cp = new ColoredPrinter.Builder(1, false)
                .foreground(FColor.WHITE)
                .background(BColor.BLACK)
                .build();
        cp.print(cp.getDateFormatted(), Ansi.Attribute.NONE, FColor.CYAN, BColor.BLACK);    
        cp.debugPrintln(" " + msg, attribute, fcolor, bcolor);
        cp.clear();
        return cp;
    }

    public static ColoredPrinter debugErrorMessage(String msg) {

        Ansi.Attribute attribute = Ansi.Attribute.BOLD;
        Ansi.FColor fcolor = Ansi.FColor.GREEN;
        Ansi.BColor bcolor = Ansi.BColor.BLACK;

        ColoredPrinter cp = new ColoredPrinter.Builder(1, false)
                .foreground(FColor.WHITE)
                .background(BColor.BLACK)
                .build();
        cp.print(cp.getDateFormatted(), Ansi.Attribute.NONE, FColor.RED, BColor.BLACK);
        cp.debugPrintln(" " + msg, attribute, fcolor, bcolor);
        cp.clear();
        return cp;
    }

    public static ColoredPrinter formatedDate() {
        ColoredPrinter cp = new ColoredPrinter.Builder(1, false)
                .foreground(FColor.WHITE).background(BColor.BLACK) //setting format
                .build();   
        cp.println(cp.getDateFormatted(), Ansi.Attribute.NONE, FColor.CYAN, BColor.BLACK);
        cp.clear();
        return cp;
    }
    
        public static ColoredPrinter printSeparator(String msg) {
            
            Ansi.Attribute attribute = Ansi.Attribute.BOLD;
            Ansi.FColor fcolor       = Ansi.FColor.WHITE;
            Ansi.BColor bcolor       = Ansi.BColor.BLACK;        
            
        ColoredPrinter cp = new ColoredPrinter.Builder(1, false)
                .foreground(FColor.WHITE)
                .background(BColor.BLACK)
                .build();
        cp.println(msg, Ansi.Attribute.NONE, FColor.WHITE, BColor.BLACK);    
        cp.clear();
        return cp;
    }
    
}
