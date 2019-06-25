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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import orangeschool.WebUtil;
import orangeschool.controller.BaseController.ActionMenuCode;
import orangeschool.controller.BaseController.MessageCode;
import orangeschool.form.ImageUploadForm;
import orangeschool.form.SoundUploadForm;
import orangeschool.form.TextContentForm;
import orangeschool.form.TopicForm;
import orangeschool.model.Admin;
import orangeschool.model.ImageContent;
import orangeschool.model.SoundContent;
import orangeschool.model.TextContent;
import orangeschool.model.Topic;
import orangeschool.service.AdminService;
import orangeschool.service.SoundContentService;
import orangeschool.service.TextContentService;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/text") // This
public class TextContentController extends BaseController {

	@Autowired
	private TextContentService textContentService;
	@Autowired
	private SoundContentService soundContentService;

	@RequestMapping(value = { "/index/{pagenumber}" }, method = RequestMethod.GET)
	public String showIndexPage(
			@PathVariable("pagenumber") int pagenumber,
			Model model,
			Principal _principal
			
			) {
		
		if(this.checkForbidden(_principal,MenuCode.Texts))
		{
			return "redirect:/home/404";
		}
		this.setAccessCode(model, _principal);
		this.setMessageAt(model, MessageCode.Index);
		Page<TextContent> page = textContentService.findAll(pagenumber,100);
		
		model.addAttribute("texts", page);
		Integer totalPages = page.getTotalPages();
		if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
		
		this.setSelectedMenu(model, MenuCode.Texts);
		this.setSelectedActionMenu(model, ActionMenuCode.List);
		return "text/index";
	}

	@RequestMapping(value = { "/add" }, method = RequestMethod.GET)
	public String showAddPage(Model model, 
			Principal _principal
			) {
		if(this.checkForbidden(_principal,MenuCode.Texts))
		{
			return "redirect:/home/404";
		}
		this.setAccessCode(model, _principal);
		this.setSelectedMenu(model, MenuCode.Texts);
		TextContentForm theForm = new TextContentForm();
		model.addAttribute("textContentForm", theForm);
		this.setSelectedActionMenu(model, ActionMenuCode.Add);
		return "text/add";
	}

	@RequestMapping(value = { "/add" }, method = RequestMethod.POST)
	public String doAddPost(HttpServletRequest request, Model model, //
			@ModelAttribute("textContentForm") TextContentForm _textContentForm, Principal _principal) {
		if(this.checkForbidden(_principal,MenuCode.Texts))
		{
			return "redirect:/home/404";
		}
		String content = _textContentForm.getContent();
		Integer soundID = _textContentForm.getSoundID();
		Integer status = 1;// _textContentForm.getStatus();
		errorMessage = "";
		if (this.IsValidate(_textContentForm)) {

			User loginedUser = (User) ((Authentication) _principal).getPrincipal();
			Admin _author = this.userService.findByUsername(loginedUser.getUsername());
			SoundContent sound = null;
			if (this.IsValidateSoundFile(_textContentForm) && this.IsValidateSoundInput(_textContentForm)) {
				sound = new SoundContent();
				if (!this.doUploadSoundOfText(request, model, _textContentForm, _author, sound, "add")) {
					errorMessage = "Sound input goes wrong";
				}
			}
			if (errorMessage.isEmpty()) {
				TextContent text = new TextContent();
				text.setContent(content);
				text.setSound(sound);
				text.setStatus(status);
				text.setAuthor(_author);
				text.setCreateDate(WebUtil.GetTime());
				this.soundContentService.save(sound);
				this.textContentService.save(text);
				this.setMessageAt(model, MessageCode.Add);
				return "redirect:/text/detail/" + text.getId();
			}
		}
		this.setAccessCode(model, _principal);
		this.setSelectedActionMenu(model, ActionMenuCode.Add);
		this.setSelectedMenu(model, MenuCode.Texts);
		model.addAttribute("textContentForm", _textContentForm);
		model.addAttribute("errorMessage", errorMessage);
		return "text/add";
	}

	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public String showDetailPage(@PathVariable("id") int id, 
			Model model,
			Principal _principal
			) {
		if(this.checkForbidden(_principal,MenuCode.Texts))
		{
			return "redirect:/home/404";
		}
		this.setMessageAt(model, MessageCode.Detail);
		model.addAttribute("text", textContentService.findByTextID(id));
		this.setSelectedMenu(model, MenuCode.Texts);
		this.setAccessCode(model, _principal);
		return "text/detail";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String showDeletePage(@PathVariable("id") int id, 
			Model model,
			Principal _principal) {
		if(this.checkForbidden(_principal,MenuCode.Texts))
		{
			return "redirect:/home/404";
		}
		try {
			textContentService.deleteById(id);
		} catch (Exception ex) {

		}
		this.setMessageAt(model, MessageCode.Delete);
		return "redirect:/text/index";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String showEditPage(@PathVariable("id") int id, Model model,
			Principal _principal
			) {
		if(this.checkForbidden(_principal,MenuCode.Texts))
		{
			return "redirect:/home/404";
		}
		model.addAttribute("message", message);
		TextContent text = textContentService.findByTextID(id);

		model.addAttribute("textID", id);
		TextContentForm theForm = new TextContentForm();

		theForm.setContent(text.getContent());
		theForm.setStatus(text.getStatus());
		theForm.setSoundDescription(text.getSoundDescription());
		theForm.setSoundName(text.getSoundName());
		model.addAttribute("textContentForm", theForm);
		this.setAccessCode(model, _principal);
		this.setSelectedMenu(model, MenuCode.Texts);
		return "text/edit";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public String showEditPost(@PathVariable("id") int id, 
			Model model,
			@ModelAttribute("textEditForm") TextContentForm theForm, 
			HttpServletRequest request, 
			Principal _principal) {
		

		if(this.checkForbidden(_principal,MenuCode.Texts))
		{
			return "redirect:/home/404";
		}
		String content = theForm.getContent();
		Integer status = theForm.getStatus();
		String soundName = theForm.getSoundName();
		String soundDescription = theForm.getSoundDescription();
		errorMessage = "";
		if (this.IsValidate(theForm)) {
			User loginedUser = (User) ((Authentication) _principal).getPrincipal();
			Admin _author = this.userService.findByUsername(loginedUser.getUsername());
			TextContent text = this.textContentService.findByTextID(id);

			SoundContent sound = null;
			if (this.IsValidateSoundFile(theForm) && this.IsValidateSoundInput(theForm)) {
				sound = new SoundContent();
				if (!this.doUploadSoundOfText(request, model, theForm, _author, sound, "edit")) {
					errorMessage = "Sound input goes wrong";
				}
			}
			if (errorMessage.isEmpty()) {

				text.setContent(content);
				if (sound != null) {
					text.setSound(sound);
					text.setStatus(status);
					text.setSoundName(soundName);
					text.setSoundDescription(soundDescription);
					this.soundContentService.save(sound);
				}
				text.setUpdateDate(WebUtil.GetTime());

				this.textContentService.save(text);
				this.setMessageAt(model, MessageCode.Edit);
				return "redirect:/text/detail/" + id;
			}

		}

		this.setAccessCode(model, _principal);
		model.addAttribute("textID", id);
		model.addAttribute("textContentForm", theForm);
		this.setSelectedMenu(model, MenuCode.Texts);
		model.addAttribute("errorMessage", errorMessage);
		return "text/edit";
	}

	private boolean IsValidateSoundFile(TextContentForm _theForm) {

		MultipartFile[] fileDatas = _theForm.getFileDatas();
		for (MultipartFile fileData : fileDatas) {

			// Tên file gốc tại Client.
			String fname = fileData.getOriginalFilename();
			int len = fname.length();
			if (len == 0) {
				return false;
			}

			String ext = fname.substring(len - 4, len);
			if (!(ext.equalsIgnoreCase(".mp3"))) {
				errorMessage = "Sound must be in mp3.";
				return false;
			}

		}
		return true;
	}

	private boolean IsValidate(TextContentForm _theForm) {
		if (_theForm.getContent().length() <= 0) {
			errorMessage = "Content field must be not empty";
			return false;
		}

		return true;
	}

	private boolean IsValidateSoundInput(TextContentForm _theForm) {
		if (_theForm.getSoundName().length() <= 3) {
			errorMessage = "Sound's name field must be longer than 3 letters";
			return false;
		} else if (_theForm.getSoundDescription().length() <= 0) {
			errorMessage = "Sound's description field must be not empty";
			return false;
		}
		return true;
	}

	private boolean doUploadSoundOfText(HttpServletRequest request, Model model, //
			TextContentForm myUploadForm, Admin _author, SoundContent _sound, String _mode) {

		String description = myUploadForm.getSoundDescription();
		System.out.println("Description: " + description);
		String name = myUploadForm.getSoundName();
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
		File soundsDir = new File(uploadRootDir, "sounds");
		if (!soundsDir.exists()) {
			soundsDir.mkdirs();
		}
		String hashName = name.substring(0, 3);
		File hashDir = new File(soundsDir, hashName);
		if (!hashDir.exists()) {
			hashDir.mkdirs();
		}

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
					SoundContent tmp = _sound;

					File oldFile = new File(tmp.getUri());
					try {
						oldFile.delete();
					} catch (Exception ex) {
						errorMessage = "Deleting old sound was not succesful";
						return false;
					}
				}
				try {
					// Tạo file tại Server.
					fname = name + fname + WebUtil.GetTime();

					url = "/upload/sounds/" + hashName + File.separator + fname.hashCode() + ext;
					uri = hashDir.getAbsolutePath() + File.separator + fname.hashCode() + ext;
					File serverFile = new File(uri);

					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(fileData.getBytes());
					stream.close();
					//
					uploadedFiles.add(serverFile);
					System.out.println("Write file: " + serverFile);
					if (_mode.equalsIgnoreCase("add")) {
						if (!url.isEmpty()) {

							_sound.setName(name);
							_sound.setDescription(description);
							_sound.setUri(uri);
							_sound.setUrl(url);
							_sound.setAuthor(_author);
							_sound.setCreateDate(WebUtil.GetTime());

						} else {
							return false;
						}

					} else if (_mode.equalsIgnoreCase("edit")) {
						if (!url.isEmpty()) {
							_sound.setUri(uri);
							_sound.setUrl(url);
							_sound.setUpdateDate(WebUtil.GetTime());
						} else {
							return false;
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