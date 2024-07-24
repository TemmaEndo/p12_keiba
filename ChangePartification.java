/**
 * EditChangePartification
 * 出走要素の変更に責任を持つクラス
 * @author Kaito Sugita
 */

 import java.util.*;
 import java.sql.*;

 public class ChangePartification extends Change{
    int RaceID,JockeyID,Odds,RaceRank,HorseAge,BracketNumber;
    String HorseName,HorseLeg;
    double RaceTime,HorseWeight;

    ChangePartification(){

    }
 }