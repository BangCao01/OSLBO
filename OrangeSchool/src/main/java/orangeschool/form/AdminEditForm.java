package orangeschool.form;

public class AdminEditForm {

	private String username;

    private String password;
    
    private String confirmPassword;
    
    private Integer permission;
    
	
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
    
    public void setPermission(Integer _permision)
    {
    	this.permission = _permision;
    }
    
    public Integer getPermission()
    {
    	return this.permission;
    }
    
    public void setConfirmPassword(String _confirmPassword)
    {
    	this.confirmPassword = _confirmPassword;
    }
    
    public String getConfirmPassword()
    {
    	return this.confirmPassword;
    }
    
}
