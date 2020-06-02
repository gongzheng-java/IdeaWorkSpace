package studentmanage.util.jpaCriteria;

import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;


public class SimpleExpression implements Criterion {


    private String fieldname;       //������  
    private Object value;           //��Ӧֵ  
    private Operator operator;      //�����  


    protected SimpleExpression(String fieldname, Object value, Operator operator) {
        this.fieldname = fieldname;
        this.value = value;
        this.operator = operator;
    }

    public String getFieldName() {
        return fieldname;
    }

    public Object getValue() {
        return value;
    }

    public Operator getOperator() {
        return operator;
    }


    @SuppressWarnings({"rawtypes", "unchecked"})
    public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Path expression = null;

        if (fieldname.contains(".")) {
            String[] names = StringUtils.split(fieldname, ".");
            expression = root.get(names[0]);
            for (int i = 1; i < names.length; i++) {
                expression = expression.get(names[i]);
            }
        } else {
            expression = root.get(fieldname);
        }

        switch (operator) {
            case EQ:
                return builder.equal(expression, value);
            case ISNULL:
                return builder.isNull(expression);
            case ISNOTNUll:
                return builder.isNotNull(expression);
            case NE:
                return builder.notEqual(expression, value);
            case LIKE:
                return builder.like((Expression<String>) expression, "%" + value + "%");
            case LT:
                return builder.lessThan(expression, (Comparable) value);
            case GT:
                return builder.greaterThan(expression, (Comparable) value);
            case LTE:
                return builder.lessThanOrEqualTo(expression, (Comparable) value);
            case GTE:
                return builder.greaterThanOrEqualTo(expression, (Comparable) value);
            default:
                return null;
        }
    }


}
