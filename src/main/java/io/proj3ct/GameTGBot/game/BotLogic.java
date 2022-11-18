package io.proj3ct.GameTGBot.game;

import java.util.HashMap;
import java.util.Map;

public class BotLogic {

    private String fileNameWords;
    private String fileNameCities;
    private Database database; //база данных создается сразу и не передается в конструктор
    private Map<String, User> dialogStateById = new HashMap<>();


    public BotLogic(String fileNameWords, String fileNameCities) {
        this.fileNameWords = fileNameWords;
        this.fileNameCities = fileNameCities;
        database = new Database(fileNameWords, fileNameCities);
    }

    public String getAnswer(String userMessage, String id) {
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
        for (Map.Entry<String, User> pair : dialogStateById.entrySet()) {
            if (pair.getValue().getState() == pair.getValue().getStateNotActive()) {
                return false;
            }
        }
        return true;

    }

    private User getUser(String Id) { //ф-я проверяет по id вел ли бот диалог с этим пользователем.
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


    //стврые ф-и из BotLogic пока просто закомментированы, могут пригодиться

    /*
    private String help(){
        return """
                    Я с радостью поиграю с тобой в виселицу!
                    Девять ошибок и ты проиграл!
                    Если ты,прекрасный человек, забыл как со мной общаться, то вот команда эксклюзивно для тебя:
                    1)Введи "start" и ты начнёшь, остальное узнаешь там)
                    2)Введи "new word", если хочешь, чтобы я загадал новое слово.
                    3)Если ты устал и хочешь закончить игру, напиши "finish".""";

    }

 */
/*
    public String getAnswer(String userMessage, String Id){ // ф-я возвращает ответ бота на сообщение пользователя
        User user = getUser(Id);
        if (userMessage.length() == 1 && user.returnState() == "wordProcessing"){// проверяем, есть ли буква в загаданном слове, возвращает сообщение с ответом да/нет, + слово с отгаданными буквами
            char letter = userMessage.charAt(0);
            return getAnswerOnLetter(letter, user);      // если нет то + кол-во права на ошибку
        }

        switch(userMessage){
            case("help"):
                return help();
           case("start"):
               return start(user);
            case("new word"):
                return newWord(user);
            case("finish"):
                return finish(user);
            default:
                break;
        }
        return help();

    }
    private String start(User dialog){
        HiddenWord hiddenWord = new HiddenWord(generateWord(database.returnWordsArray()));
        dialog.addHiddenWord(hiddenWord);
        String text = "Отлично! Попробуй отгадать моё слово!\n";
        dialog.startProcessing();
        return text + hiddenWord.wordWithHiddenLetters();
    }

 */
/*
    private String newWord(User dialog){ // ф-я возвращает новое загаданное слово
        dialog.startProcessing();
        HiddenWord hiddenWord = new HiddenWord(generateWord(database.returnWordsArray()));
        dialog.addHiddenWord(hiddenWord);
        String text = "Вот, держи новое слово\n";
        return text + hiddenWord.wordWithHiddenLetters();

    } // меняем поля в загаданном слове

    private String finish(User user){
        user.finishDialog();                  //сменяем флаг на неактивный диалог переключаясь на другой
        return "Пока, до новых встреч! Если захочешь снова поиграть, просто нипиши \"start\"!";
    }

 */

/*
    private String getAnswerOnLetter(char userMessage, User user){ //TODO: комментарии
        String text;
        int attempts = 8 - user.returnHiddenWord().mistake;
        if(isRepeatedLetter(userMessage, user)){
            return "Ты уже отгадал букву " + userMessage + ". Попробуй другую букву.";
        }
        else if (user.returnHiddenWord().isLetterFit(userMessage)) {
            if (user.returnHiddenWord().isWordSolved()) {
                user.endProcessing();
                return  user.returnHiddenWord().word +"\n"+
                        "Победа!\n" +
                        "Сыграем еще разок? Введи \"new word\"";
            }
                text = "Угадал! Есть такая буква.\n";

        } else  if (user.returnHiddenWord().mistake == 9){
            user.endProcessing();
            return "К сожалению, ты проиграл. Сыграем еще раз? Введи \"new word\"";
        }else {
            text = "Промах. Здесь нет буквы ”" + userMessage + "”.\n" +
                    "У тебя есть еще " + attempts + " попыток.\n";
        }
        return text + user.returnHiddenWord().wordWithHiddenLetters();
    }
    private User getUser(String Id){ //ф-я проверяет по id вел ли бот диалог с этим пользователем.
        if (dialogStateById.containsKey(Id)){
            //TODO надо ли менять статус?
            return dialogStateById.get(Id);
        }
        User user = new User(Id);
        dialogStateById.put(Id, user);
        return dialogStateById.get(Id);
    }

 */


/*
    private boolean isRepeatedLetter(char userMessage, User user){
        return user.returnHiddenWord().wordWithHiddenLetters().contains(Character.toString(userMessage));
    }

    private String generateWord(ArrayList<String> wordsArray) { // ф-я генерирует рандомное слово, которое будет загадывать бот, передаем массив слов
        int randNumber = (int) ( Math.random() * wordsArray.size() );
        return wordsArray.get(randNumber);
    }

 */
}
