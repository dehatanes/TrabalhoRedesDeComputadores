package ServerPackage;

import Models.Constants;
import Models.GameModel;

import java.io.*;
import java.util.*;
import java.net.*;

public class Server {

    static Map<String, ClientHandler> activeClients = new HashMap<String, ClientHandler>();
    static Map<String, GameModel> activeGames = new HashMap<String, GameModel>();

    public static void main(String[] args) throws IOException {
        ////////////////////
        //  SERVER STARTER 
        ////////////////////
        ServerSocket ss = new ServerSocket(Constants.SERVER_SOCKET);
        System.out.println("THE SERVER IS ALIVE!\n");
        // Aguarda alguém se conectar. A execução do servidor
        // fica bloqueada na chamada do método accept da classe
        // ServerSocket. Quando alguém se conectar ao servidor, o
        // método desbloqueia e retorna com um objeto da classe
        // Socket, que é uma porta da comunicação.
        System.out.println("Aguardando conexao de clientes...\n");   
        Socket cliente;
        while (true) {
            cliente = ss.accept();
            ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());
            ClientHandler handler = new ClientHandler(cliente, ois, oos);
            // Cria uma thread do servidor para tratar a conexão  
            Thread t = new Thread(handler);
            t.start();
        }
    }

    ////////////////////
    //    SERVICES 
    ////////////////////

    // MANAGING CLIENTS AND USERS

    public static boolean loginNewClient(String clientName, ClientHandler handler){
        if(activeClients.containsKey(clientName)) {
            System.out.println("Tentativa de conexao do cliente: " + clientName + ". Porem usuario ja esta em uso.");
            return false;
        } else {
            System.out.println("Novo cliente adicionado: " + clientName);
            activeClients.put(clientName, handler);
            return true;  
        }
    }

    public static boolean removeClient(String clientName){
        if(activeClients.containsKey(clientName)) {
            System.out.println("Cliente desconectado: " + clientName);
            activeClients.remove(clientName);
            return true;
        } else {
            System.out.println("Cliente: " + clientName + " nao encontrado para logout...");
            return false;
        }
    }

    // MANAGING GAME MATCHES
}
