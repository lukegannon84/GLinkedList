import java.util.*;

public class GLinkedList<E extends Comparable<E>>{
	private Node<E> head = null;//empty list
	private int size = 0;
	public void add(E x){ //add at head
		Node<E> nw = new Node<E>(x);
		nw.setNext(head);
		head = nw;
		size++;
	}

	public boolean contains(E x){
		Node<E> k = head;
		boolean found = false;
		while(k != null && !found){
			E kk = k.data();
			if(kk.compareTo(x) == 0) found = true;
			else k = k.next();
		}
		return found;
	}

	public boolean remove(E x){
		Node<E> k = head; Node<E> bk = head;
		boolean found = false;
		while(k != null && !found){
			if(k.data().compareTo(x) == 0) found = true;
			else{ bk = k; k = k.next();}
		}
		if(found){
			size--;
			if(k == head)
				head = k.next();
			else
			  bk.setNext(k.next());
			return true;
		}
		else
			return false;
	}

	public int length(){
		return size;
	}
	public void display(){
		Node<E> k = head;
		System.out.print('[');
		while(k != null){
			if(k.next != null)
			   System.out.print(k.data()+", ");
			else
			   System.out.print(k.data());
			k = k.next();
		}
		System.out.println(']');
	}
	public Iterator<E> iterator(){
		return new GIterator<E>(head, size);
	}
	private static class GIterator<E extends Comparable<E>> implements Iterator<E>{
		private Node <E> head;
		private int size;
		private int index = 0;
		GIterator(Node<E> h, int s){
			head = h; size = s;
		}
		public boolean hasNext(){
			return index < size;
		}
		public E next(){
			if(index == size) throw new NoSuchElementException();
			E item = head.data();
			head = head.next(); index++;
			return item;
		}
		public void remove(){}
	}
}
class Node<E extends Comparable<E>>{
	E data;
	Node<E> next;
	public Node(E  x){
		data = x; next = null;
	}
	public Node<E> next(){return next;}
	public void setNext(Node<E> p){
		next = p;
	}
	public void set(E x){data = x;}
	public E data(){return data;}
}

class HashList<E extends Comparable<E>>{
	private GLinkedList<E> data[];
	public HashList(int n){
		data = (GLinkedList<E>[])(new GLinkedList[n]);
		for(int j = 0; j < data.length;j++)
			data[j] = new GLinkedList<E>();
	}
	private int hashC(E x){
		int k = x.hashCode();
		//an alternative is to mask the minus using
		//int k = x.hashCode() & 0x7fffffff;

		int h = Math.abs(k % data.length);
		return(h);
	}
	public void add(E x){
		int index = hashC(x);
		data[index].add(x);
	}
	public boolean contains(E x){
		int index = hashC(x);
		return(data[index].contains(x));
	}
	public void displayLists(){
		for(GLinkedList<E> k : data){
		 if(k.length() > 0)
			  k.display();
		}
	}
	public void display(){
		System.out.print("<");
		int ind = 0;
		while(ind < data.length){
			Iterator<E> it = data[ind].iterator();
			while(it.hasNext())
				System.out.print(it.next()+" ");
			ind++;
		}
		System.out.println(">");
	}
	public int tableSize(){
		return data.length;
	}
	public double percentUsed(){
		int count = 0;
		for(int j = 0; j < data.length; j++){
			if(data[j].length() > 0)
			  count++;
		}
		double p = count *100.0 / data.length;
		return p;
	}
	public int largestBucket(){
		int max = 0;
		for(int j = 0; j < data.length; j++)
			if(data[j].length() > max) max = data[j].length();
		return max;
	}
	public int smallestBucket(){
		int min = data[0].length();
		for(int j = 1; j < data.length; j++)
			if(data[j].length() < min) min = data[j].length();
		return min;
	}
	public int[] listSizes(){
		int n = this.largestBucket();
		int d[] = new int[n+1];
		for(int j = 0; j < d.length; j++) d[j] = 0;
		for(int j = 0; j < data.length; j++){
			int m = data[j].length();
			d[m] = d[m] + 1;
		}
		return d;
	}

	public int empty(){
		int count = 0;
		for(int j = 0; j < data.length; j++)
			if(data[j].length() == 0) count++;
		return count;
	}
	public Iterator<E> iterator(){
	  ArrayList<E> items = new ArrayList<E>();
	  int ind = 0;
	  while(ind < data.length){
			Iterator<E> it = data[ind].iterator();
			while(it.hasNext())
				items.add(it.next());
			ind++;
	   }
	   return items.iterator();
	}

		public E max(){

			E maxNum = null;//to start off the max value is null
			int ind = 0;
			while(ind < data.length){
					Iterator<E> it = data[ind].iterator();
				while(it.hasNext()){
					E pointer = it.next();//compare the current value against maxNum
					if(pointer.compareTo(maxNum) == 1){
						maxNum = pointer;
					}
				}
				ind++;
			 }

			return maxNum;
		}


			public int freq(E x){

				int counter = 0;
				int ind = 0;

				while(ind < data.length){
					Iterator<E> it = data[ind].iterator();
					while(it.hasNext()){
						E pointer = it.next();
						if(pointer.compareTo(x) == 0){//compare the current value to x
													//if there equal counter gets incremented
							counter++;
						}
					}
				ind++;
				}
				return counter;

			}

			public Iterator<E> listIterator(E x){

				int ind = 0;
				while(ind < data.length){
					Iterator<E> it = data[ind].iterator();
					while(it.hasNext()){
						E pointer = it.next();

						if(pointer.compareTo(x) == 1){
							return it;
						}
					}
					ind++;
				 }
					  return null;
			}


}

class HashTestList{

	public static void main(String []args){
		HashList<Integer>list = new HashList<Integer>(1000);


		for(int j=0;j<100000;j++){
			int x=(int)(Math.random()*10000000);
			list.add(new Integer(x));
		}


		System.out.println("percentage of buckets used: "+list.percentUsed());
		System.out.println("Largest bucket size = "+list.largestBucket());
		System.out.println("Smallest bucket size ="+list.smallestBucket());
		System.out.println("frequency ="+list.freq(987));
		System.out.println("Contains ="+list.contains(987 ));
	//	System.out.println("Max = "+list.max());





	}
}

