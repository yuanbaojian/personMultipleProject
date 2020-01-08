package com.atoz.capp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atoz.capp.common.Constants;
import com.atoz.capp.dao.CategoryTreeMapper;
import com.atoz.capp.dao.TrainningTreeDataMapper;
import com.atoz.capp.exception.BusinessException;
import com.atoz.capp.model.CategoryTree;
import com.atoz.capp.model.TrainningTreeData;
import com.atoz.capp.model.User;
import com.atoz.capp.service.CategoryTreeServiceI;

/**
 * 分类树维护实现类
 * @author caicai.gao
 */
@Service("categoryServiceI")
public class CategoryTreeServiceImpl implements CategoryTreeServiceI {

	@Autowired
	private CategoryTreeMapper categoryTreeMapper;
	
	@Autowired
	private TrainningTreeDataMapper trainningTreeDataMapper;
	
	@Override
	public List<TrainningTreeData> getCategoryTree(String parentId, String treeType) {
		List<TrainningTreeData> lstResult = new ArrayList<>();
		
		if (parentId.equals("#")){
			List<TrainningTreeData> treeDatas = trainningTreeDataMapper.getChildOfCategory(0L,treeType);
			for (TrainningTreeData data :treeDatas) {
			    if (Boolean.TRUE.equals(data.getChildren())) {
			    	data.setType("folder");
			    } else {
			    	data.setType("file");
			    }
			    lstResult.add(data);
			}
		} else {
			
			List<TrainningTreeData> treeDatas = trainningTreeDataMapper.getChildOfCategory(Long.valueOf(parentId),treeType);
		    for (TrainningTreeData data :treeDatas) {
		    	if (Boolean.TRUE.equals(data.getChildren())) {
		    		data.setType("folder");
		    	} else {
		    		data.setType("file");
		    	}
		    	lstResult.add(data);
		    }
		}
		return lstResult;
	}

	@Override
	public int deleteCategory(Long oid, String treeType) {
		// 级联删除	
				int result = 0;
				List<CategoryTree> cataList = categoryTreeMapper.selectCataByParent(oid, treeType);
				if (!cataList.isEmpty()) {
					for (CategoryTree cata : cataList) {
						result = deleteCategory(cata.getOid(), treeType);
					}
				}
				//删除自身
				try {
					CategoryTree catalogue = categoryTreeMapper.selectByPrimaryKey(oid);
					categoryTreeMapper.deleteByPrimaryKey(oid);
					//更新其他节点顺序
					List<CategoryTree> lstCata = categoryTreeMapper.selectCataByParent(catalogue.getParentId(), treeType);
					for (CategoryTree cata:lstCata) {
						if (cata.getSortId()!=null&&catalogue.getSortId()!=null&&cata.getSortId()>catalogue.getSortId()){
							cata.setSortId((short) (cata.getSortId()-1));
							categoryTreeMapper.updateByPrimaryKeySelective(cata);
						}
					}
				}catch (Exception ex) {
					throw new BusinessException(Constants.ERROR_CODE_BUSINESS, Constants.STANDARD_MANAGE_MSG001); 
				}
				return result;
	}

	@Override
	public void insertCategory(CategoryTree categoryTree,
			User loginUser) {
		//重名验证
				int count = categoryTreeMapper.getCategoryByName(categoryTree.getName(),categoryTree.getParentId(), categoryTree.getTreeType());
				short maxSort = (short) categoryTreeMapper.getMaxSort(categoryTree.getParentId(),categoryTree.getTreeType());
				if (count == 0) {
					Date date = new Date();
					categoryTree.setCreatedBy(loginUser.getUserId().longValue());
					categoryTree.setCreateTime(date);
					categoryTree.setUpdatedBy(loginUser.getUserId().longValue());
					categoryTree.setUpdateTime(date);
					categoryTree.setSortId((short) (maxSort+1));
					categoryTreeMapper.insertSelective(categoryTree);
					
				} else {
					throw new BusinessException(Constants.ERROR_CODE_BUSINESS, "分类名称已存在，请重新输入");
				}
	}

	@Override
	public void updateCategory(CategoryTree categoryTree,
			User loginUser) {
		String oldName = categoryTreeMapper.getOldName(categoryTree.getOid());
		int count = categoryTreeMapper.getCategoryByNameUpdate(categoryTree.getName(),categoryTree.getParentId(),oldName,categoryTree.getTreeType());
		if (count == 0) {
			Date date = new Date();
			categoryTree.setUpdatedBy(loginUser.getUserId().longValue());
			categoryTree.setUpdateTime(date);
			//获取模型修改之前的名称
			categoryTreeMapper.updateByPrimaryKeySelective(categoryTree);
		} else {
			throw new BusinessException(Constants.ERROR_CODE_BUSINESS, "分类名称已存在，请重新输入");
		}
	}

    //节点拖拽
	@Override
	public int dragNode(String oid, String oldParentId, String parentId, String name, String treeType, Long sortId) {
		int result = 0;
		Long parentIdLong = Long.valueOf(parentId);
		Long oidLong = Long.valueOf(oid);
		int count = categoryTreeMapper.getCategoryByName(name,Long.valueOf(parentId),treeType);
		if (count == 0 || oldParentId.equals(parentId)) {
		//分类节点的拖拽处理
			List<CategoryTree> lstNewCata = categoryTreeMapper.selectCataByParent(parentIdLong,treeType);
			//拖动节点为文件夹，修改文件夹的父id
			CategoryTree objCata = categoryTreeMapper.selectByPrimaryKey(oidLong);
			Long oldParent = objCata.getParentId();
			Short oldSortId = objCata.getSortId();
			if (oldSortId==null) {
				oldSortId=(short) 1;
			}
			//判断是同级移动还是跨级移动
			if (oldParent.intValue()==parentIdLong.intValue()){
			//同级移动
				//往前移动
				if (oldSortId>sortId) {
					for (CategoryTree newCata:lstNewCata) {
						Short newCataSortId = newCata.getSortId();
						if (newCataSortId!=null&&newCataSortId>=sortId&&newCataSortId<oldSortId) {
							newCata.setSortId((short) (newCataSortId+1));
							categoryTreeMapper.updateByPrimaryKeySelective(newCata);
						}
					}
				} else {
					//往后移动
					for (CategoryTree newCata:lstNewCata) {
						Short newCataSortId = newCata.getSortId();
						if (newCataSortId!=null&&newCataSortId<=sortId&&newCataSortId>oldSortId) {
							newCata.setSortId((short) (newCataSortId-1));
							categoryTreeMapper.updateByPrimaryKeySelective(newCata);
						}
					}
				}
			} else {//跨级移动
				//目标结点下的子节点
				for (CategoryTree newCata:lstNewCata) {
					Short newCataSortId = newCata.getSortId();
					//子节点顺序号大于插入位置，顺序号+1
					if (newCataSortId!=null&&newCataSortId>=sortId) {
						newCata.setSortId((short) (newCataSortId+1));
						categoryTreeMapper.updateByPrimaryKeySelective(newCata);
					}
				}
				
				//原来节点下的子节点
				List<CategoryTree> lstOldCata = categoryTreeMapper.selectCataByParent(oldParent,treeType);
				for (CategoryTree oldCata:lstOldCata) {
					Short newTagSortId = oldCata.getSortId();
					//子节点顺序号大于移动节点的顺序号，顺序号-1
					if (newTagSortId!=null&&newTagSortId>=oldSortId) {
						oldCata.setSortId((short) (newTagSortId-1));
						categoryTreeMapper.updateByPrimaryKeySelective(oldCata);
					}
				}
			}
			objCata.setParentId(parentIdLong);
			objCata.setSortId(sortId.shortValue());
			result = categoryTreeMapper.updateByPrimaryKeySelective(objCata);
		} else {
			throw new BusinessException(Constants.ERROR_CODE_BUSINESS, "分类名称重复，请重新操作！");
		}
		return result;
	}

	@Override
	public String getType(Long belongcategory) {
		return categoryTreeMapper.getType(belongcategory);
	}
	  
}
