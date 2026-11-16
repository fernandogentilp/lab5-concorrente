import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.*;

public class Sum{

    public static int sum(FileInputStream fis) throws IOException {
        Semaphore sem = new Semaphore(1);
	    int byteRead;
        int sum = 0;
        sem.acquire();
        try {
            while ((byteRead = fis.read()) != -1) {
        	    sum += byteRead;
            }
        }
        finally {
            sem.release();
            return sum;
        }
        
    }
    
    public static long sum(String path) throws Exception {
        Thread.sleep(1000); // emulating an additional delay of 1 second
        Path filePath = Paths.get(path);
        if (Files.isRegularFile(filePath)) {
       	    FileInputStream fis = new FileInputStream(filePath.toString());
            return sum(fis);
        } else {
            throw new RuntimeException("Non-regular file: " + path);
        }
    }

    public static void main(String[] args) throws Exception {

        if (args.length < 1) {
            System.err.println("Usage: java Sum filepath1 filepath2 filepathN");
            System.exit(1);
        }

	//many exceptions could be thrown here. we don't care
        for (String path : args) {
            long sum = sum(path);
            System.out.println(path + " : " + sum);
        }
    }
}
