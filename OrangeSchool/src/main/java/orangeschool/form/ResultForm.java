package orangeschool.form;

public class ResultForm {
	private String description;
    private Integer subjectID;
    private Integer customerID;
    private Integer type;
    private Integer score;
    
    public void setDescription(String _description)
    {
    	this.description = _description;
    }
    
    public String getDescription()
    {
    	return this.description;
    }
    
    
    
    public void setType(Integer _type)
    {
    	this.type = _type;
    }
    
    public Integer getType()
    {
    	return this.type;
    }
    
    public void setScore(Integer _score)
    {
    	this.score = _score;
    }
    
    public Integer getScore()
    {
    	return this.score;
    }
    
    public void setSubjectID(Integer _subjectID)
    {
    	this.subjectID = _subjectID;
    }
    
    public Integer getSubjectID()
    {
    	return this.subjectID;
    }
   
   
    public void setCustomerID(Integer _id)
    {
    	this.customerID = _id;
    }
    
    public Integer getCustomerID()
    {
    	return this.customerID;
    }
}
