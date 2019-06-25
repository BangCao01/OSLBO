package orangeschool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import orangeschool.model.Asset;
import orangeschool.model.Paragraph;

public interface AssetRepository extends JpaRepository<Asset, Integer> {
	 Asset findByName(String _name);
	 Asset findByAssetID(Integer _id);
//	 @Query("SELECT t FROM Asset t WHERE t.id = :ids")
//	 Asset findWithId(@Param("ids") Integer _id);
	
}
