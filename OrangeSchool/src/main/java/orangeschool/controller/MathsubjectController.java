package orangeschool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.MessageDigest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import orangeschool.controller.BaseController.MessageCode;
import orangeschool.form.MathSubjectForm;
import orangeschool.form.ParagraphForm;
import orangeschool.form.TextContentForm;
import orangeschool.model.Admin;
import orangeschool.model.Category;
import orangeschool.model.Englishtest;
import orangeschool.model.Topic;
import orangeschool.model.ImageContent;
import orangeschool.model.MathSubject;
import orangeschool.model.Paragraph;
import orangeschool.model.SoundContent;
import orangeschool.model.Story;
import orangeschool.model.TextContent;
import orangeschool.service.AdminService;
import orangeschool.service.CategoryService;
import orangeschool.service.TopicService;
import orangeschool.service.ImageContentService;
import orangeschool.service.MathSubjectService;
import orangeschool.service.TextContentService;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/math") // This
public class MathsubjectController extends BaseController {

	@Autowired
	private MathSubjectService mathSubjectService;
	@Autowired
	private ImageContentService imageContentService;
	@Autowired
	private TopicService topicService;
	@Autowired
	private TextContentService textContentService;
	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public String mathIndex(Model model, Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Math)) {
			return "redirect:/home/404";
		}

		Integer min = 49;
		Integer max = 62;

		List<Category> categories = categoryService.findWithOrderInRange(min, max);
		model.addAttribute("categories", categories);
		this.setSelectedActionMenu(model, ActionMenuCode.List);
		this.setMessageAt(model, MessageCode.Index);
		model.addAttribute("mathSubjects", mathSubjectService.findAll());
		this.setSelectedMenu(model, MenuCode.Math);
		this.setAccessCode(model, _principal);
		return "math/index";
	}

	@RequestMapping(value = { "/index/{categoryid}/{status}" }, method = RequestMethod.GET)
	public String mathIndexGrade(@PathVariable("categoryid") int categoryid, @PathVariable("status") int status,
			Model model, Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Math)) {
			return "redirect:/home/404";
		}

		Integer min = 49;
		Integer max = 62;

		List<Category> categories = categoryService.findWithOrderInRange(min, max);
		model.addAttribute("categories", categories);
		model.addAttribute("categoryid", categoryid);
		model.addAttribute("status", status);

		this.setSelectedActionMenu(model, ActionMenuCode.List);
		this.setMessageAt(model, MessageCode.Index);
		model.addAttribute("mathSubjects", mathSubjectService.findByStatusAndCategoryID(status, categoryid));
		this.setSelectedMenu(model, MenuCode.Math);
		this.setAccessCode(model, _principal);
		return "math/index";
	}

	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public String showDetailPage(@PathVariable("id") int id, Model model, Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Math)) {
			return "redirect:/home/404";
		}
		this.setMessageAt(model, MessageCode.Detail);
		model.addAttribute("mathSubject", mathSubjectService.findById(id));
		this.setSelectedMenu(model, MenuCode.Math);
		this.setAccessCode(model, _principal);
		return "math/detail";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String showEditPage(@PathVariable("id") int id, Model model, Principal _principal) {

		if (this.checkForbidden(_principal, MenuCode.Math)) {
			return "redirect:/home/404";
		}
		model.addAttribute("mathSubjectID", id);
		MathSubject mathSubject = mathSubjectService.findById(id);
		MathSubjectForm theForm = new MathSubjectForm();
		theForm.setSubject(mathSubject.getSubjectContent());
		theForm.setDescription(mathSubject.getDescriptionContent());
		
		theForm.setStatus(mathSubject.getStatus());
		theForm.setTopicID(mathSubject.getTopicID());
		theForm.setCategoryID(mathSubject.getCategoryID());
		Integer min = 49;
		Integer max = 62;

		List<Category> categories = categoryService.findWithOrderInRange(min, max);
		model.addAttribute("categories", categories);
		model.addAttribute("mathSubjectForm", theForm);// 2 - type for MATH
		this.setAccessCode(model, _principal);
		model.addAttribute("topics", topicService.findByTypeCategoryID(2, mathSubject.getCategoryID()));
		this.setSelectedMenu(model, MenuCode.Math);
		return "math/edit";
	}

	@RequestMapping(value = { "/edit/{id}" }, method = RequestMethod.POST)
	public String doPostEditForm(@PathVariable("id") int id, HttpServletRequest request, Model model, //
			@ModelAttribute("mathSubjectForm") MathSubjectForm _theForm, Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Math)) {
			return "redirect:/home/404";
		}

		String subject = _theForm.getSubject();
		String description = _theForm.getDescription();
		Integer status = _theForm.getStatus();
		String answers = _theForm.getAnswers();
		String correctAnswer = _theForm.getCorrectAnswer();
		Integer topicID = _theForm.getTopicID();
		Integer categoryID = _theForm.getCategoryID();

		// System.out.println("categoryID :" + categoryID.toString());
		errorMessage = "";
		if (this.IsValidate(_theForm)) {
			Topic topic = topicService.findById(topicID);
			User loginedUser = (User) ((Authentication) _principal).getPrincipal();
			Admin _author = this.userService.findByUsername(loginedUser.getUsername());
			Category category = this.categoryService.findById(categoryID);

			MathSubject mathSubject = this.mathSubjectService.findById(id);
			TextContent subjectContent = mathSubject.getSubject();

			TextContent textSubject = this.textContentService.findByContent(subject);
			if (textSubject == null) {
				textSubject = new TextContent();
				textSubject.setContent(subject);
				textSubject.setStatus(status);
				textSubject.setAuthor(_author);
				textSubject.setCreateDate(WebUtil.GetTime());

			}
			TextContent descriptionContent = this.textContentService.findByContent(description);
			if (descriptionContent == null) {
				descriptionContent = new TextContent();
				descriptionContent.setContent(description);
				descriptionContent.setStatus(status);
				descriptionContent.setAuthor(_author);
				descriptionContent.setCreateDate(WebUtil.GetTime());
			}

			Topic item = this.topicService.findById(topicID);

			mathSubject.setSubject(subjectContent);
			mathSubject.setDescription(descriptionContent);
			mathSubject.setStatus(status);
			mathSubject.setAuthor(_author);
			mathSubject.setTopic(item);
			mathSubject.setCategory(category);
			
			mathSubject.setUpdateDate(this.GetTime());
			
			if (errorMessage.isEmpty()) {
				this.textContentService.save(descriptionContent);
				this.textContentService.save(subjectContent);
				
				this.mathSubjectService.save(mathSubject);
				this.setMessageAt(model, MessageCode.Edit);
				return "redirect:/math/detail/" + id;
			}

		}

		model.addAttribute("mathSubjectID", id);
		model.addAttribute("mathSubjectForm", _theForm);// 2 - type for MATH
		model.addAttribute("topics", topicService.findByType(2));// 2 - type for MATH
		this.setSelectedMenu(model, MenuCode.Math);
		this.setAccessCode(model, _principal);
		model.addAttribute("errorMessage", errorMessage);
		return "math/edit";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String showDeletePage(@PathVariable("id") int id, Model model, Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Math)) {
			return "redirect:/home/404";
		}
		try {
			mathSubjectService.deleteById(id);
		} catch (Exception ex) {

		}
		this.setMessageAt(model, MessageCode.Delete);
		return "redirect:/math/index";
	}

	@RequestMapping(value = { "/add/{categoryid}" }, method = RequestMethod.GET)
	public String showAddSubjectPage(@PathVariable("categoryid") int categoryid, Model model, Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Math)) {
			return "redirect:/home/404";
		}

		Integer min = 49;
		Integer max = 62;

		List<Category> categories = categoryService.findWithOrderInRange(min, max);
		model.addAttribute("categories", categories);
		this.setSelectedActionMenu(model, ActionMenuCode.Add);
		MathSubjectForm theForm = new MathSubjectForm();
		model.addAttribute("mathSubjectForm", theForm);
		model.addAttribute("topics", topicService.findByTypeCategoryID(2, categoryid));// 2 - type for MATH
		this.setSelectedMenu(model, MenuCode.Math);
		this.setAccessCode(model, _principal);
		return "math/add";
	}

	@RequestMapping(value = { "/add/{categoryid}" }, method = RequestMethod.POST)
	public String doPostSubject(@PathVariable("categoryid") int categoryid, HttpServletRequest request, Model model, //
			@ModelAttribute("mathSubjectForm") MathSubjectForm _mathSubjectForm, Principal _principal

	) {

		if (this.checkForbidden(_principal, MenuCode.Math)) {
			return "redirect:/home/404";
		}
		String subject = _mathSubjectForm.getSubject();
		String description = _mathSubjectForm.getDescription();
		String answers = _mathSubjectForm.getAnswers();
		String correctAnswer = _mathSubjectForm.getCorrectAnswer();
		Integer topicID = _mathSubjectForm.getTopicID();
		// Integer categoryID = _mathSubjectForm.getCategoryID();
		Integer status = 1;// not yet tested.
		errorMessage = "";

		if (this.IsValidate(_mathSubjectForm)) {

			User loginedUser = (User) ((Authentication) _principal).getPrincipal();
			Admin _author = this.userService.findByUsername(loginedUser.getUsername());
			Category category = this.categoryService.findById(categoryid);
			TextContent textSubject = this.textContentService.findByContent(subject);
			if (textSubject == null) {
				textSubject = new TextContent();
				textSubject.setContent(subject);
				textSubject.setStatus(status);
				textSubject.setAuthor(_author);
				textSubject.setCreateDate(this.GetTime());

			}
			TextContent textDescription = this.textContentService.findByContent(description);
			if (textDescription == null) {
				textDescription = new TextContent();
				textDescription.setContent(description);
				textDescription.setStatus(status);
				textDescription.setAuthor(_author);
				textDescription.setCreateDate(this.GetTime());
			}

			Topic item = this.topicService.findById(topicID);

			MathSubject mathSubject = new MathSubject();
			mathSubject.setSubject(textSubject);
			mathSubject.setDescription(textDescription);
			mathSubject.setStatus(status);
			mathSubject.setAuthor(_author);
			mathSubject.setTopic(item);
			mathSubject.setCategory(category);
			mathSubject.setCreateDate(this.GetTime());

			if (errorMessage.isEmpty()) {
				this.textContentService.save(textSubject);
				this.textContentService.save(textDescription);

				this.mathSubjectService.save(mathSubject);
				this.setMessageAt(model, MessageCode.Add);
				return "redirect:/math/detail/" + mathSubject.getId();
			}

		}
		this.setSelectedActionMenu(model, ActionMenuCode.Add);
		model.addAttribute("mathSubjectForm", _mathSubjectForm);
		model.addAttribute("topics", topicService.findByType(2));// 2 - type for MATH
		model.addAttribute("errorMessage", errorMessage);
		this.setSelectedMenu(model, MenuCode.Math);
		this.setAccessCode(model, _principal);
		return "math/add";
	}

	private boolean IsValidate(MathSubjectForm _theForm) {
		if (_theForm.getSubject().length() <= 0) {
			errorMessage = "Subject field must be not empty";
			return false;
		} else if (_theForm.getDescription().length() <= 0) {
			errorMessage = "Description field must be not empty";
			return false;
		}
		return true;
	}

	@RequestMapping(value = "/cache/{categoryid}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE, method = RequestMethod.GET)
	private @ResponseBody byte[] getCategoryFile(@PathVariable("categoryid") int categoryid, HttpServletRequest request)
			throws IOException {

		String categoriesRootPath = request.getServletContext().getRealPath("categories");
		//System.out.println("categoriesRootPath = " + categoriesRootPath);

		File categoryRootDir = new File(categoriesRootPath);

		if (!categoryRootDir.exists()) {
			categoryRootDir.mkdirs();
		}

		File mathDir = new File(categoryRootDir, "math");
		if (!mathDir.exists()) {
			mathDir.mkdirs();
		}

		String uri = mathDir.getAbsolutePath() + File.separator + categoryid + ".txt";
		InputStream in = getClass().getResourceAsStream(uri);
		return IOUtils.toByteArray(in);
	}

}
