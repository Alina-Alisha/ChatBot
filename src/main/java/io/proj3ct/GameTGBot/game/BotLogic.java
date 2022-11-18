package io.proj3ct.GameTGBot.game;

import java.util.HashMap;
import java.util.Map;

public class BotLogic {

    private String fileNameWords;
    private String fileNameCities;
    private Database database; //база данных создается сразу и не передается в конструктор
    private Map<Long, User> dialogStateById = new HashMap<>();


    public BotLogic(String fileNameWords, String fileNameCities) {
        this.fileNameWords = fileNameWords;
        this.fileNameCities = fileNameCities;
        database = new Database(fileNameWords, fileNameCities);
    }

    public String getAnswer(String userMessage, long id) {
        User user = getUser(id);
        String answer = user.getAnswer(userMessage, database);
        if (answer == null) {
            return help();
        }
        return answer;

        /*
            if (user.getCitiesGameState() == user.getCitiesGameIsActive()) {// проверка активна ли игра в города
            return user.getCitiesGameGetAns(userMessage, database);
        } else if(user.getCitiesGameState() == user.getCitiesGameIsHint()){ // проверка взял ли игрок подсказку, те активна ли игра в виселицу
            return user.getCitiesGameGetAns(userMessage, database);
        }
        else if(user.getGallowsGameState() == user.getGallowsGameIsActive()){
            return user.getGallowsGameGetAns(userMessage, database);
        }

        switch (userMessage) {
            case ("start cities game"):
                return user.startCitiesGame(database);
            case ("end cities game"):
                return user.endCitiesGame();
            default:
                return help();
        }

         */

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

}