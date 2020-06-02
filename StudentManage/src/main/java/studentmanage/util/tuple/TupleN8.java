package studentmanage.util.tuple;

/**
 * 元祖（8个值）
 */
public class TupleN8<A, B, C, D, E, F, G, H> extends TupleN7<A, B, C, D, E, F, G> {
    public final H value8;

    public TupleN8(A a, B b, C c, D d, E e, F f, G g, H h) {
        super(a, b, c, d, e, f, g);

        value8 = h;
    }
}
