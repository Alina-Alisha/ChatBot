public class BotLogic {
    public static String Help(){
        String help = ""; //TODO: написать help
        return help;
    }
    public static String Greеting() {
        String greeting = "Привет! Я Кiт, двай поиграем в виселицу. Попробуй отгадать мое слово.\n" +
                "Девять ошибок и ты проиграл!\n" +
                "Буквы \"е\" и \"ё\" считаются за одну.\n" +
                "буква \"ы\" мне не нравится, слов с ней загадывать не буду.\n" +
                "Если захочешть посмотреть, какие команды я понимаю, напиши \"help\".\n" +
                "Чтобы начать игру введи \"start\"!"; //TODO: написать greeting
        return greeting;
    }

    public static String GetAnswer(String userMessage, Dialog dialog){
        switch(userMessage){
            case("help"):
                return Help();
            break;
            case("start"):
                return Start(dialog);
            break;
            case("new word"):
                return NewWord(dialog);
            break;
            case("finish"):
                return Finish();
            break;

        }

        if (userMessage.length() == 1){// проверяем, есть ли буква в загаданном слове, возвращает сообщение с ответом да/нет,
            return ChekLetter(userMessage, dialog);      // если нет то + кол-во права на ошибку

        }

        if (userMessage.substring(0, 3) == "ID:"){
            dialog.AddId(userMessage);
            return NewWord(dialog);
        }


        // help // возвращаем текст справки
        // start Start() // начало диалога
        // letter Letter() // вводится буква для проверки
        // new word NewWord()
        // finish Finish()
        // change user ChangeUser()

    }

/*    public static String Start(Dialog dialog){//заводим загаданное слово, возвращает сообщение с загаданным словом
        HiddenWord hiddenWord = new HiddenWord(dialog.ReturnDatabase().GenerateWord());
        dialog.AddHiddenWord(hiddenWord.toString());
        String text = ""; // TODO: написать сообщение типа "вот я загадол новае слово"
        return text + hiddenWord.WordWithHiddenLetters();
    }
*/
    public static String Start(Dialog dialog){
        String text = "Даввай прежде познакомимся!\n" +
                "Ввведи \"ID:\", а затем без пробелов свое имя.\n";
        return text;

    }
    public static String NewWord(Dialog dialog){
        HiddenWord hiddenWord = new HiddenWord(dialog.ReturnDatabase().GenerateWord());
        dialog.AddHiddenWord(hiddenWord.toString());
        String text = ""; // TODO: написать сообщение
        return text + hiddenWord.WordWithHiddenLetters();


    } // меняем поля в загаданном слове
    public static String Finish(){
        // FinishDiolog
    } // flag = false
    /*
    public static String Bye(){} // возвращает сообщение с прощанием
    public static String Flag(){}

*/
    public static String YouWin(Dialog dialog){
        if (dialog.ReturnHiddenWord().IsWordSolved()){
            String text = "Победа!\n" +
                    "Сыграем еще разок? Введи \"new word\""
            return text
        }


    }

    public static String ChekLetter(String userMessage, Dialog dialog){

        String text;  // TODO: написать сообщение, не забыть про количество "права на ошибку"
        if (dialog.ReturnHiddenWord().ChekLetter(userMessage)) {

            if (dialog.ReturnHiddenWord().IsWordSolved()) {
                text = "Победа!\n" +
                        "Сыграем еще разок? Введи \"new word\"";
                return text;
                text = "Угадал! Есть такая буква.\n";
                return text + dialog.ReturnHiddenWord().WordWithHiddenLetters();
            } else {
                text = "Промах. Здесь нет буквы \\”" + userMessage + "\\”."; // TODO: написать сообщение, не забыть про количество "права на ошибку"
                return text + dialog.ReturnHiddenWord().WordWithHiddenLetters();
            }
        }

    }


}
