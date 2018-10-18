package ServerPackage;

import Models.Constants;

import java.io.*;
import java.util.*;
import java.net.*;

public class Server {

    static Vector<ClientHandler> ar = new Vector<>();

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(Constants.SERVER_SOCKET);
        Socket s;
        while (true) {
            s = ss.accept();
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            ClientHandler mtch = new ClientHandler(s, ois, oos);
            Thread t = new Thread(mtch);
            t.start();
            while(true){
                if(mtch.name != null) break;
            }
            ar.add(mtch);
        }
    }
}
