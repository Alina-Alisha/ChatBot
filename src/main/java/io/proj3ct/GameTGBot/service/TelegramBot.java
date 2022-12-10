package io.proj3ct.GameTGBot.service;

import io.proj3ct.GameTGBot.config.BotConfig;
import io.proj3ct.GameTGBot.game.BotLogic;
import io.proj3ct.GameTGBot.game.Database;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;

    String databaseFileNameWords = "Words.txt";
    String databaseFileNameCities = "Cities.txt";
    Database database = new Database(databaseFileNameWords, databaseFileNameCities);
    BotLogic botLogic = new BotLogic(database);


    public TelegramBot(BotConfig config){
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken()    ;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();

            long chatId = update.getMessage().getChatId();

            String botMessage = botLogic.getAnswer(messageText, chatId);

            switch(messageText){
                case "/start":
                    startCommandRecieved(chatId, update.getMessage().getChat().getFirstName());
                    break;
                default:
                    sendMessage(chatId, botMessage);

            }
        }
    }

    private void startCommandRecieved(long chatId, String name)  {
        String answer = botLogic.greeting();
        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend)  {

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try{
            execute(message);
        }
        catch (TelegramApiException e){
            System.out.println("Файл не найден: " + e.getMessage());

        }

    }



}


