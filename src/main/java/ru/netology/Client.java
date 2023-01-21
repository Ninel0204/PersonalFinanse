package ru.netology;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Client {
    public static void main(String[] args) {
        try (Socket serverSocket = new Socket("localhost", 8989);
             PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()))) {
            JSONObject request = new JSONObject("{\"title\": \"булка\", \"date\": \"2022.02.08\", \"sum\": 200}");
            out.println(request);
            System.out.println(in.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

