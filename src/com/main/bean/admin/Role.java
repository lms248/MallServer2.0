package main.bean.admin;

import java.util.HashSet;
import java.util.Set;

import main.service.admin.AdminUserService;

import common.utils.StringUtils;

/**
 * 用户角色
 */
public class Role {
	private int id;
	private String name;
	private String permissions;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPermissions() {
		return permissions;
	}
	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}
	public Set<Permission> getPermissionSet() {
		if (StringUtils.isBlank(permissions)) {
			return new HashSet<Permission>();
		}
		Set<Permission> set = new HashSet<Permission>();
		for (String permissionName : permissions.split(",")) {
			Permission permission = AdminUserService.permissionContentByName.get(permissionName);
			if (permission != null) {
				set.add(permission);
			}
		}
		return set;
	}
	
}
