package com.javasoft.ciclopemanager.ejb.jsf;

import com.javasoft.ciclopemanager.ejb.entities.Articolo;
import com.javasoft.ciclopemanager.ejb.jsf.util.JsfUtil;
import com.javasoft.ciclopemanager.ejb.jsf.util.JsfUtil.PersistAction;
import com.javasoft.ciclopemanager.ejb.session.ArticoloFacade;
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
import org.primefaces.context.RequestContext;
import org.primefaces.event.data.PageEvent;

@Named("articoloController")
@SessionScoped
public class ArticoloController implements Serializable {

    @EJB
    private com.javasoft.ciclopemanager.ejb.session.ArticoloFacade ejbFacade;
    private List<Articolo> items = null;
    private Articolo selected;
    private Articolo copySelected;

    public ArticoloController() {
    }

    public Articolo getSelected() {
        return selected;
    }

    public void setSelected(Articolo selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ArticoloFacade getFacade() {
        return ejbFacade;
    }

    public Articolo prepareCreate() {
        selected = new Articolo();
        initializeEmbeddableKey();
        return selected;
    }

    public Articolo prepareEdit() {
        copySelected = selected.deepClone();
        return copySelected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ArticoloCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ArticoloUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ArticoloDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Articolo> getItems() {
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
                    } else {
                        getFacade().edit(selected, copySelected);
                    }
                } else {
                    getFacade().remove(selected);
                }
                System.err.println("FINE OK");
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException | FacadeException ex) {
                System.err.println("FINE NON OK");
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
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

    public Articolo getArticolo(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Articolo> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Articolo> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Articolo.class)
    public static class ArticoloControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ArticoloController controller = (ArticoloController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "articoloController");
            return controller.getArticolo(getKey(value));
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
            if (object instanceof Articolo) {
                Articolo o = (Articolo) object;
                return getStringKey(o.getIdArticolo());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Articolo.class.getName()});
                return null;
            }
        }

    }

}
