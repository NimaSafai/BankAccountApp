package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class csvReader {

    public static ArrayList<String[]> read(String file) {
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