package studentmanage.biz.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import studentmanage.biz.model.Grade;
import studentmanage.biz.model.Student;
import studentmanage.biz.repository.StudentRepository;
import studentmanage.biz.service.GradeService;
import studentmanage.biz.service.StudentService;
import studentmanage.framework.base.controller.CrudBaseController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by gongzheng on 2019-07-20.
 */
@Controller
@RequestMapping("/student")
public class StudentController extends CrudBaseController<Student, StudentRepository> {

    @Autowired
    private GradeService gradeService;

    /**
     * 新增测试数据 单个
     * @return
     */
    @ResponseBody
    @RequestMapping("/save")
    public Student save() {
        System.out.println("新增");
        return ((StudentService) service).save(false);
    }

    /**
     * 批量新增
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/save2")
    public Student save2() {
        return ((StudentService) service).save(true);
    }

    /**
     * 根据id 删除
     *
     * @param stuId
     * @return
     */
    @ResponseBody
    @RequestMapping("/delete")
    public long deleteStu(int stuId) {
        return ((StudentService) service).deleteStu(stuId, false);
    }

    /**
     * 批量删除
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteAll")
    public long deleteAll(HttpServletRequest request) {
        int stuId = Integer.parseInt(request.getParameter("stuId"));
        return ((StudentService) service).deleteStu(stuId, true);
    }

    @ResponseBody
    @RequestMapping("/findAll")
    public List<Student> findAll() {
        return ((StudentService) service).findAll();
    }


    @ResponseBody
    @RequestMapping("/findOneByName")
    public Student findOneByName(String name) {
        return ((StudentService) service).findOneByName(name);
    }

    @RequestMapping("/findAll2")
    public String test(Model model) {
        List<Student> students = ((StudentService) service).findAll();
        model.addAttribute("students", students);
        System.out.println("查询所有");
        return "/showStudent";
    }

    /**
     * 分页查询
     * 多条件
     * @return
     */
    @ResponseBody
    @RequestMapping("/findPageStu2")
    public Object findPageStu2( @RequestParam(required = false, defaultValue = "1") int pageNumber,
                                @RequestParam(required = false, defaultValue = "20")int pageSize,
                                @RequestParam(required = false)Student student) {
        System.out.println();
        return ((StudentService) service).findPageStu2(pageNumber, pageSize,student);
    }

    /**
     * 分页查询
     *
     * @return
     */

    @RequestMapping("/findPageStu")
    public String findPageStu(@RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
                              @RequestParam(name = "pageSize", defaultValue = "20") int pageSize,
                              Student student,
                              Model model) {

        if (pageNumber <= 0) pageNumber = 1;
        //获取学生信息
        Page<Student> page = ((StudentService) service).findPageStu(pageNumber, pageSize,student);
        //获取年级信息
        List<Grade> grades = gradeService.findAll();
        model.addAttribute("students", page.getContent());
        model.addAttribute("grades", grades);
        model.addAttribute("student", student);
        model.addAttribute("Total", page.getTotalElements());
        model.addAttribute("TotalPage", page.getTotalPages());
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);
        return "/showStudent";
    }



}
