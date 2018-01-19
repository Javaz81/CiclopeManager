package com.javasoft.ciclopemanager.ejb.jsf;

import com.javasoft.ciclopemanager.ejb.entities.Tipolavoro;
import com.javasoft.ciclopemanager.ejb.jsf.util.JsfUtil;
import com.javasoft.ciclopemanager.ejb.jsf.util.JsfUtil.PersistAction;
import com.javasoft.ciclopemanager.ejb.session.TipolavoroFacade;
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

@Named("tipolavoroController")
@SessionScoped
public class TipolavoroController implements Serializable {

    @EJB
    private com.javasoft.ciclopemanager.ejb.session.TipolavoroFacade ejbFacade;
    private List<Tipolavoro> items = null;
    private Tipolavoro selected;
    private Tipolavoro copySelected;
    private List<Tipolavoro> filteredItems;

    public TipolavoroController() {
    }

    public Tipolavoro getSelected() {
        return selected;
    }

    public void setSelected(Tipolavoro selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

     public List<Tipolavoro> getFilteredItems() {
        return filteredItems;
    }

    public void setFilteredItems(List<Tipolavoro> filteredItems) {
        this.filteredItems = filteredItems;
    }
    
    private TipolavoroFacade getFacade() {
        return ejbFacade;
    }

    public Tipolavoro prepareEdit() {
        copySelected = selected.deepClone();
        return copySelected;
    }

    public Tipolavoro prepareCreate() {
        selected = new Tipolavoro();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TipolavoroCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TipolavoroUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TipolavoroDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Tipolavoro> getItems() {
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
    
    public Tipolavoro getTipolavoro(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Tipolavoro> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Tipolavoro> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Tipolavoro.class)
    public static class TipolavoroControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TipolavoroController controller = (TipolavoroController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tipolavoroController");
            return controller.getTipolavoro(getKey(value));
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
            if (object instanceof Tipolavoro) {
                Tipolavoro o = (Tipolavoro) object;
                return getStringKey(o.getIdTipoLavoro());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Tipolavoro.class.getName()});
                return null;
            }
        }

    }

}
