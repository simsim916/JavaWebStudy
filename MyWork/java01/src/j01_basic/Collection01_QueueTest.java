package j01_basic;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Collection01_QueueTest {
	public static void main(String[] args) {
		System.out.println("Queue");
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(30);
		q.add(80);
		q.add(20);
		
		for (Integer o : q) {
			System.out.println(o);
		}
		System.out.println("원소 삭제");
		while (!q.isEmpty()) {
			System.out.println(q.remove());
		}
		
		
		System.out.println("PriorityQueue");
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
		pq.add(30);
		pq.add(80);
		pq.add(20);

		for (Integer o : pq) {
			System.out.println(o);
		}
		System.out.println("원소 삭제");
		while (!pq.isEmpty()) {
			System.out.println(pq.remove());
		}
	}
}
