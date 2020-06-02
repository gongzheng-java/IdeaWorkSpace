package studentmanage.util.jpaCriteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class LogicalExpression implements Criterion {

    private Criterion[] criterion;  // �߼����ʽ�а����ı��ʽ
    private Operator operator;      //�����


    public LogicalExpression(Criterion[] criterions, Operator operator) {
        this.criterion = criterions;
        this.operator = operator;
    }

    public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<Predicate>();
        for (int i = 0; i < this.criterion.length; i++) {
            predicates.add(this.criterion[i].toPredicate(root, query, builder));
        }
        switch (operator) {
            case OR:
                return builder.or(predicates.toArray(new Predicate[predicates.size()]));
            default:
                return null;
        }
    }

}
