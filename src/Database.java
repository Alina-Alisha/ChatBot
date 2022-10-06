import java.io.File;
import java.util.ArrayList;
import java.io.*;

public class Database {

    //TODO: сделать массив юзеров

    public static ArrayList<String> wordsArray(String fileName) {
        File file = new File(fileName);
        String fileContent = "";
        try (FileReader fr = new FileReader(file)) {
            char[] chars = new char[(int) file.length()];
            fr.read(chars);

            fileContent = new String(chars);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<String> dataBase = new ArrayList<>();  //создание массива слов с индексом
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < fileContent.length()-1; i++) {
            int c = 0;
            while (fileContent.charAt(i + c) != ' ') {
                line.append(fileContent.charAt(i + c));
                c++;
            }
            line.append(fileContent.charAt(i + c + 1));
            dataBase.add(line.toString());
            line = new StringBuilder();
            i += c;
        }
        return dataBase;
    }

    public static String generateWord(ArrayList<String> wordsArray) { // ф-я генерирует рандомное слово, которое будет загадывать бот, передаем массив слов
        int randNumber = (int) ( Math.random() * wordsArray.size() );
        return wordsArray.get(randNumber);
    }

    Database(String fileName) {
        wordsArray(fileName);
    }
}