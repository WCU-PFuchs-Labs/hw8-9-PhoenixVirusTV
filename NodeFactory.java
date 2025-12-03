import java.util.*;
public class NodeFactory {
    private String[] ops;
    private int vars;
    public NodeFactory(String[] ops, int vars){
        this.ops=ops; this.vars=vars;
    }
    public Node randomNode(Random r, int depth){
        if(depth==0){
            if(r.nextBoolean()) return new ConstNode(r.nextDouble());
            return new VarNode(r.nextInt(vars));
        }
        String op=ops[r.nextInt(ops.length)];
        Node l=randomNode(r,depth-1);
        Node rr=randomNode(r,depth-1);
        return new BinOpNode(op,l,rr);
    }
}