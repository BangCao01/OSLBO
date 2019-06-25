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
import orangeschool.controller.BaseController.MenuCode;
import orangeschool.form.ParagraphForm;
import orangeschool.form.StoryForm;
import orangeschool.model.Admin;
import orangeschool.model.ImageContent;
import orangeschool.model.MathSubject;
import orangeschool.model.Paragraph;
import orangeschool.model.Story;
import orangeschool.model.TextContent;
import orangeschool.service.AdminService;
import orangeschool.service.ImageContentService;
import orangeschool.service.TextContentService;
import orangeschool.service.ParagraphService;
import orangeschool.service.StoryService;

@Controller
@RequestMapping(path = "/paragraph") // This
public class ParagraphController extends BaseController {

	@Autowired
	private StoryService storyService;

	@Autowired
	private ImageContentService imageContentService;
	@Autowired
	private TextContentService textContentService;
	@Autowired
	private ParagraphService paragraphService;

	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public String paragraphIndex(Model model, Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Story)) {
			return "redirect:/home/404";
		}
		this.setAccessCode(model, _principal);
		this.setMessageAt(model, MessageCode.Index);
		model.addAttribute("paragraphs", paragraphService.findAll());
		this.setSelectedMenu(model, MenuCode.Story);
		return "paragraph/index";
	}

	@RequestMapping(value = { "/index/{id}" }, method = RequestMethod.GET)
	public String paragraphIndexById(@PathVariable("id") int id, Model model, Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Story)) {
			return "redirect:/home/404";
		}
		this.setAccessCode(model, _principal);
		this.setMessageAt(model, MessageCode.Index);
		model.addAttribute("storyID", id);
		model.addAttribute("story", storyService.findWithId(id));
		model.addAttribute("paragraphs", paragraphService.findAllByStoryID(id));
		this.setSelectedMenu(model, MenuCode.Story);
		return "paragraph/index";
	}

	@RequestMapping(value = "/detail/{storyid}/{id}", method = RequestMethod.GET)
	public String showDetailPage(@PathVariable("storyid") int storyid, @PathVariable("id") int id, Model model,
			Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Story)) {
			return "redirect:/home/404";
		}
		this.setMessageAt(model, MessageCode.Detail);
		model.addAttribute("story", storyService.findWithId(storyid));
		model.addAttribute("paragraph", paragraphService.findWithId(id));
		String title = "Story " + storyid + " >> paragraph " + id;
		model.addAttribute("title", title);
		model.addAttribute("storyID", storyid);
		this.setAccessCode(model, _principal);
		this.setSelectedMenu(model, MenuCode.Story);
		return "paragraph/detail";
	}

	@RequestMapping(value = "/edit/{storyid}/{id}", method = RequestMethod.GET)
	public String showEditPage(@PathVariable("storyid") int storyid, @PathVariable("id") int id, Model model,
			Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Story)) {
			return "redirect:/home/404";
		}
		model.addAttribute("storyID", storyid);
		model.addAttribute("paragraphID", id);

		model.addAttribute("story", storyService.findWithId(storyid));
		Paragraph paragraph = paragraphService.findWithId(id);
		model.addAttribute("paragraph", paragraph);
		ParagraphForm theForm = new ParagraphForm();
		theForm.setContent(paragraph.getText());
		theForm.setPageOrder(paragraph.getPageOrder());
		theForm.setStatus(paragraph.getStatus());

		model.addAttribute("paragraphForm", theForm);
		this.setAccessCode(model, _principal);
		this.setSelectedMenu(model, MenuCode.Story);
		return "paragraph/edit";
	}

	@RequestMapping(value = { "/edit/{storyid}/{id}" }, method = RequestMethod.POST)
	public String doPostEditForm(@PathVariable("storyid") int storyid, @PathVariable("id") int id,
			HttpServletRequest request, Model model, //
			@ModelAttribute("paragraphForm") ParagraphForm _paragraphForm, Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Story)) {
			return "redirect:/home/404";
		}
		String content = _paragraphForm.getContent();
		Integer order = _paragraphForm.getPageOrder();

		Integer status = _paragraphForm.getStatus();

		errorMessage = "";
		if (this.IsValidateEditForm(_paragraphForm)) {
			Story story = storyService.findWithId(storyid);
			User loginedUser = (User) ((Authentication) _principal).getPrincipal();
			Admin _author = this.userService.findByUsername(loginedUser.getUsername());
			TextContent textContent = this.textContentService.findByContent(content);
			if (textContent == null) {
				textContent = new TextContent();
				textContent.setContent(content);
				textContent.setStatus(status);
				textContent.setAuthor(_author);
				textContent.setCreateDate(WebUtil.GetTime());

			}

			Paragraph para = paragraphService.findWithId(id);
			para.setTextContent(textContent);
			para.setStatus(status);
			para.setAuthor(_author);
			para.setPageOrder(order);
			// para.setStory(story);
			para.setCreateDate(WebUtil.GetTime());

			if (_paragraphForm.IsValidate()) {
				if (!this.doUploadImageOfParagraph(request, model, _paragraphForm, _author, para, "edit")) {
					errorMessage = "Image input go wrong";
				}
			}

			if (errorMessage.isEmpty()) {
				ImageContent image = para.getImage();
				if (image != null)
					this.imageContentService.save(image);
				this.textContentService.save(textContent);
				this.paragraphService.save(para);
				this.setMessageAt(model, MessageCode.Edit);
				return "redirect:/paragraph/index/" + storyid;
			}

		}

		model.addAttribute("storyID", storyid);
		model.addAttribute("paragraphID", id);
		model.addAttribute("story", storyService.findWithId(storyid));
		model.addAttribute("paragraphForm", _paragraphForm);
		this.setAccessCode(model, _principal);
		this.setSelectedMenu(model, MenuCode.Story);
		model.addAttribute("errorMessage", errorMessage);
		return "paragraph/edit";
	}

	@RequestMapping(value = "/delete/{storyid}/{id}", method = RequestMethod.GET)
	public String showDeletePage(
			@PathVariable("storyid") int storyid, 
			@PathVariable("id") int id, 
			Model model,
			Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Story)) {
			return "redirect:/home/404";
		}
		try {
			
			Paragraph p = paragraphService.findWithId(id);
			ImageContent tmp = p.getImage();
			if (tmp != null) {
				File oldFile = new File(tmp.getUri());
				try {
					oldFile.delete();
				} catch (Exception ex) {
					errorMessage = "Deleting old image was not succesful";
				}
			}
			paragraphService.deleteById(id);
		} catch (Exception ex) {

		}
		this.setMessageAt(model, MessageCode.Delete);
		return "redirect:/story/detail/" + storyid;
	}

	@RequestMapping(value = { "/add/{id}" }, method = RequestMethod.GET)
	public String showAddParagraphPage(@PathVariable("id") int id, Model model, Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Story)) {
			return "redirect:/home/404";
		}
		ParagraphForm theForm = new ParagraphForm();
		model.addAttribute("paragraphForm", theForm);
		model.addAttribute("storyID", id);
		model.addAttribute("story", storyService.findWithId(id));
		model.addAttribute("paragraphs", paragraphService.findAllByStoryID(id));
		this.setSelectedMenu(model, MenuCode.Story);
		this.setAccessCode(model, _principal);
		return "paragraph/add";
	}

	@RequestMapping(value = { "/add/{id}" }, method = RequestMethod.POST)
	public String doPostSubject(@PathVariable("id") int id, HttpServletRequest request, Model model, //
			@ModelAttribute("paragraphForm") ParagraphForm _paragraphForm, Principal _principal) {
		if(this.checkForbidden(_principal,MenuCode.Story))
		{
			return "redirect:/home/404";
		}
		String content = _paragraphForm.getContent();
		Integer order = _paragraphForm.getPageOrder();

		Integer status = 1;// _textContentForm.getStatus();
		errorMessage = "";

		if (this.IsValidate(_paragraphForm)) {
			Story story = storyService.findWithId(id);
			User loginedUser = (User) ((Authentication) _principal).getPrincipal();
			Admin _author = this.userService.findByUsername(loginedUser.getUsername());
			TextContent textContent = this.textContentService.findByContent(content);
			if (textContent == null) {
				textContent = new TextContent();
				textContent.setContent(content);
				textContent.setStatus(status);
				textContent.setAuthor(_author);
				textContent.setCreateDate(this.GetTime());

			}

			Paragraph para = new Paragraph();
			para.setTextContent(textContent);

			para.setStatus(status);
			para.setAuthor(_author);
			para.setPageOrder(order);
			para.setStory(story);
			para.setCreateDate(this.GetTime());

			if (_paragraphForm.IsValidate()) {
				if (!this.doUploadImageOfParagraph(request, model, _paragraphForm, _author, para, "add")) {
					errorMessage = "Image input go wrong";
				}
			}

			if (errorMessage.isEmpty()) {
				ImageContent image = para.getImage();
				if (image != null)
					this.imageContentService.save(image);
				this.textContentService.save(textContent);
				this.paragraphService.save(para);
				this.setMessageAt(model, MessageCode.Add);
				return "redirect:/paragraph/index/" + id;
			}
		}

		model.addAttribute("paragraphForm", _paragraphForm);
		model.addAttribute("storyID", id);
		model.addAttribute("story", storyService.findWithId(id));
		model.addAttribute("paragraphs", paragraphService.findAllByStoryID(id));
		this.setSelectedMenu(model, MenuCode.Story);
		this.setAccessCode(model, _principal);
		model.addAttribute("errorMessage", errorMessage);
		return "paragraph/add/";
	}

	private boolean IsValidate(ParagraphForm _theForm) {
		if (_theForm.getContent().length() <= 0) {
			errorMessage = "Content field must be not empty";
			return false;
		} else if (_theForm.getPageOrder() <= 0) {
			errorMessage = "Page's order field must be not empty";
			return false;
		} else if (!_theForm.IsValidate()) {
			errorMessage = "Image and thumb must be not empty";
			return false;
		}

		return true;
	}

	private boolean IsValidateEditForm(ParagraphForm _theForm) {
		if (_theForm.getContent().length() <= 0) {
			errorMessage = "Content field must be not empty";
			return false;
		} else if (_theForm.getPageOrder() <= 0) {
			errorMessage = "Page order field must be not empty";
			return false;
		} else if (_theForm.getStatus() <= 0) {
			errorMessage = "Status field must be not empty";
			return false;
		}

		return true;
	}

	private boolean IsValidateFileForm(ParagraphForm _theForm) {
		if (!_theForm.IsValidate()) {
			// errorMessage= "Image and thumb must be not empty";
			return false;
		}
		return true;
	}

	private boolean doUploadImageOfParagraph(HttpServletRequest request, Model model, //
			ParagraphForm myUploadForm, Admin _author, Paragraph _paragraph, String _mode) {

		String name = myUploadForm.getContent();
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

			// Tên file gốc tại Client.
			String fname = fileData.getOriginalFilename();
			int len = fname.length();
			String ext = fname.substring(len - 4, len);
			System.out.println("Client File Name = " + ext);

			if (fname != null && fname.length() > 0) {

				if (_mode.equalsIgnoreCase("edit")) {
					// delete old image before create new one.
					ImageContent tmp = _paragraph.getImage();

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
					System.out.println("Write file: " + serverFile);
					System.out.println("Time: " + WebUtil.GetTime());
					ImageContent image = null;
					if (_mode.equalsIgnoreCase("add")) {
						image = new ImageContent();
						image.setName(name);
						image.setDescription(name);
						image.setUri(uri);
						image.setUrl(url);
						image.setAuthor(_author);
						image.setCreateDate(WebUtil.GetTime());
						// imageContentService.save(image);
						_paragraph.setImage(image);
					} else if (_mode.equalsIgnoreCase("edit")) {
						image = _paragraph.getImage();
						image.setUri(uri);
						image.setUrl(url);

					}

				} catch (Exception e) {
					System.out.println("Error Write file: " + name);
					failedFiles.add(name);
					return false;
				}
			}
		}

		// model.addAttribute("description", description);
		model.addAttribute("uploadedFiles", uploadedFiles);
		model.addAttribute("failedFiles", failedFiles);
		return true;
	}
}
