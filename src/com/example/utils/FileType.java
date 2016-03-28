package com.example.utils;

import java.io.InputStream;

public enum FileType {

	/**
	 * JPEG
	 */
	JPEG("FFD8FF"),
	
	/**
	 * PNG
	 */
	PNG("89504E47"),
	
	/**
	 * GIF
	 */
	GIF("47494638"),
	
	/**
	 * TIFF
	 */
	TIFF("49492A00"),
	
	/**
	 * Windows BMP
	 */
	BMP("424D"),
	
	/**
	 * CAD
	 */
	DWG("41433130"),
	
	/**
	 * Adobe Photoshop
	 */
	PSD("38425053"),
	
	/**
	 * XML
	 */
	XML("3C3F786D6C"),
	
	/**
	 * HTML
	 */
	HTML("6846D6C3E"),
	
	/**
	 * Adobe Acrobat
	 */
	PDE("255044462D312E"),
	
	/**
	 * 
	 */
	ZIP("504B0304"),
	
	/**
	 * RAR Archive
	 */
	RAR("52617221"),
	
	/**
	 * 
	 */
	WAV("57415645"),
	
	/**
	 * AVI
	 */
	AVI("41564920");
	
	
	private String value = "";
	
	private FileType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
}
