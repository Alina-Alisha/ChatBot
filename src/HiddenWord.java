import java.util.ArrayList;

public class HiddenWord { //везде убрать статик, поменять флаг и чек
    public String word; // хранит само загаданное слово в виде строки
    private int length; // хранит длину загаданного слова
    private int mistake;// хранит кол-во ошибок (количество названных букв,
    // несодержащтихся в загаданном слове)
    private int hiddenLettersNumber; // хранит кол-во неразгаданных букв
    private ArrayList<Boolean> hiddenLetters = new ArrayList<>(); // массив в котором на i месте стоит 1,
    // если буква разгадана, 0 если нет

    public HiddenWord(String hiddenWord) {
        word = hiddenWord;
        length = hiddenWord.length();
        mistake = 0;
        hiddenLettersNumber = hiddenWord.length();
        for (int i = 0; i < hiddenWord.length() - 1; i++) {
            hiddenLetters.add(false);
        }
    }

    public boolean isLetterFit(char checkLetter) { // проверка, содержится ли буква в слове
        boolean flag = false;
        for (int i = 0; i < length - 1; i++) {
            if (word.charAt(i) == checkLetter) {
                hiddenLetters.set(i, true);
                flag = true;
            }
        }
        return flag;
    }

    public boolean isWordSolved() { // проверяет, разгадано ли слово
        boolean flag = true;
        for (int i = 0; i < length - 1; i++) {
            if (!hiddenLetters.get(i)) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    public String wordWithHiddenLetters() {
        StringBuilder word_with_hidden_letters = new StringBuilder();
        for (int i = 0; i < length - 1; i++) {
            if (hiddenLetters.get(i)) {
                word_with_hidden_letters.append(word.charAt(i));
            } else {
                word_with_hidden_letters.append("_");
            }
        }
        return word_with_hidden_letters.toString();
    }
}
