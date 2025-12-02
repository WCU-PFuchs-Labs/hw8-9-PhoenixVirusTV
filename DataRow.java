public class DataRow {
    private double[] values;
    private double target;

    public DataRow(double[] values, double target) {
        this.values = values;
        this.target = target;
    }

    public double getValue(int i) {
        return values[i];
    }

    public double getTarget() {
        return target;
    }

    public int size() {
        return values.length;
    }
}
