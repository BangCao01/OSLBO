package orangeschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import orangeschool.WebUtil;
import orangeschool.controller.BaseController.ActionMenuCode;
import orangeschool.controller.BaseController.MenuCode;
import orangeschool.controller.BaseController.MessageCode;
import orangeschool.form.TopicForm;


import orangeschool.model.Admin;
import orangeschool.model.Category;
import orangeschool.model.TextContent;
import orangeschool.model.Topic;

import orangeschool.service.TextContentService;
import orangeschool.service.TopicService;
import orangeschool.service.CategoryService;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/topic") // This
public class TopicController extends BaseController {
	

	@Autowired // This means to get the bean called userRepository
	private TextContentService textContentService;
	
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private TopicService topicService;

	// FOR TOPIC
	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public String showTopicPage(
			@RequestParam(value="page", defaultValue="1") Integer _page,
			Model model,
			Principal _principal) {

		if(this.checkForbidden(_principal,MenuCode.Topic))
		{
			return "redirect:/home/404";
		}
         Page<Topic> page = topicService.findAll(_page,100);
		
		model.addAttribute("topics", page);
		Integer totalPages = page.getTotalPages();
		if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
		
		this.setAccessCode(model, _principal);
		this.setMessageAt(model, MessageCode.Index);
		this.setSelectedMenu(model, MenuCode.Topic);
		this.setSelectedActionMenu(model, ActionMenuCode.List);
		this.setAccessCode(model, _principal);
		return "topic/index";
	}

	// FOR TOPIC
	@RequestMapping(value = { "/index/{type}" }, method = RequestMethod.GET)
	public String showTopicByIdPage(
			@RequestParam(value="page", defaultValue="1") Integer _page,
			@PathVariable("type") int type, 
			Model model,
			Principal _principal) {

		if(this.checkForbidden(_principal,MenuCode.Topic))
		{
			return "redirect:/home/404";
		}
		model.addAttribute("type", type);
		Integer min = 49;
		Integer max = 62;
		if(type ==1)
		{
			min = 100;
			max = 113;
		}
		model.addAttribute("categories", categoryService.findWithOrderInRange(min, max));
		List<Topic> topics = topicService.findByType(type);
		model.addAttribute("topics", topics);
//		Page<Topic> page = topicService.findAll(_page,100);
//		
//		model.addAttribute("topics", page);
//		Integer totalPages = page.getTotalPages();
//		if(totalPages > 0) {
//            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
//            model.addAttribute("pageNumbers", pageNumbers);
//        }
		model.addAttribute("skillcount", topics.size());
		this.setMessageAt(model, MessageCode.Index);
		this.setSelectedMenu(model, MenuCode.Topic);
		this.setAccessCode(model, _principal);
		ActionMenuCode actioncode = (type == 1)? ActionMenuCode.TopicEla:(type == 2)? ActionMenuCode.TopicMath:ActionMenuCode.TopicStory;
		this.setSelectedActionMenu(model, actioncode);
		
		return "topic/index";
	}

	@RequestMapping(value = { "/index/{type}/{category}" }, method = RequestMethod.GET)
	public String showTopicByTypePage(
			@PathVariable("type") int type,
			@PathVariable("category") int _category, 
			Model model,
			Principal _principal) {

		if(this.checkForbidden(_principal,MenuCode.Topic))
		{
			return "redirect:/home/404";
		}
		
		Integer min = 49;
		Integer max = 62;
		if(type ==1)
		{
			min = 100;
			max = 113;
		}
		model.addAttribute("type", type);
		model.addAttribute("categories", categoryService.findWithOrderInRange(min, max));
		Category category = categoryService.findById(_category);
		List<Topic> SubTopics = null;
		SubTopics = topicService.findByTypeCategory(type, category);
		
		model.addAttribute("topics", SubTopics);
		model.addAttribute("skillcount", SubTopics.size());
		this.setMessageAt(model, MessageCode.Index);
		this.setSelectedMenu(model, MenuCode.Topic);
		this.setAccessCode(model, _principal);
		ActionMenuCode actioncode = (type == 1)? ActionMenuCode.TopicEla:(type == 2)? ActionMenuCode.TopicMath:ActionMenuCode.TopicStory;
		this.setSelectedActionMenu(model, actioncode);
		
		return "topic/index";
	}
	
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public String showDetailPage(
			@PathVariable("id") int id, 
			Model model,
			Principal _principal) {
		if(this.checkForbidden(_principal,MenuCode.Topic))
		{
			return "redirect:/home/404";
		}
		this.setMessageAt(model, MessageCode.Detail);
		model.addAttribute("topic", topicService.findById(id));
		this.setSelectedMenu(model, MenuCode.Topic);
		this.setAccessCode(model, _principal);
		return "topic/detail";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String showEditPage(
			@PathVariable("id") int id, 
			Model model,
			Principal _principal) {
		
		if(this.checkForbidden(_principal,MenuCode.Topic))
		{
			return "redirect:/home/404";
		}
		
		Topic topic = topicService.findById(id);

		model.addAttribute("topicID", id);
		TopicForm theForm = new TopicForm();
		theForm.setParentID(topic.getParentID());
		theForm.setName(topic.getName());
		theForm.setType(topic.getType());
		theForm.setOrder(topic.getOrder());
		model.addAttribute("topics", topicService.findByType(topic.getType()));
		model.addAttribute("topicForm", theForm);
		
		Integer min = 49;
		Integer max = 62;
		if(topic.getType() ==1)
		{
			min = 100;
			max = 113;
		}
		List<Category> categories = categoryService.findWithOrderInRange(min, max);
		model.addAttribute("categories", categories);
		this.setAccessCode(model, _principal);
		this.setSelectedMenu(model, MenuCode.Topic);
		return "topic/edit";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public String showEditPost(
			@PathVariable("id") int id, 
			Model model,
			@ModelAttribute("topicForm") TopicForm topicForm, 
			Principal _principal) {
		
		if(this.checkForbidden(_principal,MenuCode.Topic))
		{
			return "redirect:/home/404";
		}
		errorMessage = "";
		String name = topicForm.getName();
		Integer parentID = topicForm.getParentID();
		Integer order = topicForm.getOrder();
		Integer type = topicForm.getType();

		if (this.IsValidate(topicForm)) {
			User loginedUser = (User) ((Authentication) _principal).getPrincipal();
			Admin _author = this.userService.findByUsername(loginedUser.getUsername());
			Topic topic = topicService.findById(id);
			Topic parent = topicService.findById(parentID);
			TextContent textContent = this.textContentService.findByContent(name);

			if (textContent == null) {
				textContent = new TextContent();
				textContent.setContent(name);
				textContent.setStatus(1);// not yet test
				textContent.setAuthor(_author);
				textContent.setCreateDate(this.GetTime());
				this.textContentService.save(textContent);
			}
			topic.setName(textContent);
			topic.setOrder(order);
			topic.setType(type);
			topic.setParent(parent);
			topic.setUpdateDate(WebUtil.GetTime());
			topicService.save(topic);
			this.setMessageAt(model, MessageCode.Edit);
			return "redirect:/topic/detail/" + id;
		}
		Integer min = 49;
		Integer max = 62;
		if(type ==1)
		{
			min = 100;
			max = 113;
		}
		List<Category> categories = categoryService.findWithOrderInRange(min, max);
		model.addAttribute("categories", categories);
		model.addAttribute("topicID", id);
		model.addAttribute("topics", topicService.findByType(type));
		model.addAttribute("topicForm", topicForm);
		this.setAccessCode(model, _principal);
		this.setSelectedMenu(model, MenuCode.Topic);
		model.addAttribute("errorMessage", errorMessage);
		return "topic/edit";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String showDeletePage(
			@PathVariable("id") int id, 
			Model model,
			Principal _principal) {
		
		if(this.checkForbidden(_principal,MenuCode.Topic))
		{
			return "redirect:/home/404";
		}
		try {
			topicService.deleteById(id);
		} catch (Exception ex) {

		}
		this.setMessageAt(model, MessageCode.Delete);
		return "redirect:/topic/index";
	}

	@RequestMapping(value = { "/add/{type}/{category}" }, method = RequestMethod.GET)
	public String showAddTopicPage2(
			@PathVariable("type") int type, 
			@PathVariable("category") int _category,
			Model model,
			Principal _principal) {
		if(this.checkForbidden(_principal,MenuCode.Topic))
		{
			return "redirect:/home/404";
		}

		TopicForm theForm = new TopicForm();
		theForm.setType(type);
		Category category = categoryService.findById(_category);
		model.addAttribute("topics", topicService.findByTypeCategory(type, category));
		model.addAttribute("topicForm", theForm);

		Integer min = 49;
		Integer max = 62;
		if(type ==1)
		{
			min = 100;
			max = 113;
		}
		
		List<Category> categories = categoryService.findWithOrderInRange(min, max);
		model.addAttribute("categories", categories);
		
		this.setAccessCode(model, _principal);
		this.setSelectedMenu(model, MenuCode.Topic);
		this.setSelectedActionMenu(model, ActionMenuCode.Add);
		ActionMenuCode actioncode = (type == 1)? ActionMenuCode.TopicEla:(type == 2)? ActionMenuCode.TopicMath:ActionMenuCode.TopicStory;
		this.setSelectedActionMenu(model, actioncode);
		return "topic/add";
	}
	
	@RequestMapping(value = { "/add/{type}" }, method = RequestMethod.GET)
	public String showAddTopicPage(
			@PathVariable("type") int type, 
			Model model,
			Principal _principal) {
		if(this.checkForbidden(_principal,MenuCode.Topic))
		{
			return "redirect:/home/404";
		}

		TopicForm theForm = new TopicForm();
		theForm.setType(type);
		model.addAttribute("topics", topicService.findByType(type));
		model.addAttribute("topicForm", theForm);

		Integer min = 49;
		Integer max = 62;
		if(type ==1)
		{
			min = 100;
			max = 113;
		}
		List<Category> categories = categoryService.findWithOrderInRange(min, max);
		model.addAttribute("categories", categories);
		
		this.setAccessCode(model, _principal);
		this.setSelectedMenu(model, MenuCode.Topic);
		this.setSelectedActionMenu(model, ActionMenuCode.Add);
		ActionMenuCode actioncode = (type == 1)? ActionMenuCode.TopicEla:(type == 2)? ActionMenuCode.TopicMath:ActionMenuCode.TopicStory;
		this.setSelectedActionMenu(model, actioncode);
		return "topic/add";
	}

	// Category
	@RequestMapping(value = { "/add" }, method = RequestMethod.POST)
	public String doPostTopic(
			Model model, //
			@ModelAttribute("topicForm") TopicForm topicForm, 
			Principal _principal

	) {
		if(this.checkForbidden(_principal,MenuCode.Topic))
		{
			return "redirect:/home/404";
		}
		String name = topicForm.getName();
		Integer parentID = topicForm.getParentID();
		Integer order = topicForm.getOrder();
		Integer type = topicForm.getType();
		Topic parent = topicService.findById(parentID);

		Integer categoryID = topicForm.getCategoryID();
		Category category = this.categoryService.findById(categoryID);
		errorMessage = "";
		if (this.IsValidate(topicForm)) {
			User loginedUser = (User) ((Authentication) _principal).getPrincipal();
			Admin _author = this.userService.findByUsername(loginedUser.getUsername());

			TextContent textContent = this.textContentService.findByContent(name);

			if (textContent == null) {
				textContent = new TextContent();
				textContent.setContent(name);
				textContent.setStatus(1);// not yet test
				textContent.setAuthor(_author);
				textContent.setCreateDate(this.GetTime());
				this.textContentService.save(textContent);
			}

			Topic topic = new Topic();
			topic.setName(textContent);
			topic.setOrder(order);
			topic.setType(type);
			if (parent != null)
				topic.setParent(parent);
			topic.setCategory(category);
			topic.setAuthor(_author);
			topic.setCreateDate(WebUtil.GetTime());
			topicService.save(topic);
			this.setMessageAt(model, MessageCode.Add);
			return "redirect:/topic/detail/" + topic.getId();
		}

		model.addAttribute("topics", topicService.findByType(type));
		model.addAttribute("topicForm", topicForm);

		List<Category> categories = categoryService.findWithOrderInRange(49, 62);
		model.addAttribute("categories", categories);
		this.setSelectedActionMenu(model, ActionMenuCode.Add);
		this.setSelectedMenu(model, MenuCode.Topic);
		this.setAccessCode(model, _principal);
		ActionMenuCode actioncode = (type == 1)? ActionMenuCode.TopicEla:(type == 2)? ActionMenuCode.TopicMath:ActionMenuCode.TopicStory;
		this.setSelectedActionMenu(model, actioncode);
		model.addAttribute("errorMessage", errorMessage);
		return "topic/add";
	}
	// END TOPIC

	private boolean IsValidate(TopicForm _theForm) {
		if (_theForm.getName().length() <= 0) {
			errorMessage = "Topic's name field must be not empty";
			return false;
		}

		else if (_theForm.getOrder() <= 0) {
			errorMessage = "Order field must be a number greater 0";
			return false;
		}

		return true;
	}

}
