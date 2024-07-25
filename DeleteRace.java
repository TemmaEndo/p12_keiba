/**
 * EditDeletionRace
 * レース要素の削除に責任を持つクラス
 * @author Shota Kitagawa
 */

 import java.sql.*;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Scanner;

public class DeleteRace extends Delete {
    public String raceName;
    private String selectedRaceID;
    private String selectedRaceDetail;

    public DeleteRace() {
    }

    @Override
    public void enterName() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("削除したいレースの名前を入力してください");
        raceName = scanner.nextLine();
    }
 
    @Override
    public String getSQLtemplate() {
        return "DELETE FROM race WHERE ID=?";
    }

    @Override
    public void setQuery(PreparedStatement st) throws SQLException {
        if (selectedRaceID != null) {
            st.setString(1, selectedRaceID); // 選択したレースのIDを使用
        } else {
            throw new SQLException("選択されたレースのIDが設定されていません");
        }
    }

    // run,heldテーブルの raceID を NULL にするメソッド
    @Override
    public void nullify(Connection conn) throws SQLException {
        if (selectedRaceID != null) {
            String updateSQL1 = "UPDATE run SET raceID = '' WHERE raceID = ?";
            String updateSQL2 = "UPDATE held SET raceID = '' WHERE raceID = ?";
            PreparedStatement st1 = conn.prepareStatement(updateSQL1);
            PreparedStatement st2 = conn.prepareStatement(updateSQL2);
            st1.setString(1, selectedRaceID); // 選択したレースのIDを使用
            st2.setString(1, selectedRaceID); // 選択したレースのIDを使用
            int rowsAffected1 = st1.executeUpdate();
            int rowsAffected2 = st2.executeUpdate();
            System.out.println("runテーブルを" + rowsAffected1 + "列更新しました");
            System.out.println("heldテーブルを" + rowsAffected2 + "列更新しました");
            st1.close();
            st2.close();
        } else {
            System.out.println("選択されたレースのIDが設定されていません");
        }
    }

    // raceテーブルから該当するレコードを表示するメソッド
    @Override
    public String showDetails(Connection conn) throws SQLException {
        String querySQL = "SELECT * FROM race WHERE name = ?";
        PreparedStatement st = conn.prepareStatement(querySQL);
        st.setString(1, raceName);
        ResultSet rs = st.executeQuery();   
 
        List<String> raceIDs = new ArrayList<>();
        List<String> races = new ArrayList<>();
        System.out.println("選択したレースの詳細:");
        while (rs.next()) {
            String id = rs.getString("ID");
            int year = rs.getInt("year");
            Date date = rs.getDate("date");
            int raceNum = rs.getInt("raceNumber");
            String name = rs.getString("name");
            String baba = rs.getString("baba");
            String raceRank = rs.getString("raceRank");
            int number = rs.getInt("number");
            int distance = rs.getInt("distance");

            // 日付の処理
            String dateStr = (date != null) ? date.toString() : "0000-00-00";
            String raceDetail = "ID: " + id + ", 開催年: " + year + ", 年月日: " + dateStr + ", レースNo: " + raceNum + ", 名前: " + name + ", 馬場: " + baba + ", レースランク: " + raceRank + ", 頭数: " + number + ", 距離: " + distance;
            raceIDs.add(id);
            races.add(raceDetail);
            System.out.println(raceIDs.size() + ":  " + raceDetail);
        }
         
        rs.close();
        st.close();
 
        if (raceIDs.isEmpty()) {
            System.out.println(raceName + "というレースは見つかりませんでした");
            return null;
        }
 
        if (raceIDs.size() == 1) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("これでよろしいですか？ (y/n) : ");
            String answer = scanner.nextLine();

            if (answer.equals("y")) {
                selectedRaceID = raceIDs.get(0); // 選択したレースのIDを保存
                return selectedRaceID;
            } else {
                System.out.println("削除はキャンセルされました");
                return null;
            }

        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.print("削除するレースを選んでください: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline

            if (choice < 1 || choice > raceIDs.size()) {
                System.out.println("無効な選択です。削除できませんでした。");
                scanner.close();
                return null;
            } else {
                selectedRaceDetail = races.get(choice - 1);
                System.out.println(choice + ":  " + selectedRaceDetail);
                System.out.print("これでよろしいですか？ (y/n) : ");
                String answer = scanner.nextLine();
                scanner.close();

                if (answer.equals("y")) {
                    selectedRaceID = raceIDs.get(choice - 1); // 選択したレースのIDを保存
                    return selectedRaceID;
                } else {
                    System.out.println("削除はキャンセルされました");
                    return null;
                }
            }
        }
    }
}
