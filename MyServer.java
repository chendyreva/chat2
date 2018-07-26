package ru.geekbrains.homework7;

import ru.geekbrains.homework7.Server.service.AuthService;
import ru.geekbrains.homework7.Server.service.BaseAuthService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer {
    private ServerSocket server;
    private List<ClientHandler> clients;
    private List<ClientHandler> nickMsg;
    private AuthService authService;

    public AuthService getAuthService() {
        return authService;
    }

    private final int PORT = 1234;

    public static void main(String[] args) {
        new MyServer();
    }

    public MyServer() {
        try {
            server = new ServerSocket(PORT);
            Socket socket = null;
            authService = new BaseAuthService();
            authService.start();
            nickMsg = new ArrayList<>();

                clients = new ArrayList<>();
            while (true) {
                System.out.println("Сервер ожидает подключения");
                socket = server.accept();
                System.out.println("Клиент подключился");
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при работе сервера");
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            authService.stop();
        }
    }

    public synchronized boolean isNickBusy(String nick) {
        for (ClientHandler o : clients) {
            if (o.getName().equals(nick)) return true;
        }
        return false;
    }

      public synchronized void broadcastMsg(String msg) {
        for (ClientHandler o : clients) {
            o.sendMsg(msg);

        }
    }

    public synchronized void privateMsg(String msg) {
        for (ClientHandler o : nickMsg) {
            o.sendMsg(msg);

        }
    }

    public synchronized void unsubscribe(ClientHandler o) {
        clients.remove(o);
    }

    public synchronized void subscribe(ClientHandler o) {
        clients.add(o);
    }

}

