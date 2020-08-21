package work.poi.readExcel;

import java.io.File;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Aleks
 */
public final class ReadFile {

    private final String FILE = "PrimerRaspisania.xlsx";
    private final boolean directory = false;

    XSSFWorkbook book;
    private XSSFSheet sheet;

    public ReadFile() throws EncryptedDocumentException, InvalidFormatException, IOException {
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
                readCells();
            } else {
                System.out.println("Не найдена страница!");
            }
            try {
                if (!directory) {
                    book.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
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
//		is.close();

    }

    private void openBookDirectly(final String path) throws InvalidFormatException, IOException {
        File file = new File(path);

        OPCPackage pkg = OPCPackage.open(file);
        book = new XSSFWorkbook(pkg);
        pkg.close();
    }
// метод перебирает все страницы в книге

    public void readCells() {
        FormulaEvaluator fv = book.getCreationHelper().createFormulaEvaluator();

        for (Row row : sheet) {

            for (Cell cell : row) {
                switch (fv.evaluateInCell(cell).getCellType()) {
                    case Cell.CELL_TYPE_NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            System.out.print(cell.getDateCellValue().toString());
                        } else {
                            System.out.print(cell.getNumericCellValue() + "\t\t");
                        }
                        break;
                    case Cell.CELL_TYPE_STRING:
                        System.out.print(cell.getStringCellValue() + "\t\t");
                        break;
                }
            }
            System.out.println();
        }
    }
}
