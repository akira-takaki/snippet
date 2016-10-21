import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Date startDate = new Date();
        System.out.println(startDate.toString());

        final String SRC_FILE_NAME = "/Users/akira/Downloads/aaa.flv";
        final String DEST_FILE_NAME = "/Users/akira/Downloads/COPY_FILE";

        try (
            FileChannel inputFileChannel = (new FileInputStream(SRC_FILE_NAME)).getChannel();
            FileChannel outputFileChannel = (new FileOutputStream(DEST_FILE_NAME)).getChannel();
        ) {
            inputFileChannel.transferTo(0, inputFileChannel.size(), outputFileChannel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Date endDate = new Date();
        System.out.println(endDate.toString());

        System.out.println(endDate.getTime() - startDate.getTime());
    }
}
