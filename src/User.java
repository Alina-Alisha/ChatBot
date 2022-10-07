public class User {

    //TODO:ссылка на историю диалогов

    private String Id; // имя/id пользователя
    private HiddenWord hidden_word;
    public boolean dialogState; // переменная, которая показывает диалог активен или нет

    public User(String id){
        this.Id = id;
        dialogState = true;
    }

    public void finishDialog(){ // завершает диалог
        dialogState = false;
    }

    public boolean isActive(){ // возвращает заначение состояния диалога
        return dialogState;
    }

    /*public void addId(String Id){
        id = Id;
    }

     */

   public void addHiddenWord(HiddenWord hiddenWord){
       hidden_word = hiddenWord;
       }

   public HiddenWord returnHiddenWord(){
       return hidden_word;
   }

   public String returnId(){
       return Id;
   }
}
