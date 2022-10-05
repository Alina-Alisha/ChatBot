import java.util.Scanner;
public class Main {
    public static void main(String[] args) {


        BotLogic botLogic = new BotLogic();
        User user = new User();

        System.out.println(botLogic.greeting());// ф-я, которая возвращает строку с приветствием
        Scanner console = new Scanner(System.in);

        while (user.isActive()) {
            String userMessage = console.nextLine(); // читаем с консоли сообщение пользователя
            String botMessage = botLogic.getAnswer(userMessage, user); // генерим сообщение бота
            System.out.println(botMessage); // выводим сообщение в консоль
        }

    }
}

