import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * Created by savch on 14.06.2017.
 */
public class Server {

    public static void main(String[] args) throws IOException {
        int port = 1994;

        ServerSocket socket = new ServerSocket(port);
        System.out.println("Server started:");
        Socket clientSocket = socket.accept();       //This is blocking. It will wait.
        DataInputStream inStream = new DataInputStream(clientSocket.getInputStream());
        int length = inStream.readInt();
        String s = "";// read length of incoming message
        if(length>0) {
            byte[] message = new byte[length];
            inStream.readFully(message, 0, message.length); // read the message
            s = new String(message);
        }
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("C:\\test\\test.txt"));
            try {
                JSONObject jsonObj = new JSONObject(s);
                out.write(jsonObj.getString("image"));
                byte[] decodedString = new sun.misc.BASE64Decoder().decodeBuffer(jsonObj.getString("image"));
                File of = new File("C:\\test\\yourFile.jpeg");
                FileOutputStream osf = new FileOutputStream(of);
                osf.write(decodedString);
                osf.flush();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            out.close();
        }
        catch (IOException e)
        {
            System.out.println("Exception " + e);

        }

        System.out.println("Server stoped.");
        clientSocket.close();
        socket.close();
    }
}
