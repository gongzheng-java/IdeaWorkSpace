package studentmanage.framework.base.service;


import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import studentmanage.util.convert.DataConvertUtil;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;


/**
 * service基类_增删改
 *
 * @param <T>
 * @param <R>
 */
public abstract class CrudBaseService<T, R extends JpaRepository<T, Integer>> extends DbBaseService {

    protected Pageable pageable = null;
    protected Sort sort = null;

    /**
     * dao
     */
    @Autowired
    protected R repository;

    /**
     * jpql查询
     *
     * @param jpql
     * @param values
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<T> findAllByJpql(String jpql, Object... values) {
        Query query = entityManager.createQuery(jpql);

        //赋值参数的值
        if (values != null) {
            //参数的序号，从0开始
            int i = 0;
            for (Object value : values) {
                query.setParameter(i, value);
                i++;
            }
        }

        List<T> list = query.getResultList();

        return list;
    }

    /**
     * jpql查询，只返回一条记录
     *
     * @param jpql
     * @param values
     * @return
     */
    public T findOneByJpql(String jpql, Object... values) {
        List<T> lst = findAllByJpql(jpql, values);
        //有多条记录也只返回第一条
        if (lst.size() > 0)
            return lst.get(0);
        else
            return null;
    }

    /**
     * jpql count查询
     *
     * @param jpql
     * @param values
     * @return
     */
    public long countByJpql(String jpql, Object... values) {
        Object obj = oneValueQueryByJpql(jpql, values);

        return (long) obj;
    }

    /**
     * jpql 结果为单行单字段的查询，例如count，max，min等
     *
     * @param jpql
     * @param values
     * @return
     */
    public Object oneValueQueryByJpql(String jpql, Object... values) {
        Query query = entityManager.createQuery(jpql);

        //赋值参数的值
        if (values != null) {
            //参数的序号，从0开始
            int i = 0;
            for (Object value : values) {
                query.setParameter(i, value);
                i++;
            }
        }

        List list = query.getResultList();
        //语句有且只有一条结果，且只有一列
        Object obj = list.get(0);

        return obj;
    }

    /**
     * 执行jpql（executeNoneQuery）
     *
     * @param jpql
     * @param values
     * @return
     */
    public long executeNoneQueryByJpql(String jpql, Object... values) {
        Query query = entityManager.createQuery(jpql);

        //赋值参数的值
        if (values != null) {
            //参数的序号，从0开始
            int i = 0;
            for (Object value : values) {
                query.setParameter(i, value);
                i++;
            }
        }

        int count = query.executeUpdate();

        return count;
    }

    /**
     * 原生sql查询（返回实体类）
     *
     * @param c
     * @param sql
     * @param values
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<T> findAllBySql(Class<T> c, String sql, Object... values) {
        Query query;
        if (c != null)
            //传入返回的实体类型
            query = entityManager.createNativeQuery(sql, c);
        else
            //不传入返回的实体类型，返回的字段从select部分定义
            query = entityManager.createNativeQuery(sql);

        //赋值参数的值
        if (values != null) {
            //参数的序号，从0开始
            int i = 0;
            for (Object value : values) {
                query.setParameter(i, value);
                i++;
            }
        }

        List<T> list = query.getResultList();

        return list;
    }

    /**
     * 原生sql查询（自定义返回的字段）
     *
     * @param sql
     * @param values
     * @return
     */
    public List<T> findAllBySql(String sql, Object... values) {
        return findAllBySql(null, sql, values);
    }

    /**
     * 执行原生sql（相当于executeNoneQuery）
     *
     * @param sql
     * @return
     */
    public int executeNoneQueryBySql(String sql) {
        Query query = entityManager.createNativeQuery(sql);

        int count = query.executeUpdate();

        return count;
    }

    /**
     * 通过id获取实体
     *
     * @param id
     * @return
     */
    public T get(int id) {
        T entity = null;

        if (id > 0) {

            entity = repository.getOne(id);
        }

        return entity;
    }

    /**
     * 通过id获取实体（参数名必须叫id）
     *
     * @param request
     * @return
     */
    public Object get(HttpServletRequest request) {
        //参数名必须叫id
        int id = DataConvertUtil.strToInt(request.getParameter("id"));

        return get(id);
    }

    /**
     * 删除单个对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = {Exception.class})
    public int delete(int id) throws Exception {
        T obj = this.get(id);
        this.delete(obj);
        return 1;
    }

    /**
     * 删除单个对象
     *
     * @param obj
     * @throws Exception
     */
    @Transactional(rollbackFor = {Exception.class})
    public void delete(T obj) throws Exception {
        repository.delete(obj);
    }

    /**
     * 删除，对象id通过参数ids传入，格式1,2,3,4,5
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = {Exception.class})
    public void delete(HttpServletRequest request) throws Exception {
        //把string按逗号分隔开并转为Long的List
        List<Integer> lstId = DataConvertUtil.strsToIntList(request.getParameter("ids"));
        for (Integer id : lstId) {
            if (id > 0) {
                repository.deleteById(id);
            }
        }
    }

    /**
     * 查询_带分页
     *
     * @param request
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Page<T> search(HttpServletRequest request, int pageNumber, int pageSize) {
        //需要自定义查询条件、排序字段等，可重写此方法

        //默认用id排倒序（规定id字段必须有）
        Sort sort = getDeafultSort();
        pageable = new PageRequest(pageNumber, pageSize, sort);

        Page<T> page = repository.findAll(pageable);

        return page;
    }

    /**
     * 查询实体转换成视图。当列表视图和实体字段不同，可重写此方法进行转换
     *
     * @param lst
     * @return
     */
    public List entityToView(List<T> lst) {
        return lst;
    }

    /**
     * 保存结果数据到excel文件，并返回excel文件名
     * 当需要导出excel时，此方法需要重写
     *
     * @param lst
     * @return
     * @throws IOException
     */
    public String saveListToExcel(List lst) throws IOException {
        return "";
    }

    /**
     * 获取默认排序
     *
     * @return
     */
    protected Sort getDeafultSort() {
        //默认用id排倒序（规定id字段必须有）
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "id"));

        return sort;
    }

    /**
     * search（查询）方法从request获取并添加查询条件（string类型）
     *
     * @param criteria
     * @param request
     * @param requestParamName request.getParameter的name
     * @param fieldName        字段名
     */
    protected void addSearchConditionString(Criteria criteria, HttpServletRequest request, String requestParamName, String fieldName) {
        String value = DataConvertUtil.strToStr(request.getParameter(requestParamName));
        if (!StringUtils.isEmpty(value))
            //用like而不是equal关系
            criteria.add(Restrictions.like(fieldName, value));

    }

    /**
     * search（查询）方法从request获取并添加查询条件（Long类型）
     *
     * @param criteria
     * @param request
     * @param requestParamName request.getParameter的name
     * @param fieldName        字段名
     */
    protected void addSearchConditionLong(Criteria criteria, HttpServletRequest request, String requestParamName, String fieldName) {
        Long value = DataConvertUtil.strToLongNull(request.getParameter(requestParamName));

        criteria.add(Restrictions.eq(fieldName, value));
    }

    /**
     * search（查询）方法从request获取并添加查询条件（Double类型）
     *
     * @param criteria
     * @param request
     * @param requestParamName request.getParameter的name
     * @param fieldName        字段名
     */
    protected void addSearchConditionDouble(Criteria criteria, HttpServletRequest request, String requestParamName, String fieldName) {
        Double value = DataConvertUtil.strToDouNull(request.getParameter(requestParamName));
        criteria.add(Restrictions.eq(fieldName, value));
    }

    /**
     * search（查询）方法从request获取并添加查询条件（Date类型）
     * 查询条件格式是时间访问，也就是大于等于起始时间且小于等于终止时间
     *
     * @param criteria
     * @param request
     * @param requestParamStart 起始时间参数名称
     * @param requestParamEnd   终止时间参数名称
     * @param dateFormat        date format，例如：yyyy-MM-dd
     * @param fieldName         字段名
     */
    protected void addSearchConditionDateBetween(Criteria criteria, HttpServletRequest request, String requestParamStart, String requestParamEnd, String dateFormat, String fieldName) {
        //从request取出起始时间和终止时间，并按指定格式转换到Date类型，转换失败返回null
        Date dateStart = DataConvertUtil.strToDate(request.getParameter(requestParamStart), dateFormat);
        Date dateEnd = DataConvertUtil.strToDate(request.getParameter(requestParamEnd), dateFormat);

        if (dateStart != null && dateEnd != null) {
            //添加日期范围查询条件，大于等于起始时间and小于等于终止时间
            criteria.add(Restrictions.ge(fieldName, dateStart));
            criteria.add(Restrictions.le(fieldName, dateEnd));
        }
    }

    /**
     * search（查询）方法从request获取并拼接到jpql语句（string类型）
     *
     * @param request
     * @param jpql             当前jpql
     * @param lstParam         存放参数的list
     * @param requestParamName request的参数名
     * @param fieldName        写到jpql的字段名
     * @return 返回拼接后的jpql
     */
    protected String addSearchConditionJpqlString(HttpServletRequest request, String jpql, List<Object> lstParam, String requestParamName, String fieldName) {
        String value = DataConvertUtil.strToStr(request.getParameter(requestParamName));
        if (!StringUtils.isEmpty(value)) {
            jpql += " and " + fieldName + " like ? ";
            lstParam.add("%" + DataConvertUtil.strToStr(value) + "%");
        }

        return jpql;
    }

    /**
     * search（查询）方法从request获取并拼接到jpql语句（Long类型）
     *
     * @param request
     * @param jpql             当前jpql
     * @param lstParam         存放参数的list
     * @param requestParamName request的参数名
     * @param fieldName        写到jpql的字段名
     * @return 返回拼接后的jpql
     */
    protected String addSearchConditionJpqlLong(HttpServletRequest request, String jpql, List<Object> lstParam, String requestParamName, String fieldName) {
        Long value = DataConvertUtil.strToLongNull(request.getParameter(requestParamName));

        if (value != null) {
            jpql += " and " + fieldName + " = ? ";
            lstParam.add(value);
        }

        return jpql;
    }

    /**
     * search（查询）方法从request获取并拼接到jpql语句（Double类型）
     *
     * @param request
     * @param jpql             当前jpql
     * @param lstParam         存放参数的list
     * @param requestParamName request的参数名
     * @param fieldName        写到jpql的字段名
     * @return 返回拼接后的jpql
     */
    protected String addSearchConditionJpqlDouble(HttpServletRequest request, String jpql, List<Object> lstParam, String requestParamName, String fieldName) {
        Double value = DataConvertUtil.strToDouNull(request.getParameter(requestParamName));

        if (value != null) {
            jpql += " and " + fieldName + " = ? ";
            lstParam.add(value);
        }

        return jpql;
    }

    /**
     * search（查询）方法从request获取并拼接到jpql语句（Date类型，between条件）
     *
     * @param request
     * @param jpql
     * @param lstParam
     * @param requestParamStart
     * @param requestParamEnd
     * @param dateFormat
     * @param fieldName
     */
    protected String addSearchConditionJpqlDateBetween(HttpServletRequest request, String jpql, List<Object> lstParam, String requestParamStart, String requestParamEnd, String dateFormat, String fieldName) {
        //从request取出起始时间和终止时间，并按指定格式转换到Date类型，转换失败返回null
        Date dateStart = DataConvertUtil.strToDate(request.getParameter(requestParamStart), dateFormat);
        Date dateEnd = DataConvertUtil.strToDate(request.getParameter(requestParamEnd), dateFormat);

        if (dateStart != null && dateEnd != null) {
            //添加日期范围查询条件，大于等于起始时间and小于等于终止时间
            jpql += " and " + fieldName + " between ? and ? ";
            lstParam.add(dateStart);
            lstParam.add(dateEnd);
        }

        return jpql;
    }

    /**
     * 保存，需要子类实现
     *
     * @param request
     * @return
     * @throws Exception
     */
    public abstract T saveOrUpdate(HttpServletRequest request) throws Exception;


}
