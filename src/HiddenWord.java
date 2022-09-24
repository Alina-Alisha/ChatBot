import java.util.ArrayList;

public class HiddenWord {
    private String word; // хранит само загаданное слово в виде строки
    private int length; // хранит длину загаданного слова
    private int mistake;// хранит кол-во ошибок (количество названных букв,
                        // несодержащтихся в загаданном слове)
    private int hiddenLetter; // хранит кол-во неразгаданных букв
    private boolean[] hiddenLetters = new boolean[length]; // массив в котором на i месте стоит 1, если буква разгадана, 0 если нет

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
        return false;
    }

    public static boolean IsWordSolved(){ // проверяет, разгадано ли слово

        return false;
    }

    public static void NewHiddenWord(){ // в теле ф-и вызывается функция,
                                        // генерирующая новое слово, перезаписываются поля word, length, mistake,
                                        // hiddenLetter, hiddenLetters
        // GenerateWord(){}
    }



}
