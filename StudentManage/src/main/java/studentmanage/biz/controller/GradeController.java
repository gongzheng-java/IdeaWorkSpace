package studentmanage.biz.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import studentmanage.biz.model.Grade;
import studentmanage.biz.repository.GradeRepository;
import studentmanage.framework.base.controller.CrudBaseController;

/**
 * Created by gongzheng on 2019-07-20.
 */
@Controller
@RequestMapping("/grade")
public class GradeController extends CrudBaseController<Grade, GradeRepository> {



 

}
