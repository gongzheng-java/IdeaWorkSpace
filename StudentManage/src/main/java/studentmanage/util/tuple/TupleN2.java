package studentmanage.util.tuple;

/**
 * 元祖（2个值）
 */
public class TupleN2<A, B> {
    public final A value1;
    public final B value2;

    public TupleN2(A a, B b) {
        value1 = a;
        value2 = b;
    }
}
