public class TestGP {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("You must provide a data file.");
            return;
        }

        String fileName = args[0];
        Generation g = new Generation(500, 6, fileName);

        for (int gen = 1; gen <= 50; gen++) {
            System.out.println("Generation " + gen + ":");

            g.evalAll();
            g.printBestTree();
            g.printBestFitness();
            g.printTopTen();
            System.out.println(); // REQUIRED blank line
        }
    }
}
