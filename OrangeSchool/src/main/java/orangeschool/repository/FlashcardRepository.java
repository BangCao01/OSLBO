package orangeschool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import orangeschool.model.Flashcard;

public interface FlashcardRepository extends JpaRepository<Flashcard, Integer> {
	
	Flashcard findByWordID(Integer _id);
//	@Query("SELECT t FROM Flashcard t WHERE t.id = :ids")
//	Flashcard findWithId(@Param("ids") Integer _id);
    
}
