package studentmanage.biz.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studentmanage.biz.model.Student;
import studentmanage.biz.repository.StudentRepository;
import studentmanage.framework.base.service.CrudBaseService;
import studentmanage.util.convert.DataConvertUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by gongzheng on 2019-07-20.
 */
@Service(value = "StudentService")
public class StudentService extends CrudBaseService<Student, StudentRepository> {


    /**
     * 保存
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Student saveOrUpdate(HttpServletRequest request) throws Exception {
        //从前端获取id，当id大于0就是编辑，否则是新增
        int id = DataConvertUtil.strToInt(request.getParameter("id"));

        Student entity = null;
        boolean flag = false;//新增标志

        if (id > 0) {
            entity = this.get(id);
        }

        if (entity == null) {
            flag = true;
            entity = new Student();

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


    public List<Student> findAll() {

        return this.repository.findAll();
    }

    /**
     * 新增测试数据
     * @param flag
     * @return
     */
    @Transactional(rollbackFor = {Exception.class})
    public Student save(boolean flag) {
        Student student = new Student();
        student.setName("张三");
        student.setAge(12);
        student.setGender("男");
        student.setClassid(1);
        student.setCreatetime(new Date());
        student.setDel_flag(1);

        //是否批量新增 true 批量新增，false 单个新增
        if (flag) {
            long startTime = System.currentTimeMillis();
            List<Student> students = new ArrayList<>();
            int i = 1;
            while (i <= 5000) {
                Student student1 = new Student();
                student1.setAge(23);
                student1.setName("张飞");
                student1.setGender("女");
                int num = (int) (Math.random() * 3) + 1;
                student1.setClassid(num);
                student1.setCreatetime(new Date());
                student1.setDel_flag(1);
                students.add(student1);
                i++;
            }
            System.out.println(students.size());
            this.repository.saveAll(students);
            long endTime = System.currentTimeMillis();
            long time = (endTime - startTime);
            System.out.println("批量新增耗时：" + time);
            return student;
        } else {
            return this.repository.save(student);
        }

    }

    @Transactional(rollbackFor = {Exception.class})
    public long deleteStu(int stuId, boolean flag) {
        if (flag) {
            long count = this.repository.count();
            this.repository.deleteAll();
            return count;
        } else {
            return this.repository.deleteStu(stuId);
        }

    }

    public Student findOneByName(String name) {
        return this.repository.findOneByName(name);
    }

    public Object findPageStu2(int pageNumber, int pageSize, Student student) {
        String date = DataConvertUtil.dateToStr(student.getCreatetime());
        date = "".equals(date) ? null : date;
        dataHandle(student);
        pageable = new PageRequest(pageNumber - 1, pageSize);
        System.out.println(student);
        return this.repository.findPageStu2(student.getName(), student.getGender(), student.getClassid(), date, pageable);
    }

    /**
     * 分页查询
     * 多种分页
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Page<Student> findPageStu(
            int pageNumber,
            int pageSize,
            Student student) {
        System.out.println("处理前：" + student);
        String date = DataConvertUtil.dateToStr(student.getCreatetime());
        date = "".equals(date) ? null : date;
        dataHandle(student);
        System.out.println("处理后：" + student);
        //排序 倒序
       /* Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "id"));
        pageable = new PageRequest(pageNumber-1, pageSize, sort);*/
        //不排序
        pageable = new PageRequest(pageNumber - 1, pageSize);

        Page<Student> page = null;
        //方法1：框架自带方法 没有查询条件
        //page= repository.findAll(pageable);

        //方法二：sql查询 查询速度非常慢 不推荐
        page = repository.findPageStu(student.getName(), student.getGender(), student.getClassid(), date, pageable);
        //分页
     /*   Criteria<Student> c = new Criteria<Student>();
        c.add(Restrictions.eq("name","张三"));
        Page<Student> objs = repository.findAll(c, pageable);*/
        return page;
    }

    private Student dataHandle(Student student) {
        if (student.getClassid() != null && student.getClassid().equals(0))
            student.setClassid(null);
        if ("".equals(student.getName()))
            student.setName(null);
        if ("".equals(student.getGender()))
            student.setGender(null);
        if ("".equals(student.getCreatetime()))
            student.setCreatetime(null);
        return student;
    }
}
