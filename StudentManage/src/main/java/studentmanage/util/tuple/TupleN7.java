package studentmanage.util.tuple;

/**
 * 元祖（7个值）
 */
public class TupleN7<A, B, C, D, E, F, G> extends TupleN6<A, B, C, D, E, F> {
    public final G value7;

    public TupleN7(A a, B b, C c, D d, E e, F f, G g) {
        super(a, b, c, d, e, f);

        value7 = g;
    }
}
