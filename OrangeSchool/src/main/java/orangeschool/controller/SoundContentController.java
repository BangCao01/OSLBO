package orangeschool.controller;

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
import orangeschool.controller.BaseController.MessageCode;
import orangeschool.form.SoundUploadForm;
import orangeschool.model.Admin;
import orangeschool.model.SoundContent;

import orangeschool.service.AdminService;
import orangeschool.service.SoundContentService;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/sound") // This
public class SoundContentController extends BaseController {

	@Autowired
	private SoundContentService soundContentService;
	
	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public String utilImage(Model model, Principal _principal) {
		if(this.checkForbidden(_principal,MenuCode.Sounds))
		{
			return "redirect:/home/404";
		}
		
		this.setSelectedActionMenu(model, ActionMenuCode.List);
		this.setMessageAt(model, MessageCode.Index);
		model.addAttribute("message", message);
		model.addAttribute("sounds", soundContentService.findAll());
		this.setSelectedMenu(model, MenuCode.Sounds);
		this.setAccessCode(model, _principal);
		return "sound/index";
	}

	// GET: Hiển thị trang form upload
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String uploadOneFileHandler(Model model,Principal _principal) {
		if(this.checkForbidden(_principal,MenuCode.Sounds))
		{
			return "redirect:/home/404";
		}
		this.setSelectedActionMenu(model, ActionMenuCode.Add);
		SoundUploadForm myUploadForm = new SoundUploadForm();
		model.addAttribute("soundUploadForm", myUploadForm);
		this.setSelectedMenu(model, MenuCode.Sounds);
		this.setAccessCode(model, _principal);
		return "sound/add";
	}

	// POST: Sử lý Upload
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String uploadOneFileHandlerPOST(HttpServletRequest request, //
			Model model, //
			@ModelAttribute("soundUploadForm") SoundUploadForm myUploadForm, 
			Principal _principal) {
		if(this.checkForbidden(_principal,MenuCode.Sounds))
		{
			return "redirect:/home/404";
		}
		errorMessage = "";
		if (this.IsValidate(myUploadForm)) {
			User loginedUser = (User) ((Authentication) _principal).getPrincipal();
			Admin _author = this.userService.findByUsername(loginedUser.getUsername());
			SoundContent sound = new SoundContent();
			if (!this.doUploadSound(request, model, myUploadForm, _author, sound, "add")) {
				errorMessage = "Sound input goes wrong";
			}

			if (errorMessage.isEmpty()) {
				this.soundContentService.save(sound);
				this.setMessageAt(model, MessageCode.Add);
				return "redirect:/sound/detail/" + sound.getId();
			}

		}
		this.setAccessCode(model, _principal);
		this.setSelectedActionMenu(model, ActionMenuCode.Add);
		this.setSelectedMenu(model, MenuCode.Sounds);
		model.addAttribute("errorMessage", errorMessage);
		return "sound/add";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String showEditPage(@PathVariable("id") int id, 
			Model model,
			Principal _principal
			) {
		
		if(this.checkForbidden(_principal,MenuCode.Sounds))
		{
			return "redirect:/home/404";
		}
		SoundContent sound = soundContentService.findById(id);

		model.addAttribute("soundID", id);
		SoundUploadForm theForm = new SoundUploadForm();

		theForm.setName(sound.getName());
		theForm.setDescription(sound.getDescription());
		theForm.setUrl(sound.getUrl());
		theForm.setUri(sound.getUri());
		model.addAttribute("soundUploadForm", theForm);
		this.setAccessCode(model, _principal);
		this.setSelectedMenu(model, MenuCode.Sounds);
		return "sound/edit";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public String showEditPost(@PathVariable("id") int id, 
			Model model,
			@ModelAttribute("soundUploadForm") SoundUploadForm theForm, 
			HttpServletRequest request,
			Principal _principal) {
		if(this.checkForbidden(_principal,MenuCode.Sounds))
		{
			return "redirect:/home/404";
		}
		String name = theForm.getName();
		String description = theForm.getDescription();
		errorMessage = "";
		if (this.IsValidateEditForm(theForm)) {
			User loginedUser = (User) ((Authentication) _principal).getPrincipal();
			Admin _author = this.userService.findByUsername(loginedUser.getUsername());
			SoundContent sound = soundContentService.findById(id);
			if (this.IsValidateSoundFile(theForm)) {
				if (!this.doUploadSound(request, model, theForm, _author, sound, "edit")) {
					errorMessage = "Sound input goes wrong";
				}
			}
			if (errorMessage.isEmpty()) {
				sound.setName(name);
				sound.setDescription(description);
				sound.setUpdateDate(WebUtil.GetTime());
				this.soundContentService.save(sound);
				this.setMessageAt(model, MessageCode.Edit);
				return "redirect:/sound/detail/" + sound.getId();
			}
		}

		
		model.addAttribute("soundID", id);
		model.addAttribute("soundUploadForm", theForm);
		this.setSelectedMenu(model, MenuCode.Sounds);
		model.addAttribute("errorMessage", errorMessage);
		this.setAccessCode(model, _principal);
		return "sound/edit";
	}

	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public String showDetailPage(@PathVariable("id") int id, 
			Model model,
			Principal _principal
			) {
		if(this.checkForbidden(_principal,MenuCode.Sounds))
		{
			return "redirect:/home/404";
		}
		this.setMessageAt(model, MessageCode.Detail);
		model.addAttribute("sound", soundContentService.findById(id));
		this.setSelectedMenu(model, MenuCode.Sounds);
		this.setAccessCode(model, _principal);
		return "sound/detail";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String showDeletePage(@PathVariable("id") int id, Model model,Principal _principal) {
		
		if(this.checkForbidden(_principal,MenuCode.Sounds))
		{
			return "redirect:/home/404";
		}
		try {
			soundContentService.deleteById(id);
		} catch (Exception ex) {

		}
		this.setMessageAt(model, MessageCode.Delete);
		return "redirect:/sound/index";
	}

	private boolean IsValidateEditForm(SoundUploadForm _theForm) {
		String name = _theForm.getName();
		if (name.length() < 3) {
			errorMessage = "Sound's name must be longer more than 3 letters";
			return false;
		}

		return true;
	}

	private boolean IsValidateSoundFile(SoundUploadForm _theForm) {

		MultipartFile[] fileDatas = _theForm.getFileDatas();
		for (MultipartFile fileData : fileDatas) {

			// Tên file gốc tại Client.
			String fname = fileData.getOriginalFilename();
			int len = fname.length();
			if (len == 0) {
				errorMessage = "Sound must be in mp3.";
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

	private boolean IsValidate(SoundUploadForm _theForm) {
		String name = _theForm.getName();
		if (name.length() < 3) {
			errorMessage = "Sound's name must be longer more than 3 letters";
			return false;
		}

		return IsValidateSoundFile(_theForm);
	}

	private boolean doUploadSound(HttpServletRequest request, Model model, //
			SoundUploadForm myUploadForm, Admin _author, SoundContent _sound, String _mode) {

		String description = myUploadForm.getDescription();
		System.out.println("Description: " + description);
		String name = myUploadForm.getName();
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
