package orangeschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

import orangeschool.repository.AssetRepository;
import orangeschool.model.Asset;
import orangeschool.model.Flashcard;
import orangeschool.model.Paragraph;

@Service
public class AssetServiceImpl implements AssetService {
    @Autowired
    private AssetRepository assetRepository;
//    @Autowired
//    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(Asset _asset) {
        
        
    	assetRepository.save(_asset);
    }

    @Override
    public List<Asset> findAll() {
        return assetRepository.findAll();
    }
    @Override
    public Asset findByName(String _name) {
        Asset asset =  assetRepository.findByName(_name);
        return asset;
    }
    
    @Override
    public Asset findWithId(Integer _id) {
    	Asset asset = assetRepository.findByAssetID(_id);
        return asset;
    }

    @Override
    public void deleteById(Integer _id)
    {
    	assetRepository.deleteById(_id);
    }
}