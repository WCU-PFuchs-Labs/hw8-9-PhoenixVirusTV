public class DataSet {
    private ArrayList<DataRow> rows;
    private int numIndepVars;

    public DataSet(String filename) throws IOException {
        // read CSV, populate rows, set numIndepVars
    }

    public int getNumIndepVars() { return numIndepVars; }
    public ArrayList<DataRow> getRows() { return rows; }
}
