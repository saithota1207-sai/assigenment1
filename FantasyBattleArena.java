package taskvirtusa;

import java.util.Arrays;
import java.util.Scanner;
class Character {
    protected  String name;
    protected  int health;
    protected   int attackPower;
    protected  int defense;
    protected  int speed;
    Character(String name, int health, int attackPower, int defense, int speed) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
        this.defense = defense;
        this.speed = speed;
    }
    boolean isAlive()
    {
        return health > 0;
    }
    void takeDamage(int damage) {
        int finalDamage = Math.max(1, damage - defense);
        health -= finalDamage;
        System.out.println(name + " takes " + finalDamage + " damage. (HP: " + Math.max(0, health) + ")");
    }
    void attack(Character target) {
        System.out.println(name +" attacks " +target.name +"!");
        target.takeDamage(attackPower);
    }
}
class Warrior extends Character {
    protected int rage;
    Warrior(String name,int health,int attackPower,int defense,int rage) {
        super(name,health,attackPower,defense,rage);
        this.rage=rage;
    }
    void attack(Character target){
        if (health < 30) {
            System.out.println(name+" in Berserk Mode!");
            int power=attackPower*2;
            System.out.println(name+" attacks with power "+power);
        }
        else {
            System.out.println(name+"attacks with power"+attackPower);
        }
    }
    void takeDamage(int amount){
        health-=amount;
        rage+=10;
        System.out.println(name+"takes damage,rage now"+rage);
    }
}
class Mage extends Character {
    int mana;
    Mage(String name,int health,int attackPower,int defense,int speed,int mana) {
        super(name,health,attackPower,defense,speed);
        this.mana = mana;
    }
    void showMageInfo() {
        System.out.println("Mage: "+name +",Mana:" +mana);
    }
}
class Archer extends Character {
    double criticalChance;
    Archer(String name,int health,int attackPower,int defense,int speed,double criticalChance) {
        super(name,health,attackPower,defense,speed);
        this.criticalChance = criticalChance;
    }
    void showArcherInfo() {
        System.out.println("Archer:"+name+",Critical Chance:"+criticalChance);
    }
}
public class FantasyBattleArena {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("FANTASY BATTLE ARENA");
        System.out.println("Enter number of players:");
        int n=sc.nextInt();
        sc.nextLine();
        Character[] fighters=new Character[n];
        for(int i = 0; i < n; i++) {
            System.out.println("\nChoose character type for Player " + (i + 1) + " (1.Warrior  2.Mage  3.Archer): ");
            int type=sc.nextInt();
            sc.nextLine();
            System.out.print("Enter name:");
            String name = sc.nextLine();
            System.out.print("Enter health:");
            int health=sc.nextInt();
            System.out.print("Enter attack power:");
            int attackPower=sc.nextInt();
            System.out.print("Enter defense:");
            int defense=sc.nextInt();
            System.out.print("Enter speed:");
            int speed=sc.nextInt();
            if(type==1) {
                fighters[i]=new Warrior(name,health,attackPower,defense,speed);
            }
            else if(type==2){
                System.out.print("Enter mana:");
                int mana = sc.nextInt();
                fighters[i]=new Mage(name,health,attackPower,defense,speed,mana);
            }
            else if (type==3){
                System.out.print("Enter critical chance (0.0 to 1.0):");
                double crit=sc.nextDouble();
                fighters[i]=new Archer(name,health,attackPower,defense,speed,crit);
            }
            else {
                System.out.println("Invalid choice!Defaulting to Warrior");
                fighters[i]=new Warrior(name,health,attackPower,defense,speed);
            }
        }
        System.out.println("Battle Begins");
        while(true){
            Arrays.sort(fighters,(a, b)->b.speed-a.speed);
            for(Character attacker:fighters){
                if(!attacker.isAlive())continue;
                Character target=null;
                for(Character t:fighters) {
                    if(t!=attacker&&t.isAlive()){
                        target=t;
                        break;
                    }
                }
                if(target==null) {
                    System.out.println(attacker.name+"wins the battle");
                    sc.close();
                    return;
                }
                attacker.attack(target);
                System.out.println();
                if(!target.isAlive()){
                    System.out.println(target.name+" has been defeated");
                }
            }
        }
    }
}

