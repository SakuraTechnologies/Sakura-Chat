package sakuratech.chat.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import sakuratech.chat.crypt.Cryptor;

public class VPNMain extends Cryptor implements IVpn{
    /**
     * 连接到特定频道
     *
     * @param port
     */
    @Override
    public String Connector(int port) {
        String message = null;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                message = in.readLine();
                System.out.println("Received message from client: " + message);
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                Cryptor crypt;
                clientSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error in server: " + e.getMessage());
            e.printStackTrace();
        }
        return message;
    }

    /**
     * 频道掉线
     */
    @Override
    public void Disconnect() {

    }

    /**
     * 异常掉线
     */
    @Override
    public void DisconnectedException() {

    }

    /**
     * 状态
     */
    @Override
    public int State() {
        return 0;
    }
}
