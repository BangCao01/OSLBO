package orangeschool.form;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public class TextContentForm extends FileForm {

    private String content;

    private Integer soundID;
    
    private Integer status;
    
    private String soundName;
    private String soundDescription;
	
	public TextContentForm() {
		 
    }
	
    public void setContent(String _content)
    {
    	this.content = _content;
    }
    
    public String getContent()
    {
    	return this.content;
    }
    
    public void setSoundID(Integer _soundID)
    {
    	this.soundID = _soundID;
    }
    
    public Integer getSoundID()
    {
    	return this.soundID;
    }
    
    public void setStatus(Integer _status)
    {
    	this.status = _status;
    }
    
    public Integer getStatus()
    {
    	return this.status;
    }
    
    public void setSoundName(String _name)
    {
    	this.soundName = _name;
    }
    
    public String getSoundName()
    {
    	return this.soundName;
    }
    
    public void setSoundDescription(String _description)
    {
    	this.soundDescription = _description;
    }
    
    public String getSoundDescription()
    {
    	return this.soundDescription;
    }
    
    
    
    
}