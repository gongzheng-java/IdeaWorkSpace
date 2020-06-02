package studentmanage.util.jpaCriteria;

import org.springframework.util.StringUtils;

import java.util.Collection;


public class Restrictions {


    public static SimpleExpression eq(String fieldName, Object value) {
        if (StringUtils.isEmpty(value)) return null;
        return new SimpleExpression(fieldName, value, Criterion.Operator.EQ);
    }


    public static SimpleExpression isnull(String fieldName, Object value) {
        if (StringUtils.isEmpty(value)) return null;
        return new SimpleExpression(fieldName, value, Criterion.Operator.ISNULL);
    }

    public static SimpleExpression isnonull(String fieldName, Object value) {
        if (StringUtils.isEmpty(value)) return null;
        return new SimpleExpression(fieldName, value, Criterion.Operator.ISNOTNUll);
    }


    public static SimpleExpression ne(String fieldName, Object value) {
        if (StringUtils.isEmpty(value)) return null;
        return new SimpleExpression(fieldName, value, Criterion.Operator.NE);
    }


    public static SimpleExpression like(String fieldName, String value) {
        if (StringUtils.isEmpty(value)) return null;
        return new SimpleExpression(fieldName, value, Criterion.Operator.LIKE);
    }


    public static SimpleExpression gt(String fieldName, Object value) {
        if (StringUtils.isEmpty(value)) return null;
        return new SimpleExpression(fieldName, value, Criterion.Operator.GT);
    }


    public static SimpleExpression lt(String fieldName, Object value) {
        if (StringUtils.isEmpty(value)) return null;
        return new SimpleExpression(fieldName, value, Criterion.Operator.LT);
    }


    public static SimpleExpression lte(String fieldName, Object value) {
        if (StringUtils.isEmpty(value)) return null;
        return new SimpleExpression(fieldName, value, Criterion.Operator.LTE);
    }


    public static SimpleExpression gte(String fieldName, Object value) {
        if (StringUtils.isEmpty(value)) return null;
        return new SimpleExpression(fieldName, value, Criterion.Operator.GTE);
    }


    public static LogicalExpression and(Criterion... criterions) {
        return new LogicalExpression(criterions, Criterion.Operator.AND);
    }

    public static LogicalExpression or(Criterion... criterions) {
        return new LogicalExpression(criterions, Criterion.Operator.OR);
    }


    @SuppressWarnings("rawtypes")
    public static LogicalExpression in(String fieldName, Collection value) {
        if (value == null) {
            return null;
        }
        SimpleExpression[] ses = new SimpleExpression[value.size()];
        int i = 0;
        for (Object obj : value) {
            ses[i] = new SimpleExpression(fieldName, obj, Criterion.Operator.EQ);
            i++;
        }
        return new LogicalExpression(ses, Criterion.Operator.OR);
    }

}
