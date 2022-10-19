import java.util.*;

class Catalogue{
    long ld, lr;
    long nd, nr;
    HashMap<String, Long> son;
    Catalogue(){
        son = new HashMap<>();
        ld = lr = 0;
        nd = nr = 0;
    }
}

/**
 * @author SwordFlame
 */
public class Csp20201203 {
    public static int n, cnt = 0;
    static final String N = "N";
    static final String Y = "Y";
    public static Catalogue[] node = new Catalogue[100005];

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        n = input.nextInt();
        for(int i = 0; i <= n; ++i){
            node[i] = new Catalogue();
        }
        for(int t = 1; t <= n; ++t){
            String c = input.next();
            if (Objects.equals(c, "C")) {
                String path = input.next();
                long size = input.nextLong();
                System.out.println(checkCreate(path, size));
            } else if (Objects.equals(c, "R")) {
                String path = input.next();
                checkRemove(path);
                System.out.println(Y);
            }else {
                String path = input.next();
                long ld = input.nextLong(), lr = input.nextLong();
                System.out.println(checkQuota(path, ld, lr));
            }

        }
    }


    public static String checkCreate(String path, long size){
        String[] s = path.split("/");
        int len = s.length;
        int nw = 0, pre = -1;
        boolean fg = true;
        for (int i = 1; i < len; ++i){
            if (node[nw].son.containsKey(s[i])){
                if (i<len-1 && node[nw].son.get(s[i])<=0){
                    return N;
                }
                pre = nw;
                nw = Integer.parseInt(node[nw].son.get(s[i]).toString());
            }
            else {
                fg = false;
                break;
            }
        }
        if (fg){
            if (nw>0) {
                return N;
            }
            else {
                nw = 0;
                for (int i = 1; i < len - 1; ++i){
                    if (node[nw].lr > 0 && node[nw].lr < node[nw].nr + node[pre].son.get(s[len-1]) + size){ return N; }
                    nw = Integer.parseInt(node[nw].son.get(s[i]).toString());
                }
                if (node[nw].lr > 0 && node[nw].lr < node[pre].nr + node[pre].son.get(s[len-1]) + size){ return N; }
                if (node[nw].ld > 0 && node[nw].ld < node[pre].nd + node[pre].son.get(s[len-1]) + size){ return N; }
                nw = 0;
                for (int i = 1; i < len - 1; ++i){
                    node[nw].nr += node[pre].son.get(s[len-1]) + size;
                    nw = Integer.parseInt(node[nw].son.get(s[i]).toString());
                }
                node[pre].nr += node[pre].son.get(s[len-1]) + size;
                node[pre].nd += node[pre].son.get(s[len-1]) + size;
                node[pre].son.replace(s[len-1], -size);
                return Y;
            }
        }
        else {
            nw = 0;
            for (int i = 1; i < len - 1; ++i){
                if (node[nw].lr > 0 && node[nw].lr < node[nw].nr + size){
                    return N;
                }
                if (node[nw].son.containsKey(s[i])){
                    if (node[nw].son.get(s[i])<=0){
                        return N;
                    }
                    nw = Integer.parseInt(node[nw].son.get(s[i]).toString());
                }
                else {
                    node[nw].son.put(s[i], (long)(++cnt));
                    nw = cnt;
                }
            }
            if(node[nw].lr > 0 && node[nw].lr < node[nw].nr + size){
                return N;
            }
            if (node[nw].ld > 0 && node[nw].ld < node[nw].nd + size){
                return N;
            }
            nw = 0;
            for (int i = 1; i < len - 1; ++i){
                node[nw].nr += size;
                nw = Integer.parseInt(node[nw].son.get(s[i]).toString());
            }
            node[nw].nr += size;
            node[nw].nd += size;
            node[nw].son.put(s[len-1], -size);
            return Y;
        }
    }
    public static void checkRemove(String path){
        String[] s = path.split("/");
        int len = s.length, nw = 0, pre = -1;
        for (int i = 1; i < len; ++i){
            if (node[nw].son.containsKey(s[i])){
                if (i<len-1 && node[nw].son.get(s[i])<=0){
                    return ;
                }
                pre = nw;
                nw = Integer.parseInt(node[nw].son.get(s[i]).toString());
            }
            else {
                return ;
            }
        }
        if (node[pre].son.get(s[len-1]) <= 0){
            nw = 0;
            for (int i = 1; i < len - 1; ++i){
                node[nw].nr -= -node[pre].son.get(s[len-1]);
                nw = Integer.parseInt(node[nw].son.get(s[i]).toString());
            }
            node[nw].nr -= -node[nw].son.get(s[len-1]);
            node[nw].nd -= -node[nw].son.get(s[len-1]);
            node[nw].son.remove(s[len-1]);
        }
        else{
            pre = nw;
            nw = 0;
            for (int i = 1; i < len - 1; ++i){
                node[nw].nr -= node[pre].nr;
                nw = Integer.parseInt(node[nw].son.get(s[i]).toString());
            }
            node[nw].nr -= node[pre].nr;
            node[nw].son.remove(s[len-1]);
        }
    }

    public static String checkQuota(String path, long ld, long lr){
        String[] s = path.split("/");
        int len = s.length, nw = 0, pre = -1;
        if (Objects.equals(len,0)){
            if(lr>0&&node[nw].nr > lr){
                return N;
            }
            if (ld>0&&node[nw].nd > ld) {
                return Y;
            }
            node[nw].lr = lr;
            node[nw].ld = ld;
            return Y;
        }
        for (int i = 1; i < len; ++i){
            if (node[nw].son.containsKey(s[i])){
                if (i<len-1 && node[nw].son.get(s[i])<=0){
                    return N;
                }
                pre = nw;
                nw = Integer.parseInt(node[nw].son.get(s[i]).toString());
            }
            else {
                return N;
            }
        }
        if(node[pre].son.get(s[len-1]) <= 0){
            return N;
        }
        if(lr>0&&node[nw].nr > lr){
            return N;
        }
        if (ld>0&&node[nw].nd > ld) {
            return N;
        }
        node[nw].lr = lr;
        node[nw].ld = ld;
        return Y;
    }
    public static boolean check(int x, int y){ return x>y; }

}