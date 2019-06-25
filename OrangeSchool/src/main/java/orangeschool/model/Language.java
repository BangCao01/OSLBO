package orangeschool.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class Language {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
    private Integer languageID;

    private String name;

    private String description;
    
    public Integer getId() {
		return languageID;
	}

	public void setId(Integer id) {
		this.languageID = id;
	}
	
	public Language() {
		 
    }
	public Language(String _name)
	{
		this.name = _name;
		this.description = "";
	}
	public Language(String _name, String _description)
	{
		this.name = _name;
		this.description = _description;
		
	}
	
	
	public String getName() {
		return this.name;
	}

	public void setName(String _name) {
		this.name = _name;
	}
    
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String _description) {
		this.description = _description;
	}
	
	
}
