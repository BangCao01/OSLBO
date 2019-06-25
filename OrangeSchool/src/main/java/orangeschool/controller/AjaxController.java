package orangeschool.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import orangeschool.form.ParagraphForm;

@RestController
public class AjaxController {
	
	@PostMapping("/rest/")
    public ResponseEntity<?> multiUploadFileModel(@ModelAttribute ParagraphForm form) {
 
        //System.out.println("Description:" + form.getDescription());
 
        String result = null;
//        try {
// 
//            result = this.saveUploadedFiles(form.getFileDatas());
//            form.getFileDatas();
// 
//        }
//        // Here Catch IOException only.
//        // Other Exceptions catch by RestGlobalExceptionHandler class.
//        catch (IOException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
 
        return new ResponseEntity<String>("Uploaded to: <br/>" + result, HttpStatus.OK);
 
    }

}
