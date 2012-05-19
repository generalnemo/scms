package org.scms.view.bean.document;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import org.scms.enumerate.citem.CItemType;
import org.scms.view.bean.AbstractCItemBean;

@Named("userDocument")
@ViewScoped
public class UserDocumentBean extends AbstractCItemBean {

	private static final long serialVersionUID = -5533974832781202095L;

	@Override
	@PostConstruct
	public void init() {
		super.init();
		type = CItemType.DOCUMENT;
		addProperty(PRETTY_CATALOG, "pretty:documentsCatalog");
		addProperty(PRETTY_ADD, "pretty:addDocument");
		addProperty(PRETTY_EDIT, "pretty:editDocument");
		addProperty(PRETTY_VIEW, "pretty:viewDocument");
	}

}
