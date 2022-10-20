import java.util.*;

/**
 * @author SwordFlame
 */
public class Csp20170302 {
    public static int[] id = new int[1005], a = new int[1005];
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        int n = reader.nextInt(), m = reader.nextInt();
        for (int i=1; i<=n; ++i){ id[i] = i; a[i] = i; }
        while ((m--) > 0){
            int x = reader.nextInt(), y = reader.nextInt(), pre;
            if (y>0) {
                while ((y--) > 0){
                    pre = id[x];
                    swap(id, x, a[id[x]+1]);
                    swap(a, pre, pre+1);
                }
            }
            else {
                while ((y++) < 0){
                    pre = id[x];
                    swap(id, x, a[id[x]-1]);
                    swap(a, pre, pre-1);
                }
            }
        }
        print(a, id, n);
    }
    public static void swap(int[] arr, int x, int y){
        int t = arr[x];
        arr[x] = arr[y];
        arr[y] = t;
    }

    public static void print(int[] a, int[] id, int n){
        for (int i=1; i<=n; ++i){
            a[id[i]] = i;
        }
        for (int i=1; i<=n; ++i){
            System.out.printf("%d ", a[i]);
        }
        System.out.println();
    }

}
