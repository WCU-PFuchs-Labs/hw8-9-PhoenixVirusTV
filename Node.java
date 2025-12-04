import java.util.Random;

public class Node implements Cloneable {
    Node left, right;
    Op op;

    public Node(Binop op, Node left, Node right) { this.op = op; this.left = left; this.right = right; }
    public Node(Binop op) { this(op,null,null); }
    public Node(Unop op) { this.op = op; }

    public double eval(double[] values) {
        if(op instanceof Unop) return ((Unop)op).eval(values);
        else if(op instanceof Binop) return ((Binop)op).eval(left.eval(values), right.eval(values));
        return 0;
    }

    public void addRandomKids(NodeFactory nf, int maxDepth, Random rand) {
        if(maxDepth<=0) return;
        if(op instanceof Binop) {
            if(left==null) left = (rand.nextBoolean() && maxDepth>1) ? nf.getOperator(rand) : nf.getTerminal(rand);
            left.addRandomKids(nf, maxDepth-1, rand);
            if(right==null) right = (rand.nextBoolean() && maxDepth>1) ? nf.getOperator(rand) : nf.getTerminal(rand);
            right.addRandomKids(nf, maxDepth-1, rand);
        }
    }

    @Override
    public Object clone() {
        Node o=null;
        try{ o=(Node) super.clone(); } catch(CloneNotSupportedException e) {}
        if(o.left!=null) o.left=(Node) left.clone();
        if(o.right!=null) o.right=(Node) right.clone();
        if(op!=null) o.op=(Op) op.clone();
        return o;
    }

    @Override
    public String toString() {
        if(op instanceof Unop) return op.toString();
        if(op instanceof Binop) return "("+left+" "+op+" "+right+")";
        return "";
    }

    public void traverse(Collector c){c.collect(this); if(left!=null) left.traverse(c); if(right!=null) right.traverse(c);}
    public boolean isLeaf(){return op instanceof Unop;}
}