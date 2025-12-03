import java.util.Random;

public class NodeFactory {
    private int numIndepVars;
    private Binop[] currentOps;

    public NodeFactory(Binop[] ops, int numVars) {
        currentOps = ops;
        numIndepVars = numVars;
    }

    // Always returns a valid operator Node
    public Node getOperator(Random rand) {
        Binop op = (Binop) currentOps[rand.nextInt(currentOps.length)].clone();
        return new Node(op);
    }

    // Always returns a valid terminal Node
    public Node getTerminal(Random rand) {
        if(rand.nextBoolean()) {
            double val = Math.round(rand.nextDouble() * 100.0) / 100.0;
            return new Node(new Const(val));
        } else {
            int idx = rand.nextInt(numIndepVars);
            return new Node(new Variable(idx));
        }
    }

    public int getNumOps() { return currentOps.length; }
    public int getNumIndepVars() { return numIndepVars; }
}
