package org.scms.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.jboss.solder.beanManager.BeanManagerLocator;
import org.scms.view.bean.UserBean;

@MappedSuperclass
public abstract class AbstractTemporalModel implements Serializable {

	private static final long serialVersionUID = 2434458957714267908L;
	
	public abstract Object getPrimaryKey();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by", nullable = false, updatable = false)
    private User createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "updated_by", nullable = false)
    private User updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @PrePersist
    protected void onCreate () {
        updatedAt = createdAt = new Date();
        setCreatedBy(getCurrentUser());
        setUpdatedBy(getCurrentUser());
    }

    @PreUpdate
    protected void onUpdate () {
        updatedAt = new Date();
        setUpdatedBy(getCurrentUser());
    }

    public User getCreatedBy () {
        return createdBy;
    }

    public void setCreatedBy (User createdBy) {
        this.createdBy = createdBy;
    }

    public User getUpdatedBy () {
        return updatedBy;
    }

    public void setUpdatedBy (User updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedAt () {
        return createdAt;
    }

    public Date getUpdatedAt () {
        return updatedAt;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public User getCurrentUser() {
        User user = null;
        try {
            BeanManager beanManager = new BeanManagerLocator().getBeanManager();
            AnnotatedType<UserBean> type = beanManager.createAnnotatedType(UserBean.class);
            InjectionTarget<UserBean> it = beanManager.createInjectionTarget(type);
            Bean<?> bean = beanManager.getBeans("user").iterator().next();
            CreationalContext ctx = beanManager.createCreationalContext(bean);
            UserBean userbean = it.produce(ctx);
            it.inject(userbean, ctx);
            user =userbean.getCurrentUser();
            it.dispose(userbean);
            ctx.release();
        } catch (Exception e) {
        }
        return user;
    }

}
