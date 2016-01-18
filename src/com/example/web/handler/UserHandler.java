package com.example.web.handler;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.entity.UploadImg;
import com.example.service.UploadImgService;
import com.example.utils.Const;
import com.example.utils.DfsUtils;
import com.example.utils.Direction;
import com.example.utils.Encodes;
import com.example.utils.FileUtil;
import com.example.utils.Page;

@RequestMapping("/user")
@Controller
public class UserHandler {
	
	
	@Autowired
	private UploadImgService imgService;
	
	@RequestMapping(value="/imgList", method=RequestMethod.GET)
	public String list(@RequestParam(required=false,defaultValue="1") Integer pageNum, Map<String, Object> map) {
		LinkedHashMap<String, Direction> orders = new LinkedHashMap<>();
		orders.put("uploadTime", Direction.DESC);
		Page<UploadImg> page = imgService.getPage(pageNum, 6, orders, null, null);
		map.put("page", page);
		
		return "user/list";
	}
	
	
	@RequestMapping(value="/toUpload", method=RequestMethod.GET)
	public String toUpload() {
		return "user/upload";
	}
	
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public String upload(@RequestParam MultipartFile img) {
		String filename = img.getOriginalFilename();
		System.out.println(filename);
		String extName = filename.substring(filename.lastIndexOf("."));
		filename = Encodes.encodeByMD5(filename+System.currentTimeMillis())+extName;
		System.out.println(filename);
		try {
			FileUtil.uploadFileToServer(img.getInputStream(), Const.upload_temp_dir, filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String path = DfsUtils.upload(Const.upload_temp_dir+filename);
		System.out.println("path: "+path);
		if(StringUtils.isNotEmpty(path)) {
			UploadImg uploadImg = new UploadImg();
			uploadImg.setPath(path);
			uploadImg.setUploadTime(System.currentTimeMillis());
			
			imgService.save(uploadImg);
			return "redirect:/user/imgList";
		} else {
			return "user/uploadFail";
		}
		
	}
	
	
	
}
