package orangeschool.form;

import org.springframework.web.multipart.MultipartFile;

public class FileForm {

	
	// Upload files.
    protected MultipartFile[] fileDatas = null;
   
    public MultipartFile[] getFileDatas() {
        return fileDatas;
    }
 
    public void setFileDatas(MultipartFile[] fileDatas) {
        this.fileDatas = fileDatas;
    }
    
    public boolean IsValidate()
    {
    	//System.out.println("LENGTH :" + this.fileDatas[0].getName());
    	return this.fileDatas[0].getOriginalFilename().length() > 4;
    }
    
}
