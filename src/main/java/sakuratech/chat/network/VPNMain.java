package sakuratech.chat.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class VPNMain implements IVpn{
    /**
     * 连接到特定频道
     *
     * @param port
     */
    @Override
    public String Connector(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                // 接受客户端连接
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // 读取客户端发送的消息
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String message = in.readLine();
                System.out.println("Received message from client: " + message);

                // 向客户端发送响应
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println("Message received: " + message);

                // 关闭连接
                clientSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error in server: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
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
