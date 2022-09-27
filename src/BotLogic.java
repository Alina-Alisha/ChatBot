public class BotLogic {
    public static String Help(){
        String help = ""; //TODO: написать help
        return help;
    }
    public static String Greеting() {
        String greeting = ""; //TODO: написать greeting
        return greeting;
    }

    public static String GetAnswer(String userMessage, Dialog dialog){
        if (userMessage.length() == 1){// проверяем, есть ли буква в загаданном слове, возвращает сообщение с ответом да/нет,
                                        // если нет то + кол-во права на ошибку
            if (dialog.ReturnHiddenWord().ChekLetter(userMessage)){
                String text = ""; // TODO: написать сообщение, не забыть про количество "права на ошибку"
                return text + dialog.ReturnHiddenWord().WordWithHiddenLetters();
            }
            else {
                String text = ""; // TODO: написать сообщение, не забыть про количество "права на ошибку"
                return text + dialog.ReturnHiddenWord().WordWithHiddenLetters();
            }
        }

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
        // help // возвращаем текст справки
        // start Start() // начало диалога
        // letter Letter() // вводится буква для проверки
        // new word NewWord()
        // finish Finish()
        // change user ChangeUser()

    }

    public static String Start(Dialog dialog){//заводим загаданное слово, возвращает сообщение с загаданным словом
        HiddenWord hiddenWord = new HiddenWord(dialog.ReturnDatabase().GenerateWord());
        dialog.AddHiddenWord(hiddenWord.toString());
        String text = ""; // TODO: написать сообщение типа "вот я загадол новае слово"
        return text + hiddenWord.WordWithHiddenLetters();
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
    public static String Bye(){} // возвращает сообщение с прощанием
    public static String Flag(){}




}
