
package work.poi.main;

import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import work.poi.readExcel.ReadFile;
import work.poi.readExcel.ReadFileIterator;

public class Main {
 
    public static void main(String[] args) throws IOException, InvalidFormatException {
        new ReadFile();   
        System.exit(0);
    }    
}
