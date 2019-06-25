package orangeschool.model;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.*;


@Entity // This tells Hibernate to make a table out of this class
@Table(name = "Admin")
public class Admin implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private Integer userID;

    //@Column(nullable = false)
    private String username;

    //@Column(nullable = false)
    private String password;
    //@Column(nullable = false)
    private Integer permission;
    //@Column(nullable = false)
    private Integer status;
    //@Column(nullable = false)
    private String createDate;
    private String updateDate;
    
 
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private Set<ImageContent> images;
    
	public Integer getId() {
		return userID;
	}

	public void setId(Integer id) {
		this.userID = id;
	}
	
	public Admin() {
		 
    }
 
    public Admin(String _username, String _password) {
        this.username = _username;
        this.password = _password;
        this.permission = 1;//admin
    }
    
    public Admin(String _username, String _password, Integer _permission) {
        this.username = _username;
        this.password = _password;
        this.permission = _permission;
    }
    
    public void setUsername(String _username)
    {
    	this.username = _username;
    }
    
    public String getUsername()
    {
    	return this.username;
    }
    
    public void setPassword(String _password)
    {
    	this.password = _password;
    }
    
    public String getPassword()
    {
    	return this.password;
    }
    
    public void setPermission(Integer _permission)
    {
    	this.permission = _permission;
    }
    
    public Integer getPermission()
    {
    	return this.permission;
    }
    
    public void setStatus(Integer _status)
    {
    	this.status = _status;
    }
    
    public Integer getStatus()
    {
    	return this.status;
    }
    
    public void setCreateDate(String _createDate)
    {
    	this.createDate = _createDate;
    }
    
    public String getCreateDate()
    {
    	return this.createDate;
    }
    
    public void setUpdateDate(String _updateDate)
    {
    	this.updateDate = _updateDate;
    }
    
    public String getUpdateDate()
    {
    	return this.updateDate;
    }
    
   
    public Set<ImageContent> getImages() {
        return images;
    }
    
    public void setImages(Set<ImageContent> _images) {
        this.images = _images;
    }
    
     
    
}