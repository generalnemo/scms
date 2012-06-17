package org.scms.view.bean.document;

import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import org.scms.enumerate.ControlCategory;
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
		categories = Arrays.asList(ControlCategory.CC4);
		addProperty(PRETTY_CATALOG, "pretty:documentsCatalog");
		addProperty(PRETTY_ADD, "pretty:addDocument");
		addProperty(PRETTY_EDIT, "pretty:editDocument");
		addProperty(PRETTY_VIEW, "pretty:viewDocument");
	}

}
