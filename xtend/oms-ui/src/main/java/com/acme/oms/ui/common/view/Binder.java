package com.acme.oms.ui.common.view;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Field;

/**
 * Types E and B can be the same, if E is already a valid bean
 * 
 * E type of Entity
 * B type of Bean associated to E
 */
public abstract class Binder<E, B> {

	private static final String NO_ENTITY_FORM_BEAN_FACTORY = "An EntityFormBeanFactory instance must be provided.";
	
	private final FieldGroup fieldGroup;
	private final EntityFormBeanFactory<B> factory;
	
	public Binder(EntityFormBeanFactory<B> factory) {
		if (factory == null)
			throw new IllegalArgumentException(NO_ENTITY_FORM_BEAN_FACTORY);
		this.factory = factory;
		this.fieldGroup = new FieldGroup();
	}
	
	public void bind(Field<?> field, Object propertyId) {
		fieldGroup.bind(field, propertyId);
	}
	
	public void commit() throws CommitException {
		fieldGroup.commit();
	}

	public void discard() {
		fieldGroup.discard();
	}
	
	public boolean isValid() {
	    return fieldGroup.isValid();
	}
	
	public void setReadOnly(boolean readOnly) {
	    // NullPointerException on Field with setReadOnly(...)
	    // The result is very good with setEnabled(...) so keep it as it is.
	    fieldGroup.setEnabled(! readOnly);//.setReadOnly(readOnly);
	}
	
	public BeanItem<B> getItemDataSource() {
	    // This cast is correct as fieldGroup is setup only in the bind method of this class
		@SuppressWarnings("unchecked")
		BeanItem<B> result = (BeanItem<B>) fieldGroup.getItemDataSource();
		return result;
	}
	
	public void bind(B entityBean) {
        if (entityBean == null) {
        	entityBean = factory.newBean();
        }
        BeanItem<B> item = new BeanItem<B>(entityBean);
        fieldGroup.setItemDataSource(item);
	}
	
	public abstract void bindEntity(E entity);
}