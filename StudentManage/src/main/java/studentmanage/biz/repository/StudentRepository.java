package studentmanage.biz.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import studentmanage.biz.model.Student;

import java.util.List;

/**
 * Created by gongzheng on 2019-07-20.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student,Integer>,JpaSpecificationExecutor<Student> {

    @Modifying
    @Query(value = "UPDATE student SET del_flag=0 WHERE id=?1",nativeQuery = true)
    int deleteStu(int stuId);

    @Query(value = "SELECT * FROM student WHERE del_flag=1",nativeQuery = true)
    List<Student> findAll();

    Student findOneByName(String name);



    /**
     * 分页查询
     * @param pageable
     * @return (?1 is null or t.name=?1)
     */
    @Query(value = "SELECT * FROM student t WHERE (?1 IS NULL or t.name=?1) AND (?2 is null or t.gender=?2)  AND (?3 IS NULL OR t.classId=?3) AND (?4 is null or DATE_FORMAT(t.createTime,'%Y-%m-%d')=DATE_FORMAT(?4,'%Y-%m-%d'))",
            countQuery = "SELECT count(1) FROM student t WHERE (?1 is null or t.name=?1) AND (?2 is null or t.gender=?2) AND (?3 IS NULL OR t.classId=?3) AND (?4 is null or DATE_FORMAT(t.createTime,'%Y-%m-%d')=DATE_FORMAT(?4,'%Y-%m-%d'))",
            nativeQuery = true)
    Page<Student> findPageStu(String name,String gender,Integer classid,String createtime,Pageable pageable);

    @Query(value = "SELECT * FROM student t WHERE (?1 is null or t.name=?1) AND (?2 is null or t.gender=?2) AND (?3 IS NULL OR t.classId=?3) AND (?4 is null or DATE_FORMAT(t.createTime,'%Y-%m-%d')=DATE_FORMAT(?4,'%Y-%m-%d'))",
            countQuery = "SELECT count(1) FROM student t WHERE (?1 is null or t.name=?1) AND (?2 is null or t.gender=?2) AND (?3 IS NULL OR t.classId=?3) AND (?4 is null or DATE_FORMAT(t.createTime,'%Y-%m-%d')=DATE_FORMAT(?4,'%Y-%m-%d'))",
            nativeQuery = true)
    Page<Student> findPageStu2(String name,String gender,Integer classid,String createtime,Pageable pageable);





}
