package com.seoulsystem.backboard.configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class MainConfiguration {
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:message_ko");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(5);

        return messageSource;
    }
    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory(){
        return new JPAQueryFactory(entityManager);
    }


}
