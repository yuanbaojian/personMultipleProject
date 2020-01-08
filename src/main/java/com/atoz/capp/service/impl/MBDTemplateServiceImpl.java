package com.atoz.capp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atoz.capp.common.Constants;
import com.atoz.capp.dao.MBDHistoryMapper;
import com.atoz.capp.dao.MBDTemplateMapper;
import com.atoz.capp.dao.TemplateAttrMapper;
import com.atoz.capp.dao.TemplateTagMapper;
import com.atoz.capp.exception.BusinessException;
import com.atoz.capp.model.MBDHistory;
import com.atoz.capp.model.MBDTemplate;
import com.atoz.capp.model.TemplateAttr;
import com.atoz.capp.model.TemplateTag;
import com.atoz.capp.model.TemplateTagAttr;
import com.atoz.capp.model.TrainningTreeData;
import com.atoz.capp.model.User;
import com.atoz.capp.service.MBDTemplateServiceI;

@Service("mbdTemplateService")
public class MBDTemplateServiceImpl implements MBDTemplateServiceI {

	@Autowired
	private MBDTemplateMapper mbdTemplateMapper;
	
	@Autowired
	private TemplateTagMapper templateTagMapper;
	
	@Autowired
	private TemplateAttrMapper templateAttrMapper;
	
	@Autowired
	private MBDHistoryMapper mbdHistoryMapper;

	@Override
	public long hasMbdTemplate(String nodeId) {
		return mbdTemplateMapper.hasMBDTemplate(Long.valueOf(nodeId));
	}

	@Override
	public void insertMbdTemplate(MBDTemplate mbdTemplate) {
		 mbdTemplateMapper.insertSelective(mbdTemplate);
	}

	@Override
	public void updateMbdTemplate(MBDTemplate mbdTemplate, User loginUser) {
		mbdTemplateMapper.updateByPrimaryKeySelective(mbdTemplate);
	}

	@Override
	public MBDTemplate selectByPrimaryKey(Long oid) {
		return mbdTemplateMapper.selectByPrimaryKey(oid);
	}
	
	@Override
	public void deleteByPrimaryKey(Long oid) {
		
		// 删除MBD模板
		try {
			// 先删除MBD模板的属性与分类，再删除发布历史，最后删除MBD模板
			// 1.删除属性
			templateAttrMapper.deleteByMBDId(oid);
			// 2.删除分类
			templateTagMapper.deleteByNBDId(oid);
			// 3.删除发布历史
			mbdHistoryMapper.deleteByMBDOid(oid);
			// 4.删除MBD模板
			mbdTemplateMapper.deleteByPrimaryKey(oid);
		} catch (Exception ex) {
			throw new BusinessException(Constants.ERROR_CODE_BUSINESS, Constants.MBD_MSG001);
		}
	}

	@Override
	public List<MBDTemplate> getAll(String categoryId) {
		return mbdTemplateMapper.getAll(Long.valueOf(categoryId));
	}
	
	@Override
	public List<MBDTemplate> getAllReleased(String categoryId) {
		return mbdTemplateMapper.getAllReleased(Long.valueOf(categoryId));
	}
	
	@Override
	public int checkTemplateName(String templateName) {
		return mbdTemplateMapper.selectByTemplateName(templateName);
	}

	@Override
	public int checkTemplateCode(String templateCode) {
		return mbdTemplateMapper.selectByTemplateCode(templateCode);
	}


	@Override
	public void insertMbdTemplateTag(TemplateTag templateTag) {
	    templateTagMapper.insertSelective(templateTag);
		
	}

	@Override
	public List<TemplateTagAttr> getAttrTreeTable(Long templateId) {
		List<TemplateTagAttr> tagAttrList = new ArrayList<>();
		
		List<TemplateTag> rootTag = templateTagMapper.selectByRootTag(templateId);
		TemplateTagAttr tagAttr = new TemplateTagAttr();
		tagAttr.setId("t_"+rootTag.get(0).getOid().toString());
		tagAttr.setName(rootTag.get(0).getTagName());
		tagAttr.setParentid("");
		tagAttr.setTagSort(rootTag.get(0).getTagSort());
		tagAttr.setTemplateId(rootTag.get(0).getTemplateId());
		List<TemplateTag> childTag = templateTagMapper.selectByParentTag(rootTag.get(0).getOid().toString(), templateId);
		List<TemplateAttr> attrs = templateAttrMapper.selectByTagId(rootTag.get(0).getOid());
		tagAttr.setHasChild(attrs.size() + childTag.size() != 0);
		tagAttrList.add(tagAttr);
		// 获取某模型下的属性与标签，添加至  tagAttrList中
		return getAttrsAndTags(rootTag.get(0).getOid(), templateId, tagAttrList);
		
	}
	
	  
	/** 
	 * 获取某模型下的属性与标签，添加至  tagAttrList中
	 * @param oid MBD模板ID
	 * @param tagAttrList 标签与属性列表
	 * @return List<TemplateTagAttr>    返回类型  
	 */
	            
	private List<TemplateTagAttr> getAttrsAndTags(Long oid, Long templateId, List<TemplateTagAttr> tagAttrList) {
		// 获取指定标签下的属性
		List<TemplateAttr> attrs = templateAttrMapper.selectByTagId(oid);
	    // 将属性添加至tagAttrList中
		for (TemplateAttr attr:attrs) {
			TemplateTagAttr tagAttr2 = new TemplateTagAttr();
			tagAttr2.setId("a_"+attr.getOid().toString());
			tagAttr2.setName(attr.getAttrName());
		    tagAttr2.setParentid("child-of-t_"+attr.getAttrTag().toString());
			tagAttr2.setAttrDefaultValue(attr.getAttrDefaultValue()==null? "":attr.getAttrDefaultValue());
			tagAttr2.setAttrSort(attr.getAttrSort());
			tagAttr2.setAttrType(attr.getAttrType());
			tagAttr2.setAttrTypeName(attr.getAttrTypeName());
			tagAttr2.setMbdTemplate(attr.getMbdTemplate());
			tagAttr2.setIsMultiple(attr.getIsMultiple());
			tagAttr2.setIsMultipleName(attr.getIsMultipleName());
			tagAttr2.setHasChild(false);
			tagAttrList.add(tagAttr2);
		}
		// 获取指定标签下的标签
		List<TemplateTag> tags = templateTagMapper.selectByParentTag(oid.toString(), templateId);
		for (TemplateTag tag:tags) {
			// 将标签添加至tagAttrList中
			TemplateTagAttr tagAttr = new TemplateTagAttr();
			tagAttr.setId("t_"+tag.getOid().toString());
			tagAttr.setName(tag.getTagName());
			if (tag.getParentTag()!=null) {
				tagAttr.setParentid("child-of-t_"+tag.getParentTag().toString());
			} else {
				tagAttr.setParentid("");
			}
			tagAttr.setTagSort(tag.getTagSort());
			tagAttr.setTemplateId(tag.getTemplateId());
			List<TemplateTag> childTag = templateTagMapper.selectByParentTag(tag.getOid().toString(), templateId);
			List<TemplateAttr> attr = templateAttrMapper.selectByTagId(tag.getOid());
			tagAttr.setHasChild(attr.size() + childTag.size() != 0);
			tagAttrList.add(tagAttr);
			// 获取某模型下的属性与标签，添加至  tagAttrList中
			getAttrsAndTags(tag.getOid(), templateId, tagAttrList);
		}
		return tagAttrList;
	}

	@Override
	public List<TrainningTreeData> getTagTree(String parent,Long oid) {
		List<TrainningTreeData> lstResult = new ArrayList<>();
		List<TemplateTag> lstTags;
		if ("#".equals(parent)){
			lstTags = templateTagMapper.selectByRootTag(oid);
		} else {
			lstTags = templateTagMapper.selectByParentTag(parent,oid);
		}
		
		String rootId = "";
			for (TemplateTag tag : lstTags) {
				TrainningTreeData item = new TrainningTreeData();
				item.setId( String.valueOf(tag.getOid()));
				item.setNodeId( String.valueOf(tag.getOid()));
				item.setText(tag.getTagName());
				item.setParentId(tag.getParentTag() == null ? rootId : tag.getParentTag().toString());
				// 判断是否有子节点来设置child属性
				int childCnts = templateTagMapper.hasChild(tag.getOid());
				boolean isChild = false;
				if (childCnts > 0) {
					isChild = true;
				}
				item.setChildren(isChild);
				// 判断是否有父亲来设置type属性
				item.setType(tag.getParentTag() == null ? "#" : "folder");
				lstResult.add(item);
			}
		
		return lstResult;
	}

	@Override
	public List<TemplateAttr> getAttrList(Long tagId) {
		List<TemplateAttr> attrList = templateAttrMapper.selectByTagId(tagId);
		for (TemplateAttr attr : attrList) {
			if (attr.getAttrDefaultValue()==null) {
				attr.setAttrDefaultValue(" ");
			}
		}
		return attrList;
	}

	@Override
	public void insertMbdTemplateAttr(TemplateAttr templateAttr) {
		int i = templateAttrMapper.checkNameExits(templateAttr.getAttrTag(),templateAttr.getAttrName());
		if (i > 0) {
			throw new BusinessException(Constants.ERROR_CODE_BUSINESS, Constants.TRAINNING_MSG001); 
		}
		Long attrTag = templateAttr.getAttrTag();
		List<TemplateAttr> attrList = templateAttrMapper.selectByTagId(attrTag);
		int sort = 1;
		if (!attrList.isEmpty()) {
			sort = templateAttrMapper.getMaxSort(attrTag)+1;
		}
		templateAttr.setAttrSort((short) sort);
		templateAttrMapper.insertSelective(templateAttr);
	}

	@Override
	public void updateMbdTemplateAttr(TemplateAttr templateAttr) {
		int i = templateAttrMapper.selectByAttrName(templateAttr.getAttrTag(),templateAttr.getAttrName(),templateAttr.getOid());
		if (i > 0) {
			throw new BusinessException(Constants.ERROR_CODE_BUSINESS, Constants.TRAINNING_MSG001); 
		}
		templateAttrMapper.updateByPrimaryKeySelective(templateAttr);
	}

	@Override
	public TemplateAttr selectAttrByPrimaryKey(Long oid) {
		return templateAttrMapper.selectByPrimaryKey(oid);
	}

	@Override
	public void deleteAttrByPrimaryKey(Long oid,Long attrTag) {
		templateAttrMapper.deleteByPrimaryKey(oid);
		
	}
	
	@Override
	public void resort(Long attrTag) {
		List<TemplateAttr> attrList = templateAttrMapper.selectByTagId(attrTag);
		Short i =1;
		for (TemplateAttr attr : attrList) {
			
			attr.setAttrSort(i);
			templateAttrMapper.updateByPrimaryKeySelective(attr);
			i++;
		}
	}

	@Override
	public void updateMbdTemplateAttrSort(Long oid, Long attrTag, String operType) {
		if ("moveUp".equals(operType)){
			// 上移的属性i
			TemplateAttr attr = templateAttrMapper.selectByPrimaryKey(oid);
			// 上移属性的上一个属性i-1
			TemplateAttr preAttr = templateAttrMapper.selectBySort(attrTag,(long) (attr.getAttrSort()-1));
			// 上移属性的sort-1
			attr.setAttrSort((short) (attr.getAttrSort()-1));
			// 上移属性的上一个属性sort+1
			preAttr.setAttrSort((short) (preAttr.getAttrSort()+1));
			templateAttrMapper.updateByPrimaryKey(attr);
			templateAttrMapper.updateByPrimaryKey(preAttr);
		} else {
			// 下移的属性  i
			TemplateAttr attr = templateAttrMapper.selectByPrimaryKey(oid);
			// 上移属性的下一个属性i+1
			TemplateAttr preAttr = templateAttrMapper.selectBySort(attrTag,(long)(attr.getAttrSort()+1));
			// 下移属性的sort+1
			attr.setAttrSort((short) (attr.getAttrSort()+1));
			// 上移属性的上一个属性sort-1
			preAttr.setAttrSort((short) (preAttr.getAttrSort()-1));
			templateAttrMapper.updateByPrimaryKey(attr);
			templateAttrMapper.updateByPrimaryKey(preAttr);
		}
		
	}

	@Override
	public void copyMbdTemplate(Long oid, MBDTemplate mbdTemplate,User loginUser) {
		// 第一步：复制MBD模板
		MBDTemplate oldMbd = mbdTemplateMapper.selectByPrimaryKey(oid);
		oldMbd.setOid(null);
		oldMbd.setStatus(Constants.RELEASE_STATUS_0);
		oldMbd.setTemplateName(mbdTemplate.getTemplateName());
		oldMbd.setTemplateCode(mbdTemplate.getTemplateCode());
		oldMbd.setCreatedBy(loginUser.getUserId().longValue());
		oldMbd.setCreateTime(new Date());
		oldMbd.setUpdatedBy(loginUser.getUserId().longValue());
		oldMbd.setUpdateTime(new Date());
		mbdTemplateMapper.insertSelective(oldMbd);
		// 第二步：复制tag
		// oid——oldMBD模板ID
		List<TemplateTag> oldTagList = templateTagMapper.selectByTemplateId(oid);
		List<TemplateTag> newTagList = new ArrayList<>();
		for (TemplateTag oldTag : oldTagList) {
			oldTag.setOid(null);
			oldTag.setTemplateId(oldMbd.getOid());
			oldTag.setParentTag(null);
			oldTag.setCreatedBy(loginUser.getUserId().longValue());
			oldTag.setCreateTime(new Date());
			oldTag.setUpdatedBy(loginUser.getUserId().longValue());
			oldTag.setUpdateTime(new Date());
			newTagList.add(oldTag);
		}
		// 复制tag-第一步（先将所有的tag整体复制）
		for (TemplateTag newTag : newTagList) {
			templateTagMapper.insertSelective(newTag);
		}
		// 复制tag-第二步（设置tag的父节点）
		for (TemplateTag newTag : newTagList) {
			if (!(newTag.getTagName().equals(oldMbd.getTemplateName()))){
				// 获取新生成tag的parentTag的ID
				Long parentTagId = templateTagMapper.getParentTagId(newTag.getTagName(), oid,newTag.getTemplateId());
				newTag.setParentTag(parentTagId);
				templateTagMapper.updateByPrimaryKeySelective(newTag);
			}
		}
				
		// 第三步：复制attr
		// oid——oldMBD模板ID
		List<TemplateTag> oldTagList2 = templateTagMapper.selectByTemplateId(oid);
		for (TemplateTag oldTag : oldTagList2) {
			for (TemplateTag newTag : newTagList) {
				if (oldTag.getTagName().equals(newTag.getTagName())){
					List<TemplateAttr>  oldAattrList = templateAttrMapper.selectByTagId(oldTag.getOid());
					List<TemplateAttr>  newAttrList = new ArrayList<>();
					// 赋新值
					for (TemplateAttr oldAttr : oldAattrList) {
						oldAttr.setOid(null);
						oldAttr.setAttrTag(newTag.getOid());
						oldAttr.setMbdTemplate(newTag.getTemplateId());
						newAttrList.add(oldAttr);
					}
					// 保存新的属性
					for (TemplateAttr newAttr : newAttrList) {
						templateAttrMapper.insertSelective(newAttr);
					}
				}
			}
		}		
		
		// 复制tag-第三步（设置根节点名称）
		for (TemplateTag newTag : newTagList) {
			if (newTag.getParentTag() == null){
				newTag.setTagName(mbdTemplate.getTemplateName());
				templateTagMapper.updateByPrimaryKeySelective(newTag);
			}
		}
				
	}


	@Override
	public TemplateTag saveTag(int flg, String oid, Long templateId, Long parentTag, String tagName, User loginUser) {
		TemplateTag tag = null;
		int result = 0;
		// 检查名称是否已经存在
		Map<String,Object> map = new HashMap<>();
		map.put("templateId", templateId);
		map.put("tagName", tagName);
		if (flg != 0) {
			map.put("oid",oid);
		}
		if (parentTag != null){
			map.put("parentTag",parentTag);
		} else {
			map.put("parentTag",null);
		}
		// 同名验证
		int existNum = templateTagMapper.checkNameExisted(map);
		if ( existNum> 0) {
			throw new BusinessException(Constants.ERROR_CODE_BUSINESS, Constants.TRAINNING_MSG001); 
		}
		if (flg == 0) {
			// 新增
			tag = new TemplateTag();
			tag.setTagName(tagName);
			tag.setCreatedBy(loginUser.getUserId().longValue());
			tag.setUpdatedBy(loginUser.getUserId().longValue());
			tag.setTemplateId(templateId);
			tag.setCreateTime(new Date());
			tag.setUpdateTime(new Date());
			if (parentTag != null){
				tag.setParentTag(parentTag);
				
				List<TemplateTag> tagList = templateTagMapper.selectTagByParent(parentTag);
				if (!tagList.isEmpty()) {
					int i = templateTagMapper.getMaxSort(parentTag);
					tag.setTagSort((short) (i+1));
				} else {
					tag.setTagSort((short) 1);
				}
			}
			result = templateTagMapper.insertSelective(tag);
		} else {
			tag = templateTagMapper.selectByPrimaryKey(Long.valueOf(oid));
			tag.setTagName(tagName);

			tag.setUpdatedBy(loginUser.getUserId().longValue());
			tag.setUpdateTime(new Date());
			result = templateTagMapper.updateByPrimaryKeySelective(tag);
			// 若修改的节点为根节点，则修改MBD模板的名称
			if (tag.getParentTag()==null || tag.getParentTag()==0) {
				MBDTemplate mbd = mbdTemplateMapper.selectByPrimaryKey(templateId);
				mbd.setTemplateName(tagName);
				mbdTemplateMapper.updateByPrimaryKeySelective(mbd);
			}
		}
		
		if (result > 0) {
			return tag;
		} else {
			return null;
		}
	}

	
	@Override
	public int deleteTag(Long oid) {
		// 级联删除	
		int result = 0;
		List<TemplateTag> tagList = templateTagMapper.selectTagByParent(oid);
		if (!tagList.isEmpty()) {
			for (TemplateTag tTag : tagList) {
				result = deleteTag(tTag.getOid());
			}
		}
		// 删除自身
		try {
			TemplateTag tag = templateTagMapper.selectByPrimaryKey(oid);
			templateTagMapper.deleteByPrimaryKey(oid);
			templateAttrMapper.deleteByTagId(oid);
			List<TemplateTag> lstTag = templateTagMapper.selectTagByParent(tag.getParentTag());
			for (TemplateTag templateTag:lstTag) {
				if (templateTag.getTagSort()!=null&&tag.getTagSort()!=null&&templateTag.getTagSort()>tag.getTagSort()){
					templateTag.setTagSort((short) (templateTag.getTagSort()-1));
					templateTagMapper.updateByPrimaryKeySelective(templateTag);
				}
			}
		}catch (Exception ex) {
			throw new BusinessException(Constants.ERROR_CODE_BUSINESS, Constants.BOOKTAG_MSG002); 
		}
		return result;
		
	}


	@Override
	public int dragTag(Long oid,Long parentId,Long sortId) {
		int result ;
		List<TemplateTag> lstNewTag = templateTagMapper.selectTagByParent(parentId);
		// 拖动节点为文件夹，修改文件夹的父id
		TemplateTag templateTag = templateTagMapper.selectByPrimaryKey(oid);
		Long oldParent = templateTag.getParentTag();
		Short oldSortId = templateTag.getTagSort();
		if (oldSortId==null) {
			oldSortId=(short) 1;
		}
		// 判断是同级移动还是跨级移动
		if (oldParent.intValue()==parentId.intValue()){
			// 同级移动
			//往前移动
			if (oldSortId>sortId) {
				for (TemplateTag newTag:lstNewTag) {
					Short newTagSortId = newTag.getTagSort();
					if (newTagSortId!=null&&newTagSortId>=sortId&&newTagSortId<oldSortId) {
						newTag.setTagSort((short) (newTagSortId+1));
						templateTagMapper.updateByPrimaryKeySelective(newTag);
					}
				}
			} else {
				//往后移动
				for (TemplateTag newTag:lstNewTag) {
					Short newTagSortId = newTag.getTagSort();
					if (newTagSortId!=null&&newTagSortId<=sortId&&newTagSortId>oldSortId) {
						newTag.setTagSort((short) (newTagSortId-1));
						templateTagMapper.updateByPrimaryKeySelective(newTag);
					}
				}
			}
		} else {
			// 跨级移动
				
			// 目标结点下的子节点
			for (TemplateTag newTag:lstNewTag) {
				Short newTagSortId = newTag.getTagSort();
				// 子节点顺序号大于插入位置，顺序号+1
				if (newTagSortId!=null&&newTagSortId>=sortId) {
					newTag.setTagSort((short) (newTagSortId+1));
					templateTagMapper.updateByPrimaryKeySelective(newTag);
				}
			}
				
			// 原来节点下的子节点
			List<TemplateTag> lstOldTag = templateTagMapper.selectTagByParent(oldParent);
			for (TemplateTag newTag:lstOldTag) {
				Short newTagSortId = newTag.getTagSort();
				// 子节点顺序号大于移动节点的顺序号，顺序号-1
				if (newTagSortId!=null&&newTagSortId>=oldSortId) {
					newTag.setTagSort((short) (newTagSortId-1));
					templateTagMapper.updateByPrimaryKeySelective(newTag);
				}
			}
		}
		templateTag.setParentTag(parentId);
		templateTag.setTagSort(sortId.shortValue());
		result = templateTagMapper.updateByPrimaryKeySelective(templateTag);
		return result;
	}

	@Override
	public void insertHistory(MBDHistory history) {
		mbdHistoryMapper.insertSelective(history);
	}

	@Override
	public void unRelease(String mbdId, User loginUser) {
		// 更新状态与审核人
		MBDTemplate mbd = mbdTemplateMapper.selectByPrimaryKey(Long.valueOf(mbdId));
		mbd.setAuditedBy(Long.valueOf(loginUser.getUserId()));
		mbd.setAuditTime(new Date());
		mbd.setStatus(Constants.RELEASE_STATUS_0);
		mbdTemplateMapper.updateByPrimaryKeySelective(mbd);
		// 添加发布历史记录
		MBDHistory history = new MBDHistory();
		history.setAction("取消发布");
		history.setMbdOid(Long.valueOf(mbdId));
		history.setReleasedBy(Long.valueOf(loginUser.getUserId()));
		history.setReleaseTime(new Date());
		mbdHistoryMapper.insertSelective(history);
		
	}

	@Override
	public List<MBDHistory> getHistory(String mbdId) {
		return mbdHistoryMapper.getByMBDOid(Long.valueOf(mbdId));
	}

	            
	@Override
	public List<TemplateAttr> hasAttrChild(String tagId) {
		return templateAttrMapper.selectByTagId(Long.valueOf(tagId));
	}

	@Override
	public boolean hasStructure(Long oid) {
		List<TemplateTag> tags = templateTagMapper.selectByTemplateId(oid);
		return tags.size() > 1;
		
	}

	@Override
	public int checkTemplateNameUpdate(MBDTemplate mbdTemplate) {
		return mbdTemplateMapper.checkTemplateNameUpdate(mbdTemplate);
	}

	@Override
	public int checkTemplateCodeUpdate(MBDTemplate mbdTemplate) {
		return mbdTemplateMapper.checkTemplateCodeUpdate(mbdTemplate);
	}

}
