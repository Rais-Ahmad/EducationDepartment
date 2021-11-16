package com.example.EducationDepartment.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.Index;
import java.util.Date;

@Entity

@Table(name = "t_Permission", indexes = @Index(name = "permission_name",  columnList="permissions_name"))

public class Permissions {
	@Id
	@GeneratedValue
	private long id;
	
	@Column(name = "permissions_name" , unique = true, nullable = false)
	private String name;
	@Column(nullable = true)
	private boolean isActive;
	@Column(nullable = true)
	private Date createdDate;
	@Column(nullable = true)
	private Date updatedDate;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}


}
