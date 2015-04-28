package com.acme.oms.ui.common.view

import com.vaadin.data.fieldgroup.FieldGroup
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.Field
import com.vaadin.data.util.BeanItem

/**
 * Types E and B can be the same, if E is already a valid bean
 * 
 * E type of Entity
 * B type of Bean associated to E
 */
abstract class Binder<E, B> {
    
    static val NO_ENTITY_FORM_BEAN_FACTORY = "An EntityFormBeanFactory instance must be provided."
    
    val FieldGroup fieldGroup
    val EntityFormBeanFactory<B> factory
    
    new(EntityFormBeanFactory<B> factory) {
        if (factory === null)
            throw new IllegalArgumentException(NO_ENTITY_FORM_BEAN_FACTORY)
        this.factory = factory
        this.fieldGroup = new FieldGroup
    }
    
    def bind(Field<?> field, Object propertyId) {
        fieldGroup.bind(field, propertyId)
    }
    
    def commit() throws CommitException {
        fieldGroup.commit
    }

    def discard() {
        fieldGroup.discard
    }
    
    def isValid() {
        return fieldGroup.valid
    }
    
    def setReadOnly(boolean readOnly) {
        // NullPointerException on Field with setReadOnly(...)
        // The result is very good with setEnabled(...) so keep it as it is.
        fieldGroup.enabled = ! readOnly
    }
    
    def BeanItem<B> getItemDataSource() {
        // This cast is correct as fieldGroup is setup only in the bind method of this class
        return fieldGroup.getItemDataSource() as BeanItem<B>
    }
    
    def bind(B entityBean) {
        val item = new BeanItem<B>(entityBean?: factory.newBean)
        fieldGroup.itemDataSource = item
    }
    
    def abstract void bindEntity(E entity);
    
}