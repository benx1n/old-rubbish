package com.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface BaseDao<E,P> {
    //根据主键查询
    E getModel(P id);
    //根据条件查询
    List<E> getModelList(Map<String, Object> query);
    //根据id的集合查询
    List<E> getModelInList(Map<String, Object> query);
    //查询符合条件的实体数量
    Integer getModelListCount(Map<String, Object> query);

    //插入实体，不判空
    int insertModel(E model);
    //插入实体，如果字段为空则使用数据库默认值
    int insertModelWithoutNull(E model);

    //因为ORACLE主键不自增问题，暂时不用
    /*//批量插入实体，不判空
    int batchInsertModel(List<E> list);
    //批量插入实体，如果字段为空则使用数据库默认值
    int batchInsertModelWithoutNull(List<E> list);*/

    //更新，如果字段为空，则更新为null
    int updateModel(E model);
    //更新，如果字段为空，则不更新
    int updateModelWithoutNull(E model);
    //批量更新，如果字段为空，则更新为null 分别根据主键作为查询条件
    int batchUpdateModel(List<E> list);
    //批量更新，如果字段为空，则不更新 分别根据主键作为查询条件
    int batchUpdateModelWithoutNull(List<E> list);

    //根据主键删除一个实体
    int deleteModelByPk(P id);
    //根据条件删除实体集合，请确保查询条件存在，否则会删除整张表数据
    int deleteModelList(Map<String, Object> query);
    //删除所有符合要求的id的数据
    int deleteModelByIds(Set<P> idList);

    List<E> search(String key);

}
