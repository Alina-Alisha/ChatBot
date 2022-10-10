import java.util.ArrayList;

public class HiddenWord {
    public String word; // хранит само загаданное слово в виде строки
    public int lengthOfHiddenWord; // хранит длину загаданного слова
    public int mistake;// хранит кол-во ошибок (количество названных букв, несодержащтихся в загаданном слове)
    private ArrayList<Boolean> hiddenLetters = new ArrayList<>(); // массив в котором на i месте стоит 1, если буква разгадана, 0 если нет

    public HiddenWord(String hiddenWord) {
        word = hiddenWord;
        lengthOfHiddenWord = hiddenWord.length();
        mistake = 0;
        for (int i = 0; i < hiddenWord.length() - 1; i++) {
            hiddenLetters.add(false);
        }
    }

    public boolean isLetterFit(char letter) { // проверка, содержится ли буква в слове
        boolean flag = false;
        for (int i = 0; i < lengthOfHiddenWord - 1; i++) {
            if (word.charAt(i) == letter) {
                hiddenLetters.set(i, true);
                flag = true;
            }
        }

        if (!flag)//обработка ошибок
            mistake++;

        return flag;
    }

    public boolean isWordSolved() { // проверяет, разгадано ли слово
        boolean isWordSolved = true;
        for (int i = 0; i < lengthOfHiddenWord - 1; i++) {
            if (!hiddenLetters.get(i)) {
                isWordSolved = false;
                break;
            }
        }
        return isWordSolved;
    }

    public String wordWithHiddenLetters() {
        StringBuilder word_with_hidden_letters = new StringBuilder();
        for (int i = 0; i < lengthOfHiddenWord - 1; i++) {
            if (hiddenLetters.get(i)) {
                word_with_hidden_letters.append(word.charAt(i));
            } else {
                word_with_hidden_letters.append("_");
            }
        }
        return word_with_hidden_letters.toString();
    }



}
