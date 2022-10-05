public class User {
    private String id; // имя/id пользователя
    private HiddenWord hidden_word;
    private boolean dialogState; // переменная, которая показывает диалог активен или нет
    private final Database database;


    public User(Database Database){
        database = Database;
        dialogState = true;
    }

    public void finishDialog(){ // завершает диалог
        dialogState = false;
    }

    public boolean isActive(){ // возвращает заначение состояния диалога
        return dialogState;

    }

    public void addId(String Id){
        id = Id;
    }

   public void addHiddenWord(HiddenWord hiddenWord){
       hidden_word = hiddenWord;
       }

    public Database ReturnDatabase(){  //TODO:зачем в классе диалог своя база данных ведь она общая для всех диалогов?
        return database;
    }

   public HiddenWord returnHiddenWord(){
       return hidden_word;
   }
}
