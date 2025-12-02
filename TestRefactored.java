import java.util.Random;

public class TestRefactored {
    static int numIndepVars = 3;
    static int maxDepth = 5;
    static Random rand = new Random();

    public static void main(String[] args){
        double[] data = {3.14, 2.78, 1.0};
        Binop[] ops = {new Plus(), new Minus(), new Mult(), new Divide()};
        NodeFactory nf = new NodeFactory(ops, numIndepVars);

        Node root = nf.getOperator(rand);
        root.addRandomKids(nf, maxDepth, rand);

        // Print only expression and evaluated result with 2 decimal places
        System.out.println(root + " = " + String.format("%.2f", root.eval(data)));
    }
}
