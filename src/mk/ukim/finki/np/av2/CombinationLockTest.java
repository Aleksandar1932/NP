package mk.ukim.finki.np.av2;

class CombinationLock{
    private String combination;
    private boolean isOpen;

    public CombinationLock(String combination) {
        this.combination = combination;
        this.isOpen = false;
    }

    public boolean open(String combination){
        if(this.combination.equals(combination)){
            this.isOpen = true;
        }
        return false;
    }

    public boolean changeCombo(String oldCombination, String newCombination){
        if(this.combination.equals(oldCombination)){
            this.combination = newCombination;
            this.isOpen = false;
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isOpen(){
        return this.isOpen;
    }
}

public class CombinationLockTest {
    public static void main(String[] args) {
        CombinationLock c1 = new CombinationLock("123");
        System.out.println(c1.isOpen()); //false
        System.out.println(c1.open("123")); //true
        System.out.println(c1.open("232")); //false
        System.out.println(c1.changeCombo("113","444")); //false
        System.out.println(c1.changeCombo("123","555")); //true
        System.out.println(c1.isOpen()); //false
        System.out.println(c1.open("555")); //true
    }
}
