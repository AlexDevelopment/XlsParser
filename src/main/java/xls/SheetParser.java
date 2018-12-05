package xls;


import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import xls.exception.OnErrorListener;
import xls.format.FormatParser;
import xls.format.FormatParserFactory;
import xls.util.WorkBookUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SheetParser<T>  {

    private Class<T> type;
    private OnErrorListener onErrorListener = null;
    private String nameSheet = "";

    static <T> SheetParser<T> newInstance(Class<T> type){
        return new SheetParser<T>(type);
    }

    private SheetParser(Class<T> type){
        this.type = type;
    }

    public SheetParser<T> withNameSheet(String nameSheet){
        this.nameSheet = nameSheet;
        return this;
    }

    public SheetParser<T> onError(OnErrorListener onErrorListener){
        this.onErrorListener = onErrorListener;
        return this;
    }

    public List<T> parse(File file){
        Workbook workBook = null;
        try {
            workBook = WorkbookFactory.create(file);
        } catch (IOException e) {
            onErrorListener.onError(e);
            e.printStackTrace();
        }

        FormatParser parser = FormatParserFactory.getFormatParser(WorkBookUtil.getBookTypeBy(workBook), type, nameSheet, onErrorListener);
        return parser.parse(workBook);
    }


}
