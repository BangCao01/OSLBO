package orangeschool.form;

public class CustomerEditForm {

	private String username;

    private String password;
    
    private String phonenumber;
    private String address;
    private String email;
    private Integer status;
    
	
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
    
    public void setStatus(Integer _status)
    {
    	this.status = _status;
    }
    
    public Integer getStatus()
    {
    	return this.status;
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
    
    
    
    
}
