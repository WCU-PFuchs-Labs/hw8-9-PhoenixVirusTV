import java.util.*;
public class TestGP {
    public static void main(String[] args){
        Scanner in=new Scanner(System.in);
        System.out.print("Enter data file name: ");
        String fileName=in.nextLine();
        Generation gen=new Generation(500,6,fileName);
        for(int genNum=1;genNum<=50;genNum++){
            System.out.println("\nGeneration "+genNum+":");
            gen.evalAll();
            gen.printBestTree();
            gen.printBestFitness();
            gen.evolve();
        }
    }
}