package orangeschool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import orangeschool.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	Category findByName(String _name);
	Category findByCategoryID(Integer _id);
//	@Query("SELECT t FROM Category t WHERE t.id = :ids")
//	Category findWithId(@Param("ids") Integer _id);

	@Query("SELECT t.id , t.name, t.description, t.item_order FROM Category AS t WHERE t.item_order >= :min AND t.item_order <= :max")
	List<Category> findWithOrderInRange(@Param("min") Integer _min, @Param("max") Integer _max);

}
