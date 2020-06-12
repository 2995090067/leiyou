package com.zy.item.service;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.zy.common.enums.ExceptionEnum;
import com.zy.common.exception.LyException;
import com.zy.item.mapper.SpecGroupMapper;
import com.zy.item.mapper.SpecParamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class SpecificationService {
    @Autowired
    private SpecGroupMapper groupMapper;
    @Autowired
    private SpecParamMapper specParamMapper;

    public List<SpecGroup> queryGroupByCid(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        List<SpecGroup>list=groupMapper.select(specGroup);
        if(CollectionUtils.isEmpty(list)){
            //没查到 组
            throw new LyException(ExceptionEnum.SPEC_GROUP_NOT_FOUND);
        }
        return  list;
    }

    /**
     * 规格参数分组新增
     * @param specGroup
     */
    @Transactional(rollbackFor = Exception.class)//开启回滚，不写的都是默认运行时异常bu开启回滚
    public void insertGropByIdByName(SpecGroup specGroup) {
        Boolean ok=groupMapper.insert(specGroup)>0;
        if(!(ok)){
            throw new LyException(ExceptionEnum.INSERT_GROUP_BYNAME_ERROR);
        }
    }

    /**
     * 规格参数分组删除
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    public void delecteGroupById(Long id) {
        Boolean ok=groupMapper.deleteByPrimaryKey(id)>0;
        if(!ok){
            throw  new LyException(ExceptionEnum.DELECT_GROUP_BYID_ERROR);
        }
    }

    public void updateGroupById(SpecGroup specGroup) {
        Boolean ok=groupMapper.updateByPrimaryKeySelective(specGroup)>0;
        if(!ok){
            throw  new LyException(ExceptionEnum.UPLOAD_GROUP_BYID_ERROR);
        }
    }

    public List<SpecParam> querySpecParamList(Long gid,Long cid,Boolean searching) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        specParam.setCid(cid);
        specParam.setSearching(searching);
        List<SpecParam> list = specParamMapper.select(specParam);
        if (CollectionUtils.isEmpty(list)){
            throw new LyException(ExceptionEnum.SPEC_PARAM_NOT_FOUND);
        }
        return list;
    }
    @Transactional(rollbackFor = Exception.class)
    public void insertIntoSceParam(SpecParam specParam) {
            Boolean ok =specParamMapper.insert(specParam)>0;
            if(!ok){
                throw new LyException(ExceptionEnum.INSERT_SPEC_PARAM_ERROR);
            }
    }
    @Transactional(rollbackFor = Exception.class)
    public void updateSpecParam(SpecParam specParam) {
        Boolean ok=specParamMapper.updateByPrimaryKeySelective(specParam)>0;
        if(!ok){
            throw new LyException(ExceptionEnum.UPLOAD_GROUP_BYID_ERROR);
        }
    }
    @Transactional(rollbackFor = Exception.class)
    public void deleteSpecParam(long id) {
        Boolean ok =specParamMapper.deleteByPrimaryKey(id)>0;
        if(!ok){
            throw new LyException(ExceptionEnum.DELECT_SPECPARAM_BYID_ERROR);
        }
    }

}
