package com.javasoft.ciclopemanager.ejb.jsf;

import com.javasoft.ciclopemanager.ejb.entities.Veicolo;
import com.javasoft.ciclopemanager.ejb.jsf.util.JsfUtil;
import com.javasoft.ciclopemanager.ejb.jsf.util.JsfUtil.PersistAction;
import com.javasoft.ciclopemanager.ejb.session.VeicoloFacade;
import com.javasoft.ciclopemanager.ejb.session.exception.FacadeException;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
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

@Named("veicoloController")
@SessionScoped
public class VeicoloController implements Serializable {

    @EJB
    private com.javasoft.ciclopemanager.ejb.session.VeicoloFacade ejbFacade;
    private List<Veicolo> items = null;
    private Veicolo selected;
    private Veicolo copySelected;
    private List<Veicolo> filteredItems;

    public VeicoloController() {
    }

    public Veicolo getSelected() {
        return selected;
    }

    public void setSelected(Veicolo selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

     public List<Veicolo> getFilteredItems() {
        return filteredItems;
    }

    public void setFilteredItems(List<Veicolo> filteredItems) {
        this.filteredItems = filteredItems;
    }
    
    private VeicoloFacade getFacade() {
        return ejbFacade;
    }
    public Veicolo prepareEdit() {
        copySelected = selected.deepClone();
        return copySelected;
    }
    public Veicolo prepareCreate() {
        selected = new Veicolo();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("VeicoloCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("VeicoloUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("VeicoloDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Veicolo> getItems() {
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

     public boolean filterByCategoria(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if(filterText == null||filterText.equals("")) {
            return true;
        }
         
        if(value == null) {
            return false;
        }
        return value.toString().contains(filterText);        
    }
     
    public Veicolo getVeicolo(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Veicolo> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Veicolo> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Veicolo.class)
    public static class VeicoloControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            VeicoloController controller = (VeicoloController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "veicoloController");
            return controller.getVeicolo(getKey(value));
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
            if (object instanceof Veicolo) {
                Veicolo o = (Veicolo) object;
                return getStringKey(o.getIdVeicolo());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Veicolo.class.getName()});
                return null;
            }
        }

    }

}
