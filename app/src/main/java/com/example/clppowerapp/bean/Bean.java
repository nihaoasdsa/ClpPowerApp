package com.example.clppowerapp.bean;

public class Bean {
	private String name;
	private Boolean checked;
	public Bean(String name, String phone,Boolean checked) {
		this.name = name;

		this.checked = checked;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}



}
