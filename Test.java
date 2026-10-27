import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Test {
	public static void main(String[] args) {
		List<Movie>[] lists = (List<Movie>[]) new List[3];
		lists[0] = new ArrayList<>();
		lists[1] = new LinkedList<>();
		lists[2] = new Stack<>();
		
		readData(lists, "movies.txt");
		for(int i=0; i<lists.length; i++) {
			System.out.println("List " + (i+1) + ": ");
			printList(lists[i].iterator());
		}
        List<Movie> all = new ArrayList<>();
        for(List<Movie> l: lists)
            all.addAll(l);

        // Testing reverse
        List<Movie> reversed = reverse(lists[0]);
        System.out.println("\nlist1 reversed:");
        printList(reversed.iterator());

        // Testing interleave
        interleave(lists[0], lists[1]);
        System.out.println("\nlist1 interleaved with list2");
        printList(lists[0].iterator());

        // Testing the recursive flatten
        List<Object> list = new Stack<Object>();
        list.add(lists[0].get(0));
        while(reversed.size() > 5)
            reversed.remove(reversed.size()-1);
        list.add(reversed);
        list.add(lists[1].get(4));
        while(lists[2].size() > 3)
            lists[2].remove(lists[2].size()-1);
        list.add(lists[2]);
        System.out.println("\nNested list:");
        printList(list.iterator());

        // List<Movie> flatten = flatten(list);
        // System.out.println("\nFlatten list:");
        // printList(flatten.iterator());
        
        // Testing sorting by id, title, or rating
        System.out.println("\nall sorted by id");
		sort(all, 0);
        printList(all.iterator());
        System.out.println("\nall sorted by title");
        sort(all, 1);
        printList(all.iterator());
        System.out.println("\nall sorted by rating");
        sort(all, 2);
        printList(all.iterator());
	}
    /**
	 * Method to read data from text file
	 * @param list of lists where the data read from the file is distributed
	 * @param filename the name of the file to read from
	 */
	public static void readData(List<Movie>[] list, String filename) {
		File file = new File(filename);
		int i = 0;
		try {
			Scanner read = new Scanner(file);
			while(read.hasNextLine()) {
				String line = read.nextLine();
				String[] att = line.split(",");
				Movie m = new Movie(Integer.parseInt(att[0]), att[1], Double.parseDouble(att[2]));
				list[i%3].add(m);
				i++;
			}
			read.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("File not found.");
			System.exit(0);
		}
	}
	
    /**
	 * Method to interleave two lists
	 * @param list1 the first list and the list to be updated
	 * @param list2 the second list
	 */
    // Big O : O(n^2)
    public static <E> void interleave(List<E> list1, List<E> list2){
        List<E> finalList = new ArrayList<>();
        Iterator<E> iter1 = list1.iterator();
        Iterator<E> iter2 = list2.iterator();
        while (iter1.hasNext()) {
            E item1 = iter1.next();
            finalList.add(item1);
            if (iter2.hasNext()) {
                E item2 = iter2.next();
                finalList.add(item2);
            }
        }
        list1 = finalList;
    }

    /**
     * Method to reverse a list
     * @param list the list to be reversed
     * @return the reversed list in a new list (use the concrete class ArrayList to create a new list)
     */
    // Big O notation: O(n^2)
    public static <E> List<E> reverse(List<E> list){
        List<E> reverseList = new ArrayList<>();
        Iterator<E> iter = list.iterator();
        while (iter.hasNext()) {
            E item = iter.next();
            reverseList.add(0,item);
        }
        return reverseList;
    }

    /**
	 * Method to sort a list of movies using different criteria
	 * @param list the list to be sorted
	 * @param type = 0, the list is sorted using the natural ordering of type Movie
	 *             = 1, the list should be sorted using a custom comparator for type Movie (order by title)
	 *             = 2, the list should be sorted using a custom comparator for type Movie (order by rating)
	 */
    // Big O notation: O(n log n)
    public static void sort(List<Movie> list, int type){
        class titleComparator implements Comparator<Movie> {
            public int compare(Movie m1, Movie m2) {
                return m1.getTitle().compareTo(m2.getTitle());
            }
        }
        class ratingComparator implements Comparator<Movie> {
            public int compare(Movie m1, Movie m2) {
                return Double.compare(m1.getRating(), m2.getRating());
            }
        }
        if (type == 0) {
            list.sort(null);
        } else if (type == 1) {
            list.sort(new titleComparator());
        } else if (type == 2) {
            list.sort(new ratingComparator());
        }
    }

    /**
     * Method to flatten a list of elements or other lists into a list of elements
     * @param nestedList the list that may contain simple elements or sublists
     * @return a new list with all the sublists flattened (use the concrete class ArrayList to create a new list)
     */
    public static <E> List<E> flatten(List<Object> nestedList){
        List<E> result = new ArrayList<>();
        if (nestedList.isEmpty()) {
            return result;
        }
        helpFlatten(nestedList.iterator(),result);
        return result;
    }
    public static <E> List<E> helpFlatten(Iterator<Object> iter, List<E> acc) {
        if (!iter.hasNext()) {
            return null;
        }
        Object object = iter.next();
        Iterator<E> iterE = ((List<E>) object).iterator();
        while (iterE.hasNext()) {
            E item = iterE.next();
            acc.add(item);
        }
        helpFlatten(iter, acc);
        return acc;
    }
    
    /**
	 * Method to print a list given its iterator
	 * @param iter the iterator to a generic collection
	 */
	public static <E> void printList(Iterator<E> iter) {
		System.out.println("[");
		while(iter.hasNext()) {
			System.out.println(iter.next());
		}
		System.out.println("]");
	}
	
    
}