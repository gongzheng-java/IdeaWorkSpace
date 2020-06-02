package studentmanage.framework.base.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import studentmanage.util.convert.DataConvertUtil;
import studentmanage.util.tuple.TupleN2;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;

/**
 * 数据库操作service基类
 */
public class DbBaseService {

    /**
     * entityManager
     */
    protected EntityManager entityManager = null;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * jpql查询，返回实体类
     *
     * @param c
     * @param jpql
     * @param lstValue
     * @param <Q>
     * @return
     */
    public <Q> List<Q> find(Class<Q> c, String jpql, Object... lstValue) {
        TypedQuery<Q> typedQuery = entityManager.createQuery(jpql, c);
        //写入参数值
        int i = 1;
        for (Object value : lstValue) {
            typedQuery.setParameter(i, value);
            i++;
        }

        return typedQuery.getResultList();
    }

    /**
     * jpql查询，返回实体类，只返回一条
     *
     * @param c
     * @param jpql
     * @param lstValue
     * @param <Q>
     * @return
     */
    public <Q> Q findOne(Class<Q> c, String jpql, Object... lstValue) {
        List<Q> lst = find(c, jpql, lstValue);

        return lst.size() > 0 ? lst.get(0) : null;
    }

    /**
     * jpql查询，返回实体类，指定参数名传入参数
     *
     * @param c
     * @param jpql
     * @param mapParam
     * @param <Q>
     * @return
     */
    public <Q> List<Q> findParam(Class<Q> c, String jpql, Map<String, Object> mapParam) {
        TypedQuery<Q> typedQuery = entityManager.createQuery(jpql, c);
        //写入参数值
        for (Map.Entry<String, Object> entry : mapParam.entrySet()) {
            typedQuery.setParameter(entry.getKey(), entry.getValue());
        }

        return typedQuery.getResultList();
    }

    /**
     * jpql查询，返回实体类，指定参数名传入参数，只返回一条
     *
     * @param c
     * @param jpql
     * @param mapParam
     * @param <Q>
     * @return
     */
    public <Q> Q findParamOne(Class<Q> c, String jpql, Map<String, Object> mapParam) {
        List<Q> lst = findParam(c, jpql, mapParam);

        return lst.size() > 0 ? lst.get(0) : null;
    }

    /**
     * 验证并返回正确的分页信息
     *
     * @param pageSize
     * @param pageNumber
     * @param totalCount 不分页总行数
     * @return 返回结果：0：pageSize；1：pageNumber
     */
    private TupleN2<Integer, Integer> validPageInfo(int pageSize, int pageNumber, int totalCount) {
        //处理传入的异常的当前页码
        if (pageSize <= 0) {
            pageSize = 10;
        }
        //根据中行数算出理论上最大页数（从0开始）
        int maxPageNumber = (int) (totalCount / pageSize);
        if (totalCount % pageSize != 0) {
            maxPageNumber++;
        }

        //如果传入页码超过最大页码，取最大页码
        if (pageNumber > maxPageNumber) {
            pageNumber = maxPageNumber;
        }

        return new TupleN2<>(pageSize, pageNumber);
    }

    /**
     * jpql查询，分页，返回实体类
     *
     * @param c
     * @param jpql
     * @param pageable
     * @param lstValue
     * @param <Q>
     * @return
     */
    public <Q> Page<Q> findPage(Class<Q> c, String jpql, Pageable pageable, Object... lstValue) {
        return findPageCountJpql(c, jpql, null, pageable, lstValue);
    }

    /**
     * jpql查询，分页，返回实体类，自定义总行数查询sql
     *
     * @param c
     * @param jpql
     * @param jpqlCount
     * @param pageable
     * @param lstValue
     * @param <Q>
     * @return
     */
    public <Q> Page<Q> findPageCountJpql(Class<Q> c, String jpql, String jpqlCount, Pageable pageable, Object... lstValue) {
        //先查询不分页的总行数

        //计算总行数的jpql支持自动生成或从外部传入
        if (StringUtils.isEmpty(jpqlCount)) {
            //自动生成查询总行数的select count语句，方法是把语句中from以及前面部分用select count(*) from 替换掉
            String sqlTem = jpql.toLowerCase();
            sqlTem = jpql.substring(sqlTem.indexOf(" from") + 5);
            jpqlCount = "select count(*) from " + sqlTem;
        }
        Query queryCount = entityManager.createQuery(jpqlCount);
        //写入参数值
        int i = 1;
        for (Object value : lstValue) {
            queryCount.setParameter(i, value);
            i++;
        }
        List<Object> lstRow = queryCount.getResultList();
        //到此查出不分页的总行数
        Integer totalCount = DataConvertUtil.objToInteger(lstRow.get(0));

        //处理分页信息
        TupleN2<Integer, Integer> tuple = validPageInfo(pageable.getPageSize(), pageable.getPageNumber(), totalCount);
        //每页行数
        int pageSize = tuple.value1;
        //当前页码，从0开始
        int pageNumber = tuple.value2;

        //查询当前页的内容
        TypedQuery<Q> typedQuery = entityManager.createQuery(jpql, c);
        //写入参数值
        i = 1;
        for (Object value : lstValue) {
            typedQuery.setParameter(i, value);
            i++;
        }
        //设置分页信息
        //输出结果的第一条记录的索引（从0开始）
        typedQuery.setFirstResult(pageNumber * pageSize);
        //输出结果的最多条数（相当于每页行数）
        typedQuery.setMaxResults(pageSize);
        //查询出结果
        List<Q> lstEntity = typedQuery.getResultList();

        //封装分页信息对象
        Page<Q> page = new PageImpl<>(lstEntity, new PageRequest(pageNumber, pageSize), totalCount);

        return page;
    }

    /**
     * jpql查询，分页，返回实体类
     *
     * @param c
     * @param jpql
     * @param pageable
     * @param mapParam
     * @param <Q>
     * @return
     */
    public <Q> Page<Q> findPageParam(Class<Q> c, String jpql, Pageable pageable, Map<String, Object> mapParam) {
        return findPageParamCountJpql(c, jpql, null, pageable, mapParam);
    }

    /**
     * jpql查询，分页，返回实体类，自定义总行数查询sql
     *
     * @param c
     * @param jpql
     * @param jpqlCount
     * @param pageable
     * @param mapParam
     * @param <Q>
     * @return
     */
    public <Q> Page<Q> findPageParamCountJpql(Class<Q> c, String jpql, String jpqlCount, Pageable pageable, Map<String, Object> mapParam) {
        //先查询不分页的总行数

        //计算总行数的jpql支持自动生成或从外部传入
        if (StringUtils.isEmpty(jpqlCount)) {
            //自动生成查询总行数的select count语句，方法是把语句中from以及前面部分用select count(*) from 替换掉
            String sqlTem = jpql.toLowerCase();
            sqlTem = jpql.substring(sqlTem.indexOf(" from") + 5);
            jpqlCount = "select count(*) from " + sqlTem;
        }

        Query queryCount = entityManager.createQuery(jpqlCount);
        //写入参数值
        for (Map.Entry<String, Object> entry : mapParam.entrySet()) {
            queryCount.setParameter(entry.getKey(), entry.getValue());
        }
        List<Object> lstRow = queryCount.getResultList();
        //到此查出不分页的总行数
        Integer totalCount = DataConvertUtil.objToInteger(lstRow.get(0));

        //处理分页信息
        TupleN2<Integer, Integer> tuple = validPageInfo(pageable.getPageSize(), pageable.getPageNumber(), totalCount);
        //每页行数
        int pageSize = tuple.value1;
        //当前页码，从0开始
        int pageNumber = tuple.value2;

        //查询当前页的内容
        TypedQuery<Q> typedQuery = entityManager.createQuery(jpql, c);
        //写入参数值
        for (Map.Entry<String, Object> entry : mapParam.entrySet()) {
            typedQuery.setParameter(entry.getKey(), entry.getValue());
        }
        //设置分页信息
        //输出结果的第一条记录的索引（从0开始）
        typedQuery.setFirstResult(pageNumber * pageSize);
        //输出结果的最多条数（相当于每页行数）
        typedQuery.setMaxResults(pageSize);
        //查询出结果
        List<Q> lstEntity = typedQuery.getResultList();

        //封装分页信息对象
        Page<Q> page = new PageImpl<>(lstEntity, new PageRequest(pageNumber, pageSize), totalCount);

        return page;
    }

    /**
     * 原生sql查询，返回实体类
     *
     * @param c
     * @param sql
     * @param lstValue
     * @param <Q>
     * @return
     */
    public <Q> List<Q> findSql(Class<Q> c, String sql, Object... lstValue) {
        Query query = entityManager.createNativeQuery(sql, c);

        //写入参数值
        int i = 1;
        for (Object value : lstValue) {
            query.setParameter(i, value);
            i++;
        }

        return query.getResultList();
    }

    /**
     * 原生sql查询，返回实体类，返回一条记录
     *
     * @param c
     * @param sql
     * @param lstValue
     * @param <Q>
     * @return
     */
    public <Q> Q findSqlOne(Class<Q> c, String sql, Object... lstValue) {
        List<Q> lst = findSql(c, sql, lstValue);

        return lst.size() > 0 ? lst.get(0) : null;
    }

    /**
     * 原生sql查询，返回不定类型
     *
     * @param sql
     * @param lstValue
     * @return
     */
    public List findSql(String sql, Object... lstValue) {
        Query query = entityManager.createNativeQuery(sql);

        //写入参数值
        int i = 1;
        for (Object value : lstValue) {
            query.setParameter(i, value);
            i++;
        }

        return query.getResultList();
    }

    /**
     * 原生sql查询，返回实体类，指定参数名传入参数
     *
     * @param c
     * @param sql
     * @param mapParam
     * @param <Q>
     * @return
     */
    public <Q> List<Q> findSqlParam(Class<Q> c, String sql, Map<String, Object> mapParam) {
        Query query = entityManager.createNativeQuery(sql, c);
        //写入参数值
        for (Map.Entry<String, Object> entry : mapParam.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        return query.getResultList();
    }

    /**
     * 原生sql查询，返回实体类，指定参数名传入参数，返回一条记录
     *
     * @param c
     * @param sql
     * @param mapParam
     * @param <Q>
     * @return
     */
    public <Q> Q findSqlParamOne(Class<Q> c, String sql, Map<String, Object> mapParam) {
        List<Q> lst = findSqlParam(c, sql, mapParam);

        return lst.size() > 0 ? lst.get(0) : null;
    }

    /**
     * 原生sql查询，返回不定类型，指定参数名传入参数
     *
     * @param sql
     * @param mapParam
     * @return
     */
    public List findSqlParam(String sql, Map<String, Object> mapParam) {
        Query query = entityManager.createNativeQuery(sql);

        //写入参数值
        for (Map.Entry<String, Object> entry : mapParam.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        return query.getResultList();
    }

    /**
     * 原生sql查询，分页，返回实体类
     *
     * @param c
     * @param sql
     * @param pageable
     * @param lstValue
     * @param <Q>
     * @return
     */
    public <Q> Page<Q> findSqlPage(Class<Q> c, String sql, Pageable pageable, Object... lstValue) {
        return findSqlPageCountSql(c, sql, null, pageable, lstValue);
    }

    /**
     * 原生sql查询，分页，返回实体类，自定义查询总行数sql
     *
     * @param c
     * @param sql
     * @param sqlCount
     * @param pageable
     * @param lstValue
     * @param <Q>
     * @return
     */
    public <Q> Page<Q> findSqlPageCountSql(Class<Q> c, String sql, String sqlCount, Pageable pageable, Object... lstValue) {
        //先查询不分页的总行数
        //计算总行数的jpql支持自动生成或从外部传入
        if (StringUtils.isEmpty(sqlCount)) {
            //自动生成查询总行数的select count语句，方法是在原始sql外面再包一层select count(*)
            sqlCount = "select count(*) from (" + sql + ") countTable123";
        }

        Query queryCount = entityManager.createNativeQuery(sqlCount);
        //写入参数值
        int i = 1;
        for (Object value : lstValue) {
            queryCount.setParameter(i, value);
            i++;
        }
        List<Object> lstRow = queryCount.getResultList();
        //到此查出不分页的总行数
        Integer totalCount = DataConvertUtil.objToInteger(lstRow.get(0));

        //处理分页信息
        TupleN2<Integer, Integer> tuple = validPageInfo(pageable.getPageSize(), pageable.getPageNumber(), totalCount);
        //每页行数
        int pageSize = tuple.value1;
        //当前页码，从0开始
        int pageNumber = tuple.value2;

        //查询当前页的内容
        Query query = entityManager.createNativeQuery(sql, c);
        //写入参数值
        i = 1;
        for (Object value : lstValue) {
            query.setParameter(i, value);
            i++;
        }
        //设置分页信息
        //输出结果的第一条记录的索引（从0开始）
        query.setFirstResult(pageNumber * pageSize);
        //输出结果的最多条数（相当于每页行数）
        query.setMaxResults(pageSize);
        //查询出结果
        List<Q> lstEntity = query.getResultList();

        //封装分页信息对象
        Page<Q> page = new PageImpl<>(lstEntity, new PageRequest(pageNumber, pageSize), totalCount);

        return page;
    }

    /**
     * 原生sql查询，分页，返回不定类型
     *
     * @param sql
     * @param pageable
     * @param lstValue
     * @return
     */
    public Page findSqlPage(String sql, Pageable pageable, Object... lstValue) {
        return findSqlPageCountSql(sql, null, pageable, lstValue);
    }

    /**
     * 原生sql查询，分页，返回不定类型，自定义查询总行数sql
     *
     * @param sql
     * @param sqlCount
     * @param pageable
     * @param lstValue
     * @return
     */
    public Page findSqlPageCountSql(String sql, String sqlCount, Pageable pageable, Object... lstValue) {
        //先查询不分页的总行数
        //计算总行数的jpql支持自动生成或从外部传入
        if (StringUtils.isEmpty(sqlCount)) {
            //自动生成查询总行数的select count语句，方法是在原始sql外面再包一层select count(*)
            sqlCount = "select count(*) from (" + sql + ") countTable123";
        }

        Query queryCount = entityManager.createNativeQuery(sqlCount);
        //写入参数值
        int i = 1;
        for (Object value : lstValue) {
            queryCount.setParameter(i, value);
            i++;
        }
        List<Object> lstRow = queryCount.getResultList();
        //到此查出不分页的总行数
        Integer totalCount = DataConvertUtil.objToInteger(lstRow.get(0));

        //处理分页信息
        TupleN2<Integer, Integer> tuple = validPageInfo(pageable.getPageSize(), pageable.getPageNumber(), totalCount);
        //每页行数
        int pageSize = tuple.value1;
        //当前页码，从0开始
        int pageNumber = tuple.value2;

        //查询当前页的内容
        Query query = entityManager.createNativeQuery(sql);
        //写入参数值
        i = 1;
        for (Object value : lstValue) {
            query.setParameter(i, value);
            i++;
        }
        //设置分页信息
        //输出结果的第一条记录的索引（从0开始）
        query.setFirstResult(pageNumber * pageSize);
        //输出结果的最多条数（相当于每页行数）
        query.setMaxResults(pageSize);
        //查询出结果
        List lstEntity = query.getResultList();

        //封装分页信息对象
        Page page = new PageImpl<>(lstEntity, new PageRequest(pageNumber, pageSize), totalCount);

        return page;
    }

    /**
     * 原生sql查询，分页，返回实体类，指定名称参数
     *
     * @param c
     * @param sql
     * @param pageable
     * @param mapParam
     * @param <Q>
     * @return
     */
    public <Q> Page<Q> findSqlPageParam(Class<Q> c, String sql, Pageable pageable, Map<String, Object> mapParam) {
        return findSqlPageParamCountSql(c, sql, null, pageable, mapParam);
    }

    /**
     * 原生sql查询，分页，返回实体类，指定名称参数，自定义查询总行数sql
     *
     * @param c
     * @param sql
     * @param sqlCount
     * @param pageable
     * @param mapParam
     * @param <Q>
     * @return
     */
    public <Q> Page<Q> findSqlPageParamCountSql(Class<Q> c, String sql, String sqlCount, Pageable pageable, Map<String, Object> mapParam) {
        //先查询不分页的总行数
        //计算总行数的jpql支持自动生成或从外部传入
        if (StringUtils.isEmpty(sqlCount)) {
            //自动生成查询总行数的select count语句，方法是在原始sql外面再包一层select count(*)
            sqlCount = "select count(*) from (" + sql + ") countTable123";
        }

        Query queryCount = entityManager.createNativeQuery(sqlCount);
        //写入参数值
        for (Map.Entry<String, Object> entry : mapParam.entrySet()) {
            queryCount.setParameter(entry.getKey(), entry.getValue());
        }
        List<Object> lstRow = queryCount.getResultList();
        //到此查出不分页的总行数
        Integer totalCount = DataConvertUtil.objToInteger(lstRow.get(0));

        //处理分页信息
        TupleN2<Integer, Integer> tuple = validPageInfo(pageable.getPageSize(), pageable.getPageNumber(), totalCount);
        //每页行数
        int pageSize = tuple.value1;
        //当前页码，从0开始
        int pageNumber = tuple.value2;

        //查询当前页的内容
        Query query = entityManager.createNativeQuery(sql, c);
        //写入参数值
        for (Map.Entry<String, Object> entry : mapParam.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        //设置分页信息
        //输出结果的第一条记录的索引（从0开始）
        query.setFirstResult(pageNumber * pageSize);
        //输出结果的最多条数（相当于每页行数）
        query.setMaxResults(pageSize);
        //查询出结果
        List<Q> lstEntity = query.getResultList();

        //封装分页信息对象
        Page<Q> page = new PageImpl<>(lstEntity, new PageRequest(pageNumber, pageSize), totalCount);

        return page;
    }

    /**
     * 原生sql查询，分页，返回不定类型，指定名称参数
     *
     * @param sql
     * @param pageable
     * @param mapParam
     * @return
     */
    public Page findSqlPageParam(String sql, Pageable pageable, Map<String, Object> mapParam) {
        return findSqlPageParamCountSql(sql, null, pageable, mapParam);
    }

    /**
     * 原生sql查询，分页，返回不定类型，指定名称参数，自定义查询总行数sql
     *
     * @param sql
     * @param sqlCount
     * @param pageable
     * @param mapParam
     * @return
     */
    public Page findSqlPageParamCountSql(String sql, String sqlCount, Pageable pageable, Map<String, Object> mapParam) {
        //先查询不分页的总行数
        //计算总行数的jpql支持自动生成或从外部传入
        if (StringUtils.isEmpty(sqlCount)) {
            //自动生成查询总行数的select count语句，方法是在原始sql外面再包一层select count(*)
            sqlCount = "select count(*) from (" + sql + ") countTable123";
        }

        Query queryCount = entityManager.createNativeQuery(sqlCount);
        //写入参数值
        for (Map.Entry<String, Object> entry : mapParam.entrySet()) {
            queryCount.setParameter(entry.getKey(), entry.getValue());
        }
        List<Object> lstRow = queryCount.getResultList();
        //到此查出不分页的总行数
        Integer totalCount = DataConvertUtil.objToInteger(lstRow.get(0));

        //处理分页信息
        TupleN2<Integer, Integer> tuple = validPageInfo(pageable.getPageSize(), pageable.getPageNumber(), totalCount);
        //每页行数
        int pageSize = tuple.value1;
        //当前页码，从0开始
        int pageNumber = tuple.value2;

        //查询当前页的内容
        Query query = entityManager.createNativeQuery(sql);
        //写入参数值
        for (Map.Entry<String, Object> entry : mapParam.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        //设置分页信息
        //输出结果的第一条记录的索引（从0开始）
        query.setFirstResult(pageNumber * pageSize);
        //输出结果的最多条数（相当于每页行数）
        query.setMaxResults(pageSize);
        //查询出结果
        List lstEntity = query.getResultList();

        //封装分页信息对象
        Page page = new PageImpl<>(lstEntity, new PageRequest(pageNumber, pageSize), totalCount);

        return page;
    }

    /**
     * 执行jpql
     *
     * @param jpql
     * @param lstValue
     * @return
     */
    public int executeNoneQuery(String jpql, Object... lstValue) {
        Query query = entityManager.createQuery(jpql);
        //写入参数值
        int i = 1;
        for (Object value : lstValue) {
            query.setParameter(i, value);
            i++;
        }
        return query.executeUpdate();
    }

    /**
     * 执行jpql，指定名称参数
     *
     * @param jpql
     * @param mapParam
     * @return
     */
    public int executeNoneQueryParam(String jpql, Map<String, Object> mapParam) {
        Query query = entityManager.createQuery(jpql);
        //写入参数值
        for (Map.Entry<String, Object> entry : mapParam.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query.executeUpdate();
    }

    /**
     * 执行sql
     *
     * @param sql
     * @param lstValue
     * @return
     */
    public int executeNoneQuerySql(String sql, Object... lstValue) {
        Query query = entityManager.createNativeQuery(sql);
        //写入参数值
        int i = 1;
        for (Object value : lstValue) {
            query.setParameter(i, value);
            i++;
        }
        return query.executeUpdate();
    }

    /**
     * 执行sql，指定名称参数
     *
     * @param sql
     * @param mapParam
     * @return
     */
    public int executeNoneQuerySqlParam(String sql, Map<String, Object> mapParam) {
        Query query = entityManager.createNativeQuery(sql);
        //写入参数值
        for (Map.Entry<String, Object> entry : mapParam.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query.executeUpdate();
    }
}
