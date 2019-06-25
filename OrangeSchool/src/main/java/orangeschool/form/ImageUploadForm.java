package orangeschool.form;

import org.springframework.web.multipart.MultipartFile;

public class ImageUploadForm extends FileForm{

	private String uri;

	public String url;
	
    private String name;
    
    private String description;
	
   
    public void setUri(String _uri)
    {
    	this.uri = _uri;
    }
    
    public String getUrl()
    {
    	return this.url;
    }
    
    public void setUrl(String _url)
    {
    	this.url = _url;
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
    
    public void setDescription(String _description)
    {
    	this.description = _description;
    }
    
    public String getDescription()
    {
    	return this.description;
    }
    
 
    
}
