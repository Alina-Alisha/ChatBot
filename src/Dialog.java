public class Dialog {
    private static String id; // имя/id пользователя
    private static HiddenWord hidden_word; // загаданное слово
    private static boolean diologState; // переменная, которая показывает диалог активен или нет
    public Dialog (){
        diologState = true;
    }

    public static void FinishDiolog(){ // завершает диалог
        diologState = false;
    }

    public static boolean IsActive(){ // возвращает заначение состояния диалога
        return diologState;

    }

    public static void AddId(String Id){
        id = Id;
    }

    public static void AddHiddenWord(String hiddenWord){
        hidden_word = hiddenWord;
    }
}
