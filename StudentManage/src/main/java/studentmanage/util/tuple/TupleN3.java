package studentmanage.util.tuple;

/**
 * 元祖（3个值）
 */
public class TupleN3<A, B, C> extends TupleN2<A, B> {
    public final C value3;

    public TupleN3(A a, B b, C c) {
        super(a, b);

        value3 = c;
    }
}
