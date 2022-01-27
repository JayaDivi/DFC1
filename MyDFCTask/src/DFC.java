import com.documentum.com.IDfClientX;

import java.util.Date;

import org.apache.derby.tools.sysinfo;

import com.documentum.com.DfClientX;
import com.documentum.fc.client.DfQuery;
import com.documentum.fc.client.IDfACL;
import com.documentum.fc.client.IDfClient;
import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfDocument;
import com.documentum.fc.client.IDfFolder;
import com.documentum.fc.client.IDfPersistentObject;
import com.documentum.fc.client.IDfQuery;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfSessionManager;
import com.documentum.fc.client.IDfSysObject;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.DfId;
import com.documentum.fc.common.IDfId;
import com.documentum.fc.common.IDfLoginInfo;
import com.documentum.fc.common.IDfTime;
import com.documentum.operations.IDfCopyOperation;
import com.documentum.operations.IDfExportNode;
import com.documentum.operations.IDfExportOperation;
import com.documentum.operations.IDfImportNode;
import com.documentum.operations.IDfImportOperation;
import com.documentum.operations.IDfMoveOperation;

public class DFC {

	static IDfClientX clientX = new DfClientX();

	static void multipleCabinets(IDfSession session, String cab1[][]) throws DfException {

		for (int index = 0; index < cab1.length; index++) {
			String ca[] = cab1[index];
			creatingCabinet(session, ca[0], ca[1]);
		}
	}

	static void creatingCabinet(IDfSession session, String cabinetName, String title) throws DfException {
		IDfSysObject cabinetcreation = (IDfSysObject) session.newObject("dm_cabinet");
		cabinetcreation.setObjectName(cabinetName);
		cabinetcreation.setTitle(title);
		cabinetcreation.save();
		System.out.println("Document is created as " + cabinetName + " " + title);
	}

	static void createFolder(IDfSession session, String ObjectName) throws DfException {
		IDfFolder folderCreation = (IDfFolder) session.newObject("dm_folder");
		folderCreation.setObjectName(ObjectName);
		folderCreation.link("/DFC1");
		folderCreation.save();
		System.out.println("Folder is created with " + ObjectName);
	}

	static void linkingFolder(IDfSession session) throws DfException {
		IDfFolder linkFolder = (IDfFolder) session.getObject(new DfId("0b02cba0811a82e8"));
		linkFolder.link("/DFC1");
		linkFolder.save();
		;// 2
		IDfFolder cab = (IDfFolder) session.getObject(linkFolder.getFolderId(linkFolder.getFolderIdCount() - 1));

		System.out.println("folder is linked to " + cab.getFolderPath(0));
	}

	static void copyDocuemnt(IDfSession session, IDfClientX clientX) throws DfException {
		String destinationFolderID = "0b02cba080d31110";
		IDfDocument copyDocument = (IDfDocument) session.getObject(new DfId(destinationFolderID));
		IDfCopyOperation copyOperation = clientX.getCopyOperation();
		copyOperation.setDestinationFolderId(new DfId(destinationFolderID));
		copyOperation.execute();
		System.out.println("document copied to location");
	}

	static void copyingmultiDocuments(IDfSession session) throws DfException {
		String folderID = "0b02cba081037df";
		String destinationFolderID = "0b02cba080eeb13d";
		IDfFolder folder = (IDfFolder) session.getObject(new DfId(folderID));
		String folderName = folder.getObjectName();
		IDfCopyOperation copyingFolder = clientX.getCopyOperation();
		copyingFolder.add(folder);
		copyingFolder.setDestinationFolderId(new DfId(destinationFolderID));
		if (copyingFolder.execute()) {
			System.out.println("Documents copied from " + folderName);
		} else {
			System.out.println("Operation Failed");
		}
	}

	static void movingDocument(IDfSession session, IDfClientX clientX) throws DfException {
		try {
			String orginalFolderID = "0b02cba0810708de";
			String documentID = "0902cba0810708eb";
			IDfMoveOperation movingDocument = clientX.getMoveOperation();
			movingDocument.add((IDfDocument) session.getObject(new DfId(documentID)));
			String destinationID = "0b02cba081063ad7";
			movingDocument.setDestinationFolderId(new DfId(destinationID));
			movingDocument.setSourceFolderId(new DfId(orginalFolderID));
			movingDocument.execute();
			System.out.println("document moved");
		} catch (Exception e) {
			System.out.println("document move operation failed");
		}
	}

	static void importingDocument(IDfSession session) throws Exception {
		IDfDocument doc = (IDfDocument) session.newObject("dm_document");
		doc.setObjectName("DOS complaints documentation.pdf");
		doc.setContentType("pdf");
		doc.setFile("C:\\Users\\ecm.dev3.ext\\Downloads\\DOS complaints documentation.pdf");
		doc.link("/Temp/test");
		doc.save();
		System.out.println("document imported with the name as " + doc.getObjectName());
	}

	static void importOperation(IDfSession session) throws Exception {
		String CabinetID = "0c02cba0811a8183";
		String NewCabinetID = "0c02cba0811a819d";
		IDfImportOperation importDocument = clientX.getImportOperation();
		importDocument.setSession(session);
		importDocument.setDestinationFolderId(new DfId(CabinetID));
		IDfImportNode newnode = (IDfImportNode) importDocument
				.add("C:\\Users\\ecm.dev3.ext\\Downloads\\Get_Started_With_Smallpdf.pdf");
		newnode.setDestinationFolderId(new DfId(NewCabinetID));
		importDocument.add("C:\\Users\\ecm.dev3.ext\\Downloads\\213211967525051.pdf");
		importDocument.execute();
	}

	static void updatingMultipleDocuments(IDfSession session) throws DfException {
		String[][] multipleObjects = { { "test", "0b02cba081080b58" }, { "testdfc", "0b02cba081080b29" } };
		for (int index = 0; index < multipleObjects.length; index++) {
			IDfDocument documentObject = (IDfDocument) session.getObject(new DfId(multipleObjects[index][1]));
			documentObject.setString("object_name", multipleObjects[index][0]);
			documentObject.save();
			System.out.println("document names were updated");
		}
	}

	static void UpdateDocumentName(IDfSession session) throws DfException {
		IDfQuery query = new DfQuery();
		query.setDQL("update dm_document object set object_name ='New Test Doc' where r_object_id='0902cba08107831b'");
		IDfCollection col = query.execute(session, IDfQuery.DF_READ_QUERY);
		if (col.next()) {
			System.out.println("object Name:" + col.getValueAt(0));
		}
		col.close();
	}

	static void exportDocument(IDfSession session, IDfClientX clientX) throws DfException {
		String sourceFolID = "0902cba0811a82f9";
		try {
			IDfSysObject newObject = (IDfSysObject) session.getObject(new DfId(sourceFolID));
			IDfExportOperation exportFol = clientX.getExportOperation();
			exportFol.setSession(session);
			exportFol.setDestinationDirectory("C:\\temp");
			IDfExportNode newnode = (IDfExportNode) exportFol.add(newObject);
			newnode.setFormat("excel12book");
			exportFol.execute();
			System.out.println("Folder exported");
		} catch (Exception e) {
			System.out.println("export Operation Failed");
		}
	}

	public static void main(String[] args) throws Exception {

		IDfClientX clientX = new DfClientX();
		IDfClient client = clientX.getLocalClient();
		IDfLoginInfo loginInfo = clientX.getLoginInfo();
		loginInfo.setUser("dmadmin");
		loginInfo.setPassword("P@ssword01");
		IDfSessionManager sessionManager = client.newSessionManager();
		sessionManager.setIdentity("NABARDUAT", loginInfo);
		IDfSession session = sessionManager.newSession("NABARDUAT");
		System.out.println(session);

		// importOperation(session);
		// documentImporting(session);
		// UpdateDocumentName(session);
		// creatingCabinet(session,"DFC Cabinet","DFC");
		// creatingCabinet(session,"DFC2","DFC2");
		// String cab1[][]= {{"DFC3","dfc3"},{"DFC4","dfc4"}};
		// multipleCabinets(session,cab1);
		// createFolder(session,"DFC Folder test");
		// linkingFolder(session);
		// copyingmultiDocuments(session);
		// exportDocument(session, clientX);

		IDfQuery query = new DfQuery();
		query.setDQL("select * from dm_document where folder('/DFC1/DFC Folder test')");
		IDfCollection collection = query.execute(session, IDfQuery.DF_READ_QUERY);
		try {
			collection.next();
			Integer ownerPermit = collection.getInt("owner_permit");
			System.out.println("Owner Permit Num is : " + ownerPermit);
			collection.next();
			String objectName = collection.getString("object_name");
			System.out.println("Object Name is : " + objectName);
			collection.next();
			IDfTime creationDate = collection.getTime("r_creation_date");
			System.out.println("Creation Date is : " + creationDate);
			collection.close();
		} catch (Exception e) {
			System.out.println("table data not exist" + e);
		}

		IDfQuery query1 = new DfQuery();
		query1.setDQL("select * from dm_document where folder('/Temp')");
		IDfCollection collection1 = query1.execute(session, IDfQuery.DF_READ_QUERY);
		try {
			while (collection1.next()) {
				String objname = collection1.getString("object_name");
				System.out.println("Object Name : " + objname);
			}
			collection1.close();
		} catch (Exception e) {
			System.out.println("Object Name Doesn't exist.");
		}

	}
}
