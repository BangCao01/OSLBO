package orangeschool.api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import orangeschool.WebUtil;
import orangeschool.model.Category;
import orangeschool.model.Customer;
import orangeschool.model.MathSubject;
import orangeschool.model.Result;
import orangeschool.model.Topic;
import orangeschool.service.CategoryService;
import orangeschool.service.EnglishtestService;
import orangeschool.service.MathSubjectService;
import orangeschool.service.ResultService;
import orangeschool.service.TopicService;
import orangeschool.service.UserService;


@RestController
public class MathSubjectApiController {
	private Integer type = 2;

	@Autowired
	private MathSubjectService mathSubjectService;

	@Autowired
	private EnglishtestService elaService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private TopicService topicService;
	
	
	enum SubjectType{
		Math,
				
	}
	
	
	@RequestMapping("/math/generate")
    public void generateMathDataBy(
    		@RequestParam(value="categoryid", defaultValue="0") int _categoryid,
    		HttpServletRequest request
    		//@RequestParam(value="status", defaultValue="5") int _status
    		
    		) {
		Category category = categoryService.findById(_categoryid);
		List<Topic> Maths = topicService.findByTypeParent(this.type, null);
		List<Topic> SubTopics = null;
		if(Maths.size() >0)
		SubTopics = topicService.findByTypeCategoryParent(this.type, category, Maths.get(0));
		
		List<Topic> ret = new ArrayList<Topic>();
		
		System.out.println(" SubTopics.size() :" + SubTopics.size());

		
		String _ret ="{\"data\":[";
		
		for(int i =0; i < SubTopics.size(); i++)
		{
			Topic topic = SubTopics.get(i);
			_ret += topic.toJsonString();
			_ret +=",";
			
		}
		_ret +="]}";
		
		
		String categoriesRootPath = request.getServletContext().getRealPath("categories");
		System.out.println("categoriesRootPath = " + categoriesRootPath);

		File categoryRootDir = new File(categoriesRootPath);

		// Tạo thư mục gốc upload nếu nó không tồn tại.
		if (!categoryRootDir.exists()) {
			categoryRootDir.mkdirs();
		}
		
		File mathDir = new File(categoryRootDir, "math");
		if (!mathDir.exists()) {
			mathDir.mkdirs();
		}
		
		String uri = mathDir.getAbsolutePath() + File.separator + _categoryid + ".txt";
		File serverFile = new File(uri);
		if(!serverFile.exists())
		{
			try {
				serverFile.createNewFile();
			}catch(Exception ex)
			{
				System.out.println("could not make new file");
			}
		}
		WebUtil.WriteText2File(_ret, uri);
		

	}
	
	
	@RequestMapping("/math/cache")
    public void getCacheMathDataBy(
    		@RequestParam(value="categoryid", defaultValue="0") int _categoryid,
    		HttpServletRequest request
    		//@RequestParam(value="status", defaultValue="5") int _status
    		
    		) {
		
		String categoriesRootPath = request.getServletContext().getRealPath("categories");
		System.out.println("categoriesRootPath = " + categoriesRootPath);

		File categoryRootDir = new File(categoriesRootPath);

		if (!categoryRootDir.exists()) {
			categoryRootDir.mkdirs();
		}
		
		File mathDir = new File(categoryRootDir, "math");
		if (!mathDir.exists()) {
			mathDir.mkdirs();
		}
		
		String uri = mathDir.getAbsolutePath() + File.separator + _categoryid + ".txt";
		ClassPathResource cacheFile = new ClassPathResource(uri);
		//cacheFile.getInputStream();

	}
	
	@RequestMapping("/math/add")
    public void addMathDataBy(
    		@RequestParam(value="topicid", defaultValue="0") Integer _topicid,
    		@RequestParam(value="categoryid", defaultValue="0") Integer _categoryid,
    		@RequestParam(value="subject", defaultValue="0") String _subject,
    		@RequestParam(value="description", defaultValue="0") String _description,
    		HttpServletRequest request
    		
    		) {
		
		
		
	}
	
	
}
