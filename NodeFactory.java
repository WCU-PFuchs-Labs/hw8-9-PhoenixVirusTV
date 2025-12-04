import java.util.Random;

public class NodeFactory {
    private int numIndepVars;
    private Binop[] currentOps;
    public NodeFactory(Binop[] ops,int numVars){currentOps=ops; numIndepVars=numVars;}
    public Node getOperator(Random rand){Binop op=(Binop) currentOps[rand.nextInt(currentOps.length)].clone(); return new Node(op);}
    public Node getTerminal(Random rand){if(rand.nextBoolean()){double val=Math.round(rand.nextDouble()*100.0)/100.0; return new Node(new Const(val));} else{return new Node(new Variable(rand.nextInt(numIndepVars)));}}
    public int getNumIndepVars(){return numIndepVars;}
}