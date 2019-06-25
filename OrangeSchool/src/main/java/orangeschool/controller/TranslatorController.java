package orangeschool.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import orangeschool.WebUtil;
import orangeschool.controller.BaseController.ActionMenuCode;
import orangeschool.controller.BaseController.MenuCode;
import orangeschool.controller.BaseController.MessageCode;
import orangeschool.form.TopicForm;
import orangeschool.form.TransactionForm;
import orangeschool.form.TranslatorForm;
import orangeschool.model.Admin;
import orangeschool.model.Category;
import orangeschool.model.TextContent;
import orangeschool.model.Topic;
import orangeschool.model.Translator;
import orangeschool.service.TextContentService;
import orangeschool.service.TranslatorService;


@Controller // This means that this class is a Controller
@RequestMapping(path = "/translator") // This
public class TranslatorController extends BaseController {
	
	@Autowired // This means to get the bean called userRepository
	private TextContentService textContentService;
	@Autowired
	private TranslatorService translatorService;
	
	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public String showIndexPage(
			Model model,
			Principal _principal) {

		if(this.checkForbidden(_principal,MenuCode.Translator))
		{
			return "redirect:/home/404";
		}
		
		model.addAttribute("translators", translatorService.findAll());
		this.setAccessCode(model, _principal);
		this.setMessageAt(model, MessageCode.Index);
		this.setSelectedMenu(model, MenuCode.Translator);
		this.setSelectedActionMenu(model, ActionMenuCode.List);
		this.setAccessCode(model, _principal);
		return "translator/index";
	}
	
	
		@RequestMapping(value = { "/index/{authorid}" }, method = RequestMethod.GET)
		public String showTranslatorByAuthorPage(
				@PathVariable("authorid") int authorid, 
				Model model,
				Principal _principal) {

			if(this.checkForbidden(_principal,MenuCode.Translator))
			{
				return "redirect:/home/404";
			}
			
			model.addAttribute("translators", translatorService.findByAuthorID(authorid));
			this.setMessageAt(model, MessageCode.Index);
			this.setSelectedMenu(model, MenuCode.Translator);
			this.setAccessCode(model, _principal);
			
			return "translator/index";
		}
		
		
		@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
		public String showDetailPage(
				@PathVariable("id") int id, 
				Model model,
				Principal _principal) {
			if (this.checkForbidden(_principal, MenuCode.Translator)) {
				return "redirect:/home/404";
			}
			this.setMessageAt(model, MessageCode.Detail);
			model.addAttribute("translator", translatorService.findByTranslatorID(id));
			this.setSelectedMenu(model, MenuCode.Translator);
			this.setAccessCode(model, _principal);
			return "translator/detail";
		}
		
		@RequestMapping(value = { "/add/{contentid}" }, method = RequestMethod.GET)
		public String showAddTopicPage2( 
				@PathVariable("contentid") int _contentid,
				Model model,
				Principal _principal) {
			if(this.checkForbidden(_principal,MenuCode.Translator))
			{
				return "redirect:/home/404";
			}

			TranslatorForm theForm = new TranslatorForm();
			theForm.setContentID(_contentid);
			model.addAttribute("content", textContentService.findByTextID(_contentid));
			model.addAttribute("translatorForm", theForm);

			this.setAccessCode(model, _principal);
			this.setSelectedMenu(model, MenuCode.Translator);
			this.setSelectedActionMenu(model, ActionMenuCode.Add);
			return "translator/add";
		}
		
		@RequestMapping(value = { "/add" }, method = RequestMethod.POST)
		public String doPostTopic(
				Model model, //
				@ModelAttribute("translatorForm") TranslatorForm theForm, 
				Principal _principal

		) {
			if(this.checkForbidden(_principal,MenuCode.Topic))
			{
				return "redirect:/home/404";
			}
			Integer contentID = theForm.getContentID();
			String language = theForm.getLanguage();
			String translatedContent = theForm.getTranslatedContent();
			Integer status = theForm.getStatus();
			
			errorMessage = "";
			if (this.IsValidate(theForm)) {
				
				Admin _author = this.getAdminUser(_principal);

				TextContent textContent = this.textContentService.findByTextID(contentID);

				if (textContent != null) {
					Translator translator = this.translatorService.findByContentID(contentID);
					boolean isCreate = false;
					
					if(translator == null)
					{
						translator = new Translator();
						translator.setContent(textContent);
						isCreate = true;
					}
					translator.setStatus(status);
					if(language.equalsIgnoreCase("Vietnamese"))
					{
						translator.setVietnamese(translatedContent);
					}
					else if(language.equalsIgnoreCase("Japanese"))
					{
						translator.setJapanese(translatedContent);
					}
					else if(language.equalsIgnoreCase("Portuguese"))
					{
						translator.setPortuguese(translatedContent);
					}
					else if(language.equalsIgnoreCase("Indonesian"))
					{
						translator.setIndonesian(translatedContent);
					}
							
					
					translator.setAuthor(_author);
					if(isCreate)
						translator.setCreateDate(WebUtil.GetTime());
					else
						translator.setUpdateDate(WebUtil.GetTime());
					
					translatorService.save(translator);
					this.setMessageAt(model, MessageCode.Add);
					return "redirect:/translator/detail/" + translator.getId();
					
					
				}

				
			}

			
			this.setSelectedActionMenu(model, ActionMenuCode.Add);
			this.setSelectedMenu(model, MenuCode.Translator);
			this.setAccessCode(model, _principal);
			
			model.addAttribute("errorMessage", errorMessage);
			return "translator/add";
		}
		
		
		@RequestMapping(value = "/edit/{id}/{language}", method = RequestMethod.GET)
		public String showEditPage(
				@PathVariable("id") int id, 
				@PathVariable("language") String language, 
				Model model,
				Principal _principal) {
			
			if(this.checkForbidden(_principal,MenuCode.Topic))
			{
				return "redirect:/home/404";
			}
			
			Translator translator = translatorService.findByTranslatorID(id);

			model.addAttribute("topicID", id);
			TranslatorForm theForm = new TranslatorForm();
			theForm.setContentID(translator.getContentID());
			theForm.setLanguage(language);
			theForm.setStatus(translator.getStatus());
			String translatedContent = "";
			if(language.equalsIgnoreCase("Vietnamese"))
				translatedContent = translator.getVietnamese();
			else if(language.equalsIgnoreCase("Japanese"))
				translatedContent = translator.getJapanese();	
			else if(language.equalsIgnoreCase("Indonesian"))
				translatedContent = translator.getIndonesian();	
			else if(language.equalsIgnoreCase("Portuguese"))
				translatedContent = translator.getPortuguese();	
			
			theForm.setTranslatedContent(translatedContent);
			TextContent content = this.textContentService.findByTextID(translator.getContentID());
			model.addAttribute("content", content);
			model.addAttribute("language", language);
			
			model.addAttribute("translatorForm", theForm);
			
			
			this.setAccessCode(model, _principal);
			this.setSelectedMenu(model, MenuCode.Translator);
			return "translator/edit";
		}
		
		
		@RequestMapping(value = { "/edit/{id}" }, method = RequestMethod.POST)
		public String doPostEdit(
				@PathVariable("id") int id,
				Model model, //
				@ModelAttribute("translatorForm") TranslatorForm theForm, 
				Principal _principal

		) {
			if(this.checkForbidden(_principal,MenuCode.Topic))
			{
				return "redirect:/home/404";
			}
			Integer contentID = theForm.getContentID();
			String language = theForm.getLanguage();
			String translatedContent = theForm.getTranslatedContent();
			Integer status = theForm.getStatus();
			
			errorMessage = "";
			if (this.IsValidate(theForm)) {
				
				Admin _author = this.getAdminUser(_principal);

				TextContent textContent = this.textContentService.findByTextID(contentID);

				if (textContent != null) {
					Translator translator = this.translatorService.findByTranslatorID(id);
					
					translator.setStatus(status);
					if(language.equalsIgnoreCase("Vietnamese"))
					{
						translator.setVietnamese(translatedContent);
					}
					else if(language.equalsIgnoreCase("Japanese"))
					{
						translator.setJapanese(translatedContent);
					}
					else if(language.equalsIgnoreCase("Portuguese"))
					{
						translator.setPortuguese(translatedContent);
					}
					else if(language.equalsIgnoreCase("Indonesian"))
					{
						translator.setIndonesian(translatedContent);
					}
							
					
					translator.setUpdateDate(WebUtil.GetTime());
					
					translatorService.save(translator);
					this.setMessageAt(model, MessageCode.Edit);
					return "redirect:/translator/detail/" + translator.getId();
					
					
				}

				
			}

			TextContent content = this.textContentService.findByTextID(contentID);
			model.addAttribute("content", content);
			model.addAttribute("language", language);
			
			model.addAttribute("translatorForm", theForm);
			
			this.setSelectedActionMenu(model, ActionMenuCode.Add);
			this.setSelectedMenu(model, MenuCode.Translator);
			this.setAccessCode(model, _principal);
			
			model.addAttribute("errorMessage", errorMessage);
			return "translator/edit";
		}
		
		private boolean IsValidate(TranslatorForm _theForm) {
			if (_theForm.getTranslatedContent().length() <= 0) {
				errorMessage = "Translated content field must be not empty";
				return false;
			}

			return true;
		}

}
