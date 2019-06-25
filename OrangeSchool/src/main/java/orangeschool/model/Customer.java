package orangeschool.model;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity // This tells Hibernate to make a table out of this class
@Table(name="User")
public class Customer {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private Integer customerID;

    private String username;

    private String password;
    
    private String phonenumber;
    private String address;
    private String email;
    private Integer status;
    @Column(name="create_date")
    private String createDate;
    @Column(name="update_date")
    private String updateDate;
    @Column(name="expire_date")
    private String expireDate;
    
	public Integer getId() {
		return customerID;
	}

	public void setId(Integer id) {
		this.customerID = id;
	}
	
	public Customer() {
		 
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
    
    public void setPhonenumber(String _phonenumber)
    {
    	this.phonenumber = _phonenumber;
    }
    
    public String getPhonenumber()
    {
    	return this.phonenumber;
    }
    
    
    public void setAddress(String _address)
    {
    	this.address = _address;
    }
    
    public String getAddress()
    {
    	return this.address;
    }
    
    public void setEmail(String _email)
    {
    	this.email = _email;
    }
    
    public String getEmail()
    {
    	return this.email;
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
    
    public void setExpireDate(String _expireDate)
    {
    	this.expireDate = _expireDate;
    }
    
    public String getExpireDate()
    {
    	return this.expireDate;
    }
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Transaction> transactions;
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Result> results;
}