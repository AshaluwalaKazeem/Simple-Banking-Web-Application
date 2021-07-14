package com.examble.demo;

public class PerfectSquareCount {

	public static void main(String[] args) {
		solve(1, 1);
		solve(3, 3);
		solve(4, 5);
	}

	static void solve(int l, int b) {
		int i = 0;
		int num = Math.min(l, b);
		int answer = 0;
		
		while(num > 0) {
			answer += (l - i) * (b - i);
			i++;
			num--;
		}
		
		System.out.println(answer);
	}
}
