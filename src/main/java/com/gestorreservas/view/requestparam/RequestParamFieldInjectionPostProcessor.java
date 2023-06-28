package com.gestorreservas.view.requestparam;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

class RequestParamFieldInjectionPostProcessor implements BeanPostProcessor {
 
    final ApplicationContext applicationContext;

    public RequestParamFieldInjectionPostProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        this.scanParamAnnotation(bean, beanName);
        return bean;
    }
 
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) 
      throws BeansException {
        return bean;
    }
 
    protected void scanParamAnnotation(Object bean, String beanName) {
        this.configureInjection(bean);
    }
 
    private void configureInjection(Object bean) {
        Class<?> managedBeanClass = bean.getClass();
        FieldCallback fieldCallback = new RequestParamFieldCallback(bean, applicationContext);
        ReflectionUtils.doWithFields(managedBeanClass, fieldCallback, f -> f.isAnnotationPresent(RequestParam.class));
    }
}
