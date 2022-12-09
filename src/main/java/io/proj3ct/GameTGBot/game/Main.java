package io.proj3ct.GameTGBot.game;

import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        String databaseFileNameWords = "Words.txt";
        String databaseFileNameCities = "Cities.txt";
        Database database = new Database(databaseFileNameWords, databaseFileNameCities);
        BotLogic botLogic = new BotLogic(database);
        long Id = 1;

        System.out.println(botLogic.greeting());// ф-я, которая возвращает строку с приветствием
        Scanner console = new Scanner(System.in);

        do {
            String userMessage = console.nextLine(); // читаем с консоли сообщение пользователя
            String botMessage = botLogic.getAnswer(userMessage, Id); // генерим сообщение бота
            System.out.println(botMessage); // выводим сообщение в консоль
        }
        while (botLogic.thereAreActiveUsers()); // проверяем, есть ли активные диалоги

    }

}

