public class BotLogic {

    //TODO:сделать поле базы данных
    public String fileName = "Words.txt";
    public Database database = new Database(fileName);

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

    public String getAnswer(String userMessage, User dialog, String fileName){

        if (userMessage.length() == 1){// проверяем, есть ли буква в загаданном слове, возвращает сообщение с ответом да/нет, + слово с отгаданными буквами
            char letter = userMessage.charAt(0);
            return getAnswerOnLetter(letter, dialog);      // если нет то + кол-во права на ошибку
        }

        if (userMessage.substring(0,3).equals("ID:") ){
            dialog.addId(userMessage);
            return newWord(dialog, fileName);
        }

        switch(userMessage){
            case("help"):
                return help();
            case("start"):
                return start(dialog);
            case("new word"):
                return newWord(dialog, fileName);
            case("finish"):
                return finish(dialog);
            default:
                break;
        }

        return help();

    }

    public String start(User user){
        return "Давай прежде познакомимся!\n" +
                "Ввведи \"ID:\", а затем без пробелов свое имя.";
    }

    public String newWord(User dialog, String fileName){
        HiddenWord hiddenWord = new HiddenWord(dialog.ReturnDatabase().generateWord(dialog.ReturnDatabase().wordsArray(fileName))); // В Generate передае массива слов
        dialog.addHiddenWord(hiddenWord);
        String text = ""; // TODO: написать сообщение
        return text + hiddenWord.wordWithHiddenLetters();

    } // меняем поля в загаданном слове

    public String finish(User user){
        user.finishDialog();                  //сменяем флаг на неактивный диалог переключаясь на другой
        return "Пока, до новых встреч!";


    }

    public String youWin(User user){
        if (user.returnHiddenWord().isWordSolved()){
            return "Победа!\n" +
                    "Сыграем еще разок? Введи \"new word\"";
        }
        return "";
    }

    //TODO: обработать проигрыш

    public String getAnswerOnLetter(char userMessage, User user){
        String text;  // TODO: написать сообщение, не забыть про количество "права на ошибку"
        if (user.returnHiddenWord().isLetterFit(userMessage)) {
            if (user.returnHiddenWord().isWordSolved()) {
                return  user.returnHiddenWord().word +"\n"+
                        "Победа!\n" +
                        "Сыграем еще разок? Введи \"new word\"";
            }
                text = "Угадал! Есть такая буква.\n";
        } else {
            text = "Промах. Здесь нет буквы \\”" + userMessage + "\\”.\n"; // TODO: написать сообщение,
                                                                            // не забыть про количество "права на ошибку"
        }
        return text + user.returnHiddenWord().wordWithHiddenLetters();
    }
}
