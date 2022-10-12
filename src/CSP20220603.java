import java.util.*;

class UserSet{
    String name;
    HashSet<String> v, n, o;

    UserSet(String s){
        this.name = s;
        this.v = new HashSet<>();
        this.o = new HashSet<>();
        this.n = new HashSet<>();
    }

    public void addV(String s){ v.add(s); }
    public void addO(String s){ o.add(s); }
    public void addN(String s){ n.add(s); }
}

/**
 * @author SwordFlame
 */
public class CSP20220603 {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt(), m = input.nextInt(), q = input.nextInt();
        int nv, no, nn, ns, ng, cnt1 = 0, cnt2 = 0;
        HashMap<String, Integer> link = new HashMap<>(n+5);
        HashMap<String, Integer> userlink = new HashMap<>(200005);
        HashMap<String, Integer> usergrouplink = new HashMap<>(200005);
        HashSet<String>[] useroperate = new HashSet[200005];
        HashSet<String>[] usergroupoperate = new HashSet[200005];
        String s, s2, s3;
        UserSet[] a = new UserSet[n+5];
        for(int i = 1; i <= n; ++i){
            s = input.next();
            link.put(s, i);
            a[i] = new UserSet(s);
            nv = input.nextInt(); for(int j = 1; j <= nv; ++j){ a[i].addV(input.next()); }
            no = input.nextInt(); for(int j = 1; j <= no; ++j){ a[i].addO(input.next()); }
            nn = input.nextInt(); for(int j = 1; j <= nn; ++j){ a[i].addN(input.next()); }
        }
        for(int i = 1; i <= m; ++i){
            s = input.next(); ns = input.nextInt();
            for(int j = 1; j <= ns; ++j) {
                s2 = input.next(); s3 = input.next();
                if (Objects.equals(s2, "u")) {
                    if (userlink.containsKey(s3)) {
                        useroperate[userlink.get(s3)].add(s);
                    } else {
                        ++cnt1;
                        useroperate[cnt1] = new HashSet<>();
                        userlink.put(s3, cnt1);
                        useroperate[cnt1].add(s);
                    }
                } else {
                    if (usergrouplink.containsKey(s3)) {
                        usergroupoperate[usergrouplink.get(s3)].add(s);
                    } else {
                        ++cnt2;
                        usergroupoperate[cnt2] = new HashSet<>();
                        usergrouplink.put(s3, cnt2);
                        usergroupoperate[cnt2].add(s);
                    }
                }
            }
        }
        String[] usergroup = new String[405];
        String username;
        while((q--)>0){
            boolean fg = false;
            username = input.next();
            ng = input.nextInt();
            for(int j = 1; j <= ng; ++j){ usergroup[j] = input.next(); }
            s = input.next(); s2 = input.next(); s3 = input.next();
            if(userlink.containsKey(username)){
                Enumeration<String> k = Collections.enumeration(useroperate[userlink.get(username)]);
                while(k.hasMoreElements()){
                    String character = k.nextElement();
                    int id = link.get(character);
                    if(check(a, id, s, s2, s3)) {
                        fg = true; break;
                    }
                }
            }
            if(fg){ System.out.println(1);  continue; }
            for(int j = 1; j <= ng; ++j){
                if(usergrouplink.containsKey(usergroup[j])) {
                    Enumeration<String> k = Collections.enumeration(usergroupoperate[usergrouplink.get(usergroup[j])]);
                    while (k.hasMoreElements()) {
                        String character = k.nextElement();
                        int id = link.get(character);
                        if(check(a, id, s, s2, s3)) {
                            fg = true; break;
                        }
                    }
                    if (fg) { break; }
                }
            }
            System.out.println(fg ? 1 : 0);
        }
    }

    public static boolean check(UserSet[] a, int id, String s, String s2, String s3){
        if(a[id].v.contains(s) || a[id].v.contains("*")){
            if(a[id].o.contains(s2) || a[id].o.contains("*")){
                return a[id].n.contains(s3) || a[id].n.isEmpty();
            }
        }
        return false;
    }

}