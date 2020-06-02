package studentmanage.util.tuple;

/**
 * 元祖（5个值）
 */
public class TupleN5<A, B, C, D, E> extends TupleN4<A, B, C, D> {
    public final E value5;

    public TupleN5(A a, B b, C c, D d, E e) {
        super(a, b, c, d);

        value5 = e;
    }
}
