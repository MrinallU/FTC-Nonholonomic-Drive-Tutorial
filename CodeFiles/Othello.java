import java.util.*;
import java.io.*;

public class Main {

    static Kattio io;
    static {
        try {
            io = new Kattio("talent");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static int n, w, sum;
    static long [][] dp;
    public static void main(String[] args) throws Exception {
       
    }

    static class pair implements Comparable<pair>{
        int p1, p2;
        public pair(int p1, int p2){
            this.p1 = p1;
            this.p2 = p2;
        }

        @Override
        public int compareTo(pair o) {
            return Integer.compare(this.p1, o.p1);
        }
    }

    static class Kattio extends PrintWriter {
        private BufferedReader r;
        private StringTokenizer st;

        // standard input
        public Kattio() { this(System.in, System.out); }
        public Kattio(InputStream i, OutputStream o) {
            super(o);
            r = new BufferedReader(new InputStreamReader(i));
        }
        // USACO-style file input
        public Kattio(String problemName) throws IOException {
            super(new FileWriter(problemName + ".out"));
            r = new BufferedReader(new FileReader(problemName + ".in"));
        }

        // returns null if no more input
        public String next() {
            try {
                while (st == null || !st.hasMoreTokens())
                    st = new StringTokenizer(r.readLine());
                return st.nextToken();
            } catch (Exception e) { }
            return null;
        }

        public int nextInt() { return Integer.parseInt(next()); }
        public double nextDouble() { return Double.parseDouble(next()); }
        public long nextLong() { return Long.parseLong(next()); }
    }

}