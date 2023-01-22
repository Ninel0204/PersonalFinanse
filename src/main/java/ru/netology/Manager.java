package ru.netology;

import org.json.JSONObject;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Manager {

    static Map<String, String> readTSV(File tsv) {
        Map<String, String> goodsByCategory = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(tsv))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                goodsByCategory.put(parts[0], parts[1]);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return goodsByCategory;
    }


    public static JSONObject makeJson(Map<String, Integer> mapCosts) {

        String[] maxCategory = HandlerImpl.category(mapCosts);
        JSONObject jsonMaxInner = new JSONObject();
        jsonMaxInner.put("category", maxCategory[0]);
        jsonMaxInner.put("sum", Integer.parseInt(maxCategory[1]));
        JSONObject jsonMax = new JSONObject();
        jsonMax.put("maxCategory", jsonMaxInner);
        return jsonMax;
    }

    public static void saveAllDataToBinFile(File dataFile, Map<String, Integer> mapCosts) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            oos.writeObject(mapCosts);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public static Map<String, Integer> loadAllDataFromBinFile(File dataFile) {
        Map<String, Integer> map = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFile))) {
            map = ( Map<String, Integer>)  ois.readObject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return map;
    }

}