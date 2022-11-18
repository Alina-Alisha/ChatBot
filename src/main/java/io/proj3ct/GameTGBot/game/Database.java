package io.proj3ct.GameTGBot.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Database {
    private String fileNameWords;
    private String fileNameCities;
    private ArrayList<String> wordsArray;
    private ArrayList<String> citiesArray;
    private Map<Character, ArrayList> citiesHashMap = new HashMap<>();
    Database(String fileNameWords, String fileNameCities) {
        this.fileNameWords = fileNameWords;
        this.fileNameCities = fileNameCities;
        wordsArray = createArray(fileNameWords);
        citiesHashMap = createMap();
    }

    private ArrayList<String> createArray(String fileName)  {

        String fileContent = "";

        try(Reader reader = new FileReader(fileName)){
        BufferedReader buffReader = new BufferedReader(reader);

            while (buffReader.ready()) {
                fileContent += buffReader.readLine();
            }

            reader.close();
            buffReader.close();
        } catch (IOException e) { //TODO: исправить
            e.printStackTrace();
       }

        ArrayList<String> dataBase = new ArrayList<>();
        String str = fileContent;
        String[] words = str.split(" ");
        dataBase.addAll(Arrays.asList(words));

        return dataBase;
    }

    public ArrayList<String> returnWordsArray(){
        return wordsArray;
    }
    public ArrayList<String> returnCitiesArray(){
        return citiesArray;
    }
    public Map<Character, ArrayList> createMap() { // ф-я создает HashMap, где ключ - первая буква слова, значение - ArrayList с городами на эту букву
        Character[] alphabet = {'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н',
                'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я' };
        Map<Character, ArrayList> words = new HashMap<>();
        citiesArray = createArray(fileNameCities);
        for (int i = 0; i < alphabet.length; i++) {
            ArrayList<String> sameFirstLetter = new ArrayList<>();
            for (int j = 0; j < citiesArray.size(); j++) {
                if (alphabet[i] == citiesArray.get(j).charAt(0)) {
                    sameFirstLetter.add(citiesArray.get(j));
                }
            }
            words.put(alphabet[i], sameFirstLetter);
        }
        return words;
    }

    public HashMap getHashMap(){
        return (HashMap) citiesHashMap;
    }

}