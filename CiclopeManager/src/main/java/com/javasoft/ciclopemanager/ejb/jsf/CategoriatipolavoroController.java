package com.javasoft.ciclopemanager.ejb.jsf;

import com.javasoft.ciclopemanager.ejb.entities.Categoriatipolavoro;
import com.javasoft.ciclopemanager.ejb.jsf.util.JsfUtil;
import com.javasoft.ciclopemanager.ejb.jsf.util.JsfUtil.PersistAction;
import com.javasoft.ciclopemanager.ejb.session.CategoriatipolavoroFacade;
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

@Named("categoriatipolavoroController")
@SessionScoped
public class CategoriatipolavoroController implements Serializable {

    @EJB
    private com.javasoft.ciclopemanager.ejb.session.CategoriatipolavoroFacade ejbFacade;
    private List<Categoriatipolavoro> items = null;
    private Categoriatipolavoro selected;
    private Categoriatipolavoro copySelected;
    private List<Categoriatipolavoro> filteredItems;

    public CategoriatipolavoroController() {
    }

    public Categoriatipolavoro getSelected() {
        return selected;
    }

    public void setSelected(Categoriatipolavoro selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }
 public List<Categoriatipolavoro> getFilteredItems() {
        return filteredItems;
    }

    public void setFilteredItems(List<Categoriatipolavoro> filteredItems) {
        this.filteredItems = filteredItems;
    }
    private CategoriatipolavoroFacade getFacade() {
        return ejbFacade;
    }

    public Categoriatipolavoro prepareEdit() {
        copySelected = selected.deepClone();
        return copySelected;
    }

    public Categoriatipolavoro prepareCreate() {
        selected = new Categoriatipolavoro();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CategoriatipolavoroCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CategoriatipolavoroUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CategoriatipolavoroDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Categoriatipolavoro> getItems() {
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

    public Categoriatipolavoro getCategoriatipolavoro(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Categoriatipolavoro> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Categoriatipolavoro> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Categoriatipolavoro.class)
    public static class CategoriatipolavoroControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CategoriatipolavoroController controller = (CategoriatipolavoroController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "categoriatipolavoroController");
            return controller.getCategoriatipolavoro(getKey(value));
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
            if (object instanceof Categoriatipolavoro) {
                Categoriatipolavoro o = (Categoriatipolavoro) object;
                return getStringKey(o.getIdCategoriaTipoLavoro());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Categoriatipolavoro.class.getName()});
                return null;
            }
        }

    }

}
