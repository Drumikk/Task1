import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FileDependencyResolver {

   // private static LinkedList<String> sortedFiles = new LinkedList<>();
    public static void main(String[] args) {
        String rootFolder = "Root";
        File rootDirectory = new File(rootFolder);
        LinkedList <String> filesList = new LinkedList<>();
        LinkedList <String> dependencies = new LinkedList<>();

        if (rootDirectory.exists()&&rootDirectory.isDirectory())
        {
            searchFiles(rootDirectory, filesList);

        }
        System.out.println(rootDirectory.getName());
        System.out.println(filesList);

        extractDependencies(rootDirectory,filesList,dependencies);
        System.out.println(dependencies);
        LinkedHashSet<String > set = new LinkedHashSet<>(dependencies);
        dependencies.clear();
        dependencies.addAll(set);
        System.out.println(dependencies);
        concatenateFiles(dependencies);

    }

    private static void extractDependencies(File directory,List <String>filesList, List <String>dependencies){
        int count = 0;
        String root = directory.getName();
        for (String item :filesList){
            try (BufferedReader reader = new BufferedReader(new FileReader(filesList.get(count)))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Проверяем наличие директивы require и извлекаем путь к файлу
                    if (line.startsWith("require")) {
                        int startIndex = line.indexOf("'") + 1;
                        int endIndex = line.lastIndexOf("'");
                        if (startIndex != -1 && endIndex != -1) {
                            String dependencyPath = root+"/"+ line.substring(startIndex, endIndex);
                            dependencies.add(new File(dependencyPath).getAbsolutePath());
                        }
                    }
                }
                dependencies.add(new File(filesList.get(count)).getAbsolutePath());
                count++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        try (FileWriter outputWriter = new FileWriter("output")) {
            for (String file : files) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        outputWriter.write(line + System.lineSeparator());
                    }
                }
            }
            System.out.println("Конкатенация успешно выполнена. Результат в файле output");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
