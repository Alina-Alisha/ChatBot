package io.proj3ct.GameTGBot.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//TODO: добавить историю названных слов

public class CitiesGame {
    private Map<Character, ArrayList> citiesHashMap = new HashMap<>();
    //private String databaseFileName;
    private Database database;

    enum State {active, notActive, hintProcessing} // состояния игры

    private State state;
    private String city;

    public CitiesGame(Database database) {
        state = State.active;
        this.database = database;
        //ArrayList<String> wordsArray = database.returnCitiesArray();
        citiesHashMap = database.createMap();
    }

    public CitiesGame() {
        state = State.notActive;
    } //почему nonActive?

    public String getAnswer(String message, Database database) {
        switch (message) {
            case ("help"):
                return help();
            case ("finish"):
                return finish();
            case ("start"):
                return greeting();
            case ("hint"):
                hint(database);
                return null;
            default:
                break;
        }
        return getAnswerOnUsersCity(message);


    }

    public String start() {
        state = State.active;
        return greeting() + "\n" + generateRandomCity();

    }

    public String continueCitiesGame() {
        state = State.active;
        return generateRandomCity();

    }

    private String finish() {
        state = State.notActive;
        return "";  // TODO: написать сообщение
    }

    public String help() {
        return """
                Если ты забыл, как со мной общаться, вот команды, которые я понимаю:
                1) Введи "start cities game", чтобы начать игру с начала.
                2) Введи "hint", если не можешь придумать город на заданную букву, и получишь подсказку.
                3) Введи "finish", чтобы завершить игру
                """;
    }

    private String greeting() { //todo: поправить
        return """
                Правила просты. Вы называете город, я
                называю другой город на последнюю букву
                вашего. Мягкий знак и буква "ы" не
                считаются. Называем только города, деревни
                и даже поселки городского типа в игре не
                участвуют. В любой момент я могу дать вам
                подсказку или напомнить, на какую букву
                нужно ходить.
                """;
    }

    private void hint(Database database) { // ф-я запускает виселицу, где игрок должен отгадать город, начинающийся с определенной буквы
        state = State.hintProcessing;
    }
//TODO: разобраться с дублирующими методами endLetter и capEndLetter
    public char endLetter() { // ф-я выводит, город на какую букву должен назвать пользователь
        if (city.charAt(city.length() - 1) == 'ы' || city.charAt(city.length() - 1) == 'ь') {
            return city.charAt(city.length() - 2);
        }
        return city.charAt(city.length() - 1);
    }


    public char capEndLetter() { // ф-я выводит, город на какую букву должен назвать пользователь
        if (city.charAt(city.length() - 1) == 'ы' || city.charAt(city.length() - 1) == 'ь') {
            char letter = city.charAt(city.length() - 2);
            letter = String.valueOf(letter).toUpperCase().charAt(0);
            return letter;
        }
        char letter = city.charAt(city.length() - 1);
        letter = String.valueOf(letter).toUpperCase().charAt(0);
        return letter;
    }

    private String generateRandomCity() { // ф-я генерирует город
        int min = 0;//192;
        int max = 25;//223;
        Character[] alph = {'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н',
                'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я'};
        int randLetterIndex = (int) (Math.random() * ++max) + min;
        Character randLetter = alph[randLetterIndex];
        int cityIndex = citiesHashMap.get(randLetter).size();
        int randIndex = (int) (Math.random() * cityIndex);
        ArrayList cities = citiesHashMap.get(randLetter);
        city = (String) cities.get(randIndex);

        return city;
    }


    private String getAnswerOnUsersCity(String message) {// ф-ю, которая генерирует город на заданную букву
        String userCity = message;
        char firstLetter = message.charAt(0);
        firstLetter = String.valueOf(firstLetter).toLowerCase().charAt(0); //обратно в чар
        int c = 0;
        if (endLetter() != firstLetter) {
            return "Город должен начинаться с последней буквы моего города, придумай другой.";
        } else
            for (int i = 0; i < database.returnCitiesArray().size(); i++) {
                if (Objects.equals(database.returnCitiesArray().get(i), userCity))
                    break;
                else
                    c++;
            }
        if (c == database.returnCitiesArray().size()) {
            return "Такого города не существует";
        }
        {
            char lastLetter = message.charAt(message.length() - 1);
            lastLetter = String.valueOf(lastLetter).toUpperCase().charAt(0);
            int numOfSameLetterCities = citiesHashMap.get(lastLetter).size();
            int randIndex = (int) (Math.random() * numOfSameLetterCities);
            ArrayList cities = citiesHashMap.get(lastLetter);
            city = (String) cities.get(randIndex);

            return city;
        }
    }


    public void saveHistoryOfDialog(String message) {

    }


    public State returnCitiesGameState() {
        return state;
    }

    public void finishGame() {
        state = State.notActive;
    }

    public static State returnActiveState() {
        return State.active;
    }

    public static State returnHintState() {
        return State.hintProcessing;
    }



}
