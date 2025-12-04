public abstract class Op implements Cloneable {
    public Object clone() {
        try { return super.clone(); } catch(CloneNotSupportedException e){ return null; }
    }
}