package com.service;

import com.dao.BaseDao;
import com.utils.MapUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public  class BaseService<E,P> {

    public BaseDao<E,P> baseDao;

    /**
     * 根据主键获取实体
     * @param id 主键
     * @return 实体
     */
    public E getModel(P id) {
        return baseDao.getModel(id);
    }

    /**
     * 根据条件查询
     * @param  query 条件集合
     * @return
     */
    public List<E> getModelList(Map<String,Object> query) {
        return baseDao.getModelList(query);
    }

    /**
     * 查询符合条件的第一条数据
     * @param query 入参 调用MapUtil构造
     * @return
     */
    public E selectOneModel(Map<String, Object> query){
        List<E> modelList = baseDao.getModelList(query);
        if (modelList != null && modelList.size() >0){
            return modelList.get(0);
        }else {
            return null;
        }
    }
    /**
     * 根据id集合查询一组实体
     * @param idList 入参 调用MapUtil构造
     * @return
     */
    public List<E> getModelInList(Set<P> idList) {
        if(idList==null || idList.isEmpty()){
            return null;
        }
        return baseDao.getModelInList(MapUtil.buildMap("idList",idList));
    }

    /**
     * 查询符合查询条件实体集合的个数
     * @param query
     * @return
     */
    public int getModelListCount(Map<String, Object> query) {
        Integer count = baseDao.getModelListCount(query);
        if(count == null){
            count = 0;
        }
        return count;
    }

    /*
     * 保存实体
     * @param model
     * @return null
     */
    public int insertModel(E model) {
        return baseDao.insertModel(model);
    }

    /**
     *  保证Model 主键为空  插入单个实体 遇到入参null转换成数据库默认值
     * @param model
     * @return null
     */
    public int insertModelWithoutNull(E model) {
        return baseDao.insertModelWithoutNull(model);
    }

    /**
     * 更新实体 如果字段为空，则更新为null
     * 保证实体中的主键不为空
     * @param model
     */
    public int updateModel(E model) {
        return baseDao.updateModel(model);
    }

    /**
     * 更新实体 null字段忽略更新
     * 保证实体中的主键不为空
     * @param model
     */
    public int updateModelWithoutNull(E model) {
        return baseDao.updateModelWithoutNull(model);
    }

    /**
     * 批量更新实体 保证list中的实体 主键不为空  null值会字段设置数据库默认字段
     * @param list
     */
    public int batchUpdateModel(List<E> list) {
        return baseDao.batchUpdateModel(list);
    }

    /**
     * 批量更新实体 保证list中的实体 主键不为空 null字段忽略更新
     * @param list
     */
    public int batchUpdateModelWithoutNull(List<E> list) {
        return baseDao.batchUpdateModelWithoutNull(list);
    }

    /**
     * 根据主键删除实体
     * @param id 主键
     */
    public int deleteModel(P id) {
        return baseDao.deleteModelByPk(id);
    }

  /**
     * 删除实体集合 务必保证query的字段对应的值存在 否则会造成整个表清空
     * @param query 如果字段值不为空 将作为查询条件
     */
    public int deleteModel(Map<String,Object> query){
        return baseDao.deleteModelList(query);
    }

    /**
     * 根据主键list 批量删除实体
     * @param idList
     */
    public int deleteModel(Set<P> idList) {
        return baseDao.deleteModelByIds(idList);
    }


    public List<E> search(String key){
        return baseDao.search(key);
    };
}
