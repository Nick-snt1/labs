package src.elements;

import java.util.stream.Stream;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Objects;
import java.util.UUID;

/**
    An object of this class describes a man riding somewhere with some weapon in a not very good mood
    Class used to fulfil the collection, with which programme gonna operate.
    Implements interface Comparable, to fulfil objects to the collection in a specified order
    @see Comparable
*/
public class HumanBeing implements Comparable<HumanBeing> {
    /** Unic id, must be greater that zero */
    private long id;
    /** Name of the human, must not be null or empty*/
    private String name;
    /** Current position of the human, must not be null */
    private Coordinates coordinates;
    /** Humans creationDate such as 2007-12-03T10:15:30+01:00 Europe/Paris, must not be null */
    private java.time.ZonedDateTime creationDate;
    /** Tells, whether this human a real hero, must not be null */
    private boolean realHero;
    /** Tells, whether this human has a toothpick, can be null */
    private Optional<Boolean> hasToothpick;
    /** Humans speed */
    private int impactSpeed;
    /** Name of soundtrack human listening to, must not be null*/
    private String soundtrackName;
    /** Type of weapon human carry, must not be null*/
    private WeaponType weaponType;
    /** Type of human's mood, can be null*/
    private Optional<Mood> mood;
    /** Human's car, can be null*/
    private Optional<Car> car;

    /**
        Creates new HumanBeing object.
        <p>
        This constructor uses to create objects via user's input - standard input or script.
        Field id and creationDate creates automatically
        @param name human's name (not null)
        @param coordinates human's current position (not null)
        @param realHero if human a real hero
        @param hasToothpick if human has a toothpick
        @param impactSpeed human's current speed
        @param soundtrackName name of track, human listens to (not null)
        @param weaponType type of weapon, which human carry on (not null)
        @param mood current state of human's mind
        @param car current human's car
        @see Coordinates
        @see WeaponType
        @see Mood
        @see Car
    */
    public HumanBeing(String name, Coordinates coordinates, boolean realHero, Boolean hasToothpick, int impactSpeed,
                      String soundtrackName, WeaponType weaponType, Mood mood, Car car) {
        this(id(), name, coordinates, ZonedDateTime.now(), realHero,
            hasToothpick, impactSpeed, soundtrackName, weaponType, mood, car );
    }

    /**
        Creates new HumanBeing object.
        <p>
        This constructor uses to create objects via xml file.
        @param id human's unic id, must be > 0
        @param name human's name (not null)
        @param coordinates human's current position (not null)
        @param creationDate object creation date
        @param realHero if human a real hero
        @param hasToothpick if human has a toothpick
        @param impactSpeed human's current speed
        @param soundtrackName name of track, human listens to (not null)
        @param weaponType type of weapon, which human carry on (not null)
        @param mood current state of human's mind
        @param car current human's car
        @see Coordinates
        @see ZonedDateTime
        @see WeaponType
        @see Mood
        @see Car
    */
    public HumanBeing(long id, String name, Coordinates coordinates, ZonedDateTime creationDate, boolean realHero,
                     Boolean hasToothpick, int impactSpeed, String soundtrackName, WeaponType weaponType,
                     Mood mood, Car car) {
        this.id = id;
        this.name = Objects.requireNonNull(name);
        this.coordinates = Objects.requireNonNull(coordinates);
        this.creationDate = Objects.requireNonNull(creationDate);
        this.realHero = realHero;
        this.hasToothpick = Optional.ofNullable(hasToothpick);
        this.impactSpeed = impactSpeed;
        this.soundtrackName = Objects.requireNonNull(soundtrackName);
        this.weaponType = Objects.requireNonNull(weaponType);
        this.mood = Optional.ofNullable(mood);
        this.car = Optional.ofNullable(car);
    }

    /**
        Creates unic positiv id
        @return humas id
        @see UUID
    */
    private static long id() {
        long cur = UUID.randomUUID().getMostSignificantBits();
        return cur >= 0 ? cur*2+1 : -cur*2;
    }

    /**
        Gets value of the field id
        @return human's id
    */
    public long getId() { return id; }

    /**
        Gets value of the field name
        @return human's name
    */
    public String getName() { return name; }

    /**
        Gets value of the field coordinates
        @return human's current position
        @see Coordinates
    */
    public Coordinates getCoordinates() { return coordinates; }

    /**
        Gets value of the field creationDate
        @return object's creation date
    */
    public java.time.ZonedDateTime getCreationDate() { return creationDate; }

    /**
        Gets value of the field realHero
        @return if human a real hero
    */
    public boolean isRealHero() { return realHero; }

    /**
        Gets value of the field hasToothpick
        @return if human has a toothpick (can be null)
        @see Optional
    */
    public Optional<Boolean> hasToothpick() { return hasToothpick; }

    /**
        Gets value of the field impactSpeed
        @return human's impact speed
    */
    public int getImpactSpeed() { return impactSpeed; }

    /**
        Gets value of the field soundtrackName
        @return human's soundtrack name
    */
    public String getSoundtrackName() { return soundtrackName; }

    /**
        Gets value of the field weaponType
        @return human's weapon type
        @see WeaponType
    */
    public WeaponType getWeaponType() { return weaponType; }

    /**
        Gets value of the field mood
        @return human's mood (can be null)
        @see Mood
    */
    public Optional<Mood> getMood() { return mood; }

    /**
        Gets value of the field car
        @return human's car (can be null)
        @see Car
    */
    public Optional<Car> getCar() { return car; }

    @Override
    public String toString() {
        return "HumanBeing [id=" + id
                + ", name=" + name
                + ", coordinates=" + coordinates.toString()
                + ", creationDate=" + creationDate.toString()
                + ", realHero=" + realHero
                + ", hasToothpick=" + hasToothpick.toString()
                + ", impactSpeed=" + impactSpeed
                + ", soundtrackName=" + soundtrackName
                + ", weaponType=" + weaponType
                + ", mood=" + mood.toString()
                + ", car=" + car.toString() + "]";

    }

    @Override
    public int hashCode() {
        int result = 7;
        result = result * 19 + (int) (id - (id >>> 32));
        result = result * 19 + name.hashCode();
        result = result * 19 + creationDate.hashCode();
        result = result * 19 + coordinates.hashCode();
        result = result * 19 + hasToothpick.hashCode();
        result = result * 19 + (realHero ? 1 : 0);
        result = result * 19 + impactSpeed;
        result = result * 19 + soundtrackName.hashCode();
        result = result * 19 + weaponType.hashCode();
        result = result * 19 + mood.hashCode();
        result = result * 19 + car.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof HumanBeing) {
            HumanBeing other = (HumanBeing) o;
            return Stream.of(
                id == other.getId(), name.equals(other.getName()), coordinates.equals(other.getCoordinates()),
                creationDate.equals(other.getCreationDate()), realHero == other.isRealHero(),
                hasToothpick.equals(other.hasToothpick()), impactSpeed == other.getImpactSpeed(),
                soundtrackName.equals(other.getSoundtrackName()), weaponType.equals(other.getWeaponType()),
                mood.equals(other.getMood()), car.equals(other.getCar())).allMatch(x -> x == true);
        }
        return false;
    }

    @Override
    public int compareTo(HumanBeing human) {
        //if (this.equals(human)) return 0;
        int comparableVal1 = coordinates.getX() +
                             coordinates.getY().intValue() +
                             (realHero ? 10 : 0) +
                             impactSpeed;
        int comparableVal2 = human.getCoordinates().getX() +
                             human.getCoordinates().getY().intValue() +
                             (human.isRealHero() ? 10 : 0) +
                             human.getImpactSpeed();

        return comparableVal1 > comparableVal2 ? 1 :
               comparableVal1 < comparableVal2 ? -1 : 0;

        //return comparableVal1 == comparableVal2
        //    ? (countSum(this.name) > countSum(human.getName()) ? 1 : -1)
        //    : (comparableVal1 > comparableVal2 ? 1 : -1);
    }
 }
