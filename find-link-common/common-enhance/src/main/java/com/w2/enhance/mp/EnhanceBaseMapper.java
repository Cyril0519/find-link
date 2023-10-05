package com.w2.enhance.mp;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 增强的mapper对象
 * @param <T> 数据库对象
 * @param <V> Vo对象
 */
public interface EnhanceBaseMapper<T, V> extends BaseMapper<T> {

    /**
     * 获取vo对象，通过泛型获取类
     * @return vo的类
     */
    default Class<V> currentVoClass() {
        return (Class<V>) ReflectionKit.getSuperClassGenericType(this.getClass(), EnhanceBaseMapper.class, 1);
    }


    default V selectVoById(Serializable id) {
        return selectVoById(id, this.currentVoClass());
    }

    /**
     * 根据 ID 查询
     */
    default <C> C selectVoById(Serializable id, Class<C> voClass) {
        T obj = this.selectById(id);
        if (ObjectUtil.isNull(obj)) {
            return null;
        }
        return BeanUtil.toBean(obj, voClass);
    }

    default List<V> selectVoById(Collection<? extends Serializable> idList) {
        return selectVoBatchIds(idList, this.currentVoClass());
    }

    /**
     * 查询（根据ID 批量查询）
     */
    default <C> List<C> selectVoBatchIds(Collection<? extends Serializable> idList, Class<C> voClass) {
        List<T> list = this.selectBatchIds(idList);
        if (CollUtil.isEmpty(list)) {
            return CollUtil.newArrayList();
        }
        return BeanUtil.copyToList(list, voClass);
    }


    default V selectVoOne(Wrapper<T> wrapper) {
        return selectVoOne(wrapper, this.currentVoClass());
    }

    /**
     * 根据 entity 条件，查询一条记录
     */
    default <C> C selectVoOne(Wrapper<T> wrapper, Class<C> voClass) {
        T obj = this.selectOne(wrapper);
        if (ObjectUtil.isNull(obj)) {
            return null;
        }
        return BeanUtil.toBean(obj, voClass);
    }

    default List<V> selectVoList(Wrapper<T> wrapper) {
        return selectVoList(wrapper, this.currentVoClass());
    }

    /**
     * 根据 entity 条件，查询全部记录
     */
    default <C> List<C> selectVoList(Wrapper<T> wrapper, Class<C> voClass) {
        List<T> list = this.selectList(wrapper);
        if (CollUtil.isEmpty(list)) {
            return CollUtil.newArrayList();
        }
        return BeanUtil.copyToList(list, voClass);
    }

    default <P extends IPage<V>> P selectVoPage(IPage<T> page, Wrapper<T> wrapper) {
        return selectVoPage(page, wrapper, this.currentVoClass());
    }

    /**
     * 分页查询VO
     */
    default <C, P extends IPage<C>> P selectVoPage(IPage<T> page, Wrapper<T> wrapper, Class<C> voClass) {
        IPage<T> pageData = this.selectPage(page, wrapper);
        IPage<C> voPage = new Page<>(pageData.getCurrent(), pageData.getSize(), pageData.getTotal());
        if (CollUtil.isEmpty(pageData.getRecords())) {
            return (P) voPage;
        }
        voPage.setRecords(BeanUtil.copyToList(pageData.getRecords(), voClass));
        return (P) voPage;
    }
}
