public class DataRow {
    private double y;
    private double[] xValues;
    public DataRow(double y, double[] xValues) { this.y = y; this.xValues = xValues; }
    public double getY() { return y; }
    public double[] getXValues() { return xValues; }
}