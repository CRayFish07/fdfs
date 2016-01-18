package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table
public class UploadImg extends SuperEntity {

	private static final long serialVersionUID = 3689670015163702429L;
	
	@Column(name="f_path", length=200)
	private String path;
	
	@Column(name="f_uploadTime")
	private Long uploadTime;

	public UploadImg() {
		super();
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Long getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Long uploadTime) {
		this.uploadTime = uploadTime;
	}
	
}
