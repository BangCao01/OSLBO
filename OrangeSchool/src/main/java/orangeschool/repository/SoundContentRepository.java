package orangeschool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import orangeschool.model.SoundContent;

public interface SoundContentRepository extends JpaRepository<SoundContent, Integer> {
	 SoundContent findByName(String _name);
	 SoundContent findBySoundID(Integer _id);
	 //SoundContent findBy(Integer _id);
//	 @Query("SELECT m FROM Soundcontent AS m WHERE m.id = :id")
//	 SoundContent findWithId(@Param("id") Integer _id);
}
