public class User {

    //TODO:ссылка на историю диалогов

    private String Id; // имя/id пользователя
    private HiddenWord hidden_word;
    private String dialogState; // переменная, которая показывает диалог активен или нет


    public User(String id){
        this.Id = id;
        dialogState = "active";
    }

    public void finishDialog(){ // завершает диалог
        dialogState = "notActive";
    }

    public String returnState(){ // возвращает заначение состояния диалога
        return dialogState;
    }


   public void addHiddenWord(HiddenWord hiddenWord){
       hidden_word = hiddenWord;
       }

   public HiddenWord returnHiddenWord(){
       return hidden_word;
   }

   public String returnId(){
       return Id;
   }

   public void startProcessing(){
        dialogState = "wordProcessing";
   }

    public void endProcessing(){
        dialogState = "active";
    }
}
