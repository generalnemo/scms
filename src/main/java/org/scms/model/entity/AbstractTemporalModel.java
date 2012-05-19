package org.scms.model.entity;

import java.util.Date;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.jboss.solder.beanManager.BeanManagerLocator;
import org.scms.view.bean.UserBean;

@MappedSuperclass
public abstract class AbstractTemporalModel extends AbstractIdentityModel {

	private static final long serialVersionUID = 2434458957714267908L;

	@ManyToOne
	@JoinColumn(name = "created_by", nullable = false, updatable = false)
	private User createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", nullable = false, updatable = false)
	private Date createdAt;

	@PrePersist
	protected void onCreate() {
		createdAt = new Date();
		setCreatedBy(getCurrentUser());
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public User getCurrentUser() {
		User user = null;
		try {
			BeanManager beanManager = new BeanManagerLocator().getBeanManager();
			AnnotatedType<UserBean> type = beanManager
					.createAnnotatedType(UserBean.class);
			InjectionTarget<UserBean> it = beanManager
					.createInjectionTarget(type);
			Bean<?> bean = beanManager.getBeans("user").iterator().next();
			CreationalContext ctx = beanManager.createCreationalContext(bean);
			UserBean userbean = it.produce(ctx);
			it.inject(userbean, ctx);
			user = userbean.getCurrentUser();
			it.dispose(userbean);
			ctx.release();
		} catch (Exception e) {
		}
		return user;
	}

}
