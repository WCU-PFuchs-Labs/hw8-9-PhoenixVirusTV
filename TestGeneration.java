public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter CSV file name: ");
    String fileName = scanner.nextLine();

    try {
        Generation g = new Generation(500, 6, fileName);
        g.evalAll();
        System.out.println("Best tree:");
        g.printBestTree();
        System.out.println("Best fitness:");
        g.printBestFitness();
        System.out.println("Top 10 fitness values:");
        ArrayList<GPTree> top = g.getTopTen();
        for(int i=0;i<top.size();i++) {
            System.out.printf("%.2f", top.get(i).getFitness());
            if(i<top.size()-1) System.out.print(", ");
        }
        System.out.println();
    } catch(IOException e) {
        System.out.println("Error reading file: " + e.getMessage());
    }
}

