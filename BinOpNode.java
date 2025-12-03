public class BinOpNode extends Node {
    private String op;
    private Node left,right;
    public BinOpNode(String op, Node l, Node r){
        this.op=op; left=l; right=r;
    }
    public double eval(double[] x){
        double a=left.eval(x), b=right.eval(x);
        switch(op){
            case "+": return a+b;
            case "-": return a-b;
            case "*": return a*b;
            case "/": return b==0?0:a/b;
        }
        return 0;
    }
    public Node cloneNode(){ return new BinOpNode(op,left.cloneNode(),right.cloneNode()); }
    public String toString(){ return "("+left+" "+op+" "+right+")"; }
}