import java.util.ArrayList;

public class DataSet {

    private ArrayList<DataRow> rows;

    public DataSet(String fileName) {
        rows = new ArrayList<>();
        loadCSV(fileName);
    }

    private void loadCSV(String fileName) {
        try {
            java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(fileName));

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                double[] inputs = new double[parts.length - 1];

                for (int i = 0; i < parts.length - 1; i++) {
                    inputs[i] = Double.parseDouble(parts[i]);
                }

                double target = Double.parseDouble(parts[parts.length - 1]);

                rows.add(new DataRow(inputs, target));
            }

            br.close();
        } catch (Exception e) {
            System.out.println("Error loading CSV: " + e);
        }
    }

    public int size() {
        return rows.size();
    }

    public DataRow getRow(int i) {
        return rows.get(i);
    }

    public ArrayList<DataRow> getRows() {
        return rows;
    }
}
