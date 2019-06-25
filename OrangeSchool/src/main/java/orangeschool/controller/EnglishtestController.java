package orangeschool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.security.MessageDigest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import orangeschool.WebUtil;
import orangeschool.controller.BaseController.ActionMenuCode;
import orangeschool.controller.BaseController.MenuCode;
import orangeschool.form.EnglishtestForm;
import orangeschool.form.StoryForm;
import orangeschool.form.TextContentForm;
import orangeschool.model.Admin;
import orangeschool.model.Category;
import orangeschool.model.ImageContent;
import orangeschool.model.Englishtest;
import orangeschool.model.SoundContent;
import orangeschool.model.Story;
import orangeschool.model.TextContent;
import orangeschool.model.Topic;
import orangeschool.service.AdminService;
import orangeschool.service.CategoryService;
import orangeschool.service.ImageContentService;
import orangeschool.service.SoundContentService;
import orangeschool.service.EnglishtestService;
import orangeschool.service.TextContentService;
import orangeschool.service.TopicService;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/ela") // This
public class EnglishtestController extends BaseController {

	@Autowired
	private EnglishtestService englishtestService;

	@Autowired
	private ImageContentService imageContentService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private TextContentService textContentService;
	@Autowired
	private TopicService topicService;
	@Autowired
	private SoundContentService soundContentService;

	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public String showIndexPage(Model model, Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Ela)) {
			return "redirect:/home/404";
		}
		
		Integer min = 100;
		Integer max = 113;
		
		List<Category> categories = categoryService.findWithOrderInRange(min, max);
		model.addAttribute("categories", categories);
		this.setSelectedActionMenu(model, ActionMenuCode.List);
		model.addAttribute("englishtests", englishtestService.findAll());
		this.setMessageAt(model, MessageCode.Index);
		this.setSelectedMenu(model, MenuCode.Ela);
		this.setAccessCode(model, _principal);
		return "ela/index";
	}
	
	@RequestMapping(value = { "/index/{categoryid}/{status}" }, method = RequestMethod.GET)
	public String showIndex2(
			@PathVariable("categoryid") int categoryid,
			@PathVariable("status") int status,
			Model model, 
			Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Ela)) {
			return "redirect:/home/404";
		}
		
		Integer min = 100;
		Integer max = 113;
		
		List<Category> categories = categoryService.findWithOrderInRange(min, max);
		model.addAttribute("categories", categories);
		model.addAttribute("categoryid", categoryid);
		this.setSelectedActionMenu(model, ActionMenuCode.List);
		model.addAttribute("englishtests", englishtestService.findByStatusAndCategoryID(status,categoryid));
		this.setMessageAt(model, MessageCode.Index);
		this.setSelectedMenu(model, MenuCode.Ela);
		this.setAccessCode(model, _principal);
		return "ela/index";
	}

	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public String showDetailPage(@PathVariable("id") int id, Model model, Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Ela)) {
			return "redirect:/home/404";
		}
		this.setMessageAt(model, MessageCode.Detail);
		model.addAttribute("englishtest", englishtestService.findWithId(id));
		this.setSelectedMenu(model, MenuCode.Ela);
		this.setAccessCode(model, _principal);
		return "ela/detail";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String showEditPage(@PathVariable("id") Integer id, Model model, Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Ela)) {
			return "redirect:/home/404";
		}
		model.addAttribute("englishtestID", id);
		

		Integer min = 100;
		Integer max = 113;
		
		List<Category> categories = categoryService.findWithOrderInRange(min, max);
		model.addAttribute("categories", categories);

		Englishtest englishtest = englishtestService.findWithId(id);
		EnglishtestForm theForm = new EnglishtestForm();

		theForm.setContent(englishtest.getContent());
		theForm.setTopicID(englishtest.getTopicID());
		theForm.setCategoryID(englishtest.getCategoryID());

		model.addAttribute("englishtestForm", theForm);
		
		model.addAttribute("topics", topicService.findByTypeCategoryID(1, englishtest.getCategoryID()));// 2 - type for MATH
		this.setSelectedMenu(model, MenuCode.Ela);
		this.setAccessCode(model, _principal);
		return "ela/edit";
	}

	@RequestMapping(value = { "/edit/{id}" }, method = RequestMethod.POST)
	public String doPostEditTest(@PathVariable("id") int id, HttpServletRequest request, Model model, //
			@ModelAttribute("englishtestForm") EnglishtestForm _theForm, Principal _principal) {

		if (this.checkForbidden(_principal, MenuCode.Ela)) {
			return "redirect:/home/404";
		}
		String content = _theForm.getContent();
		String answers = _theForm.getAnswers();
		String correctAnswer = _theForm.getCorrectAnswer();

		Integer topicID = _theForm.getTopicID();
		Integer type = _theForm.getQuestionType();
		Integer status = _theForm.getStatus();
		Integer categoryID = _theForm.getCategoryID();

		errorMessage = "";
		if (this.IsValidate(_theForm)) {

			User loginedUser = (User) ((Authentication) _principal).getPrincipal();
			Admin _author = this.userService.findByUsername(loginedUser.getUsername());
			TextContent textContent = this.textContentService.findByContent(content);
			Topic topic = this.topicService.findById(topicID);
			Category category = this.categoryService.findById(categoryID);
			Englishtest englishtest = this.englishtestService.findWithId(id);

			if (textContent == null) {
				textContent = new TextContent();
				textContent.setContent(content);
				textContent.setStatus(status);
				textContent.setAuthor(_author);
				textContent.setCreateDate(this.GetTime());

			}

			englishtest.setContent(textContent);

			englishtest.setStatus(status);
			englishtest.setAuthor(_author);
			englishtest.setTopic(topic);
			englishtest.setCategory(category);
			englishtest.setUpdateDate(this.GetTime());

			ImageContent image = null;
			SoundContent sound = null;
			
			if (errorMessage.isEmpty()) {
				this.textContentService.save(textContent);
				this.englishtestService.save(englishtest);
				this.setMessageAt(model, MessageCode.Edit);
				return "redirect:/ela/detail/" + englishtest.getId();
			}

		}

		model.addAttribute("englishtestID", id);
		model.addAttribute("topics", topicService.findByType(1));// 2 - type for MATH
		List<Category> types = categoryService.findWithOrderInRange(200, 204);
		model.addAttribute("types", types);
		model.addAttribute("englishtestForm", _theForm);
		this.setSelectedMenu(model, MenuCode.Ela);
		this.setAccessCode(model, _principal);
		model.addAttribute("errorMessage", errorMessage);
		return "ela/edit";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String showDeletePage(@PathVariable("id") int id, Model model, Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Ela)) {
			return "redirect:/home/404";
		}
		try {

			englishtestService.deleteById(id);
		} catch (Exception ex) {

		}
		this.setMessageAt(model, MessageCode.Delete);
		return "redirect:/ela/index";
	}

	@RequestMapping(value = { "/add/{categoryid}" }, method = RequestMethod.GET)
	public String showAddPage(
			@PathVariable("categoryid") int categoryid,
			Model model, Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Ela)) {
			return "redirect:/home/404";
		}
		
		Integer min = 100;
		Integer max = 113;
		
		List<Category> categories = categoryService.findWithOrderInRange(min, max);
		model.addAttribute("categories", categories);
		model.addAttribute("categoryid", categoryid);
		EnglishtestForm theForm = new EnglishtestForm();
		model.addAttribute("englishtestForm", theForm);
		model.addAttribute("topics", topicService.findByTypeCategoryID(1, categoryid));// 2 - type for MATH

		List<Category> types = categoryService.findWithOrderInRange(200, 204);
		model.addAttribute("types", types);
		this.setSelectedMenu(model, MenuCode.Ela);
		this.setSelectedActionMenu(model, ActionMenuCode.Add);
		this.setAccessCode(model, _principal);
		return "ela/add";
	}

	@RequestMapping(value = { "/add" }, method = RequestMethod.POST)
	public String doPostTest(HttpServletRequest request, Model model, //
			@ModelAttribute("mathSubjectForm") EnglishtestForm _theForm, Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Ela)) {
			return "redirect:/home/404";
		}

		String content = _theForm.getContent();
		String answers = _theForm.getAnswers();
		String correctAnswer = _theForm.getCorrectAnswer();

		Integer topicID = _theForm.getTopicID();
		Integer type = _theForm.getQuestionType();
		Integer categoryID = _theForm.getCategoryID();
		Integer status = 1;// not yet tested
		Englishtest englishtest = null;

		errorMessage = "";
		if (this.IsValidate(_theForm)) {

			User loginedUser = (User) ((Authentication) _principal).getPrincipal();
			Admin _author = this.userService.findByUsername(loginedUser.getUsername());
			TextContent textContent = this.textContentService.findByContent(content);
			Category category = this.categoryService.findById(categoryID);
			Topic topic = this.topicService.findById(topicID);
			if (textContent == null) {
				textContent = new TextContent();
				textContent.setContent(content);
				textContent.setStatus(status);
				textContent.setAuthor(_author);
				textContent.setCreateDate(this.GetTime());

			}
			englishtest = new Englishtest();
			englishtest.setContent(textContent);

			englishtest.setStatus(status);
			englishtest.setAuthor(_author);
			englishtest.setTopic(topic);
			englishtest.setCategory(category);
			
			englishtest.setCreateDate(WebUtil.GetTime());
			
			if (errorMessage.isEmpty()) {
				this.textContentService.save(textContent);
				this.englishtestService.save(englishtest);
				this.setMessageAt(model, MessageCode.Add);
				return "redirect:/ela/detail/" + englishtest.getId();
			}

		}

		model.addAttribute("englishtestForm", _theForm);
		model.addAttribute("topics", topicService.findByType(1));// 2 - type for MATH
		List<Category> types = categoryService.findWithOrderInRange(200, 204);
		model.addAttribute("types", types);
		model.addAttribute("errorMessage", errorMessage);
		this.setSelectedMenu(model, MenuCode.Ela);
		this.setAccessCode(model, _principal);
		this.setSelectedActionMenu(model, ActionMenuCode.Add);
		return "ela/add";
	}

	private boolean IsValidate(EnglishtestForm _theForm) {
		if (_theForm.getContent().length() <= 0) {
			errorMessage = "Subject field must be not empty";
			return false;
		}

		return true;
	}

	private boolean IsValidateSoundInput(EnglishtestForm _theForm, boolean _logError) {

		if (!(_theForm.getFileDatas()[1].getOriginalFilename().length() > 4)) {
			if (_logError)
				errorMessage = "Sound input must be not empty";
			return false;
		}
		return true;
	}

	private boolean IsValidateImageInput(EnglishtestForm _theForm, boolean _logError) {

		if (!(_theForm.getFileDatas()[0].getOriginalFilename().length() > 4)) {
			if (_logError)
				errorMessage = "Image input must be not empty";
			return false;
		}
		return true;
	}

	
}
