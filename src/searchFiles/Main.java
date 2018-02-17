package searchFiles;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SearchFileVisitor sfv = new SearchFileVisitor();
        try(
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))
        ){
            System.out.println("Type the path to the directory");
            String directory = reader.readLine();

            System.out.println("Type the path to the output file");
            String file = reader.readLine();

            //walk all directories and files endsWith txt
            Files.walkFileTree(Paths.get(directory), sfv);

            List<Path> foundFiles = sfv.getFoundFiles();

            //sort by file name
            Collections.sort(foundFiles, new Comparator<Path>() {
                @Override
                public int compare(Path o1, Path o2) {
                    return o1.getFileName().toString().compareToIgnoreCase(o2.getFileName().toString());
                }
            });

            //write to file, all bytes from foundFiles
            for (Path p : foundFiles) {
                FileInputStream fis = new FileInputStream(p.toString());
                FileOutputStream fos = new FileOutputStream(file, true);
                write(fis,fos);
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void write(InputStream is, OutputStream os){
        try (
            BufferedInputStream bis = new BufferedInputStream(is);
            BufferedOutputStream bos = new BufferedOutputStream(os)
        ){
            byte[] buffer = new byte[1024];
            int length;
            while ((length = bis.read(buffer)) != -1){
                bos.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
