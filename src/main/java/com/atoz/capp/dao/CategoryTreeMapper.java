package com.atoz.capp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.atoz.capp.model.CategoryTree;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryTreeMapper {
    int deleteByPrimaryKey(Long oid);

    int insert(CategoryTree record);

    int insertSelective(CategoryTree record);

    CategoryTree selectByPrimaryKey(Long oid);

    int updateByPrimaryKeySelective(CategoryTree record);

    int updateByPrimaryKey(CategoryTree record);

	List<CategoryTree> selectCataByParent(@Param("oid") Long oid, @Param("treeType") String treeType);

	int getMaxSort(@Param("parentId") Long parentId, @Param("treeType") String treeType);

	int getCategoryByName(@Param("name") String name, @Param("parentId") Long parentId, @Param("treeType") String treeType);

	String getOldName(Long oid);

	int getCategoryByNameUpdate(@Param("name") String name, @Param("parentId") Long parentId, @Param("oldName") String oldName, @Param("treeType") String treeType);

	String getType(Long belongcategory);
}