import java.util.*;
public class Generation {
    private GPTree[] trees;
    private DataSet data;
    private NodeFactory factory;
    private Random rand;
    public Generation(int size,int maxDepth,String fileName){
        data=new DataSet(fileName);
        Binop[] ops={new Plus(),new Minus(),new Mult(),new Divide()};
        factory=new NodeFactory(ops,data.getNumIndepVars());
        rand=new Random();
        trees=new GPTree[size];
        for(int i=0;i<size;i++) trees[i]=new GPTree(factory,maxDepth,rand);
    }
    public void evalAll(){for(GPTree t:trees) t.evalFitness(data); Arrays.sort(trees);}
    public ArrayList<GPTree> getTopTen(){ArrayList<GPTree> top=new ArrayList<>(); for(int i=0;i<10 && i<trees.length;i++) top.add(trees[i]); return top;}
    public void printBestFitness(){System.out.println("Best Fitness: "+trees[0].getFitness());}
    public void printBestTree(){System.out.println("Best Tree: "+trees[0]);}
    public void evolve(){GPTree[] next=new GPTree[trees.length]; for(int i=0;i<trees.length;i+=2){GPTree p1=trees[rand.nextInt(trees.length/2)].clone(); GPTree p2=trees[rand.nextInt(trees.length/2)].clone(); p1.crossover(p2,rand); next[i]=p1; if(i+1<trees.length) next[i+1]=p2;} trees=next;}
}