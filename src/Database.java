import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Database {

    public static ArrayList<String> wordsArray(String fileName) {
        File file = new File(fileName);
        String fileContent = "";
        try (FileReader fr = new FileReader(file)) {
            char[] chars = new char[(int) file.length()];
            fr.read(chars);

            String words = new String(chars);
            fileContent = words;
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<String> dataBase = new ArrayList<>();  //создание массива слов с индексом
        String line = "";
        for (int i = 0; i < fileContent.length()-1; i++) {
            int c = 0;
            while (fileContent.charAt(i + c) != ' ') {
                line += fileContent.charAt(i + c);
                c++;
            }
            dataBase.add(line);
            line = "";
            i += c;
        }
        return dataBase;
    }

    public static String GenerateWord(ArrayList<String> wordsArray) { // ф-я генерирует рандомное слово, которое будет загадывать бот, передаем массив слов
        int randNumber = (int) ( Math.random() * wordsArray.size() );
        return wordsArray.get(randNumber);
    }

    Database(String fileName) {
        wordsArray(fileName);
    }
}