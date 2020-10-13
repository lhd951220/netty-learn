import java.io.IOException;
import java.net.Socket;
import java.util.Date;

/**
 * @author Haidong Liu
 * @date 2020/10/13 14:26
 */
public class IOClient {
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Socket socket = new Socket("127.0.0.1", 8000);
                int i = 0;
                while (i < 5) {
                    i++;
                    try {
                        socket.getOutputStream().write((new Date() + ": hello world").getBytes());
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                while (true){}
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
