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

public class IterableSheetParser<T> {

    private Class<T> type;
    private OnErrorListener onErrorListener = null;

    static <T> IterableSheetParser<T> newInstance(Class<T> type){
        return new IterableSheetParser<T>(type);
    }

    private IterableSheetParser(Class<T> type){
        this.type = type;
    }

    public IterableSheetParser<T> onError(OnErrorListener onErrorListener){
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
        FormatParser parser = FormatParserFactory.getIterableSheetsFormatParser(WorkBookUtil.getBookTypeBy(workBook), type, onErrorListener);
        return parser.parse(workBook);
    }
}
