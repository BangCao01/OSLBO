package orangeschool.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import orangeschool.WebUtil;
import orangeschool.controller.BaseController.ActionMenuCode;
import orangeschool.controller.BaseController.MenuCode;
import orangeschool.form.ImageUploadForm;
import orangeschool.form.SoundUploadForm;
import orangeschool.form.StoryForm;
import orangeschool.model.Admin;
import orangeschool.model.Category;
import orangeschool.model.ImageContent;
import orangeschool.model.Product;
import orangeschool.model.SoundContent;
import orangeschool.model.Story;
import orangeschool.model.TextContent;
import orangeschool.service.AdminService;
import orangeschool.service.CategoryService;
import orangeschool.service.ImageContentService;
import orangeschool.service.StoryService;
import orangeschool.service.TextContentService;
import orangeschool.service.ParagraphService;

@Controller
@RequestMapping(path = "/story") // This
public class StoryController extends BaseController {

	@Autowired
	private StoryService storyService;
	@Autowired
	private ImageContentService imageContentService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private TextContentService textContentService;

	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public String mathIndex(Model model, Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Story)) {
			return "redirect:/home/404";
		}

		this.setMessageAt(model, MessageCode.Index);
		model.addAttribute("stories", storyService.findAll());
		this.menucode = "Story";
		model.addAttribute("menucode", this.menucode);
		this.setSelectedActionMenu(model, ActionMenuCode.List);
		this.setAccessCode(model, _principal);
		return "story/index";
	}

	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public String showDetailPage(@PathVariable("id") int id, Model model, Principal _principal) {

		if (this.checkForbidden(_principal, MenuCode.Story)) {
			return "redirect:/home/404";
		}
		this.setMessageAt(model, MessageCode.Detail);
		model.addAttribute("storyID", id);
		this.setAccessCode(model, _principal);
		model.addAttribute("story", storyService.findWithId(id));
		this.setSelectedMenu(model, MenuCode.Story);
		return "story/detail";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String showEditPage(@PathVariable("id") int id, Model model, Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Story)) {
			return "redirect:/home/404";
		}
		model.addAttribute("storyID", id);

		Story story = storyService.findWithId(id);

		StoryForm theForm = new StoryForm();

		theForm.setTitle(story.getTitle());
		theForm.setDescription(story.getDescription());
		theForm.setCategoryID(story.getCategoryID());
		theForm.setStatus(story.getStatus());

		model.addAttribute("storyForm", theForm);
		model.addAttribute("categories", categoryService.findAll());
		this.setAccessCode(model, _principal);
		this.setSelectedMenu(model, MenuCode.Story);
		return "story/edit";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public String showEditPost(@PathVariable("id") int id, Model model, @ModelAttribute("storyForm") StoryForm theForm,
			HttpServletRequest request, Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Story)) {
			return "redirect:/home/404";
		}
		String title = theForm.getTitle();
		String description = theForm.getDescription();
		Integer status = theForm.getStatus();
		Integer categoryID = theForm.getCategoryID();
		errorMessage = "";
		if (this.IsValidateEditForm(theForm)) {
			User loginedUser = (User) ((Authentication) _principal).getPrincipal();
			Admin _author = this.userService.findByUsername(loginedUser.getUsername());
			Story story = storyService.findWithId(id);
			TextContent textTitle = this.textContentService.findByContent(title);
			if (textTitle == null) {
				textTitle = new TextContent();
				textTitle.setContent(title);
				textTitle.setStatus(status);
				textTitle.setAuthor(_author);
				textTitle.setCreateDate(this.GetTime());

			}

			TextContent textDescription = this.textContentService.findByContent(description);
			if (textDescription == null) {
				textDescription = new TextContent();
				textDescription.setContent(description);
				// text.setSound(sound);
				textDescription.setStatus(status);
				textDescription.setAuthor(_author);
				textDescription.setCreateDate(this.GetTime());

			}

			Category item = this.categoryService.findById(categoryID);

			story.setTitle(textTitle);
			story.setDescription(textDescription);
			story.setStatus(status);
			story.setCategory(item);
			story.setUpdateDate(WebUtil.GetTime());
			this.storyService.save(story);
			if (this.IsValidateFileEditForm(theForm)) {
				if (!this.doUploadImageOfStory(request, model, theForm, _author, story, "edit")) {
					errorMessage = "Image input go wrong";
				}

			}

			if (errorMessage.isEmpty()) {
				this.textContentService.save(textTitle);
				this.textContentService.save(textDescription);
				this.imageContentService.save(story.getImage());
				this.storyService.save(story);
				this.setMessageAt(model, MessageCode.Edit);
				return "redirect:/story/detail/" + story.getId();
			}

		}

		model.addAttribute("storyID", id);
		model.addAttribute("storyForm", theForm);
		model.addAttribute("categories", categoryService.findAll());
		this.setSelectedMenu(model, MenuCode.Story);
		model.addAttribute("errorMessage", errorMessage);
		this.setAccessCode(model, _principal);
		return "story/edit";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String showDeletePage(@PathVariable("id") int id, Model model, Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Story)) {
			return "redirect:/home/404";
		}
		try {
			
			Story p = storyService.findWithId(id);
			ImageContent tmp = p.getImage();
			
			File oldFile = new File(tmp.getUri());
			try {
				oldFile.delete();
			} catch (Exception ex) {
				errorMessage = "Deleting old image was not succesful";
			}
			
			tmp = p.getThumb();
			
			oldFile = new File(tmp.getUri());
			try {
				oldFile.delete();
			} catch (Exception ex) {
				errorMessage = "Deleting old thumb was not succesful";
			}
			
			storyService.deleteById(id);
		} catch (Exception ex) {

		}
		this.setMessageAt(model, MessageCode.Delete);
		return "redirect:/story/index";
	}

	@RequestMapping(value = { "/add" }, method = RequestMethod.GET)
	public String showAddStoryPage(Model model, Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Story)) {
			return "redirect:/home/404";
		}
		StoryForm theForm = new StoryForm();
		model.addAttribute("storyForm", theForm);
		model.addAttribute("categories", categoryService.findAll());
		this.setSelectedMenu(model, MenuCode.Story);
		this.setSelectedActionMenu(model, ActionMenuCode.Add);
		this.setAccessCode(model, _principal);
		return "story/add";
	}

	@RequestMapping(value = { "/add" }, method = RequestMethod.POST)
	public String doPostSubject(HttpServletRequest request, Model model, //
			@ModelAttribute("storyForm") StoryForm _storyForm, Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Story)) {
			return "redirect:/home/404";
		}

		String title = _storyForm.getTitle();
		String description = _storyForm.getDescription();

		Integer categoryID = _storyForm.getCategoryID();
		Integer status = 1;// _textContentForm.getStatus();

		errorMessage = "";
		if (this.IsValidateAddForm(_storyForm)) {

			User loginedUser = (User) ((Authentication) _principal).getPrincipal();
			Admin _author = this.userService.findByUsername(loginedUser.getUsername());
			TextContent textTitle = this.textContentService.findByContent(title);
			if (textTitle == null) {
				textTitle = new TextContent();
				textTitle.setContent(title);
				textTitle.setStatus(status);
				textTitle.setAuthor(_author);
				textTitle.setCreateDate(this.GetTime());

			}
			TextContent textDescription = this.textContentService.findByContent(description);
			if (textDescription == null) {
				textDescription = new TextContent();
				textDescription.setContent(description);
				// text.setSound(sound);
				textDescription.setStatus(status);
				textDescription.setAuthor(_author);
				textDescription.setCreateDate(this.GetTime());

			}
			Category item = this.categoryService.findById(categoryID);
			Story story = new Story();
			story.setTitle(textTitle);
			story.setDescription(textDescription);
			story.setStatus(status);
			story.setAuthor(_author);
			story.setCategory(item);
			story.setCreateDate(this.GetTime());

			if (!this.doUploadImageOfStory(request, model, _storyForm, _author, story, "add")) {
				errorMessage = "Image input go wrong";
			}

			if (errorMessage.isEmpty()) {
				this.textContentService.save(textTitle);
				this.textContentService.save(textDescription);
				this.imageContentService.save(story.getImage());
				this.storyService.save(story);
				this.setMessageAt(model, MessageCode.Add);
				return "redirect:/story/detail/" + story.getId();
			}
		}
		this.setSelectedActionMenu(model, ActionMenuCode.Add);
		this.setSelectedMenu(model, MenuCode.Story);
		model.addAttribute("storyForm", _storyForm);
		model.addAttribute("categories", categoryService.findAll());
		model.addAttribute("errorMessage", errorMessage);
		this.setAccessCode(model, _principal);
		return "story/add";
	}

	private boolean IsValidateImageFile(StoryForm _theForm) {

		MultipartFile[] fileDatas = _theForm.getFileDatas();
		for (MultipartFile fileData : fileDatas) {

			// Tên file gốc tại Client.
			String fname = fileData.getOriginalFilename();
			int len = fname.length();
			if (len == 0) {
				// errorMessage = "Image must be in png or jpg.";
				return false;
			}
			String ext = fname.substring(len - 4, len);
			if (!(ext.equalsIgnoreCase(".png") || ext.equalsIgnoreCase(".jpg"))) {
				errorMessage = "Image must be in png or jpg.";
				return false;
			}

		}
		return true;
	}

	private boolean IsValidate(StoryForm _theForm) {
		if (_theForm.getTitle().length() <= 0) {
			errorMessage = "Subject field must be not empty";
			return false;
		} else if (_theForm.getDescription().length() <= 0) {
			errorMessage = "Description field must be not empty";
			return false;
		}

		return true;
	}

	private boolean IsValidateEditForm(StoryForm _theForm) {
		return IsValidate(_theForm);
	}

	private boolean IsValidateFileEditForm(StoryForm _theForm) {
		if (!((_theForm.getFileDatas()[0].getOriginalFilename().length() > 4)
				|| (_theForm.getFileDatas()[1].getOriginalFilename().length() > 4)

		)) {

			return false;
		}

		return true;
	}

	private boolean IsValidateAddForm(StoryForm _theForm) {

		if (!((_theForm.getFileDatas()[0].getOriginalFilename().length() > 4)
				&& (_theForm.getFileDatas()[1].getOriginalFilename().length() > 4)

		)) {
			errorMessage = "Image and thumb must be not empty";
			return false;
		}
		return IsValidate(_theForm);
	}

	private boolean doUploadImageOfStory(HttpServletRequest request, Model model, //
			StoryForm myUploadForm, Admin _author, Story _story, String _mode) {

		String description = myUploadForm.getDescription();
		System.out.println("Description: " + description);
		String name = myUploadForm.getTitle();
		String uri = "";
		String url = "";
		// Thư mục gốc upload file.
		String uploadRootPath = request.getServletContext().getRealPath("upload");
		System.out.println("uploadRootPath=" + uploadRootPath);

		File uploadRootDir = new File(uploadRootPath);

		// Tạo thư mục gốc upload nếu nó không tồn tại.
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();

		}
		File storyDir = new File(uploadRootDir, "stories");
		if (!storyDir.exists()) {
			storyDir.mkdirs();
		}

		File imageDir = new File(storyDir, "images");
		if (!imageDir.exists()) {
			imageDir.mkdirs();
		}
		String hashName = name.substring(0, 3);
		File hashDir = new File(imageDir, hashName);
		if (!hashDir.exists()) {
			hashDir.mkdirs();
		}
		List<ImageContent> images = new ArrayList<ImageContent>();

		MultipartFile[] fileDatas = myUploadForm.getFileDatas();
		//
		List<File> uploadedFiles = new ArrayList<File>();
		List<String> failedFiles = new ArrayList<String>();

		for (MultipartFile fileData : fileDatas) {

			String fname = fileData.getOriginalFilename();
			int len = fname.length();
			String ext = fname.substring(len - 4, len);
			System.out.println("Client File Name = " + ext);

			if (fname != null && fname.length() > 0) {

				if (_mode.equalsIgnoreCase("edit")) {
					// delete old image before create new one.
					ImageContent tmp = null;
					if (fileData.getName() == "thumb") {
						tmp = _story.getThumb();
					} else if (fileData.getName() == "image") {
						tmp = _story.getImage();
					}

					File oldFile = new File(tmp.getUri());
					try {
						oldFile.delete();
					} catch (Exception ex) {
						errorMessage = "Deleting old image was not succesful";

						return false;

					}
				}

				try {

					// Tạo file tại Server.
					fname = name + fname + WebUtil.GetTime();

					url = "/upload/stories/images/" + hashName + File.separator + fname.hashCode() + ext;
					uri = hashDir.getAbsolutePath() + File.separator + fname.hashCode() + ext;
					File serverFile = new File(uri);

					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(fileData.getBytes());
					stream.close();
					//
					uploadedFiles.add(serverFile);

					if (_mode.equalsIgnoreCase("add")) {
						ImageContent image = new ImageContent();
						image.setName(name);
						image.setDescription(description);
						image.setUri(uri);
						image.setUrl(url);
						image.setAuthor(_author);
						image.setCreateDate(WebUtil.GetTime());
						// imageContentService.save(image);
						if (fileData.getName().equalsIgnoreCase("thumb")) {
							_story.setThumb(image);
						} else if (fileData.getName().equalsIgnoreCase("image")) {
							_story.setImage(image);
						}
					} else if (_mode.equalsIgnoreCase("edit")) {
						if (fileData.getName().equalsIgnoreCase("thumb")) {
							ImageContent thumb = _story.getThumb();
							thumb.setUri(uri);
							thumb.setUrl(url);
						} else if (fileData.getName().equalsIgnoreCase("image")) {
							ImageContent image = _story.getImage();
							image.setUri(uri);
							image.setUrl(url);
						}
					}

				} catch (Exception e) {
					System.out.println("Error Write file: " + name);
					failedFiles.add(name);
					return false;
				}
			}
		}

		model.addAttribute("description", description);
		model.addAttribute("uploadedFiles", uploadedFiles);
		model.addAttribute("failedFiles", failedFiles);
		return true;
	}
}
