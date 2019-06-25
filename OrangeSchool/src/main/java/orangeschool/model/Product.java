package orangeschool.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity // This tells Hibernate to make a table out of this class
public class Product {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
    private Integer productID;

    private Integer price;

    private Integer period;
    
    public Integer getId() {
		return productID;
	}

	public void setId(Integer id) {
		this.productID = id;
	}
	
	public Product() {
		 
    }
	
	public Integer getNameID() {
		return (name != null)?this.name.getId():0;
	}

	public String getName()
	{
		return (name != null)?this.name.getContent():"";
	}
	public void setName(TextContent _name) {
		this.name = _name;
	}
    
	public Integer getDescriptionID()
	{
		return (this.description != null) ? this.description.getId():0;
	}
	
	public String getDescription()
	{
		return (description != null)?this.description.getContent():"";
	}
	public void setDescription(TextContent _description)
	{
		this.description = _description;
	}
	
	public Integer getImageID() {
		
		return (image!=null)? this.image.getId():0;
	}

	public String getImageUrl()
	{
		return (image != null)?this.image.getUrl():"";
	}
	public ImageContent getImage()
	{
		return this.image;
	}
	public void setImage(ImageContent _image) {
		this.image = _image;
	}
	
	public Integer getPrice()
	{
		return this.price;
	}
	
	public void setPrice(Integer _price)
	{
		this.price = _price;
	}
	
	public Integer getPeriod()
	{
		return this.period;
	}
	
	public void setPeriod(Integer _period)
	{
		this.period = _period;
	}
	
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="nameID")
    private TextContent name;
	
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="descriptionID")
    private TextContent description;
	
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="imageID")
    private ImageContent image;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Transaction> transactions;
	
}
