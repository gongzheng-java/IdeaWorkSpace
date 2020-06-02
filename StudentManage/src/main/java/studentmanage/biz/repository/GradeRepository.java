package studentmanage.biz.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import studentmanage.biz.model.Grade;

/**
 * Created by gongzheng on 2019-07-20.
 */
@Repository
public interface GradeRepository extends JpaRepository<Grade,Integer>,JpaSpecificationExecutor<Grade> {








}
