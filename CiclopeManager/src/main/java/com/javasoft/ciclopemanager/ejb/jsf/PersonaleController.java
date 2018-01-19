package com.javasoft.ciclopemanager.ejb.jsf;

import com.javasoft.ciclopemanager.ejb.entities.Personale;
import com.javasoft.ciclopemanager.ejb.jsf.util.JsfUtil;
import com.javasoft.ciclopemanager.ejb.jsf.util.JsfUtil.PersistAction;
import com.javasoft.ciclopemanager.ejb.session.PersonaleFacade;
import com.javasoft.ciclopemanager.ejb.session.exception.FacadeException;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("personaleController")
@SessionScoped
public class PersonaleController implements Serializable {

    @EJB
    private com.javasoft.ciclopemanager.ejb.session.PersonaleFacade ejbFacade;
    private List<Personale> items = null;
    private Personale selected;
    private Personale copySelected;
    private List<Personale> filteredItems;

    public PersonaleController() {
    }

    public Personale getSelected() {
        return selected;
    }

    public void setSelected(Personale selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

     public List<Personale> getFilteredItems() {
        return filteredItems;
    }

    public void setFilteredItems(List<Personale> filteredItems) {
        this.filteredItems = filteredItems;
    }
    
    private PersonaleFacade getFacade() {
        return ejbFacade;
    }

    public Personale prepareEdit() {
        copySelected = selected.deepClone();
        return copySelected;
    }
    
    public Personale prepareCreate() {
        selected = new Personale();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PersonaleCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PersonaleUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PersonaleDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Personale> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

     private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    if (persistAction == PersistAction.CREATE) {
                        getFacade().create(selected);
                    } else { //...UPDATE
                        getFacade().edit(selected, copySelected);                       
                        if (!JsfUtil.isValidationFailed()) {
                            selected = null; // Remove selection
                            items = null;    // Invalidate list of items to trigger re-query.
                        }
                    }
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException | FacadeException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {                    
                    msg = cause.getLocalizedMessage();                    
                }
                if (msg.length() > 0) {
                    if(msg.contains("Cannot delete or update a parent row"))
                        msg=ResourceBundle.getBundle("/Bundle").getString("MySQLException_1451");
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
                selected.restoreFromClone(copySelected);
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Personale getPersonale(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Personale> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Personale> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Personale.class)
    public static class PersonaleControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PersonaleController controller = (PersonaleController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "personaleController");
            return controller.getPersonale(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Personale) {
                Personale o = (Personale) object;
                return getStringKey(o.getIdPersonale());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Personale.class.getName()});
                return null;
            }
        }

    }

}
