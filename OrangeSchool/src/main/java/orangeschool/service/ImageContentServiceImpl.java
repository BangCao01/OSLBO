package orangeschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

import orangeschool.repository.ImageContentRepository;
import orangeschool.model.ImageContent;

@Service
public class ImageContentServiceImpl implements ImageContentService {
    @Autowired
    private ImageContentRepository imageRepository;
   
    @Override
    public void save(ImageContent _image) {
        
    	imageRepository.save(_image);
    }

    @Override
    public ImageContent findByName(String _name) {
        ImageContent image =  imageRepository.findByName(_name);
        return image;
    }
    @Override
    public List<ImageContent> findAll()
    {
    	return this.imageRepository.findAll();
    }
    
    @Override
    public void deleteById(Integer _id)
    {
    	this.imageRepository.deleteById(_id);
    }

    
    @Override
    public ImageContent findById(Integer _id)
    {
    
    	return this.imageRepository.findByImageID(_id);
    	
    }
}