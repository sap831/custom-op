package com.red.moj;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.automation.core.Constants;
import org.nuxeo.ecm.automation.core.annotations.Context;
import org.nuxeo.ecm.automation.core.annotations.Operation;
import org.nuxeo.ecm.automation.core.annotations.OperationMethod;
import org.nuxeo.ecm.automation.core.annotations.Param;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.PathRef;

/**
 *
 */
@Operation(id = CreateBox.ID, category = Constants.CAT_DOCUMENT, label = "Create Box", description = "Create Collection of Type Box")
public class CreateBox {

	public static final String ID = "Document.CreateBox";
	private static final Log LOG = LogFactory.getLog(CreateBox.class);

	@Context
	protected CoreSession session;

	@Param(name = "path", required = false)
	protected String path;

	@Param(name = "boxName", required = false)
	private String boxName;

	@Param(name = "boxTypee", required = false)
	private String boxTypee;

	@OperationMethod
	public DocumentModel run() {
		if (StringUtils.isBlank(path)) {
			return session.getRootDocument();
		} else {
			return session.getDocument(new PathRef(path));
		}
	}

	public DocumentModel createCollection(final CoreSession session, String title, String boxtypee, String path) {
		LOG.error(" Inside createCollection()---" + session + "title====" + title + "boxtypee>>>>" + boxtypee
				+ "path+++++" + path);

		DocumentModel newCollection = null;
		if (StringUtils.isEmpty(path)) {
			// A default collection is created with the given name
			newCollection = createCollection(title, boxtypee, null, session);
		} else {
			// If the path does not exist, an exception is thrown
			if (!session.exists(new PathRef(path))) {
				LOG.error(" Path \"%s\" specified in parameter not found()" + path);
			}
			// Create a new collection in the given path
			DocumentModel collectionModel = session.createDocumentModel(path, title, "box");
			collectionModel.setPropertyValue("dc:title", title);
			collectionModel.setPropertyValue("box:boxTypee", boxtypee);
			newCollection = session.createDocument(collectionModel);
		}
		return newCollection;
	}

	protected DocumentModel createCollection(final String newTitle, final String newBoxTypee,
			final DocumentModel context, final CoreSession session) {
		LOG.error(" Inside createCollection()---" + newTitle);
		DocumentModel newCollection = session.createDocumentModel("".toString(), newTitle, "box");
		newCollection.setPropertyValue("dc:title", newTitle);
		newCollection.setPropertyValue("box:boxTypee", newBoxTypee);
		return session.createDocument(newCollection);
	}
}
