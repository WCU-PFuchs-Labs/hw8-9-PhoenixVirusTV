public class VarNode extends Node {
    private int index;
    public VarNode(int i){ index=i; }
    public double eval(double[] x){ return x[index]; }
    public Node cloneNode(){ return new VarNode(index); }
    public String toString(){ return "X"+index; }
}