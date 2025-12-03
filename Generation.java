import java.io.*;
import java.util.*;
public class Generation {
    private ArrayList<GPTree> pop=new ArrayList<>();
    private NodeFactory nf;
    private DataSet data;
    private Random r=new Random();
    public Generation(int size, int depth, String file) throws IOException{
        data=new DataSet(file);
        String[] ops={"+","-","*","/"};
        nf=new NodeFactory(ops,data.getNumIndepVars());
        for(int i=0;i<size;i++){
            Node root=nf.randomNode(r,depth);
            GPTree t=new GPTree(root);
            t.computeFitness(data);
            pop.add(t);
        }
    }
    public ArrayList<GPTree> getTopTrees(int n){
        pop.sort(Comparator.comparingDouble(GPTree::getFitness));
        return new ArrayList<>(pop.subList(0,n));
    }
    public GPTree getBestTree(){
        pop.sort(Comparator.comparingDouble(GPTree::getFitness));
        return pop.get(0);
    }
    public void evolve(int gens){
        for(int g=0;g<gens;g++){
            for(GPTree t: pop) t.computeFitness(data);
            pop.sort(Comparator.comparingDouble(GPTree::getFitness));
        }
    }
}