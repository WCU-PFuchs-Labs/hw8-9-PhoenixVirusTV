package tabular;

import java.io.*;
import java.util.*;

public class DataSet {

    private ArrayList<DataRow> rows;
    private int numIndepVars;

    public DataSet(String fileName) {
        rows = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String header = br.readLine();  // skip first line

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                double y = Double.parseDouble(parts[0]);

                double[] xvals = new double[parts.length - 1];
                for (int i = 1; i < parts.length; i++) {
                    xvals[i - 1] = Double.parseDouble(parts[i]);
                }

                rows.add(new DataRow(y, xvals));
                numIndepVars = xvals.length;
            }

            br.close();

        } catch (Exception e) {
            System.out.println("ERROR loading dataset: " + e);
        }
    }

    public int size() {
        return rows.size();
    }

    public DataRow getRow(int i) {
        return rows.get(i);
    }

    public int getNumIndepVars() {
        return numIndepVars;
    }
}
