package io.proj3ct.GameTGBot.game;

import java.util.ArrayList;
import java.util.HashMap;

public class GallowsGame {
    public HiddenWord hiddenWord;
    enum  State{active, notActive}
    private State state;

    public String city;

    public GallowsGame(){
        state = State.notActive;
    }


    public String getAnswer(String userMessage, Database database){// ф-я возвращает ответ бота на сообщение пользователя

        switch(userMessage){
            case("help"):
                return help();
            case("start"):
                return start(database);
            case("new word"):
                return newWord(database);
            case("finish"):
                return finish();
            default:
                break;
        }

        if (userMessage.length() == 1){// проверяем, есть ли буква в загаданном слове, возвращает сообщение с ответом да/нет, + слово с отгаданными буквами
            char letter = userMessage.charAt(0);
            return getAnswerOnLetter(letter, database);      // если нет то + кол-во права на ошибку
        }

        return getAnswerOnFullWord(userMessage);
    }
    public String help(){
        return """
                    Я с радостью поиграю с тобой в виселицу!
                    Девять ошибок и ты проиграл!
                    Если ты,прекрасный человек, забыл как со мной общаться, то вот команда эксклюзивно для тебя:
                    1)Введи "start" и ты начнёшь, остальное узнаешь там)
                    2)Введи "new word", если хочешь, чтобы я загадал новое слово.
                    3)Если ты устал и хочешь закончить игру, напиши "finish".""";
    }

    public String start(Database database){
        hiddenWord = new HiddenWord(generateWord(database.returnWordsArray()));
        String text = "Отлично! Попробуй отгадать моё слово!\n";
        state = State.active;
        return text + hiddenWord.wordWithHiddenLetters();
    }

    public String startFromCitiesGame(char letter, Database database){
        state = State.active;
        return "Вот загаданный город: " +"\n" + guessTheCity(letter, database);
    }

    public String guessTheCity(char letter, Database database){
        HashMap hashMap = database.getHashMap();
        ArrayList cities = (ArrayList) hashMap.get(letter);
        int randIndex = (int) (Math.random()* cities.size()+1);
        hiddenWord = new HiddenWord((String) cities.get(randIndex));
        hiddenWord.hiddenLetters.set(0, true) ;
        return hiddenWord.wordWithHiddenLetters();
    }


    public String newWord(Database database){ // ф-я возвращает новое загаданное слово
        //dialog.startProcessing(); //TODO: добавить состояние обработка буквы
        hiddenWord = new HiddenWord(generateWord(database.returnWordsArray()));
        String text = "Вот, держи новое слово\n";

        return text + hiddenWord.wordWithHiddenLetters();
    }

    private String finish(){
        state = State.notActive;                //сменяем флаг на неактивный диалог переключаясь на другой
        return "Пока, до новых встреч! Если захочешь снова поиграть, просто нипиши \"start\"!";
    }
    private String getAnswerOnLetter(char userMessage, Database database){ //TODO: комментарии
        String text;
        int attempts = 8 - hiddenWord.mistake;
        if(isRepeatedLetter(userMessage)){
            return "Ты уже отгадал букву " + userMessage + ". Попробуй другую букву.";
        }
        else if (hiddenWord.isLetterFit(userMessage)) {
            if (hiddenWord.isWordSolved()) {
                city = hiddenWord.word;
                state = State.notActive;
                return null;
                //return  hiddenWord.word +"\n"+
                //        "Победа!\n" +
                //        "Сыграем еще разок? Введи \"new word\"";
            }else text = "Угадал! Есть такая буква.\n";

        } else  if (hiddenWord.mistake == 9){
            //return "К сожалению, ты проиграл. Сыграем еще раз? Введи \"new word\"";
            return "К сожалению, ты проиграл. Попробуй еще раз" + "\n" +
                    guessTheCity(hiddenWord.getHiddenwordFirstLetter(), database);
        }else {
            text = "Промах. Здесь нет буквы ”" + userMessage + "”.\n" +
                    "У тебя есть еще " + attempts + " попыток.\n";
        }
        return text + hiddenWord.wordWithHiddenLetters();
    }

    private String getAnswerOnFullWord(String userMessage){
        if (hiddenWord.isFullWordIsHiddenWord(userMessage)){
            //return "Победа!";
            city = userMessage;
            state = State.notActive;
            return null;
        } else{
            int attempts = 8 - hiddenWord.mistake;
            return "Не угадал, попробуй снова\n" +
                    "У тебя есть еще " + attempts + " попыток.";
        }
    }

        private boolean isRepeatedLetter(char userMessage){ //TODO: не работает
        return hiddenWord.wordWithHiddenLetters().contains(Character.toString(userMessage));
    }

    private String generateWord(ArrayList<String> wordsArray) { // ф-я генерирует рандомное слово, которое будет загадывать бот, передаем массив слов
        int randNumber = (int) ( Math.random() * wordsArray.size() );
        return wordsArray.get(randNumber);
    }

    public State getState() {
        return state;
    }

    public static State returnActiveState() {
        return State.active;
    }

}