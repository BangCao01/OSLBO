package orangeschool.form;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public class StoryForm extends FileForm {

    private String title;
    private String description;
    private Integer categoryID;
    
    private Integer status;
    
   
	public StoryForm() {
		 
    }
	
    public void setTitle(String _story)
    {
    	this.title = _story;
    }
    
    public String getTitle()
    {
    	return this.title;
    }
    
    public void setCategoryID(Integer _categoryID)
    {
    	this.categoryID = _categoryID;
    }
    
    public Integer getCategoryID()
    {
    	return this.categoryID;
    }
    
    public void setStatus(Integer _status)
    {
    	this.status = _status;
    }
    
    public Integer getStatus()
    {
    	return this.status;
    }
    
    
    public void setDescription(String _description)
    {
    	this.description = _description;
    }
    
    public String getDescription()
    {
    	return this.description;
    }
    
    
    
    
}