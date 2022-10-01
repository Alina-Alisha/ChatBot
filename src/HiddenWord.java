public class HiddenWord { //везде убрать статик, поменять флаг и чек
    private static String word; // хранит само загаданное слово в виде строки
    private static int length; // хранит длину загаданного слова
    private int mistake;// хранит кол-во ошибок (количество названных букв,
    // несодержащтихся в загаданном слове)
    private int hiddenLettersNumber; // хранит кол-во неразгаданных букв (изменила название тк с массив так же называется)
    private static boolean[] hiddenLetters = new boolean[length]; // массив в котором на i месте стоит 1,
    // если буква разгадана, 0 если нет

    public HiddenWord(String hiddenWord) { // конструктор  //т е мы передаем слово а у него нулевая длина
        word = hiddenWord;
        length = hiddenWord.length();
        mistake = 0;
        hiddenLettersNumber = hiddenWord.length();
        for (int i = 0; i < hiddenWord.length() - 1; i++) { //?выход за границы массива?
            hiddenLetters[i] = false;
        }
    }

    public static boolean CheckLetter(char checkLetter) { // проверка, содержится ли буква в слове //надо переименовать - 2 метода называются одинаково, в ботлогик chekletter
        boolean flag = false;
        for (int i = 0; i < length; i++) {
            if (word.charAt(i) == checkLetter) {   //изменила на char тк была ошибка
                hiddenLetters[i] = true;
                flag = true;
            }
        }
        return flag;
    }

    public static boolean IsWordSolved() { // проверяет, разгадано ли слово
        boolean flag = true;
        for (int i = 0; i < length; i++) {
            if (!hiddenLetters[i]) {
                flag = false;
            }
        }
        if (flag) {
            return true;
        } else {
            return false;
        }
    }

    public static String WordWithHiddenLetters() {
        String word_with_hidden_letters = "";
        for (int i = 0; i < length; i++) {
            if (hiddenLetters[i]) {
                word_with_hidden_letters += word.charAt(i);
            } else {
                word_with_hidden_letters += "_";
            }
        }
        return word_with_hidden_letters;
    }


}
