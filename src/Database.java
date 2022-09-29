import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Database {

    public static ArrayList<String> wordsArray(String file) throws FileNotFoundException { // создание массива слов

        Scanner sc = new Scanner(new File(file)); // присвоение содержимого файла строке
        String fileContent = "";
        while (sc.hasNextLine()) {
            fileContent = sc.nextLine();
        }

        ArrayList<String> dataBase = new ArrayList<>();  //создание массива слов с индексом
        String line = ""; //переменная для набора слова до пробела
        int k = 0;
        for (int i = 0; i < fileContent.length(); i++) {
            int c = 0;
            while (fileContent.charAt(i + c) != ' ') {
                c++;
                line += fileContent.charAt(i);
            }
            dataBase.add(line); //добавление слова в массив
            line = "";
            i += c + 1;
        }
        return dataBase;
    }

    public static String GenerateWord(ArrayList<String> wordsArray) { // ф-я генерирует рандомное слово, которое будет загадывать бот, передаем массив слов
        int randNumber = (int) ( Math.random() * wordsArray.size() );
        return wordsArray.get(randNumber);
    }

    Database(String fileName) throws FileNotFoundException {
        wordsArray(fileName);
    }
}