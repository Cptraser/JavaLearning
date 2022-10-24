import org.apache.log4j.Logger;
import java.util.*;

/**
 * @author SwordFlame
 */
class Servant{
    int hp, atk;
    Servant() { hp = 0; atk = 0; }
    Servant(int atk, int hp) { this.atk = atk; this.hp = hp; }
}

/**
 * @author SwordFlame
 */
class Battlefield{
    private static final Logger logger = Logger.getLogger(BattlefieldTest.class);
    Servant[] side;
    final static int BATTLEFIELD_WIDE = 7;
    int cnt;
    Battlefield() {
        side = new Servant[BATTLEFIELD_WIDE];
        for (int i = 0; i < BATTLEFIELD_WIDE; ++i){
            side[i] = new Servant();
        }
        cnt = 0;
    }
    public void push(int position, int atk, int hp){
        position = position - 1;
        for (int i = cnt; i >= position + 1; --i){
            side[i] = side[i-1];
        }
        side[position] = new Servant(atk, hp);
        cnt = cnt + 1;
    }
    public boolean del(int position){
        position = position - 1;
        try {
            for (int i = position; i < cnt - 1; ++i){
                side[i] = side[i+1];
            }
        }catch (ArrayIndexOutOfBoundsException e){
            logger.debug("Runtime Error");
        }
        cnt = cnt - 1;
        return true;
    }
    public void check(int position){
        if (side[position].hp <= 0) {
            del(position + 1);
        }
    }
    public void printout(){
        System.out.printf("%d", cnt);
        for (int i = 0; i < cnt; ++i){
            System.out.printf(" %d",side[i].hp);
        }
        System.out.println();
    }

}

/**
 * @author SwordFlame
 */
public class Csp20160903 {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        int n = reader.nextInt(), turn = 0, wins = 0;
        int[] heroHp = new int[2];
        Battlefield[] a = new Battlefield[2];
        a[0] = new Battlefield();
        a[1] = new Battlefield();
        heroHp[0] = heroHp[1] = 30;
        while((n--) > 0){
            String operate = reader.next();
            switch (operate) {
                case "summon":
                    int position = reader.nextInt();
                    int atk = reader.nextInt(), hp = reader.nextInt();
                    a[turn].push(position, atk, hp);
                    break;
                case "attack":
                    int attacker = reader.nextInt() - 1, defender = reader.nextInt() - 1;
                    if (defender >= 0) {
                        a[turn].side[attacker].hp -= a[turn^1].side[defender].atk;
                        a[turn^1].side[defender].hp -= a[turn].side[attacker].atk;
                        a[turn].check(attacker);
                        a[turn^1].check(defender);
                    } else {
                        heroHp[turn ^ 1] -= a[turn].side[attacker].atk;
                    }
                    break;
                default:
                    turn = turn ^ 1;
                    break;
            }
            if (heroHp[turn^1] <= 0){
                wins = turn == 0 ? 1 : -1;
                break;
            }
        }
        System.out.println(wins);
        System.out.println(heroHp[0]);
        a[0].printout();
        System.out.println(heroHp[1]);
        a[1].printout();
    }
}
