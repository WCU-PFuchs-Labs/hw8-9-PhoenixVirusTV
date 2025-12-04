public abstract class Op implements Cloneable {
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Op can't clone.");
            return null;
        }
    }
}