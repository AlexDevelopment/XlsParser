package xls;

import xls.exception.OnErrorListener;
import xls.format.FormatParser;
import xls.format.FormatParserFactory;

import java.io.File;
import java.util.List;

import static xls.util.FileUtil.getFileExtension;

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
        String fileExtension = getFileExtension(file);
        FormatParser parser = FormatParserFactory.getIterableSheetsFormatParser(fileExtension, type, onErrorListener);
        return parser.parse(file);
    }
}
