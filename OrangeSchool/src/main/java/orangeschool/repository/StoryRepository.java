package orangeschool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import orangeschool.model.Story;

public interface StoryRepository extends JpaRepository<Story, Integer> {
	Story findByTitle(String _subject);
	//Story findByStoryID(Integer _id);
	@Query("SELECT t FROM Story t WHERE t.id = :ids")
	Story findWithId(@Param("ids") Integer _id);

}
