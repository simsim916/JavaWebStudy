import java.util.PriorityQueue;

public class Test2 {
	public static void main(String[] args) {
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
