import java.util.Random;
import java.util.List;
import java.util.Arrays;
import java.util.Date;
import java.text.SimpleDateFormat;

class RandomWrapper{
    public static double getRanDouble(double min, double max){
        Random r = new Random();
        return min + r.nextDouble() * (max - min);
    }

    public static boolean getRanBoolean(){
        return new Random().nextBoolean();
    }
}

class Name{
    private String firstName;
    private String lastName;

    public Name(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String toString(){
        return firstName + " " + lastName;
    }
}

class BMI{
    private double heightM;
    private double weightKg;

    public BMI(double heightM, double weightKg){
        this.heightM = heightM;
        this.weightKg = weightKg;
    }

    public double getValue(){
        return weightKg / (heightM * heightM);
    }

    public String toString(){
        return "heightM: " + heightM + ", weightKg: " + weightKg;
    }
}

class Animal{
    protected String species;
    protected BMI bmi;
    protected Date spawnTime;
    protected Date deathTime;

    public Animal(String species, double heightM, double weightKg){
        this.species = species;
        this.bmi = new BMI(heightM, weightKg); // コンポジション
        this.spawnTime = new Date();
    }

    public void die(){
        deathTime = new Date();
    }

    public String dateCreated(){
        return new SimpleDateFormat("yyyy/mm/dd hh:mm:ss").format(spawnTime);
    }

    public String toString(){
        return species + " " + bmi + ". ";
    }
}

class Mammal extends Animal{
    private double bodyTemperatureC;
    private double avgBodyTemperatureC;

    public Mammal(String species, double heightM, double weightKg, double bodyTemperatureC){
        super(species, heightM, weightKg);

        this.bodyTemperatureC = bodyTemperatureC;
        this.avgBodyTemperatureC = bodyTemperatureC;
    }

    public String toString(){
        return super.toString() + "This mammal's body temperature is " + bodyTemperatureC + ". ";
    }
}

class Person extends Mammal{
    public static final String SPECIES = "Human";
    public static final double BODY_TEMPERATURE = 36.5;
    public Name name;
    public int age;

    public Person(double heightM, double weightKg, String firstName, String lastName, int age){
        super(Person.SPECIES, heightM, weightKg, Person.BODY_TEMPERATURE);

        this.name = new Name(firstName, lastName);
        this.age = age;
    }

    public String getName(){
        return name.toString();
    }

    public String toString(){
        return super.toString() + "This person's name is " + getName() + ". ";
    }
}

interface PlayfulPet{
    abstract public String play();
    abstract public String playWithPerson(Person person);
    abstract public String playNoise();
    abstract public String getPetName();
    abstract public boolean likesActivity(String activity);
    abstract public boolean dislikesActivity(String activity);
    abstract public String doActivity(String activity);
}

class Cat extends Mammal implements PlayfulPet{
    public static final String SPECIES = "Cat";
    public static final double BODY_TEMPERATURE = 38;
    private static final String[] LIKED_ACTIVITIES = {"eat","nap","groom","drink","crawl","explore","pet"};
    private static final String[] DISLIKED_ACTIVITIES = {"bath"};

    public Cat(double heightM, double weightKg){
        super(Cat.SPECIES, heightM, weightKg, Cat.BODY_TEMPERATURE);
    }

    public String play(){
        return "This cat likes playing with humans, especially they have food... ";
    }

    public String playWithPerson(Person person){
        return "This cat plays with " + person.getName() + ". ";
    }

    public String playNoise(){
        return "Meow~";
    }

    public String getPetName(){
        return species;
        // return SPECIES;
    }

    public boolean likesActivity(String activity){
        return Arrays.asList(LIKED_ACTIVITIES).contains(activity);
    }

    public boolean dislikesActivity(String activity){
        return Arrays.asList(DISLIKED_ACTIVITIES).contains(activity);
    }

    public String doActivity(String activity){
        if (likesActivity(activity)) return "The cat like the activity: " + activity + "!! ";
        else if (dislikesActivity(activity)) return "The cat do not like the activity: " + activity + "... ";
        else return "The cat felt indiferent about the activity: " + activity + ". ";
    }
}

abstract class PlayfulPetAssistant{
    protected static final double DEFAULT_RENT_TIME = 1.0;
    protected static final String DEFAULT_TOUR = "all-rounder pack";

    protected Person currentPerson;
    protected double currentRentTime = PlayfulPetAssistant.DEFAULT_RENT_TIME;
    protected static String[] availableActivities = {"eat","walk","drink","nap","pet","run","explore"};
    protected static String[] availableTours = {"all-rounder pack", "deluxe rounder pack"}; 

    public String[] getActivities(){
        return this.availableActivities;
    }

    public String[] getAvailableTours(){
        return this.availableTours;
    }

    public void setPerson(Person p){
        this.currentPerson = p;
    }

    public void setHours(double hours){
        this.currentRentTime = hours;
    }

    public double getCurrentRentTime(){
        return this.currentRentTime;
    }

    public void reset(){
        currentPerson = null;
        currentRentTime = DEFAULT_RENT_TIME;
    }

    // Factory Method
    public abstract PlayfulPet createPlayfulPet();
}

// Factory Methodの実装
class PlayfulCatAssistant extends PlayfulPetAssistant{
    public PlayfulPet createPlayfulPet(){
        return new Cat(RandomWrapper.getRanDouble(0.15, 0.40), RandomWrapper.getRanDouble(5.0, 20.0));
    }
}

class FairyWorld{
    public void rentPet(PlayfulPetAssistant assistant, Person p){
        System.out.println("Thank you for your pet rental!");
        System.out.println(p.getName() + " rents a " + assistant.createPlayfulPet().getPetName() + " for " + assistant.currentRentTime + " hrs. ");
    }
}

class Main{
    public static void main(String[] args){
        FairyWorld fairyWorld = new FairyWorld();
        Person alex = new Person(1.89, 84, "Alex", "James", 21);
        fairyWorld.rentPet (new PlayfulCatAssistant(), alex);

        // System.out.println(alex);
    }
}
