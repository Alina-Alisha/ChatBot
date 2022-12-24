package io.proj3ct.GameTGBot.game;

import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;


public class BotLogic {


    private String fileNameWords;
    private String fileNameCities;
    private Database database; //база данных создается сразу и не передается в конструктор
    private Map<Long, User> dialogStateById = new HashMap<>();

/*
    public BotLogic(String fileNameWords, String fileNameCities) {
        this.fileNameWords = fileNameWords;
        this.fileNameCities = fileNameCities;
        database = new Database(fileNameWords, fileNameCities);
    }

 */
    public BotLogic(Database database) {
        this.database = database;

    }


    public String getAnswer(String userMessage, long id){
        User user = getUser(id);
        String answer = user.getAnswer(userMessage, database);
        if (answer == null) {
            return help();
        }


        return answer;

    }

    public boolean thereAreActiveUsers() { //ф-я проверяет, есть ли активные диалоги в массиве ArrayUsers
        for (Map.Entry<Long, User> pair : dialogStateById.entrySet()) {
            if (pair.getValue().getState() == pair.getValue().getStateNotActive()) {
                return false;
            }
        }
        return true;

    }

    private User getUser(long Id) { //ф-я проверяет по id вел ли бот диалог с этим пользователем.
        if (dialogStateById.containsKey(Id)) {
            return dialogStateById.get(Id);
        }
        User user = new User(Id);
        dialogStateById.put(Id, user);
        return dialogStateById.get(Id);
    }

    private String help() {
        return """
                Если ты забыл, как со  мной общаться, вот команды, которые я понимаю:
                1) Введи "start cities game", чтобы начать игру в города
                2) Введи "end cities game", чтобы завершить игру
                """;
    }

    public String greeting() {
        return """
                Привет! Я Кiт, давай поиграем в города.
                Если захочешть посмотреть, какие команды я понимаю, напиши "help".
                Для подсказки введи "hint".
                Для начала игры введи "start cities game"!""";
    }
    public List<KeyboardRow> getKeyboardRows(long id) {
        User user = getUser(id);
        return user.KeyboardRowsForMessage();
    }

    public boolean getImageState(long id) {
        //User user = getUser(id);
        //return user.getImageState();
        return getUser(id).getImageState();
    }
    public InputFile getImageFile(long id){
        User user = getUser(id);
        if (user.getImage() != null)
            return user.getImage();
        else{
            return null;
        }
    }
}
