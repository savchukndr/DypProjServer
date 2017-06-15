import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * Created by Andrii Savchuk on 14.06.2017.
 * All rights are reserved.
 * If you will have any cuastion, please
 * contact via email (savchukndr@gmail.com)
 */
public class Server {

    public static void main(String[] args) throws IOException {
        int port = 1994;

        ServerSocket socket = new ServerSocket(port);
        System.out.println("Server started:");
        System.out.println();
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
                if (!jsonObj.getString("image").equals("")) {
                    out.write(jsonObj.getString("image"));
                    byte[] decodedString = new sun.misc.BASE64Decoder().decodeBuffer(jsonObj.getString("image"));
                    File of = new File("C:\\test\\yourFile.jpeg");
                    FileOutputStream osf = new FileOutputStream(of);
                    osf.write(decodedString);
                    osf.flush();
                }
                System.out.println("login: " + jsonObj.getString("login"));
                System.out.println("name: " + jsonObj.getString("name"));
                System.out.println("localization: " + jsonObj.getString("localiztion"));
                if (jsonObj.getString("image").equals("")){
                    System.out.println("commente: no comment");
                }else {
                    System.out.println("comment: " + jsonObj.getString("comment"));
                }
                if (jsonObj.getString("image").equals("")){
                    System.out.println("image: no image");
                }else {
                    System.out.println("photo path: C:\\test\\yourFile.jpeg");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            out.close();
        }
        catch (IOException e)
        {
            System.out.println("Exception " + e);

        }

        System.out.println();
        System.out.println("Server stoped.");
        clientSocket.close();
        socket.close();
    }
}
