package orangeschool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import orangeschool.model.TextContent;

public interface TextContentRepository extends PagingAndSortingRepository<TextContent, Integer> {
	 TextContent findByContent(String _content);
	 TextContent findByTextID(Integer _id);
	 
}
