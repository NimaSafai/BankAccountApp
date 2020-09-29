package com.company;

import java.io.*;
import java.util.ArrayList;

public class CsvFileHandler {
    /**
     * Saves application data to file as CSV
     * @param dataArrayList Array of strings to be written to file.
     */
    public static void saveFile(ArrayList<String> dataArrayList, String filePath) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));
        for (String dataString : dataArrayList) {
            bufferedWriter.write(dataString);
        }
    }

    public static ArrayList<String[]> readFile(String file) {
        ArrayList<String[]> data = new ArrayList<>();
        String dataRow;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            while((dataRow = br.readLine()) != null) {
                String[] dataRecords = dataRow.split(",");
                data.add(dataRecords);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not find file!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Could not read file!");
            e.printStackTrace();
        }
        return data;
    }
}