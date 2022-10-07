import java.util.ArrayList;

public class BotLogic {

    //TODO: проверка id пользователя в массиве id

    //TODO: обработать двойное введение одной и той же буквы

    public String fileName = "Words.txt";
    public Database database = new Database(fileName);
    private ArrayList<User> ArrayUsers = new ArrayList<>();

    public String help(){
        return "Если ты,прекрасный человек, забыл как со мной общаться, то вот команда эксклюзивно для тебя:\n"+
                    "1)Введи \"start\" и ты начнёшь, остальное узнаешь там) ";

    }
    public String greeting() {
        return """
                Привет! Я Кiт, двай поиграем в виселицу. Попробуй отгадать мое слово.
                Девять ошибок и ты проиграл!
                Буквы "е" и "ё" считаются за одну.
                буква "ы" мне не нравится, слов с ней загадывать не буду.
                Если захочешть посмотреть, какие команды я понимаю, напиши "help".
                Чтобы начать игру введи "start"!""";
    }

    public String getAnswer(String userMessage, String Id){
        User user = getUser(Id);
        if (userMessage.length() == 1){// проверяем, есть ли буква в загаданном слове, возвращает сообщение с ответом да/нет, + слово с отгаданными буквами
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

    public String start(User user){
        return "Давай прежде познакомимся!\n" +
                "Ввведи \"ID:\", а затем без пробелов свое имя.";
    }

    public String newWord(User dialog){
        HiddenWord hiddenWord = new HiddenWord(database.generateWord(database.wordsArray(fileName)));
        dialog.addHiddenWord(hiddenWord);
        String text = ""; // TODO: написать сообщение
        return text + hiddenWord.wordWithHiddenLetters();

    } // меняем поля в загаданном слове

    public String finish(User user){
        user.finishDialog();                  //сменяем флаг на неактивный диалог переключаясь на другой
        return "Пока, до новых встреч!";


    }

    public String youWin(User user){ //не нужно
        if (user.returnHiddenWord().isWordSolved()){
            return "Победа!\n" +
                    "Сыграем еще разок? Введи \"new word\"";
        }
        return "";
    }

    public String youLoss(User user){
        if (user.returnHiddenWord().mistake == 9){
            user.dialogState = false;
            return "К сожалению, ты проиграл. Сыграем еще раз?";
        }
        return "";
    }


    public String getAnswerOnLetter(char userMessage, User user){ //TODO:следует все сообщения тоже выделить в методы и уже возвращать методы
        String text;
        int attempts = 8 - user.returnHiddenWord().mistake;
        if (user.returnHiddenWord().isLetterFit(userMessage)) {
            if (user.returnHiddenWord().isWordSolved()) {
                return  user.returnHiddenWord().word +"\n"+
                        "Победа!\n" +
                        "Сыграем еще разок? Введи \"new word\"";
            }
                text = "Угадал! Есть такая буква.\n";

        } else  if (user.returnHiddenWord().mistake == 9){
            user.dialogState = false;
            return "К сожалению, ты проиграл. Сыграем еще раз?";
        }else {
            text = "Промах. Здесь нет буквы ”" + userMessage + "”.\n" +
                    "У тебя есть еще " + attempts + " попыток.\n";
        }
        return text + user.returnHiddenWord().wordWithHiddenLetters();
    }
    public User getUser(String Id){ //ф-я проверяет по id вел ли бот диалог с этим пользователем.
                                    // Если вел, возвращаем этого user, если нет - создаем новый user c данным id.
        for (int i = 0; i < ArrayUsers.size(); i++ ){
            if (ArrayUsers.get(i).returnId() == Id){
                return ArrayUsers.get(i);
            }
        }
        return new User(Id);
    }

    public boolean thereAreActiveUsers(){
        boolean flag = false;
        for (int i = 0; i < ArrayUsers.size(); i++ ){
            if (ArrayUsers.get(i).isActive()){
                flag = true;
            }
        }
        return flag;
    }
}
