import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Date startDate = new Date();
        System.out.println(startDate.toString());

        final String SRC_FILE_NAME = "/Users/akira/Downloads/aaa.flv";
        final String DEST_FILE_NAME = "/Users/akira/Downloads/COPY_FILE";

        Path inputPath = FileSystems.getDefault().getPath(SRC_FILE_NAME);
        Path outputPath = FileSystems.getDefault().getPath(DEST_FILE_NAME);
        try {
            Files.copy(inputPath, outputPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Date endDate = new Date();
        System.out.println(endDate.toString());

        System.out.println(endDate.getTime() - startDate.getTime());
    }
}
