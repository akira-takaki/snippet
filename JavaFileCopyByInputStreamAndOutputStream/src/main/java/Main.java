import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Date startDate = new Date();
        System.out.println(startDate.toString());

        final String SRC_FILE_NAME = "/Users/akira/Downloads/aaa.flv";
        final String DEST_FILE_NAME = "/Users/akira/Downloads/COPY_FILE";

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(SRC_FILE_NAME));
            outputStream = new BufferedOutputStream(new FileOutputStream(DEST_FILE_NAME));

            final int BUFFER_SIZE = 1024 * 1024;
            final byte[] buffer = new byte[BUFFER_SIZE];
            int readSize = -1;
            while (-1 != (readSize = inputStream.read(buffer))) {
                outputStream.write(buffer, 0, readSize);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Date endDate = new Date();
        System.out.println(endDate.toString());

        System.out.println(endDate.getTime() - startDate.getTime());
    }
}