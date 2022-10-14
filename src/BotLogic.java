import java.util.ArrayList;

public class BotLogic {

    private String fileName = "Words.txt";
    private Database database = new Database(fileName);
    private ArrayList<User> arrayUsers = new ArrayList<>();

    public String help(){
        return """
                    Я с радостью поиграю с тобой в виселицу!
                    Девять ошибок и ты проиграл!
                    Если ты,прекрасный человек, забыл как со мной общаться, то вот команда эксклюзивно для тебя:
                    1)Введи "start" и ты начнёшь, остальное узнаешь там)
                    2)Введи "new word", если хочешь, чтобы я загадал новое слово.
                    3)Если ты устал и хочешь закончить игру, напиши "finish".""";

    }
    public String greeting() {
        return """
                Привет! Я Кiт, двай поиграем в виселицу. Попробуй отгадать мое слово.
                Девять ошибок и ты проиграл!
                Если захочешть посмотреть, какие команды я понимаю, напиши "help".
                Чтобы начать игру введи "start"!""";
    }

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
    public String start(User dialog){
        HiddenWord hiddenWord = new HiddenWord(generateWord(database.words()));
        dialog.addHiddenWord(hiddenWord);
        String text = "Отлично! Попробуй отгадать моё слово!\n";
        dialog.startProcessing();
        return text + hiddenWord.wordWithHiddenLetters();
    }

    public String newWord(User dialog){ // ф-я возвращает новое загаданное слово
        dialog.startProcessing();
        HiddenWord hiddenWord = new HiddenWord(generateWord(database.words()));
        dialog.addHiddenWord(hiddenWord);
        String text = "Вот, держи новое слово\n";
        return text + hiddenWord.wordWithHiddenLetters();

    } // меняем поля в загаданном слове

    public String finish(User user){
        user.finishDialog();                  //сменяем флаг на неактивный диалог переключаясь на другой
        return "Пока, до новых встреч! Если захочешь снова поиграть, просто нипиши \"start\"!";
    }

    public String getAnswerOnLetter(char userMessage, User user){ //TODO: комментарии
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
    public User getUser(String Id){ //ф-я проверяет по id вел ли бот диалог с этим пользователем.
                                    // Если вел, возвращаем этого user, если нет - создаем новый user c данным id.
        for (int i = 0; i < arrayUsers.size(); i++ ){
            if (arrayUsers.get(i).returnId() == Id){
                return arrayUsers.get(i);
            }
        }
        User user = new User(Id);
        arrayUsers.add(user);
        return user;
    }

    public boolean thereAreActiveUsers(){ //ф-я проверяет, есть ли активные диалоги в массиве ArrayUsers
        boolean isConsistActvUser = false;
        for (int i = 0; i < arrayUsers.size(); i++ ){
            if (arrayUsers.get(i).returnState() != "notActive"){
                isConsistActvUser = true;
            }
        }
        return isConsistActvUser;
    }

    public boolean isRepeatedLetter(char userMessage, User user){
        return user.returnHiddenWord().wordWithHiddenLetters().contains(Character.toString(userMessage));
    }

    public String generateWord(ArrayList<String> wordsArray) { // ф-я генерирует рандомное слово, которое будет загадывать бот, передаем массив слов
        int randNumber = (int) ( Math.random() * wordsArray.size() );
        return wordsArray.get(randNumber);
    }
}
