package com.wallace.spring.boot.enums;

public enum Permission {

	USER_READ("user:read"), USER_WRITE("user:write"),

	ADMIN_READ("admin:read"), ADMIN_WRITE("admin:write"), ADMIN_DELETE("admin:delete"), ADMIN_UPTADE("admin:uptade");

	private final String permission;

	Permission(String permission) {
		this.permission = permission;
	}

	public String getPermission() {
		return permission;
	}
}