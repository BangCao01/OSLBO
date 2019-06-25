package orangeschool.form;

public class ProductForm extends FileForm{
	private String name;

    private String description;
    
    private Integer price;

    public ProductForm()
    {
    	
    }
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

    
    public void setPrice(Integer _price)
    {
    	this.price = _price;
    }
    
    public Integer getPrice()
    {
    	return this.price;
    }
    
   
}
