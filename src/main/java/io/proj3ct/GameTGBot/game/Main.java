package io.proj3ct.GameTGBot.game;

import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Scanner;

import static io.proj3ct.GameTGBot.service.GoogleSheets.getSheetContent;

public class Main {
    public static void main(String[] args) throws GeneralSecurityException, IOException {
        String databaseFileNameWords = "Words.txt";
        String databaseFileNameCities = "Cities.txt";
        //BotLogic botLogic = new BotLogic(databaseFileNameWords, databaseFileNameCities);
        Database database = new Database(getSheetContent());
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

