import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Dialog {
    private static String id; // имя/id пользователя
    //  private static HiddenWord hidden_word; // загаданное слово
    public static boolean dialogState; // переменная, которая показывает диалог активен или нет
    public Dialog (){
        dialogState = true;
    }

    public static void FinishDiolog(){ // завершает диалог
        dialogState = false;
    }

    public static void SwitchDialog(String Message){ //выходит из цикла если введен ID, нужно записать в botlogic
        if (Message.charAt(0) == 'I' && Message.charAt(1) == 'D'){
            dialogState = false;
        }
    }

    public static boolean IsActive(){ // возвращает заначение состояния диалога
        return dialogState;
    }

    public static void AddId(String Id){

        id = Id;
    }

    public static void AddHiddenWord(String hiddenWord){
        //hidden_word = hiddenWord;
    }



    public static void DialogHistory(String id, String Message){ //создается файл с именем ID пользователя, в него записывается сообщение получаемое или передаваемое
        if (Message.charAt(0) != 'I' && Message.charAt(1) != 'D') { //если не ID сохраняем в историю сообщение
            File file = new File("");
            String fileName = "";
            try {
                fileName = "C:\\Users\\User\\IdeaProjects\\ChatBot\\DialogsHistory" + id + ".txt";
                File f = new File(fileName); //создание файла для каждого пользователя
                file = f;
            } catch (Exception e) {
                System.err.println(e); //обработка ошибки
            }

            try (FileWriter writer = new FileWriter(fileName)) {
                // запись всей строки
                String text = Message + '\n'; //передача сообщения в переменную для записи в файл
                writer.write(text);
                writer.flush();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void CleanScreen() {

    }

}