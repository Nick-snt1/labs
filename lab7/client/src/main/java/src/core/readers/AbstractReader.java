package src.core.readers;

import java.util.Scanner;

public abstract class AbstractReader {

    public abstract String[] getFirstLine();

    protected abstract String getName();

    protected abstract String getX();

    protected abstract String getY();

    protected abstract String getRealHero();

    protected abstract String getToothpick();

    protected abstract String getImpactSpeed();

    protected abstract String getSoundtrackName();

    protected abstract String getWeaponType();

    protected abstract String getMood();

    protected abstract String getCar();

    public String[] getHumanBeing() {
        return new String[]{ getName(), getX(), getY(), getRealHero(), getToothpick(),
            getImpactSpeed(), getSoundtrackName(), getWeaponType(), getMood(), getCar() };
    }
}
