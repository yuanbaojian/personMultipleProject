package com.atoz.capp.model;

import java.util.List;
import java.util.Map;

public class PermissionTreeRootData {
	private String id;
	private String nodeId;
	private String text;
	private String icon;
	private List<PermissionTreeData> children;
	private String parentId;
	private String type;
	private Map<String,Boolean> state;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public List<PermissionTreeData> getChildren() {
		return children;
	}
	public void setChildren(List<PermissionTreeData> children) {
		this.children = children;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Map<String, Boolean> getState() {
		return state;
	}
	public void setState(Map<String, Boolean> state) {
		this.state = state;
	}
}
