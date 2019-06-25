package orangeschool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import orangeschool.model.Paragraph;

public interface ParagraphRepository extends JpaRepository<Paragraph, Integer> {
//	@Query("SELECT * FROM Paragraph t WHERE t.storyID = :ids")
//	List<Paragraph> findByStoryId(@Param("ids") Integer _id);
//	
	List<Paragraph> findByStoryId(Integer _id);
	Paragraph findByParagraphID(Integer _id);
	@Query("SELECT t FROM Paragraph t WHERE t.id = :ids")
	Paragraph findWithId(@Param("ids") Integer _id);

}
