package com.rabbit.dayfilm.common;

import com.rabbit.dayfilm.item.entity.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.Arrays;

public class CodeToEnumConverterFactory implements ConverterFactory<String, Enum<? extends Category>> {
    @Override
    public <T extends Enum<? extends Category>> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToEnumsConverter<>(targetType);
    }

    private static final class StringToEnumsConverter<T extends Enum<? extends Category>> implements Converter<String, T> {

        private final Class<T> enumType;
        private final boolean constantEnum;

        public StringToEnumsConverter(Class<T> enumType) {
            this.enumType = enumType;
            this.constantEnum = Arrays.stream(enumType.getInterfaces()).anyMatch(i -> i == Category.class);
        }

        @Override
        public T convert(String source) {
            if (source.isEmpty()) {
                return null;
            }
            T[] constants = enumType.getEnumConstants();
            for (T c : constants) {
                if (constantEnum) {
                    if (((Category) c).getValue().equals(source.trim())) {
                        return c;
                    }
                } else {
                    if (c.name().equals(source.trim())) {
                        return c;
                    }
                }
            }
            return null;
        }
    }
}
