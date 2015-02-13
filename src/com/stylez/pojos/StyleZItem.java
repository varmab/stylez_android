package com.stylez.pojos;

public class StyleZItem {
	
	private String id,gender,image,styleId,userId,styleName,enable;
	
	public StyleZItem(String id, String gender, String image, String styleId,
			String userId, String styleName, String enable) {
		super();
		this.id = id;
		this.gender = gender;
		this.image = image;
		this.styleId = styleId;
		this.userId = userId;
		this.styleName = styleName;
		this.enable = enable;
	}
	
	
	
	

	public String getId() {
		return id;
	}

	public String getGender() {
		return gender;
	}

	public void setId(String id) {
		this.id = id;
	}





	public void setGender(String gender) {
		this.gender = gender;
	}





	public void setImage(String image) {
		this.image = image;
	}





	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}





	public void setUserId(String userId) {
		this.userId = userId;
	}





	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}





	public void setEnable(String enable) {
		this.enable = enable;
	}





	public String getImage() {
		return image;
	}

	public String getStyleId() {
		return styleId;
	}

	public String getUserId() {
		return userId;
	}

	public String getStyleName() {
		return styleName;
	}

	public String getEnable() {
		return enable;
	}

}
