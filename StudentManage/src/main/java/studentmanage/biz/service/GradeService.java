package studentmanage.biz.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studentmanage.biz.model.Grade;
import studentmanage.biz.repository.GradeRepository;
import studentmanage.framework.base.service.CrudBaseService;
import studentmanage.util.convert.DataConvertUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by gongzheng on 2019-07-20.
 */
@Service(value = "GradeService")
public class GradeService extends CrudBaseService<Grade,GradeRepository> {

    /**
     * 保存
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Grade saveOrUpdate(HttpServletRequest request) throws Exception {
        //从前端获取id，当id大于0就是编辑，否则是新增
        int id = DataConvertUtil.strToInt(request.getParameter("id"));

        Grade entity = null;
        boolean flag = false;//新增标志

        if (id > 0) {
            entity = this.get(id);
        }

        if (entity == null) {
            flag = true;
            entity = new Grade();

            entity.setCreatetime(new Date());
        }

      /*  //各字段赋值
        //DataConvertUtil包含各种类型的转换函数，而且会处理格式错误的情况
        //以下字段保存代码是自动生成，不需要保存的字段可以屏蔽
        Long deviceTypeId = DataConvertUtil.strToLongNull(request.getParameter("deviceTypeId"));

        entity.setFacilityId(DataConvertUtil.strToLongNull(request.getParameter("facilityId")));
        entity.setName(DataConvertUtil.strToStr(request.getParameter("name")));
        entity.setDeviceTypeId(deviceTypeId);
        entity.setGateId(DataConvertUtil.strToLongNull(request.getParameter("gateId")));
        entity.setFrontBack(DataConvertUtil.strToStr(request.getParameter("frontBack")));
//        entity.setCreateDate(DataConvertUtil.strToDate(request.getParameter("createDate")));*/



        return entity;
    }

    /**
     * 获取所有的班级信息
     * @return
     */
    public List<Grade> findAll(){
        return this.repository.findAll();
    }

}
