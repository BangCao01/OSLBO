package orangeschool.api;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import orangeschool.WebUtil;
import orangeschool.model.Customer;
import orangeschool.model.Result;
import orangeschool.service.EnglishtestService;
import orangeschool.service.MathSubjectService;
import orangeschool.service.ResultService;
import orangeschool.service.UserService;


@RestController
public class ApiController {
	
	@Autowired
	private ResultService resultService;

	@Autowired
	private MathSubjectService mathSubjectService;

	@Autowired
	private EnglishtestService elaService;
	
	@Autowired
	private UserService userService;
	

	enum SubjectType{
		Math,
				
	}
	@RequestMapping("/loganswer")
    public Result logAnswer(
    		@RequestParam(value="customerid", defaultValue="0") Integer _customerid,
    		@RequestParam(value="subjectid", defaultValue="0") Integer _subjectid,
    		@RequestParam(value="type", defaultValue="0") Integer _type,
    		@RequestParam(value="answer", defaultValue="0") Integer _answer
    		
    		) {
        
		Customer customer= this.userService.findById(_customerid);
		
		
		Result ret = new Result();
		ret.setAnswer(_answer.toString());
		ret.setCustomer(customer);
		ret.setSubjectID(_subjectid);
		ret.setType(_type);
		ret.setCreateDate(WebUtil.GetTime());
		
		this.resultService.save(ret);
		
		return ret;
    }

}
