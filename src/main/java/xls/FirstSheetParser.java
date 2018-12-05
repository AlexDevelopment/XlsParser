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

public class FirstSheetParser<T> {

    private Class<T> type;
    private OnErrorListener onErrorListener = null;

    static <T> FirstSheetParser<T> newInstance(Class<T> type){
        return new FirstSheetParser<T>(type);
    }

    private FirstSheetParser(Class<T> type){
        this.type = type;
    }

    public FirstSheetParser<T> onError(OnErrorListener onErrorListener){
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
        FormatParser parser = FormatParserFactory.getFirstSheetFormatParser(WorkBookUtil.getBookTypeBy(workBook), type, onErrorListener);
        return parser.parse(workBook);
    }
}
