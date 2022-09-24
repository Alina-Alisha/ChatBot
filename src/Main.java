import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Database text = new Database("Database.txt");

        System.out.println(BotLogic.PrintHelp());// ф-я, которая возвращает строку со справкой
        Dialog dialog = new Dialog(); // ф-я, создающая объект класса Dialog

        Scanner console = new Scanner(System.in);

        while (dialog.IsActive()) {
            String userMessage = console.nextLine(); // читаем с консоли сообщение пользователя
            String botMessage = BotLogic.GetAnswer(userMessage, dialog); // генерим сообщение бота
            System.out.println(botMessage); // выводим сообщение в консоль
        }

    }
}