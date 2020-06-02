package studentmanage.framework.base.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import studentmanage.framework.base.service.CrudBaseService;
import studentmanage.util.page.ReturnPageResult;
import studentmanage.util.web.ResponseResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * controller基类_增删改
 *
 * @param <T>
 * @param <R>
 */
public class CrudBaseController<T, R extends JpaRepository<T, Integer>> {

    /**
     * 对应实体的service
     */
    @Autowired
    protected CrudBaseService<T, R> service;

    /**
     * 列表分页查询方法
     *
     * @param pageNumber
     * @param pageSize
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", produces = {"application/json;charset=UTF-8"})
    public Object list(int pageNumber, int pageSize, HttpServletRequest request) {
        try {
            //PS：关于页码，由于前段页码从1开始，而后台查询从0开始，因此要减1
            Page objs = service.search(request, pageNumber - 1, pageSize);

            //构建返回的分页信息
            ReturnPageResult returnPageResult = new ReturnPageResult();

            //实体列表转为视图列表
            List lstViewList = service.entityToView(objs.getContent());
            returnPageResult.setRecords(lstViewList);
            returnPageResult.setPageNumber(pageNumber);
            returnPageResult.setPageSize(pageSize);
            returnPageResult.setTotalRecord(objs.getTotalElements());
            returnPageResult.setTotalPage(objs.getTotalPages());

            return ResponseResult.getReturnResult(request, returnPageResult);
        } catch (Exception e) {
            return ResponseResult.getReturnResultError(request, e);
        }
    }

    /**
     * 导出excel
     *
     * @param pageNumber
     * @param pageSize
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/export")
    public Object export(int pageNumber, int pageSize, HttpServletRequest request) {
        try {
            //默认的导出excel功能主要用于导出excel的内容与列表内容一样的情况

            //跟查询列表数据一样的功能，最终查询出结果的数据
            Page objs = service.search(request, pageNumber - 1, pageSize);
            List lstViewList = service.entityToView(objs.getContent());

            //把结果数据保存到excel文件，并返回excel文件名
            String excelFileName = service.saveListToExcel(lstViewList);

            return ResponseResult.getReturnResult(request, excelFileName);
        } catch (Exception e) {
            return ResponseResult.getReturnResultError(request, e);
        }
    }

    /**
     * 通过id获取实体
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get", produces = {"application/json;charset=UTF-8"})
    public Object get(HttpServletRequest request) {
        try {
            Object obj = service.get(request);

            return ResponseResult.getReturnResult(request, obj);
        } catch (Exception e) {
            return ResponseResult.getReturnResultError(request, e);
        }
    }

    /**
     * 删除
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete", produces = {"application/json;charset=UTF-8"})
    public Object delete(HttpServletRequest request) {
        try {
            service.delete(request);

            return ResponseResult.getReturnResult(request, "");
        } catch (Exception e) {
            return ResponseResult.getReturnResultError(request, e);
        }
    }

    /**
     * 保存
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/save", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Object save(HttpServletRequest request) {
        try {
            return ResponseResult.getReturnResult(request, service.saveOrUpdate(request));
        } catch (Exception e) {
            return ResponseResult.getReturnResultError(request, e);
        }
    }
}
