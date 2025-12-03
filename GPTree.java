import java.util.*;
public class GPTree {
    public Node root;
    private double fitness;
    public GPTree(Node r){ root=r; }
    public void computeFitness(DataSet data){
        double sum=0;
        for(int i=0;i<data.size();i++){
            DataRow row=data.getRow(i);
            double pred=root.eval(row.x);
            sum += Math.abs(pred-row.y);
        }
        fitness=sum;
    }
    public double getFitness(){ return fitness; }
    public GPTree cloneTree(){ return new GPTree(root.cloneNode()); }
    public String toString(){ return root.toString(); }
}