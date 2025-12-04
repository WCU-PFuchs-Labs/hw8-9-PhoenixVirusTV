public class Divide extends Binop {
    @Override public double eval(double left, double right) { return Math.abs(right)<0.0001?1:left/right; }
    @Override public String toString() { return "/"; }
}