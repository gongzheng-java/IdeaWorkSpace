package studentmanage.util.tuple;

/**
 * 元祖（4个值）
 */
public class TupleN4<A, B, C, D> extends TupleN3<A, B, C> {
    public final D value4;

    public TupleN4(A a, B b, C c, D d) {
        super(a, b, c);

        value4 = d;
    }
}
