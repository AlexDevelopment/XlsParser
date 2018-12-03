package xls;


import xls.exception.OnErrorListener;
import xls.format.FormatParser;
import xls.format.FormatParserFactory;

import java.io.File;
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
        String fileExtension = getFileExtension(file);
        FormatParser parser = FormatParserFactory.getFormatParser(fileExtension, type, nameSheet, onErrorListener);
        return parser.parse(file);
    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
}
