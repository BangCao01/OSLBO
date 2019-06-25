package orangeschool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import orangeschool.model.Category;
import orangeschool.model.Englishtest;


public interface EnglishtestRepository extends JpaRepository<Englishtest, Integer> {
    Englishtest findByContent( String _content);
    Englishtest findByEnglishtestID(Integer _id);
    List<Englishtest> findByCategory(Category _category);
    List<Englishtest> findByStatusAndCategory(Integer _status, Category _category);
//	@Query("SELECT t FROM Englishtest t WHERE t.id = :ids")
//	Englishtest findWithId(@Param("ids") Integer _id);
	
}
