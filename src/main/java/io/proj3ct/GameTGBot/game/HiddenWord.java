package io.proj3ct.GameTGBot.game;

import java.util.ArrayList;
import java.util.Objects;

public class HiddenWord {
    public String word; // хранит само загаданное слово в виде строки
    public int lengthOfHiddenWord; // хранит длину загаданного слова
    public int mistake;// хранит кол-во ошибок (количество названных букв, несодержащтихся в загаданном слове)
    public ArrayList<Boolean> hiddenLetters = new ArrayList<>(); // массив в котором на i месте стоит 1, если буква разгадана, 0 если нет

    public HiddenWord(String hiddenWord) {
        word = hiddenWord;
        lengthOfHiddenWord = hiddenWord.length();
        mistake = 0;
        for (int i = 0; i < hiddenWord.length() ; i++) {
            hiddenLetters.add(false);
        }
    }

    public boolean isLetterFit(char letter) { // проверка, содержится ли буква в слове
        boolean isLetterFit = false;
        for (int i = 0; i < lengthOfHiddenWord; i++) {
            if (word.charAt(i) == letter) {
                hiddenLetters.set(i, true);
                isLetterFit = true;
            }
        }

        if (!isLetterFit)//обработка ошибок
            mistake++;

        return isLetterFit;
    }

    public boolean isFullWordIsHiddenWord(String fullWord) {
        if (Objects.equals(word, fullWord))
            return true;
        else{
            mistake++;
            return false;
        }
    }

        public boolean isWordSolved() { // проверяет, разгадано ли слово
        boolean isWordSolved = true;
        for (int i = 0; i < lengthOfHiddenWord; i++) {
            if (!hiddenLetters.get(i)) {
                isWordSolved = false;
                break;
            }
        }
        return isWordSolved;
    }

    public String wordWithHiddenLetters() {
        StringBuilder wordWithHiddenLetters = new StringBuilder();
        for (int i = 0; i < lengthOfHiddenWord; i++) {
            if (hiddenLetters.get(i)) {
                wordWithHiddenLetters.append(word.charAt(i));
            } else {
                wordWithHiddenLetters.append("_");
            }
        }
        return wordWithHiddenLetters.toString();
    }
    public char getHiddenwordFirstLetter(){
        return word.charAt(0);
    }

}
