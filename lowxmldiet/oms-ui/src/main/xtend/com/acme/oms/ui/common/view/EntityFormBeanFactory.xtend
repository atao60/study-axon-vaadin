package com.acme.oms.ui.common.view

/**
 * 
 * If the entity is not a bean (e.g. no public constructor without arguments
 * or with missing setters), a wrapping bean must be provided
 * 
 * @param <B> type of the bean for the entity
 */
interface EntityFormBeanFactory<B> {
    def B newBean();
}