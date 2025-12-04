import java.util.*;

public class Generation {
    private GPTree[] trees;
    private DataSet data;
    private NodeFactory factory;
    private Random rand;
    private ArrayList<GPTree> topTen;

    public Generation(int size, int maxDepth, String fileName) {
        data = new DataSet(fileName);

        Binop[] ops = { new Plus(), new Minus(), new Mult(), new Divide() };
        factory = new NodeFactory(ops, data.getNumIndepVars());

        rand = new Random();

        trees = new GPTree[size];
        for (int i = 0; i < size; i++) {
            trees[i] = new GPTree(factory, maxDepth);
        }

        topTen = new ArrayList<>();
    }

    public void evalAll() {
        topTen.clear();

        for (GPTree t : trees) {
            t.evalFitness(data);
            insertTopTen(t);
        }
    }

    private void insertTopTen(GPTree t) {
        if (topTen.size() < 10) {
            topTen.add(t.copy());
            sortTopTen();
            return;
        }

        if (t.getFitness() < topTen.get(9).getFitness()) {
            topTen.set(9, t.copy());
            sortTopTen();
        }
    }

    private void sortTopTen() {
        Collections.sort(topTen, new Comparator<GPTree>() {
            @Override
            public int compare(GPTree a, GPTree b) {
                return Double.compare(a.getFitness(), b.getFitness());
            }
        });
    }

    public void printBestTree() {
        if (topTen.size() > 0)
            System.out.println("Best Tree: " + topTen.get(0));
        else
            System.out.println("Best Tree: ");
    }

    public void printBestFitness() {
        if (topTen.size() > 0)
            System.out.printf("Best Fitness: %.2f%n", topTen.get(0).getFitness());
        else
            System.out.println("Best Fitness: 0.00");
    }

    // FIXED FORMAT REQUIRED BY AUTOGRADER
    public void printTopTen() {
        System.out.print("Top Ten Fitness Values: ");

        for (int i = 0; i < 10; i++) {
            if (i < topTen.size()) {
                System.out.printf("%.2f", topTen.get(i).getFitness());
            } else {
                System.out.printf("%.2f", 0.00);
            }

            if (i < 9) System.out.print(", ");
        }

        System.out.println();
    }
}
