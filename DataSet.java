import java.io.*;
import java.util.*;

public class DataSet {
    private ArrayList<DataRow> rows;
    public int numIndepVars;

    public DataSet(String fileName) throws IOException {
        rows = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine(); // skip header
        line = br.readLine();
        if(line != null) {
            String[] parts = line.split(",");
            numIndepVars = parts.length - 1; // first column = y, rest = x
            do {
                double y = Double.parseDouble(parts[0].trim());
                double[] x = new double[numIndepVars];
                for(int i=0; i<numIndepVars; i++) {
                    x[i] = Double.parseDouble(parts[i+1].trim());
                }
                rows.add(new DataRow(y, x));
                line = br.readLine();
                if(line != null) parts = line.split(",");
            } while(line != null);
        }
        br.close();
    }

    public ArrayList<DataRow> getRows() {
        return rows;
    }
}

