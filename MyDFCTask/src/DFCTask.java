
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Properties;

import com.aspose.pdf.Group;
import com.documentum.com.DfClientX;
import com.documentum.com.IDfClientX;
import com.documentum.fc.client.DfGroup;
import com.documentum.fc.client.DfQuery;
import com.documentum.fc.client.IDfACL;
import com.documentum.fc.client.IDfClient;
import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfFolder;
import com.documentum.fc.client.IDfGroup;
import com.documentum.fc.client.IDfPermit;
import com.documentum.fc.client.IDfPersistentObject;
import com.documentum.fc.client.IDfQuery;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfSessionManager;
import com.documentum.fc.client.IDfSysObject;
import com.documentum.fc.client.IDfUser;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.DfId;
import com.documentum.fc.common.IDfId;
import com.documentum.fc.common.IDfList;
import com.documentum.fc.common.IDfLoginInfo;
import com.documentum.operations.IDfDeleteOperation;

public class DFCTask {
	static IDfGroup group = null;

	public static void main(String[] args) throws Exception {

		IDfClientX clientX = null;
		IDfSession session = getSession();
//		deleteObjects(session);
	}

	static void showACLPermission(IDfSession session, String objectId) throws Exception {
		IDfACL acl = (IDfACL) session.getObject(new DfId(objectId));
		IDfList list = acl.getPermissions();
		for (int i = 0; i < list.getCount(); i++) {
			IDfPermit permit = (IDfPermit) list.get(i);
			System.out.println("User :" + permit.getAccessorName() + " permission:" + permit.getPermitValueString());
		}
	}

	static IDfSession getSession() throws DfException {
		IDfClientX clientX = new DfClientX();
		IDfClient client = clientX.getLocalClient();
		IDfLoginInfo loginInfo = clientX.getLoginInfo();
		loginInfo.setUser("dmadmin");
		loginInfo.setPassword("P@ssword01");

		IDfSessionManager sessionManager = client.newSessionManager();
		sessionManager.setIdentity("NABARDUAT", loginInfo);

		IDfSession session = sessionManager.newSession("NABARDUAT");
		System.out.println(session);
		return session;
	}

	static IDfUser newUser(IDfSession session, String UserName, String UserLoginName) throws DfException {

		IDfUser usercreation = (IDfUser) session.newObject("dm_user");
		try {
			usercreation.setUserName(UserName);
			usercreation.setUserLoginName(UserLoginName);
			usercreation.setString("user_address", UserName + "@mail.com");
			usercreation.setString("user_source", "inline Password");
			usercreation.setString("user_password", "Tetsing@123");
			usercreation.setString("user_os_name", "dfc_test_user");
			usercreation.setString("description", "DFC User");
			usercreation.setDefaultFolder("/Temp", true);
			usercreation.save();
			System.out.println("User created with the name of " + usercreation.getUserName());
		} catch (Exception e) {
			System.out.println("user creation failed..." + e.getMessage());
		}
		return usercreation;
	}

	private static void releaseSession(IDfSession session) {

	}

	static IDfGroup newGroup(IDfSession session) throws DfException {
		IDfGroup group = null;
		try {
			group = (IDfGroup) session.newObject("dm_group");
			group.setGroupName("dfc_test_group");
			group.save();
			System.out.println("group name " + group.getGroupName() + " is created");
		} catch (Exception e) {
			System.out.println("group creation fails");
		} finally {
			System.out.println("end");
		}
		return group;

	}

	static IDfGroup addingUserintoGRP(IDfSession session, IDfUser user) throws DfException {
		IDfGroup userintogrp = null;
		try {
			userintogrp = (IDfGroup) session.getObject(new DfId("1202cba08007152b"));
			userintogrp.addUser(user.getUserName());
			userintogrp.save();
			System.out.println("user added ");
		} catch (Exception e) {
			System.out.println("User already existed into group" + e.getMessage());
		} finally {
			System.out.println("The End...");
		}
		return userintogrp;
	}

	static IDfACL newACL(IDfSession session) throws DfException {
		IDfACL createACL = null;
		try {
			createACL = (IDfACL) session.newObject("dm_acl");
			createACL.setObjectName("dfc_test_acl");
			createACL.grant("dfc_test_group", IDfACL.DF_PERMIT_WRITE, null);
			createACL.grant("dmadmin", IDfACL.DF_PERMIT_DELETE, null);
			createACL.save();
			System.out.println("ACL Created as " + createACL.getObjectName());
			showACLPermission(session, createACL.getObjectId().getId());
		} catch (Exception e) {
			System.out.println("ACL Failed " + e);
		} finally {
			System.out.println("The End");
		}
		return createACL;
	}

	static IDfFolder newFolder(IDfSession session, IDfACL acl) throws DfException {
		IDfFolder folder = null;
		try {
			folder = (IDfFolder) session.newObject("dm_folder");
			folder.setObjectName("dfc_test");
			folder.link("/DFC2");
			folder.setACL(acl);
			folder.save();
			System.out.println("Folder created with " + folder.getObjectName());
		} catch (Exception e) {
			System.out.println("failed to create");
		}
		return folder;
	}

	static void deleteObjects(IDfSession session) throws DfException {
		Properties prop = null;
		try {
			prop = new Properties();
			prop.load(new FileReader("id_list.properties"));
			IDfFolder fold = (IDfFolder) session.getFolderByPath("/DFC2/dfc_test");
			IDfUser user = (IDfUser) session.getUser("dfc_test_user");
			IDfGroup group = (IDfGroup) session.getGroup("dfc_test_group");
			IDfACL acl = (IDfACL) session.getACL("dmadmin", "dfc_test_acl");
			fold.destroy();
			user.destroy();
			group.destroy();
			acl.destroyACL(true);
			System.out.println("folder is deleted");
			System.out.println("User is deleted");
			System.out.println("Group is deleted");
			System.out.println("ACL is deleted");
		} catch (Exception e) {
			System.out.println("failed to delete objects " + e);
		}
	}

	static void createObjects() throws Exception {
		IDfSession session = getSession();
		Properties prop = new Properties();
		IDfUser userObj = newUser(session, "dfc_test_user", "dfc.user");
		if (userObj != null) {
			prop.put("user_object_id", userObj.getObjectId().getId());
			addingUserintoGRP(session, userObj);
		} else {
			System.out.println("failed to add user ");
		}
		IDfGroup groupOb = newGroup(session);
		prop.put("group_object_id", groupOb.getObjectId().getId());
		IDfACL aclObj = newACL(session);
		if (aclObj != null) {
			prop.put("acl_id", aclObj.getObjectId().getId());
			newFolder(session, aclObj);
		} else {
			System.out.println("Failed to create acl Object");
		}
		showACLPermission(session, "4502cba08015905a");
		prop.save(new FileOutputStream("id_list.properties"), "details");
	}
}
