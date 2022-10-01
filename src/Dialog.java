public class Dialog {
    private static String id; // имя/id пользователя
    //private static String hidden_word; // загаданное слово //почему стринг а не объект класса хиден ворд?

    private static HiddenWord hidden_word; //изменила на объект hiddenword
    private static boolean dialogState; // переменная, которая показывает диалог активен или нет
    private static Database database;


    public Dialog (Database Database){
        database = Database;
        dialogState = true;
    }

    public static void FinishDialog(){ // завершает диалог
        dialogState = false;
    }

    public static boolean IsActive(){ // возвращает заначение состояния диалога
        return dialogState;

    }

    public static void AddId(String Id){
        id = Id;
    }

   // public static void AddHiddenWord(String hiddenWord){
        //hidden_word = hiddenWord;
    //}
   public static void AddHiddenWord(HiddenWord hiddenWord){
       hidden_word = hiddenWord;
       }

    public static Database ReturnDatabase(){  //зачем в классе диалог своя база данных ведь она общая для всех диалогов?
        return database;
    }

   // public static String ReturnHiddenWord(){
      //  return hidden_word;
    //}
   public static HiddenWord ReturnHiddenWord(){
       return hidden_word;
   }
}
