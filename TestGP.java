public class TestGP {
    public static void main(String[] args){
        if(args.length!=1){
            System.out.println("Usage: java TestGP <csv>");
            return;
        }
        try{
            Generation gen=new Generation(500,6,args[0]);
            gen.evolve(50);
            GPTree best=gen.getBestTree();
            System.out.println("Best: "+best+" fitness="+best.getFitness());
        }catch(Exception e){ System.out.println(e); }
    }
}