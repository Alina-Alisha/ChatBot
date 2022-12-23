package io.proj3ct.GameTGBot.game;

import java.util.Scanner;
public class Main {
    public static void main(String[] args){
        String databaseFileNameWords = "Words.txt";
        String databaseFileNameCities = "Cities.txt";
        BotLogic botLogic = new BotLogic(databaseFileNameWords, databaseFileNameCities);
        long Id = 1;

        System.out.println(botLogic.greeting());// ф-я, которая возвращает строку с приветствием
        Scanner console = new Scanner(System.in);

//        do {
//            String userMessage = console.nextLine(); // читаем с консоли сообщение пользователя
//            String botMessage = botLogic.getAnswer(userMessage, Id); // генерим сообщение бота
//            System.out.println(botMessage); // выводим сообщение в консоль
//        }
//        while (botLogic.thereAreActiveUsers()); // проверяем, есть ли активные диалоги

    }

}

