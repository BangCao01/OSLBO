package orangeschool.service;

import java.util.List;

import orangeschool.model.Admin;
import orangeschool.model.Translator;

public interface TranslatorService {
    void save(Translator _tranlator);

    Translator findByContent(String _name);
    Translator findByContentID(Integer _id);
    Translator findByTranslatorID(Integer _id);
    List<Translator> findAll();
    void deleteById(Integer _id);
    List<Translator> findByAuthor(Admin _author);
    List<Translator> findByAuthorID(Integer _authorID);
    
    
}