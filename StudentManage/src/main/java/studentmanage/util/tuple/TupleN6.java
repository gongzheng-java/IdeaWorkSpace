package studentmanage.util.tuple;

/**
 * 元祖（6个值）
 */
public class TupleN6<A, B, C, D, E, F> extends TupleN5<A, B, C, D, E> {
    public final F value6;

    public TupleN6(A a, B b, C c, D d, E e, F f) {
        super(a, b, c, d, e);

        value6 = f;
    }
}
