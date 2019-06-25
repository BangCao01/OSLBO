package orangeschool.service;

import java.util.List;

import orangeschool.model.Category;
import orangeschool.model.Englishtest;

public interface EnglishtestService {
    void save(Englishtest _subject);

    Englishtest findByContent(String _content);
    Englishtest findWithId(Integer _id);
    List<Englishtest> findAll();
    List<Englishtest> findByCategory(Category _category);
    List<Englishtest> findByCategoryID(Integer _categoryID);
    List<Englishtest> findByStatusAndCategory(Integer _status, Category _category);
    List<Englishtest> findByStatusAndCategoryID(Integer _status, Integer _categoryID);
    void deleteById(Integer _id);
    
}