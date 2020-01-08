package com.atoz.capp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.atoz.capp.model.TrainningTreeData;
import com.atoz.capp.model.TreeJsonData;

/**
 * 分类树维护mapper
 * @author caicai.gao
 */
public interface TrainningTreeDataMapper {

	/**
	 * 获取某分类的子节点
	 * @param oid 节点ID
	 * @param treeType 分类树类型
	 * @return List<TrainningTreeData>  返回类型
	 */
	List<TrainningTreeData> getChildOfCategory(@Param("oid") Long oid, @Param("treeType") String treeType);

	/**
	 * 获取权限分类根节点的子节点
	 * @return List<TreeJsonData>    返回类型  
	 */
	List<TreeJsonData> getChildOfRoot();

	/**
	 * 获取权限分类某节点的子节点
	 * @param parent 节点ID
	 * @return List<TreeJsonData>    返回类型
	 */
	List<TreeJsonData> getChildOfPermission(Long parent);
}