package com.javasoft.ciclopemanager.ejb.jsf;

import com.javasoft.ciclopemanager.ejb.entities.Pratichespeciali;
import com.javasoft.ciclopemanager.ejb.jsf.util.JsfUtil;
import com.javasoft.ciclopemanager.ejb.jsf.util.JsfUtil.PersistAction;
import com.javasoft.ciclopemanager.ejb.session.PratichespecialiFacade;

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

@Named("pratichespecialiController")
@SessionScoped
public class PratichespecialiController implements Serializable {

    @EJB
    private com.javasoft.ciclopemanager.ejb.session.PratichespecialiFacade ejbFacade;
    private List<Pratichespeciali> items = null;
    private Pratichespeciali selected;
    private Pratichespeciali copySelected;
    private List<Pratichespeciali> filteredItems;

    public PratichespecialiController() {
    }

    public Pratichespeciali getSelected() {
        return selected;
    }

    public void setSelected(Pratichespeciali selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

     public List<Pratichespeciali> getFilteredItems() {
        return filteredItems;
    }

    public void setFilteredItems(List<Pratichespeciali> filteredItems) {
        this.filteredItems = filteredItems;
    }
    
    private PratichespecialiFacade getFacade() {
        return ejbFacade;
    }

    public Pratichespeciali prepareEdit() {
        copySelected = selected.deepClone();
        return copySelected;
    }
    
    public Pratichespeciali prepareCreate() {
        selected = new Pratichespeciali();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PratichespecialiCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PratichespecialiUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PratichespecialiDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Pratichespeciali> getItems() {
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
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                     if(msg.contains("Cannot delete or update a parent row"))
                        msg=ResourceBundle.getBundle("/Bundle").getString("MySQLException_1451");
                }
                if (msg.length() > 0) {
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

    public Pratichespeciali getPratichespeciali(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Pratichespeciali> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Pratichespeciali> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Pratichespeciali.class)
    public static class PratichespecialiControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PratichespecialiController controller = (PratichespecialiController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "pratichespecialiController");
            return controller.getPratichespeciali(getKey(value));
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
            if (object instanceof Pratichespeciali) {
                Pratichespeciali o = (Pratichespeciali) object;
                return getStringKey(o.getIdPraticaSpeciale());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Pratichespeciali.class.getName()});
                return null;
            }
        }

    }

}
