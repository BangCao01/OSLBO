package orangeschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import orangeschool.repository.AdminRepository;
import orangeschool.repository.TextContentRepository;
import orangeschool.repository.TranslatorRepository;
import orangeschool.model.Admin;
import orangeschool.model.TextContent;
import orangeschool.model.Translator;

@Service
public class TranslatorServiceImpl implements TranslatorService {
    @Autowired
    private TranslatorRepository translatorRepository;
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private TextContentRepository textContentRepository;
    
    @Override
    public void save(Translator _translator) {
        
    	translatorRepository.save(_translator);
    }

    
    
    @Override
    public Translator findByContentID(Integer _id) {
    	TextContent content = textContentRepository.findByTextID(_id);
    	Translator translator = translatorRepository.findByContent(content);
        return translator;
    }
    
    @Override
    public Translator findByContent(String _content) {
    	TextContent content = textContentRepository.findByContent(_content);
    	Translator translator = translatorRepository.findByContent(content);
        return translator;
    }
    
    @Override
    public Translator findByTranslatorID(Integer _id)
    {
    	return this.translatorRepository.findByTranslatorID(_id);
    }
    
    
    @Override
    public List<Translator> findByAuthor(Admin _author)
    {
    	return this.translatorRepository.findByAuthor(_author);
    }
    @Override
    public List<Translator> findByAuthorID(Integer _authorID)
    {
    	Admin author = this.adminRepository.findByUserID(_authorID);
    	return this.findByAuthor(author);
    }
    
    @Override
    public List<Translator> findAll() {
        return translatorRepository.findAll();
    }

    
    
    @Override
    public void deleteById(Integer _id)
    {
    	this.translatorRepository.deleteById(_id);
    }
    
    

}