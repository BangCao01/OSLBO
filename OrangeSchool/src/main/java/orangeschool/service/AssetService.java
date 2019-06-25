package orangeschool.service;

import java.util.List;

import orangeschool.model.Asset;
import orangeschool.model.Flashcard;
import orangeschool.model.Paragraph;

public interface AssetService {
    void save(Asset _image);
    List<Asset> findAll();
    Asset findByName(String _name);
    Asset findWithId(Integer _id);
    void deleteById(Integer _id);
}