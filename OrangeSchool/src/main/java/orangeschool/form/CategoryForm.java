package orangeschool.form;

public class CategoryForm {
	private String name;

    private String description;
    
    private Integer order;

    private Integer skillcount;
    
    public void setName(String _name)
    {
    	this.name = _name;
    }
    
    public String getName()
    {
    	return this.name;
    }
    
    public void setDescription(String _description)
    {
    	this.description = _description;
    }
    
    public String getDescription()
    {
    	return this.description;
    }

    
    public void setOrder(Integer _order)
    {
    	this.order = _order;
    }
    
    public Integer getOrder()
    {
    	return this.order;
    }
    
    public Integer getSkillcount()
	{
		return this.skillcount;
	}
	
	public void setSkillcount(Integer _count)
	{
		this.skillcount = _count;
	}
}
