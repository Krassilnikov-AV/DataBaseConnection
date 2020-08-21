package work.poi.readExcel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadFileIterator {

    private final String FILE = "PrimerRaspisania.xlsx";
    private final boolean directory = false;

    XSSFWorkbook book;
    private XSSFSheet sheet;

    public ReadFileIterator() throws IOException, InvalidFormatException {
        if (directory) {
            openBookDirectly("FILE");
        } else {
            openBook(FILE);
        }
        if (book != null) {
            System.out.println("Книга Excel открыта");
            sheet = book.getSheet("GroupRegistrations2020_06_18_15");    // указывать название листа со страницы
            if (sheet != null) {
                System.out.println("Страница открыта!");
// чтение из ячеек
                readCells(FILE);
            } else {
                System.out.println("Не найдена страница!");
            }
            if (!directory) {
                book.close();
            }
        } else {
            System.out.println("Ошибка чтения файла Excel");
        }
    }

    private void openBook(final String path) throws FileNotFoundException,
            EncryptedDocumentException, InvalidFormatException, IOException {

        File file = new File(path);
        book = (XSSFWorkbook) WorkbookFactory.create(file);

//			InputStream is = new FileInputStream(FILE);
//			book = (XSSFWorkbook) WorkbookFactory.create(is);
//			is.close();       
    }

    private void openBookDirectly(final String path)
            throws InvalidFormatException, IOException {
        File file = new File(path);
        OPCPackage pkg = OPCPackage.open(file);
        book = new XSSFWorkbook(pkg);
        pkg.close();
    }

    public List<DataSpreadsheet> readCells(final String FILE) {
        List<DataSpreadsheet> data = new LinkedList<DataSpreadsheet>();

   //     sheet = book.getSheetAt(0);  // проверка только первой страницы

        Iterator<Row> rowIt = sheet.rowIterator(); // перебор всех строк

        if (rowIt.hasNext()) {
            rowIt.next();            // пропустить верхнюю строку страницы
        }

        while (rowIt.hasNext()) {
            XSSFRow row = (XSSFRow) rowIt.next();
            XSSFCell cell = row.getCell(4);
            DataSpreadsheet cod = new DataSpreadsheet();
            cod.setCode(cell.getStringCellValue());
            data.add(cod);
        }
        return data;
    }
    

}
