import java.util.ArrayList;

public class HiddenWord {
    private static String word; // хранит само загаданное слово в виде строки
    private static int length; // хранит длину загаданного слова
    private int mistake;// хранит кол-во ошибок (количество названных букв,
                        // несодержащтихся в загаданном слове)
    private int hiddenLetter; // хранит кол-во неразгаданных букв
    private static boolean[] hiddenLetters = new boolean[length]; // массив в котором на i месте стоит 1, если буква разгадана, 0 если нет

    public HiddenWord(String w){ // конструктор
        word = w;
        length = w.length();
        mistake = 0;
        hiddenLetter = w.length();
        for (int i=0; i < w.length(); i++) {
            hiddenLetters[i] = false;
        }
    }

    public static boolean ChekLetter(String chekLetter){ // проверка, содержится ли буква в слове
        boolean flag = false;
        for (int i = 0; i < length; i++){
            if (word[i] == chekLetter){
                hiddenLetters[i] = true;
                flag = true;
            }
        }
        return flag;
    }

    public static boolean IsWordSolved(){ // проверяет, разгадано ли слово
        boolean flag = true;
        for (int i =0; i < length; i++){
            if (!hiddenLetters[i]){
                flag = false;
            }
        }
        if (flag){return true;}
        else {return false;}
    }

    /*
    ЭТО ВСЕ ДОЛЖНО БЫТЬ В Dialog
    public static void NewHiddenWord(){ // в теле ф-и вызывается функция,
                                        // генерирующая новое слово, перезаписываются поля word, length, mistake,
                                        // hiddenLetter, hiddenLetters
        // GenerateWord(){}
    }
    */

    public static String WordWithHiddenLetters(){
        String word_with_hidden_letters = "";
        for (int i = 0; i < length; i++){
            if (hiddenLetters[i]){
                word_with_hidden_letters += word[i];
            }
            else {
                word_with_hidden_letters += "_";
            }
        }
        return word_with_hidden_letters;
    }



}
