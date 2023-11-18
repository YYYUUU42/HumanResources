package com.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {
    private BeanCopyUtils(){}

    //单个复制
    public static <V> V copyBean(Object source,Class<V> clazz){
        V result=null;

        try {
            //创建目标对象
            result= clazz.newInstance();

            //实现属性的copy
            BeanUtils.copyProperties(source,result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    //集合复制
    public static <O,V> List<V> copyBeanList(List<O> list,Class<V> clazz){
        return list.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }


}
