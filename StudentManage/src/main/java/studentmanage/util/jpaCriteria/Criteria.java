package studentmanage.util.jpaCriteria;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class Criteria<T> implements Specification<T> {

    private List<Criterion> criterions = new ArrayList<Criterion>();


    /**
     * ���Ӽ��������ʽ
     *
     * @param expression0 void
     * @Methods Name add
     */
    public void add(Criterion criterion) {
        if (criterion != null) {
            criterions.add(criterion);
        }
    }


    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (!criterions.isEmpty()) {
            List<Predicate> predicates = new ArrayList<Predicate>();
            for (Criterion c : criterions) {
                predicates.add(c.toPredicate(root, query, builder));
            }

            // ������������ and ��������
            if (predicates.size() > 0) {
                return builder.and(predicates.toArray(new Predicate[predicates.size()]));
            }

        }
        return builder.conjunction();
    }

}
