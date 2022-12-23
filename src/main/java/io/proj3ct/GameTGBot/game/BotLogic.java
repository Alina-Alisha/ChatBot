package io.proj3ct.GameTGBot.game;
import io.proj3ct.GameTGBot.dataBase.dataBase;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;


public class BotLogic {

    public Update update;
    private String fileNameWords;
    private String fileNameCities;
    private Database database; //база данных создается сразу и не передается в конструктор
    private Map<Long, User> dialogStateById = new HashMap<>();

    private dataBase dataBase = new dataBase();

    public BotLogic(String fileNameWords, String fileNameCities) {
        this.fileNameWords = fileNameWords;
        this.fileNameCities = fileNameCities;
        database = new Database(fileNameWords, fileNameCities);
    }

    public String getAnswer(String userMessage, long id){
        User user = getUser(id);
        String answer = user.getAnswer(userMessage, database);
        if (answer == null) {
            return help();
        }
        user.historyOfDialog += userMessage + "\n"+ answer +"\n";
        dataBase.writeDataInTable(id, user.historyOfDialog); //сохранили историю диалога для конкретного пользователя
        return answer;

    }

    public List<KeyboardRow> getKeyboardRows(long id){
        User user = getUser(id);
        return user.KeyboardRowsForMessage();
    }

    public InputFile getImageFile(long id){
        User user = getUser(id);
        return user.getImage();
    }

    public boolean getImageState(long id){
        User user = getUser(id);
        return user.getImageState();
    }

    private User getUser(long Id) {//ф-я проверяет по id вел ли бот диалог с этим пользователем.

        if (dialogStateById.containsKey(Id)) {
            return dialogStateById.get(Id);
        }
        User user = new User(Id);
        dataBase.writeDataInTable(Id,""); //внесли пользоватея в базу
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
