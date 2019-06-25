package orangeschool.model;


import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "Asset")
public class Asset extends AbstractModel{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private Integer assetID;

    private String uri;

    private String url;
    
    private String name;
        
   
	public Integer getId() {
		return assetID;
	}

	public void setId(Integer id) {
		this.assetID = id;
	}
	
	public Asset() {
		 
    }
	public Asset(String _uri) {
        this.uri = _uri;
    }
    
    
    public void setUri(String _uri)
    {
    	this.uri = _uri;
    }
    
    public String getUri()
    {
    	return this.uri;
    }
    
    public void setName(String _name)
    {
    	this.name = _name;
    }
    
    public String getName()
    {
    	return this.name;
    }
     
    public void setUrl(String _url)
    {
    	this.url = _url;
    }
    
    public String getUrl()
    {
    	return this.url;
    }
    

    @OneToOne(fetch=FetchType.LAZY, mappedBy="asset")
    private Flashcard flashcard;
}