package orangeschool.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.Table;

@Entity // This tells Hibernate to make a table out of this class
@Table(name="Flashcard")
public class Flashcard extends AbstractModel{
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
    private Integer wordID;

    //private Integer wordID;

    private Integer status;

    
    public Integer getId() {
		return wordID;
	}

	public void setId(Integer id) {
		this.wordID = id;
	}
	
	public Flashcard() {
		 
    }
	
	
	public Integer getWordID() {
		return this.word.getId();
	}
	public String getWordText()
	{
		return this.word.getContent();
	}

	public TextContent getWord()
	{
		return this.word;
	}
	
	public void setWord(TextContent _word) {
		this.word = _word;
	}
    
    public void setAsset(Asset _asset)
    {
    	this.asset = _asset;
    }
    
    public String getAssetUrl()
    {
    	return this.asset.getUrl();
    }
	
    public Asset getAsset()
    {
    	return this.asset;
    }
	
	public Integer getStatus()
	{
		return this.status;
	}
	
	public void setStatus(Integer _status)
	{
		this.status = _status;
	}
	
	
	
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="wordID")
    private TextContent word;
	
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="assetID")
    private Asset asset;
	
}
