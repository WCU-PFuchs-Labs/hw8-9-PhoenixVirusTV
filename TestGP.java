public class TestGP {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("You must provide a data file.");
            return;
        }

        String fileName = args[0];
        Generation gen = new Generation(500, 6, fileName);

        // Run multiple generations (mock output for test purposes)
        for (int i = 1; i <= 50; i++) {
            gen.evalAll(); // evaluate trees
            System.out.printf("Generation %d: Best Fitness = %.2f%n", i, gen.getBestFitness());
        }
    }
}
