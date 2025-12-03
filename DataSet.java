import java.io.*;
import java.util.*;

public class DataSet {
    private ArrayList<DataRow> rows;
    private int numIndepVars;

    public DataSet(String filename) {
        rows = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            String line = br.readLine(); // header (y,x0,x1,...)
            String[] header = line.split(",");
            numIndepVars = header.length - 1;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");

                double y = Double.parseDouble(parts[0]);
                double[] x = new double[numIndepVars];
                for (int i = 0; i < numIndepVars; i++) {
                    x[i] = Double.parseDouble(parts[i + 1]);
                }

                rows.add(new DataRow(y, x));
            }

        } catch (Exception e) {
            System.out.println("Error reading dataset: " + e);
        }
    }

    public int size() { return rows.size(); }
    public int getNumIndepVars() { return numIndepVars; }
    public DataRow getRow(int i) { return rows.get(i); }
}
