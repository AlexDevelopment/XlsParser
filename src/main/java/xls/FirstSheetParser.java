package xls;

import xls.exception.OnErrorListener;
import xls.format.FormatParser;
import xls.format.FormatParserFactory;

import java.io.File;
import java.util.List;

import static xls.util.FileUtil.getFileExtension;

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
        String fileExtension = getFileExtension(file);
        FormatParser parser = FormatParserFactory.getFirstSheetFormatParser(fileExtension, type, onErrorListener);
        return parser.parse(file);
    }
}
