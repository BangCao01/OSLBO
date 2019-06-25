package orangeschool.service;

import java.util.List;

import orangeschool.model.Category;

public interface CategoryService {
    void save(Category _category);

    Category findByName(String _name);
    Category findById(Integer _id);
    List<Category> findAll();
    List<Category> findWithOrderInRange(Integer _min, Integer _max);
    void deleteById(Integer _id);
}