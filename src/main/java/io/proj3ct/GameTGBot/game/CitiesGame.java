package io.proj3ct.GameTGBot.game;

import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//TODO: добавить историю названных слов

public class CitiesGame {
    //private Map<Character, ArrayList> citiesHashMap = new HashMap<>();
    private Database database;

    private ArrayList<String> historyOfCities = new ArrayList<>();

    enum State {active, notActive, hintProcessing} // состояния игры

    private State state;
    //private String city;
    private City city;
    boolean isAnswerCity = false;
    private Map<Character, List<City>> citiesHashMap = new HashMap<>();

    public CitiesGame(Database database) {
        state = State.active;
        this.database = database;
        citiesHashMap = database.getCitiesHashMap();
    }

    public CitiesGame() {
        state = State.notActive;
    }

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

    public static List<KeyboardRow> KeyboardRowsForCity(){
        List<KeyboardRow> KeyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("help");
        row.add("hint");
        row.add("finish");
        KeyboardRows.add(row);
        return KeyboardRows;
    }


    public String start() {
        state = State.active;
        return greeting() + "\n" + generateRandomCity();

    }

    public String continueCitiesGame(String cityFromHint) {
        state = State.active;
        return getAnswerOnUsersCity(cityFromHint);

    }

    private String finish() {
        state = State.notActive;
        return "";  // TODO: написать сообщение
    }

    public String help() {
        return """
                Если ты забыл, как со мной общаться, вот команды, которые я понимаю:
                1) Введи "hint", если не можешь придумать город на заданную букву, и получишь подсказку.
                2) Введи "finish", чтобы завершить игру
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

    public char endLetter() { // ф-я выводит, город на какую букву должен назвать пользователь
        if (city.getName().charAt(city.getName().length() - 1) == 'ы' || city.getName().charAt(city.getName().length() - 1) == 'ь') {
            return city.getName().charAt(city.getName().length() - 2);
        }
        return city.getName().charAt(city.getName().length() - 1);
    }


    public char capEndLetter() { // ф-я выводит, город на какую букву должен назвать пользователь
        return String.valueOf(endLetter()).toUpperCase().charAt(0);
    }

    private String generateRandomCity() { // ф-я генерирует город
        int min = 0;
        int max = 25;
        Character[] alph = {'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н',
                'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я'};
        int randLetterIndex = (int) (Math.random() * ++max) + min;
        Character randLetter = alph[randLetterIndex];
        int cityIndex = citiesHashMap.get(randLetter).size();
        int randIndex = (int) (Math.random() * cityIndex);
        List<City> cities = citiesHashMap.get(randLetter);
        city = cities.get(randIndex);

        historyOfCities.add(city.getName());

        return city.getName() + "\n" + city.getInfo();
    }


    private String getAnswerOnUsersCity(String message) {// ф-ю, которая генерирует город на заданную букву
        String userCity = message;
        char firstLetter = message.charAt(0);
        firstLetter = String.valueOf(firstLetter).toLowerCase().charAt(0);
        int c = 0;
        if (endLetter() != firstLetter) {
            isAnswerCity = false;
            return "Город должен начинаться с последней буквы моего города, придумай другой.";
        } else

            for (int i = 0; i < database.getcitiesArray().size(); i++) {// ПЕРЕПИСАНО
                if (Objects.equals(database.getcitiesArray().get(i), userCity))// ПЕРЕПИСАНО
                    break;
                else
                    c++;
            }
        if (c == database.getcitiesArray().size()) {// ПЕРЕПИСАНО
            return "Такого города не существует";
        }

        for (int i = 0; i < historyOfCities.size(); i++) {
            if (Objects.equals(historyOfCities.get(i), message)) {
                isAnswerCity = false;
                return "Такой город уже был, придумай другой";
            }
        }

        historyOfCities.add(message);

        char lastLetter;

        if (message.charAt(message.length() - 1) == 'ы' || message.charAt(message.length() - 1) == 'ь') {
            lastLetter = message.charAt(message.length() - 2);
        } else
            lastLetter = message.charAt(message.length() - 1);

        lastLetter = String.valueOf(lastLetter).toUpperCase().charAt(0);
        int numOfSameLetterCities = citiesHashMap.get(lastLetter).size();
        int randIndex = (int) (Math.random() * numOfSameLetterCities);
        List<City> cities = citiesHashMap.get(lastLetter);
        city = cities.get(randIndex);

        historyOfCities.add(city.getName());

        isAnswerCity = true;

        //return city + "\n" + getCityInformation();
        return city.getName() + "\n" + city.getInfo();

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


    public void saveImage() {

        String link = city.getImage();
        try {
            URL url = new URL(link);
            BufferedImage image = ImageIO.read(url);
            String typ = "jpg";
            File f1 = new File("Images\\" + city.getName().toString() + ".jpg");
            ImageIO.write(image, typ, f1);
        } catch (IOException e) {
            System.out.println("Ссылка не найдена" + e.getMessage()); //TODO: поправить
        }
    }

    public InputFile getImageFile() {

        saveImage();
        File f = new File("Images\\" + city.getName().toString() + ".jpg");
        InputFile file = new InputFile(f, "Images\\" + city.getName().toString() + ".jpg");

        return file;

    }



}
