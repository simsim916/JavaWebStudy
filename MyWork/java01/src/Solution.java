
public class Solution {
	public static int solution(String[] friends, String[] gifts) {
        int answer = 0;
        int[][] ar = new int[friends.length][friends.length];
        int[] giftTakenCnt = new int[friends.length];
        int giver = 0, taker = 0;
        
        for ( String i : gifts ) {
        	String[] giftsToken = i.split(" ");
        	for (int idx = 0; idx < friends.length ; idx++) {
        		if(giftsToken[0].equals(friends[idx])) {
        			giver = idx;
        			break;
        		}
        	}
        	for (int idx = 0; idx < friends.length ; idx++) {
        		if(giftsToken[1].equals(friends[idx])) {
        			taker = idx;
        			break;
        		}
        	}
        	++ar[giver][taker];
        }
        
        for (int i = 0 ; i < ar.length ; i++) {
        	for (int j : ar[i]) {
        		giftTakenCnt[i]+=j;
        	}
        }
        
        
        for (int i = 0 ; i < friends.length-1 ; i++) {
        	for (int j = i+1 ; j < friends.length ; j++) {
        		if (ar[i][j]>ar[j][i]) {
        			
        		} else if (ar[i][j]==ar[j][i]) {
        			
        		} else {
        			
        		}
        	}
        }
        
        for ( int i : giftTakenCnt) {
        	System.out.println(i);
        }
        
        for ( int[] i : ar ) {
        	for (int j : i) {
        		System.out.print(j);
        	}
        	System.out.println();
        }
        return answer;
    }

	public static void main(String[] args) {
		String[] friends = {"muzi", "ryan", "frodo", "neo"};
		String[] gifts = {"muzi frodo", "muzi frodo", "ryan muzi", "ryan muzi", "ryan muzi", "frodo muzi", "frodo ryan", "neo muzi"};
		
		System.out.println(solution(friends, gifts));
	}
}
