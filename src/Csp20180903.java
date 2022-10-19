import java.util.*;

class Element{
    String em;
    int ext;
    String id;
    Element(){ this.ext = 0; }
    public void insert(String s, int cnt){
        String[] ins = s.split(" ");
        int len = ins.length;
        this.em = ins[0].substring(cnt*2).toLowerCase();
        if (len > 1) {
            this.ext = 1;
            this.id = ins[1];
        }
    }

}


/**
 * @author SwordFlame
 */
public class Csp20180903 {
    public static final char C = '#';
    public static int top = 0;
    public static int[] stk = new int[105];
    public static String[] sp;
    public static Map<String, Vector<Integer> > map = new HashMap<>(105);
    public static Element[] ele = new Element[105];
    public static int[] nid = new int[105], f = new int[105];

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        int n = reader.nextInt(), m = reader.nextInt();
        reader.nextLine();
        for (int i=1; i<=n; ++i) {
            ele[i] = new Element();
            String s = reader.nextLine();
            int cnt = getCent(s);
            ele[i].insert(s, cnt);
            if (!map.containsKey(ele[i].em)) { map.put(ele[i].em, new Vector<>()); }
            if (ele[i].ext > 0) {
                map.put(ele[i].id, new Vector<>());
                map.get(ele[i].id).add(i);
            }
            map.get(ele[i].em).add(i);
            nid[cnt] = i;
            if (Objects.equals(cnt, 0)) { continue; }
            f[i] = nid[cnt-1];
        }
        for (int i=1; i<=m; ++i){
            String s =reader.nextLine();
            solve(s);
        }
    }

    public static int getCent(String s){
        int res = 0, len = s.length();
        for (int i=0; i<len; ++i){
            if (Objects.equals(s.charAt(i), '.')){ ++res; }
            else { break; }
        }
        return res/2;
    }
    public static void solve(String s){
        sp = s.split(" ");
        int len = sp.length;
        top = 0;
        if (!map.containsKey(rep(sp[len-1]))){
            System.out.println(0);
            return ;
        }
        int sz = map.get(rep(sp[len-1])).size();
        Vector<Integer> nw = map.get(rep(sp[len-1]));
        for (int i=0; i<sz; ++i){
            if(dfs(nw.get(i), len-1)){
                stk[++top] = nw.get(i);
            }
        }
        System.out.printf("%d", top);
        for (int i=1; i<=top; ++i){
            System.out.printf(" %d", stk[i]);
        }
        System.out.println();
    }
    public static String rep(String s){
        if (Objects.equals(s.charAt(0), C)){
            return s;
        }
        return s.toLowerCase();
    }
    public static boolean dfs(int x, int lst){
        if (Objects.equals(lst, 0)) {
            return true;
        }
        if (Objects.equals(x, 1)) {
            return false;
        }
        if (Objects.equals(rep(sp[lst-1]).charAt(0), C)) {
            if (ele[f[x]].ext > 0 && Objects.equals(ele[f[x]].id, rep(sp[lst-1]))){
                return dfs(f[x], lst-1);
            }
            else {
                return dfs(f[x], lst);
            }
        }
        else {
            if (Objects.equals(ele[f[x]].em, rep(sp[lst-1]))){
                return dfs(f[x], lst-1);
            }
            else {
                return dfs(f[x], lst);
            }
        }
    }

}
