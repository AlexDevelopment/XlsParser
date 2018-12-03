package xls;


public class XlsParser {

   public static <T> SheetParser<T> withType(Class<T> type){
       return SheetParser.newInstance(type);
   }

   public static <T> FirstSheetParser<T> takeFirstSheetWithType(Class<T> type){
       return FirstSheetParser.newInstance(type);
   }

   public static <T> IterableSheetParser<T> forEachSheetsWithType(Class<T> type){
       return IterableSheetParser.newInstance(type);
   }

}
