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


    public static JSONObject makeJson(Map<String, String> maxCategory, Map<String, String> maxYearCategory,
                                      Map<String, String> maxMonthCategory, Map<String, String> maxDayCategory) {

        JSONObject jsoMaxCat = new JSONObject();
        jsoMaxCat.put("category", maxCategory.get("category"));
        jsoMaxCat.put("sum", Integer.parseInt(maxCategory.get("sum")));
        JSONObject jsoMaxYearCat  = new JSONObject();
        jsoMaxYearCat.put("category", maxYearCategory.get("category"));
        jsoMaxYearCat.put("sum", Integer.parseInt(maxYearCategory.get("sum")));

        JSONObject jsoMaxMonthCat = new JSONObject();
        jsoMaxMonthCat.put("category", maxMonthCategory.get("category"));
        jsoMaxMonthCat.put("sum", Integer.parseInt(maxMonthCategory.get("sum")));

        JSONObject jsoMaxDayCat = new JSONObject();
        jsoMaxDayCat.put("category", maxDayCategory.get("category"));
        jsoMaxDayCat.put("category", maxDayCategory.get("category"));
        jsoMaxDayCat.put("sum", Integer.parseInt(maxDayCategory.get("sum")));

        JSONObject jsoResult = new JSONObject();
        jsoResult.put("maxCategory", jsoMaxCat);
        jsoResult.put("maxYearCategory", jsoMaxYearCat);
        jsoResult.put("maxMonthCategory", jsoMaxMonthCat);
        jsoResult.put("maxDayCategory", jsoMaxDayCat);

        return jsoResult;
    }


    public static void saveAllDataToBinFile(File dataFile,  List<String[]> listProducts) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            oos.writeObject(listProducts);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public static List<String[]> loadAllDataFromBinFile(File dataFile) {
        List<String[]> listProducts = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFile))) {
            listProducts = (List<String[]>)  ois.readObject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return listProducts;
    }

}