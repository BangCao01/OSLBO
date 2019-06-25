package orangeschool.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "Soundcontent")
public class SoundContent extends AbstractModel{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private Integer soundID;

    private String uri;
    
    private String url;
    
    private String name;
    
    private String description;
    
	public Integer getId() {
		return soundID;
	}

	public void setId(Integer id) {
		this.soundID = id;
	}
	
	public SoundContent() {
		 
    }
	public SoundContent(String _uri) {
        this.uri = _uri;
    }
    public SoundContent(String _uri, String _name) {
        this.uri = _uri;
        this.name = _name;
    }
    
    public SoundContent(String _uri, String _name, String _description) {
        this.uri = _uri;
        this.name = _name;
        this.description = _description;
    }
    
    public void setUri(String _uri)
    {
    	this.uri = _uri;
    }
    
    public String getUri()
    {
    	return this.uri;
    }
    
    public void setUrl(String _url)
    {
    	this.url = _url;
    }
    
    public String getUrl()
    {
    	return this.url;
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
    
    @OneToOne(fetch=FetchType.LAZY, mappedBy="sound")
	private TextContent text;
    
    @OneToOne(fetch=FetchType.LAZY, mappedBy="sound")
	private ImageContent image;
    
}