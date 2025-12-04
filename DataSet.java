import java.io.*;
import java.util.*;

public class DataSet {
    private List<double[]> inputs = new ArrayList<>();
    private List<Double> outputs = new ArrayList<>();
    private int numIndep;

    public DataSet(String fileName) {
        loadCSV(fileName);
    }

    private void loadCSV(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean first = true;

            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split(",");

                if (first) {
                    numIndep = parts.length - 1;
                    first = false;
                }

                double[] in = new double[numIndep];
                for (int i = 0; i < numIndep; i++) {
                    in[i] = Double.parseDouble(parts[i]);
                }

                double out = Double.parseDouble(parts[numIndep]);

                inputs.add(in);
                outputs.add(out);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error loading CSV: " + e.getMessage());
        }
    }

    public int getNumIndep() {
        return numIndep;
    }

    public int size() {
        return inputs.size();
    }

    public double[] getInput(int i) {
        return inputs.get(i);
    }

    public double getOutput(int i) {
        return outputs.get(i);
    }
}
