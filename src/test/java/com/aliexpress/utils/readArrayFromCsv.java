package com.aliexpress.utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class readArrayFromCsv {
    public static void main(String[] args) throws IOException, CsvValidationException {
        String filePath = "LocalizationButtonsData.csv";

        CSVReader csvReader = new CSVReader(new FileReader(filePath));
        List<String[]> csvList = new ArrayList<>();

        String[] row;
        while ((row = csvReader.readNext()) != null) {
            String[] newRow = new String[row.length - 1];
            for (int i = 1; i < row.length; i++) {
                newRow[i - 1] = row[i];
            }
            csvList.add(newRow);
        }

        csvReader.close();

        System.out.println(csvList);
    }
}

//In this modified example, we create a new array for each row that has one less column than the original row. We then copy all but
// the first column of the original row into the new row. Finally, we add the new row to the list of rows.
//
//        Note that this assumes that all rows have the same number of columns. If some rows have fewer
//        columns than others, you'll need to modify the code to handle this case.
