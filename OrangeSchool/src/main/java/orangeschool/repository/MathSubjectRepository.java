package orangeschool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import orangeschool.model.Category;
import orangeschool.model.MathSubject;
import orangeschool.model.Topic;

public interface MathSubjectRepository extends JpaRepository<MathSubject, Integer> {
	MathSubject findBySubject(String _subject);
	MathSubject findByMathID(Integer _id);
	List<MathSubject> findByTopic(Topic _topic);
	List<MathSubject> findByCategory(Category _category);
	List<MathSubject> findByStatusAndCategory(Integer _status, Category _category);
}
