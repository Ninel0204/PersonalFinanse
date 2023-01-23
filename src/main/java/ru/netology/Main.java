package ru.netology;

import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        File tsvFile = new File("categories.tsv");
        File dataFile = new File("data.bin");
        List<String[]> listProducts = new ArrayList<>();
        String category;

        Map<String, String> mapFromFile = Manager.readTSV(tsvFile);
        Set<String> categorySet = new HashSet<>(mapFromFile.values());
        categorySet.add("другое");
        Set<String> dateSet = new HashSet<>();

        if (dataFile.exists()) {
            listProducts = Manager.loadAllDataFromBinFile(dataFile);
            for (String[] list : listProducts) {
                dateSet.add(list[2]);
            }
        }


        try (ServerSocket serverSocket = new ServerSocket(8989)) {
            System.out.println("Сервер стартовал");
            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream())
                ) {

                    JSONObject js = new JSONObject(in.readLine());
                    String title = (String) js.get("title");
                    String date = (String) js.get("date");
                    String sum = String.valueOf(js.get("sum"));

                    category = mapFromFile.getOrDefault(title, "другое");


                    String[] string = new String[]{title, category, date, sum};
                    listProducts.add(string);
                    dateSet.add(string[2]);

                    Map<String, String> maxCategory = HandlerImpl.category(listProducts, categorySet);
                    Map<String, String> maxYearCategory = HandlerImpl.periodCategory(listProducts, categorySet, dateSet, 4);
                    Map<String, String> maxMonthCategory = HandlerImpl.periodCategory(listProducts, categorySet, dateSet, 7);
                    Map<String, String> maxDayCategory = HandlerImpl.periodCategory(listProducts, categorySet, dateSet, 10);



                    JSONObject jsonMax = Manager.makeJson(maxCategory, maxYearCategory, maxMonthCategory, maxDayCategory);
                    Manager.saveAllDataToBinFile(dataFile, listProducts);
                    out.println(jsonMax);
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}



