import java.util.*;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        ChangeHorse horse = new ChangeHorse();
        ChangeHeld held = new ChangeHeld();
        ChangeJockey jockey = new ChangeJockey();
        ChangeRace race = new ChangeRace();
        //horse.DoChange();
        //held.DoChange();
        jockey.DoChange();
        //race.DoChange();
    }
}