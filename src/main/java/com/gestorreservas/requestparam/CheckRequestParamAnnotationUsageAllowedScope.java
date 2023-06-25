package com.gestorreservas.requestparam;

import com.google.common.base.MoreObjects;
import java.text.MessageFormat;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionValidationException;

public class CheckRequestParamAnnotationUsageAllowedScope implements BeanFactoryPostProcessor {

    static Set<String> REQUEST_PARAM_ALLOWED_SCOPES = Stream.of("request", "view").collect(Collectors.toSet());
    
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Stream.of(beanFactory.getBeanDefinitionNames())
                .map(name -> beanFactory.getBeanDefinition(name))
                .filter(this::isRequestParamAnnotationPresent)
                .forEach(this::checkAllowedScopeAnnotationUsage);
    }

    private void checkAllowedScopeAnnotationUsage(BeanDefinition beanDefinition) {
        if(!REQUEST_PARAM_ALLOWED_SCOPES.contains(beanDefinition.getScope())){
            throw new BeanDefinitionValidationException(MessageFormat.format("No es posible usar la anotación @RequestParam en {0} por ser un bean de scope ''{1}''. Scopes permitidos: {2}", beanDefinition.getBeanClassName(), beanDefinition.getScope(), REQUEST_PARAM_ALLOWED_SCOPES));
        }
    }
    
    private boolean isRequestParamAnnotationPresent(BeanDefinition beanDefinition) {
        return isRequestParamAnnotationInConstructor(beanDefinition) || isRequestParamAnnotationInFields(beanDefinition);
    }
    
    private boolean isRequestParamAnnotationInConstructor(BeanDefinition beanDefinition){
        return Stream.of(getBeanClass(beanDefinition).getDeclaredConstructors())
                     .flatMap(constructor -> Stream.of(constructor.getParameters()))
                     .anyMatch(parameter -> parameter.isAnnotationPresent(RequestParam.class) || parameter.isAnnotationPresent(org.springframework.web.bind.annotation.RequestParam.class));
    }
    
    private boolean isRequestParamAnnotationInFields(BeanDefinition beanDefinition){
        return Stream.of(getBeanClass(beanDefinition).getFields())
                     .anyMatch(field -> field.isAnnotationPresent(RequestParam.class));
    }
    
    private Class getBeanClass(BeanDefinition beanDefinition) {
        try {
            return Class.forName(MoreObjects.firstNonNull(beanDefinition.getBeanClassName(), beanDefinition.getFactoryBeanName()));
        } catch (ClassNotFoundException ex) {
            return Object.class; 
        }
    }
}
