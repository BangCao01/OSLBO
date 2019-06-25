package orangeschool.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity // This tells Hibernate to make a table out of this class
public class Translator extends AbstractModel{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private Integer translatorID;

    @Column(name="japanese")
    private String jp;
    @Column(name="vietnamese")
    private String vn;
    @Column(name="indonesian")
    private String indo;
    @Column(name="portuguese")
    private String ptu;
    

    //private Integer contentID;
    
    private Integer status;
    
    
	public Integer getId() {
		return translatorID;
	}

	public void setId(Integer id) {
		this.translatorID = id;
	}
	
	public Translator() {
		 
    }
	
    
    public void setContent(TextContent _content)
    {
    	this.content = _content;
    }
    
    public String getContent()
    {
    	return (this.content != null)? this.content.getContent():"";
    }
    
    public Integer getContentID()
    {
    	return this.content.getId();
    }
    
    public void setJapanese(String _content)
    {
    	this.jp = _content;
    }
    
    public String getJapanese()
    {
    	return this.jp;
    }
    
    public void setVietnamese(String _content)
    {
    	this.vn = _content;
    }
    
    public String getVietnamese()
    {
    	return this.vn;
    }
    
    public void setIndonesian(String _content)
    {
    	this.indo = _content;
    }
    
    public String getIndonesian()
    {
    	return this.indo;
    }
    
    public void setPortuguese(String _content)
    {
    	this.ptu = _content;
    }
    
    public String getPortuguese()
    {
    	return this.ptu;
    }
    
    public void setStatus(Integer _status)
    {
    	this.status = _status;
    }
    
    public Integer getStatus()
    {
    	return this.status;
    }
    
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="contentID")
    private TextContent content;
    
    
}