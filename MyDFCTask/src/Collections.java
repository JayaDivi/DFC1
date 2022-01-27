import java.util.ArrayList;

import com.documentum.com.DfClientX;
import com.documentum.com.IDfClientX;
import com.documentum.fc.client.DfQuery;
import com.documentum.fc.client.IDfClient;
import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfQuery;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfSessionManager;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.IDfLoginInfo;
import com.documentum.fc.common.IDfTime;

public class Collections {

	private static IDfSession session;

	public static void main(String[] args) throws Exception {

//		getDocumentUsingAL();
//		sessionManager();
//		getUsernameMail();
		getObjects();
	}

	static void sessionManager() throws DfException {
		IDfClientX clientX = new DfClientX();
		IDfClient client = clientX.getLocalClient();
		IDfLoginInfo loginInfo = clientX.getLoginInfo();
		loginInfo.setUser("dmadmin");
		loginInfo.setPassword("P@ssword01");
		IDfSessionManager sessionManager = client.newSessionManager();
		sessionManager.setIdentity("NABARDUAT", loginInfo);
		session = sessionManager.newSession("NABARDUAT");
		System.out.println(session);
	}

	static void getObjects() throws DfException {
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
	}

	static void documentCollection() throws DfException {
		IDfQuery query1 = new DfQuery();
		query1.setDQL("select * from dm_document where folder('/Temp')");
		IDfCollection collection1 = query1.execute(session, IDfQuery.DF_READ_QUERY);
		try {
			while (collection1.next()) {
				String objname = collection1.getString("object_name");
				System.out.println("Object Name : " + objname);
				collection1.close();
			}
		} catch (Exception e2) {
			System.out.println("Object Name Doesn't exist.");
		}
	}

	static void getDocumentUsingAL() throws DfException {
		IDfQuery query2 = new DfQuery();
		query2.setDQL("select object_name from dm_document where folder('/DFC1/DFC Folder test')");
		IDfCollection collection2 = query2.execute(session, IDfQuery.DF_READ_QUERY);
		ArrayList<Object> objectNames = new ArrayList<Object>();
		try {
			while (collection2.next()) {
				objectNames.add(collection2.getString("object_name"));
				System.out.println("Object Names added");
				collection2.close();
			}
		} catch (Exception e1) {
			System.out.println("adding of object names failed");
		}
	}

	static void getUsernameMail() throws DfException {
		IDfQuery query3 = new DfQuery();
		query3.setDQL("select user_name,user_address from dm_user where user_login_name='nimitha'");
		IDfCollection collection3 = query3.execute(session, IDfQuery.DF_READ_QUERY);
		ArrayList<Object> userDetails = new ArrayList<Object>();
		try {
			while (collection3.next()) {
				userDetails.add(collection3.getString("user_name"));
				userDetails.add(collection3.getString("user_address"));
				System.out.println("User Details : " + userDetails);
			}
		} catch (Exception e) {
			System.out.println("Failed " + e.getMessage());
			collection3.close();
		}
	}

	public static void sort(ArrayList<Integer> numbers) {
		// TODO Auto-generated method stub
		int Numbers;

	}
}
