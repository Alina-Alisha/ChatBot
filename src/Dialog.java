public class Dialog {
    private static String id; // имя/id пользователя
    private static HiddenWord hidden_word; // загаданное слово
    private static boolean dialogState; // переменная, которая показывает диалог активен или нет
    private static Database database;


    public Dialog (Database Database){
        database = Database;
        dialogState = true;
    }

    public static void FinishDiolog(){ // завершает диалог
        dialogState = false;
    }

    public static boolean IsActive(){ // возвращает заначение состояния диалога
        return dialogState;

    }

    public static void AddId(String Id){
        id = Id;
    }

    public static void AddHiddenWord(String hiddenWord){
        hidden_word = hiddenWord;
    }

    public static Database ReturnDatabase(){
        return database;
    }

    public static HiddenWord ReturnHiddenWord(){
        return hidden_word;
    }
}
