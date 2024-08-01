import java.util.*;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        ChangeHorse horse = new ChangeHorse();
        ChangeJockey jockey = new ChangeJockey();
        ChangeRace race = new ChangeRace();
        ChangeRetiredHorse Rhorse = new ChangeRetiredHorse();
        ChangeTrainer trainer = new ChangeTrainer();
        //horse.DoChange();
        jockey.DoChange();
        //race.DoChange();
        //Rhorse.DoChange();
        //trainer.DoChange();
    }
}