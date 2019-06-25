package orangeschool.form;

public class TopicForm {
	private String name;

    private Integer parentID;
    private Integer categoryID;
    private Integer order;
    private Integer type;

    public TopicForm()
    {
    	order = 0;
    }
    public void setName(String _name)
    {
    	this.name = _name;
    }
    
    public String getName()
    {
    	return this.name;
    }
    
    public void setType(Integer _type)
    {
    	this.type = _type;
    }
    
    public Integer getType()
    {
    	return this.type;
    }

    public void setCategoryID(Integer _categoryID)
    {
    	this.categoryID = _categoryID;
    }
    
    public Integer getCategoryID()
    {
    	return this.categoryID;
    }
    public void setOrder(Integer _order)
    {
    	this.order = _order;
    }
    
    public Integer getOrder()
    {
    	return this.order;
    }
    
    public void setParentID(Integer _parentID)
    {
    	this.parentID = _parentID;
    }
    
    public Integer getParentID()
    {
    	return this.parentID;
    }
}
