package com.gestorreservas.view.requestparam;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.converters.AbstractConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jj
 */
class RequestParametersExtractor {

    private static final Logger logger = LoggerFactory.getLogger(RequestParametersExtractor.class);
    private static final ConvertUtilsBean CONVERT_UTILS_BEAN = buildConvertUtilsBean();

    final HttpServletRequest request;

    public RequestParametersExtractor(HttpServletRequest request) {
        this.request = request;
    }

    public boolean containsParameter(String parameterName) {
        return request.getParameterValues(parameterName) != null;
    }

    public Object get(String parameterName, String type, String genericType, String beanClassName) {
        return getOptional(parameterName, type, genericType, beanClassName).orElse(null);
    }

    public Optional<Object> getOptional(String parameterName, String type, String genericType, String beanClassName) {
        if (!containsParameter(parameterName)) {
            return Optional.empty();
        }

        String[] parameterValues = request.getParameterValues(parameterName);
        Class<?> parameterType = getClass(type);
        List convertedRequestParams = Stream.of(parameterValues)
                .map(p -> convertSingleParameter(p, this.getClass(genericType), parameterName, beanClassName))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (convertedRequestParams != null) {
            try {
                Object convertedParam = CONVERT_UTILS_BEAN.convert(convertedRequestParams, parameterType);
                return Optional.of(convertedParam);
            } catch (ConversionException ex) {
                if (logger.isDebugEnabled()) {
                    logger.debug("No se ha podido convertir el parámetro '{}' a inyectar mediante @RequestParam en el bean {}. Motivo: {}", parameterName, beanClassName, ex.getMessage());
                }
            }
        }
        return Optional.empty();
    }

    private Object convertSingleParameter(Object item, Class target, String parameterName, String beanClassName) {
        try {
            return CONVERT_UTILS_BEAN.convert(item, target);
        } catch (ConversionException ex) {
            if (logger.isDebugEnabled()) {
                logger.debug("No se ha podido convertir un valor del parámetro '{}' a inyectar mediante @RequestParam en el bean {}. Motivo: {}", parameterName, beanClassName, ex.getMessage());
            }
        }
        return null;
    }

    private Class getClass(String clazz) {
        try {
            return Class.forName(clazz);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static ConvertUtilsBean buildConvertUtilsBean() {
        ConvertUtilsBean convertUtilsBean = new ConvertUtilsBean();
        convertUtilsBean.register(true, false, 0);
        convertUtilsBean.register(new UUIDConverter(), UUID.class);
        return convertUtilsBean;
    }

    static class UUIDConverter extends AbstractConverter {

        @Override
        protected <T> T convertToType(Class<T> type, Object value) throws Throwable {
            return type.cast(UUID.fromString(String.valueOf(value)));
        }

        @Override
        protected Class<?> getDefaultType() {
            return UUID.class;
        }
    }

    public static String buildExpression(String parameterName, Class clazz, Class genericClass, String beanClassName) {
        return "#{@requestParametersExtractor.get('" + parameterName + "','" + clazz.getName() + "','" + genericClass.getName() + "','" + beanClassName + "')}";
    }
}
