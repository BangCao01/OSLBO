package orangeschool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import orangeschool.model.ImageContent;


public interface ImageContentRepository extends JpaRepository<ImageContent, Integer> {
	 ImageContent findByName(String _name);
	 ImageContent findByImageID(Integer _id);
}
