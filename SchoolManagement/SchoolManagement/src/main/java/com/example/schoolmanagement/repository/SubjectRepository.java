package com.example.schoolmanagement.repository;

import com.example.schoolmanagement.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, String> {
    @Query("select school.name as schoolName,sub.name as subjectName,std.name as standardName " + "FROM School school ,Standard std ,Subject sub " + " WHERE school.name = :school AND sub.name = :subject AND ( (:isLesserThan = true AND std.name <= :range)" + " OR (:isLesserThan = false AND std.name >= :range) )")
    List<Object> search(@Param("school") final String school, @Param("subject") final String subject, @Param("range") final String range, @Param("isLesserThan") final Boolean isLesserThan);
}
