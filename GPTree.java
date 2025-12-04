import java.util.Random;

public class GPTree implements Comparable<GPTree>, Cloneable {
    Node root;
    private double fitness;
    public GPTree(NodeFactory nf,int maxDepth,Random rand){root=nf.getOperator(rand); root.addRandomKids(nf,maxDepth,rand);}
    public double eval(double[] x){return root.eval(x);}
    public void evalFitness(DataSet data){double sum=0; for(int i=0;i<data.size();i++){DataRow row=data.getRow(i); double diff=eval(row.getXValues())-row.getY(); sum+=diff*diff;} fitness=sum;}
    public double getFitness(){return fitness;}
    public void traverse(){root.traverse(node->{ });}
    public GPTree clone(){GPTree copy=null; try{copy=(GPTree) super.clone();}catch(CloneNotSupportedException e){} copy.root=(Node) root.clone(); copy.fitness=fitness; return copy;}
    public void crossover(GPTree other, Random rand){Node temp=this.root; this.root=other.root; other.root=temp;}
    @Override public int compareTo(GPTree o){return Double.compare(fitness,o.fitness);}
    @Override public String toString(){return root.toString();}
    public String getCrossNodes(){return "";}
}