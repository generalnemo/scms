package org.scms.model;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.scms.model.entity.CItemRevision;

public class CItemRevisionTreeNode extends DefaultTreeNode {

	private static final long serialVersionUID = 7492498378926483072L;

	private CItemRevision revision;

	public CItemRevisionTreeNode(){
		super();
	}
	
	public CItemRevisionTreeNode(CItemRevision revision, TreeNode root) {
		super("Версия:"+revision.getId()+" Дата:"+revision.getFormattedCreatedAtDate(), root);
		this.revision = revision;
	}

	public CItemRevision getRevision() {
		return revision;
	}


}
