package com.atoz.capp.service;

import java.util.List;

import com.atoz.capp.model.CategoryTree;
import com.atoz.capp.model.TrainningTreeData;
import com.atoz.capp.model.User;

/**
 * 分类树管理接口类
 * @author caicai.gao
 */
public interface CategoryTreeServiceI {

	List<TrainningTreeData> getCategoryTree(String parentId, String treeType);

	int dragNode(String oid, String oldParentId, String parentTag, String name, String treeType, Long sortId);

	int deleteCategory(Long oid, String treeType);

	void insertCategory(CategoryTree categoryTree, User loginUser);

	void updateCategory(CategoryTree categoryTree, User loginUser);

	String getType(Long belongcategory);

}
