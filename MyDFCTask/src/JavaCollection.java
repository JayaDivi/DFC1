import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.Collections;

public class JavaCollection {

	public static void main(String[] args) {

		// searchingelements();
		// Iterateelements();
		// listConversion();
		// UpdatingElements();
		// conversionArraytoSet();
		// convertLLtoAl();
		// reverseOrder();
		// listComparing();
		// convertKeysnValues();
		//sortingKeys();
		joiningTwoLists();

	}

	static void searchingelements() {
		HashMap<Integer, String> hashmap = new HashMap<Integer, String>();
		hashmap.put(100, "Eclipse");
		hashmap.put(200, "IntelliJ");
		hashmap.put(300, "Anaconda");
		hashmap.put(400, "Visual Studio");

		System.out.println("Original hashMap : " + hashmap);
		System.out.println("The Key 100 is present : " + hashmap.containsKey(100));
		System.out.println("The key 500 is present : " + hashmap.containsKey(500));
	}

	static void listConversion() {
		LinkedList<String> Llist = new LinkedList<String>();
		Llist.add("India");
		Llist.add("US");
		Llist.add("Germany");
		Llist.add("France");
		Llist.add("Australia");
		System.out.println("Linked List : " + Llist);
		ArrayList<String> arraylist = new ArrayList<String>();
		for (String string : Llist) {
			arraylist.addAll(Llist);
		}
		ArrayList<String> Ll = new ArrayList(Llist);
		System.out.println("ArrayList : " + arraylist);
	}

	static void Iterateelements() {
		ArrayList<Integer> alist = new ArrayList<Integer>();
		alist.add(10);
		alist.add(11);
		alist.add(12);
		alist.add(13);
		alist.add(14);
		System.out.println("Arraylist Elements : " + alist);

		for (int i = 0; i < alist.size(); i++) {
			System.out.print(alist.get(i) + ", ");
		}
	}

	static void UpdatingElements() {
		ArrayList<String> list3 = new ArrayList<String>();
		list3.add("German Shepherd");
		list3.add("Pug");
		list3.add("Golden Retriever");
		list3.add("Shitzu");
		System.out.println("Original Arryalist :" + list3);
		list3.set(1, "Chow Chow");
		System.out.println("Updtaed ArrayList : " + list3);
	}

	static void conversionArraytoSet() {
		String[] array = { "j", "a", "y", "a", "s", "r", "i" };

		Set<String> set = new HashSet<>(Arrays.asList(array));
		System.out.println("Array Converted to Set  : " + set);
	}

	static void convertLLtoAl() {
		LinkedList<String> Ll = new LinkedList<String>();
		Ll.add("Java");
		Ll.add("C++");
		Ll.add("C");
		ArrayList<String> al = new ArrayList<>(Ll);
		System.out.println("conversion of LL to AL : " + al);
	}

	static void reverseOrder() {
		LinkedList<String> linklist = new LinkedList<String>();
		linklist.add("Python");
		linklist.add("Java");
		linklist.add("JS");
		linklist.add("C");
		System.out.println("Original Linked List : " + linklist);
		for (int i = linklist.size() - 1; i >= 0; i--) {
			System.out.println("Linked List After reversing : " + linklist.get(i));
		}
	}

	static void listComparing() {
		LinkedList<Integer> list1 = new LinkedList<Integer>();

		for (int i = 0; i <= 10; i++) {
			list1.add(i);
		}
		LinkedList<Integer> list2 = new LinkedList<Integer>();
		for (int i = 0; i <= 9; i++) {
			list2.add(i);
		}
		boolean torf = list1.equals(list2);
		if (torf == true) {
			System.out.println("Both list are same : " + torf);
		} else {
			System.out.println("list are not same..." + torf);

		}
	}

	static void convertKeysnValues() {
		String Keys[] = { "11", "12", "13", "14" };
		String Values[] = { "Tata", "Hyundai", "Suzuki", "Honda" };
		Map<String, String> hashmap = new HashMap<String, String>();
		for (int i = 0; i < Keys.length; i++) {
			hashmap.put(Keys[i], Values[i]);

		}
		System.out.println("HashMap : " + hashmap);
	}

	static void sortingKeys() {
		ArrayList<Integer> Numbers = new ArrayList<Integer>();
		Numbers.add(10);
		Numbers.add(2);
		Numbers.add(7);
		Numbers.add(5);
		sortingHashmap(Numbers);
		Collections.sort(Numbers);
		System.out.println("after sorting the numbers...");
		sortingHashmap(Numbers);
		}

	private static void sortingHashmap(ArrayList<Integer> Numbers) {
		for (Integer value : Numbers) {
			System.out.println(value);
		}
	}
	
	
	
	static void joiningTwoLists() {
		List<Integer> joinList=new ArrayList<Integer>();
		{
			joinList.add(10);
			joinList.add(20);
			joinList.add(30);
			List<Integer> joinList1=new ArrayList<Integer>();{
			joinList1.add(40);
			joinList1.add(50);
			joinList1.add(60);
			System.out.println("1st List : "+ joinList);
			System.out.println("2nd List : "+joinList1);
			List<Integer> concatList=new ArrayList<Integer>();
			{
				for(int i=0;i<joinList.size();i++)
				{
					concatList.add(joinList.get(i));
				}
				for(int i=0;i<joinList1.size();i++)
				{
					concatList.add(joinList1.get(i));
				}
                System.out.println("After Joining the Lists : "+concatList);
			}
			}
		}
	}
}

			
	

	

