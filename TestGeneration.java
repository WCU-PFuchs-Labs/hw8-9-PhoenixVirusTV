public class TestGeneration {
    public static void main(String[] args){
        if(args.length!=1){
            System.out.println("Usage: java TestGeneration <csv>");
            return;
        }
        try{
            Generation g=new Generation(500,6,args[0]);
            System.out.println("Collect has all binops!");
            for(GPTree t: g.getTopTrees(10)){
                System.out.println(t+" fitness="+t.getFitness());
            }
        }catch(Exception e){ System.out.println(e); }
    }
}