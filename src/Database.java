import java.util.ArrayList;
import java.io.*;
import java.util.Arrays;

public class Database {
    private String fileName;
    Database(String fileName) {
        this.fileName = fileName;
    }

    public ArrayList<String> words()  {

        String fileContent = "";

        try(Reader reader = new FileReader(fileName)){
        BufferedReader buffReader = new BufferedReader(reader);

            while (buffReader.ready()) {
                fileContent += buffReader.readLine();
            }

            reader.close();
            buffReader.close();
        } catch (IOException e) {
            e.printStackTrace();
       }

        ArrayList<String> dataBase = new ArrayList<>();
        String str = fileContent;
        String[] words = str.split(" ");
        dataBase.addAll(Arrays.asList(words));

        return dataBase;
    }

}