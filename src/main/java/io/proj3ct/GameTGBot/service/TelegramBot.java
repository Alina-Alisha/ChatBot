package io.proj3ct.GameTGBot.service;

import io.proj3ct.GameTGBot.config.BotConfig;
import io.proj3ct.GameTGBot.game.BotLogic;
import io.proj3ct.GameTGBot.game.Database;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import static io.proj3ct.GameTGBot.service.GoogleSheets.getSheetContent;


@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;

    String databaseFileNameWords = "Words.txt";
    String databaseFileNameCities = "Cities.txt";
    //BotLogic botLogic = new BotLogic(databaseFileNameWords, databaseFileNameCities);
    Database database = new Database(getSheetContent());
    BotLogic botLogic = new BotLogic(database);

    public TelegramBot(BotConfig config) throws GeneralSecurityException, IOException {
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


            switch(messageText){
                case "/start":
                    startCommandRecieved(chatId, update.getMessage().getChat().getFirstName());
                    break;
                default:
                    sendMessage(chatId, botLogic.getAnswer(messageText, chatId));
                    if (botLogic.getImageState(chatId)){ //!TODO: ?????????????? ?? ????????????!
                        if(botLogic.getImageFile(chatId) != null){
                            sendImage(chatId, botLogic.getImageFile(chatId));
                        }
                    }

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

        createKeyboardForMessage(botLogic.getKeyboardRows(chatId), message);

        try{
            execute(message);
        }
        catch (TelegramApiException e){

        }

    }

    private void sendImage(long chatId, InputFile file) {
        SendPhoto image = new SendPhoto();
        image.setChatId(String.valueOf(chatId));
        image.setPhoto(file);

        try{
            execute(image);
        }
        catch (TelegramApiException e){
            System.out.println("???????? ???? ????????????:  " + e.getMessage());
        }


    }
    private void createKeyboardForMessage(List<KeyboardRow> KeyboardRows, SendMessage message){
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setKeyboard(KeyboardRows);
        message.setReplyMarkup(keyboardMarkup);
    }


}


