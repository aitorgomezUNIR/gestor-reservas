package com.gestorreservas.view.requestparam;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

/**
 *
 * @author jj
 */
class RequestParamFieldCallback implements FieldCallback {

    private static final Logger logger = LoggerFactory.getLogger(RequestParam.class);

    private final Object bean;
    private final ApplicationContext applicationContext;

    public RequestParamFieldCallback(Object bean, ApplicationContext applicationContext) {
        this.bean = bean;
        this.applicationContext = applicationContext;
    }

    @Override
    public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
        ReflectionUtils.makeAccessible(field);
        String name = field.getDeclaredAnnotation(RequestParam.class).value();
        if (name == null || name.isEmpty()) {
            name = field.getName();
        }
        final String requestParameterName = name;
        RequestParametersExtractor requestParametersExtractor = applicationContext.getBean(RequestParametersExtractor.class);

        if (!requestParametersExtractor.containsParameter(requestParameterName)) {
            return;
        }

        final Class fieldTargetClass = field.getType();
        final Class singleParameterTargetClass = getSingleItemTargetClass(field);

        Optional<Object> convertedParam = requestParametersExtractor.getOptional(requestParameterName, fieldTargetClass.getName(), singleParameterTargetClass.getName(), bean.getClass().getName());
        if (convertedParam.isPresent()) {
            field.set(bean, convertedParam.get());
        }
    }

    private Class getSingleItemTargetClass(Field field) {
        if (field.getGenericType() instanceof ParameterizedType) {
            return (Class) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
        } else {
            return field.getType();
        }
    }
}
