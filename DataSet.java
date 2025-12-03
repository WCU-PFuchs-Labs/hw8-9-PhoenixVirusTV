import java.io.*;
import java.util.*;
public class DataSet {
    private ArrayList<DataRow> rows=new ArrayList<>();
    private int numIndepVars;
    public DataSet(String file) throws IOException{
        BufferedReader br=new BufferedReader(new FileReader(file));
        String line;
        while((line=br.readLine())!=null){
            String[] p=line.split(",");
            double[] x=new double[p.length-1];
            for(int i=0;i<p.length-1;i++) x[i]=Double.parseDouble(p[i]);
            double y=Double.parseDouble(p[p.length-1]);
            rows.add(new DataRow(x,y));
        }
        numIndepVars=rows.get(0).x.length;
    }
    public int size(){ return rows.size(); }
    public DataRow getRow(int i){ return rows.get(i); }
    public int getNumIndepVars(){ return numIndepVars; }
}