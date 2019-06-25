package orangeschool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import orangeschool.model.Admin;
import orangeschool.model.TextContent;
import orangeschool.model.Translator;

public interface TranslatorRepository extends JpaRepository<Translator, Integer> {
	
	Translator findByContent(TextContent _content);
	List<Translator> findByAuthor(Admin _author);
	Translator findByTranslatorID(Integer _translatorID);
	
}
