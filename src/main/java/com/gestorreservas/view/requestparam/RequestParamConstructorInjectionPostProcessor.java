package com.gestorreservas.view.requestparam;

import com.google.common.base.MoreObjects;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.BeanDefinitionValidationException;

/**
 * @author jj 
 */
public class RequestParamConstructorInjectionPostProcessor implements BeanFactoryPostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(RequestParamConstructorInjectionPostProcessor.class);
    
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {        
        Stream.of(beanFactory.getBeanDefinitionNames())
                .map(name -> beanFactory.getBeanDefinition(name))
                .filter(this::isParamAllowedScope)
                .filter(b -> b.getBeanClassName() != null)
                .forEach(this::processBeanDefinition);
    }
    
    private void processBeanDefinition(BeanDefinition beanDefinition){
        try {
            for (Constructor<?> constructor : getBeanClass(beanDefinition).getDeclaredConstructors()) {
                int index = 0;
                for (Parameter constructorParameter : constructor.getParameters()) {
                    String requestParamAnnotationValue = getRequestParamAnnotationValue(constructorParameter);
                    if(requestParamAnnotationValue != null){
                        Class parameterClass = constructorParameter.getType();
                        Class genericClass = getGenericClass(constructorParameter.getParameterizedType());
                        if(requestParamAnnotationValue.isEmpty() && !constructorParameter.isNamePresent()) {
                            throw new BeanDefinitionValidationException(MessageFormat.format("No es posible realizar la inyección mediante @RequestParam en {0} ({1}). Para usar la anotación sin especificar el value es necesario compilar con la opción -parameters o -g", beanDefinition.getBeanClassName(), constructorParameter.getName()));
                        } else {
                            String requestParameterName = !requestParamAnnotationValue.isEmpty() ? requestParamAnnotationValue : constructorParameter.getName();
                            String beanClassName = beanDefinition.getBeanClassName();

                            String expression = RequestParametersExtractor.buildExpression(requestParameterName, parameterClass, genericClass, beanClassName);

                            TypedStringValue typedStringValue = new TypedStringValue(expression);
                            beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(index, typedStringValue, constructorParameter.getType().getName());
                        }                        
                    }
                    index++;
                }
            }
        } catch (ClassNotFoundException | SecurityException | IllegalArgumentException ex) {
            LoggerFactory.getLogger(RequestParamConstructorInjectionPostProcessor.class).error(ex.getLocalizedMessage(), ex);
        }
    }
    
    private String getRequestParamAnnotationValue(Parameter parameter){
        RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
        if(requestParam != null) return requestParam.value();
        
        org.springframework.web.bind.annotation.RequestParam springMvcRequestParam = parameter.getAnnotation(org.springframework.web.bind.annotation.RequestParam.class);
        if(springMvcRequestParam != null) return springMvcRequestParam.value();
        
        return null;
    }
    
    private Class getBeanClass(BeanDefinition beanDefinition) throws ClassNotFoundException {
        return Class.forName(MoreObjects.firstNonNull(beanDefinition.getBeanClassName(), beanDefinition.getFactoryBeanName()));
    }
    
    private boolean isParamAllowedScope(BeanDefinition definition){        
        return CheckRequestParamAnnotationUsageAllowedScope.REQUEST_PARAM_ALLOWED_SCOPES.contains(definition.getScope());
    }
    
    private Class getGenericClass(Type type) throws ClassNotFoundException{
        if(type instanceof ParameterizedType){
            return (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
        }
        return (Class)type;
    }
}
