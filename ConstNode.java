public class ConstNode extends Node {
    private double value;
    public ConstNode(double v){ value=v; }
    public double eval(double[] x){ return value; }
    public Node cloneNode(){ return new ConstNode(value); }
    public String toString(){ return String.valueOf(value); }
}