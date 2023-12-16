import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FileDependencyResolver {

   // private static LinkedList<String> sortedFiles = new LinkedList<>();

    public static void main(String[] args) {
        String rootFolder = "Root";
        File rootDirectory = new File(rootFolder);
        LinkedList <String> filesList = new LinkedList<>();

        if (rootDirectory.exists()&&rootDirectory.isDirectory())
        {
            searchFiles(rootDirectory, filesList);
        }


            concatenateFiles(filesList);

    }
    private static void searchFiles(File directory, List<String>fileList)
    {
        File [] files = directory.listFiles();

        if (files !=null)
        //Обход всех папок и файлов
        {
            for (File file: files){
                if (file.isDirectory()){
                    searchFiles(file,fileList);
                }else {
                    fileList.add(file.getAbsolutePath());
                }
            }
        }
    }

    private static void concatenateFiles(List<String> files) {
        try (FileWriter outputWriter = new FileWriter("output.txt")) {
            for (String file : files) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        outputWriter.write(line + System.lineSeparator());
                    }
                }
            }
            System.out.println("Конкатенация успешно выполнена. Результат в файле output.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
