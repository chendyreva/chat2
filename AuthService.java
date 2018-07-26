package ru.geekbrains.homework7.Server.service;

public interface AuthService {

    void start();

    String getNickByLoginPass(String login, String pass);

    void stop();

}
