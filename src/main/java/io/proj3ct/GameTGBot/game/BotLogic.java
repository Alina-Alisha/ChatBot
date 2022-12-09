package io.proj3ct.GameTGBot.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BotLogic {

    private Database database; //база данных создается сразу и не передается в конструктор
    private Map<Long, User> dialogStateById = new HashMap<>();


    public BotLogic(Database database) {
        this.database = database;
    }

    public String getAnswer(String userMessage, long id) {
        User user = getUser(id);
        if (Objects.equals(userMessage, "\\start")) {
            return greeting();
        }
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
                Вот команды, которые я понимаю:
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
