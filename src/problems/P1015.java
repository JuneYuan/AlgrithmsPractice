package smartjune;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

public class P1015 {
	private static String txt, pat;
	private static int N, M;
	private static int[][] dfa;
	private static int R = 26;
	
	public static int brute1() {
		for (int i = 0; i <= N - M; i++) {			
			for (int j = 0; j < M; j++) {
				if (txt.charAt(i + j) != pat.charAt(j))
					break;
				if (j == M - 1)
					return i;  // 找到
			}
		}
		return N;
	}
	
	public static int brute2() {
		int i, j;
		for (i = 0, j = 0; i < N && j < M; i++) {
			if (txt.charAt(i) == pat.charAt(j))		j++;
			else { i -= j;  j = 0; }
		}
		
		if (j == M)		return i - M;
		else	return N;  // NOT found
		
	}
	
	public static void kmpSearch() {
		int i, j, k = 0, ret = 0;
		do {
			for (i = k, j = 0; i < N && j < M; i++)
				j = dfa[txt.charAt(i) - 65][j];
			
			if (j == M) {
				k = i - M + 1;
				ret++;
			} else 
				break;
		} while (k > 0);
		
		System.out.println(ret);
	}
	
	private static void dfa() {
		/*
		 * For Each j:
		 * Copy dfa[][X] to dfa[][j];
		 * Set dfa[pat.charAt(j)][j] = j + 1;
		 * Update X = dfa[pat.charAt(j)][X]
		 */

		dfa[pat.charAt(0) - 65][0] = 1;
		for (int X = 0, j = 1; j < M; j++) {
			for (int c = 0; c < R; c++)
				dfa[c][j] = dfa[c][X];
			dfa[pat.charAt(j) - 65][j] = j + 1;
			X = dfa[pat.charAt(j) - 65][X];
		}
	}
	
	public static void main(String[] args) {
		InputReader in = new InputReader(System.in);
		//PrintWriter out = new PrintWriter(System.out);
		int T = in.nextInt();
		for (int i = 0; i < T; i++) {
			pat = in.next();
			txt = in.next();
			N = txt.length();
			M = pat.length();
			dfa = new int[R][M];			
			
			dfa();
			kmpSearch();
		}		
		//out.close();
	}

	private static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

    }

}
