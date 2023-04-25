package com.rabbit.dayfilm.common.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

public class CopyUtil {

    public static String[] getNullPropertyNames(Object source) {
        // 소스 객체에 대한 BeanWrapper 객체 생성
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        // null 값을 가진 속성의 이름을 저장하기 위한 집합 만들기
        Set<String> emptyNames = new HashSet<>();

        // 소스 객체의 모든 속성을 반복
        // 값이 null이면 속성 이름을 집합에 추가
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
