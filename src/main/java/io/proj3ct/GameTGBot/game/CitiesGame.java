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
    private Map<Character, ArrayList> citiesHashMap = new HashMap<>();
    private Database database;

    private ArrayList<String> historyOfCities = new ArrayList<>();

    enum State {active, notActive, hintProcessing} // состояния игры

    private State state;
    private String city;
    boolean isAnswerCity = false;

    public CitiesGame(Database database) {
        state = State.active;
        this.database = database;
        citiesHashMap = database.createMap();
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
        if (city.charAt(city.length() - 1) == 'ы' || city.charAt(city.length() - 1) == 'ь') {
            return city.charAt(city.length() - 2);
        }
        return city.charAt(city.length() - 1);
    }


    public char capEndLetter() { // ф-я выводит, город на какую букву должен назвать пользователь
        return String.valueOf(endLetter()).toUpperCase().charAt(0);
    }

    private String generateRandomCity() { // ф-я генерирует город
        int min = 0;
        int max = 25;
        Character[] alph = {'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н',
                'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Э', 'Ю', 'Я'};
        int randLetterIndex = (int) (Math.random() * ++max) + min;
        Character randLetter = alph[randLetterIndex];
        int cityIndex = citiesHashMap.get(randLetter).size();
        int randIndex = (int) (Math.random() * cityIndex);
        ArrayList cities = citiesHashMap.get(randLetter);
        city = (String) cities.get(randIndex);

        historyOfCities.add(city);

        isAnswerCity = true;

        return city;
    }


    private String getAnswerOnUsersCity(String message) {// ф-ю, которая генерирует город на заданную букву
        char firstLetter = message.charAt(0);
        firstLetter = String.valueOf(firstLetter).toLowerCase().charAt(0);
        int c = 0;
        if (endLetter() != firstLetter) {
            isAnswerCity = false;
            return "Город должен начинаться с последней буквы моего города, придумай другой.";
        } else
            for (int i = 0; i < database.returnCitiesArray().size(); i++) {
                if (Objects.equals(database.returnCitiesArray().get(i), message))
                    break;
                else
                    c++;
            }
        if (c == database.returnCitiesArray().size()) {
            isAnswerCity = false;
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
        ArrayList cities = citiesHashMap.get(lastLetter);
        city = (String) cities.get(randIndex);

        historyOfCities.add(city);

        isAnswerCity = true;

        return city + "\n" + getCityInformation();

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


    //методы для картинки и информации о городе

    public String getHTMLContent() {
        String htmlPageContent = "";

        String wikiLink = "https://ru.wikipedia.org/wiki/";

        try {
            URL oracle = new URL(wikiLink + city);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(oracle.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                htmlPageContent += inputLine;
            in.close();
        } catch (IOException e) {
            System.out.println("Ссылка не найдена" + e.getMessage());
        }

        return htmlPageContent;
    }


    public String getCityInformation() {

        String htmlPageContent = getHTMLContent();


        Pattern p = Pattern.compile("<p><b>(.*?)</p>");  //первый абзац википедиии html
        Matcher m = p.matcher(htmlPageContent);
        String text = "";
        String cityInformation = "";
        if (m.find())
            text += m.group().subSequence(1, m.group().length() - 1);


        Pattern rus = Pattern.compile((">(.*?)<")); //выделение текста из html фрагмента
        Matcher match = rus.matcher(text);
        while (match.find())
            cityInformation += match.group().subSequence(1, match.group().length() - 1);


        Pattern del = Pattern.compile(("\\d*&(.*?);"));  //удаление лишних остатков
        Matcher matcher = del.matcher(cityInformation);
        while (matcher.find())
            cityInformation = cityInformation.replace(matcher.group(), "");


        return cityInformation;
    }


    public String getImageCityLink() {

        String htmlPageContent = getHTMLContent();

        Pattern link = Pattern.compile("<meta property=\"og:image\" content=\"(.*?)\"/>");
        Matcher match = link.matcher(htmlPageContent);

        String imageLink = "";

        if (match.find())
            imageLink = (String) match.group().subSequence(35, match.group().length() - 3);


        return imageLink;
    }

    public void saveImage() {

        String link = getImageCityLink();
        try {
            URL url = new URL(link);
            BufferedImage image = ImageIO.read(url);
            String typ = "jpg";
            File f1 = new File("Images\\" + city + ".jpg");
            ImageIO.write(image, typ, f1);
        } catch (IOException e) {
            System.out.println("Ссылка не найдена" + e.getMessage()); //TODO: поправить
        }
    }

    public InputFile getImageFile() {

        saveImage();
        File f = new File("Images\\" + city + ".jpg");
        InputFile file = new InputFile(f, "Images\\" + city + ".jpg");

        return file;

    }

}
