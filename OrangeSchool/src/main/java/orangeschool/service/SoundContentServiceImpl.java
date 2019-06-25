package orangeschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

import orangeschool.repository.SoundContentRepository;
import orangeschool.model.ImageContent;
import orangeschool.model.SoundContent;

@Service
public class SoundContentServiceImpl implements SoundContentService {
    @Autowired
    private SoundContentRepository soundContentRepository;

    @Override
    public void save(SoundContent _sound) {
        
        
    	soundContentRepository.save(_sound);
    }

    @Override
    public SoundContent findByName(String _name) {
        SoundContent sound =  soundContentRepository.findByName(_name);
        return sound;
    }
    
//    @Override
//    public SoundContent findBy(Integer _id) {
//        SoundContent sound =  soundContentRepository.findBy(_id);
//        return sound;
//    }
    @Override
    public List<SoundContent> findAll() {
        return soundContentRepository.findAll();
    }
    @Override
    public void deleteById(Integer _id)
    {
    	this.soundContentRepository.deleteById(_id);
    }
    @Override
    public SoundContent findById(Integer _id)
    {
    	return soundContentRepository.findBySoundID(_id);
    	
    }


}