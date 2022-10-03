public class BotLogic { //переименовать методы с маленькой буквы

    //TODO:сделать поле базы данных

    public static String Help(){
        return "Если ты,прекрасный человек, забыл как со мной общаться, то вот команда эксклюзивно для тебя:\n"+
                    "1)Введи \"start\" и ты начнёшь, остальное узнаешь там) ";

    }
    public static String Greеting() {
        String greeting = "Привет! Я Кiт, двай поиграем в виселицу. Попробуй отгадать мое слово.\n" +
                "Девять ошибок и ты проиграл!\n" +
                "Буквы \"е\" и \"ё\" считаются за одну.\n" +
                "буква \"ы\" мне не нравится, слов с ней загадывать не буду.\n" +
                "Если захочешть посмотреть, какие команды я понимаю, напиши \"help\".\n" +
                "Чтобы начать игру введи \"start\"!";
        return greeting;
    }

    public static String GetAnswer(String userMessage, Dialog dialog, String fileName){

        if (userMessage.length() == 1){// проверяем, есть ли буква в загаданном слове, возвращает сообщение с ответом да/нет, + слово с отгаданными буквами
            char letter = userMessage.charAt(0);
            return ChekLetter(letter, dialog);      // если нет то + кол-во права на ошибку
        }

        if (userMessage.substring(0,3).equals("ID:") ){
            dialog.AddId(userMessage);
            return NewWord(dialog, fileName);  //? почему то не обрабатывает и не возвращает а переходит к Help(), После ввода ID выводится хелп
        }

        switch(userMessage){
            case("help"):
                return Help();
            case("start"):
                return Start(dialog);
            case("new word"):
                return NewWord(dialog, fileName);
            case("finish"):
                return Finish(dialog);
            default:
                break;
        }

        return Help();

    }

    public static String Start(Dialog dialog){
        String text = "Давай прежде познакомимся!\n" +
                "Ввведи \"ID:\", а затем без пробелов свое имя.";
        return text;

    }
    public static String NewWord(Dialog dialog, String fileName){ //изменила чтоб работало с базой данных
        HiddenWord hiddenWord = new HiddenWord(dialog.ReturnDatabase().GenerateWord(dialog.ReturnDatabase().wordsArray(fileName))); // В Generate передае массива слов
        //HiddenWord hiddenWord = new HiddenWord("cat");
        //dialog.AddHiddenWord(hiddenWord.toString());  //? зачем ту стринг? учесть что я изменила стринг в классе диалог на хидден ворд
        dialog.AddHiddenWord(hiddenWord);
        String text = ""; // TODO: написать сообщение
        return text + hiddenWord.WordWithHiddenLetters();

    } // меняем поля в загаданном слове

    public static String Finish(Dialog dialog){
        dialog.FinishDialog();                  //сменяем флаг на неактивный диалог переключаясь на другой
        String text = "Пока, до новых встреч!";
        return text;

    }

    public static String YouWin(Dialog dialog){
        if (dialog.ReturnHiddenWord().IsWordSolved()){
            String text = "Победа!\n" +
                    "Сыграем еще разок? Введи \"new word\"";
            return text;
        }
        return "";
    }

    //TODO: обработать проигрыш

    public static String ChekLetter(char userMessage, Dialog dialog){
        String text;  // TODO: написать сообщение, не забыть про количество "права на ошибку"
        if (dialog.ReturnHiddenWord().CheckLetter(userMessage)) {

            if (dialog.ReturnHiddenWord().IsWordSolved()) {
                text = "Победа!\n" +
                        "Сыграем еще разок? Введи \"new word\"";
                return text;
            }
                text = "Угадал! Есть такая буква.\n";
                return text + dialog.ReturnHiddenWord().WordWithHiddenLetters();
        } else {
            text = "Промах. Здесь нет буквы \\”" + userMessage + "\\”."; // TODO: написать сообщение,
                                                                            // не забыть про количество "права на ошибку"
            return text + dialog.ReturnHiddenWord().WordWithHiddenLetters();
        }
    }
}
