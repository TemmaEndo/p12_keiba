/**
 * EditDeletionJockey
 * 騎手要素の削除に責任を持つクラス
 * @author Shota Kitagawa
 */

 import java.sql.*;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Scanner;

public class EditDeletionJockey extends EditDeletion {
    public String jockeyName;
    private int selectedJockeyID;
    private String selectedJockeyDetail;

    public EditDeletionJockey(String jockeyName) {
        this.jockeyName = jockeyName;
    }

    @Override
    public String getSQLtemplate() {
        return "DELETE FROM jockey WHERE ID=?";
    }

    @Override
    public void setQuery(PreparedStatement st) throws SQLException {
        st.setInt(1, selectedJockeyID); // 選択したレースのIDを使用
    }

    // runテーブルの jockeyID を NULL にするメソッド
    @Override
    public void nullify(Connection conn) throws SQLException {
        String updateSQL1 = "UPDATE run SET jockeyID = 0 WHERE jockeyID = ?";
        try(PreparedStatement st1 = conn.prepareStatement(updateSQL1)){
            st1.setInt(1, selectedJockeyID);
            int rowsAffected1 = st1.executeUpdate();
            System.out.println("runテーブルを" + rowsAffected1 + "列更新しました");
            st1.close();
        }
    }

    // jockeyテーブルから該当するレコードを表示するメソッド
    @Override
    public String showDetails(Connection conn) throws SQLException {
        String querySQL = "SELECT * FROM jockey WHERE name = ?";
        try(PreparedStatement st = conn.prepareStatement(querySQL)){
            st.setString(1, jockeyName);
            try(ResultSet rs = st.executeQuery()){
                List<Integer> jockeyIDs = new ArrayList<>();
                List<String> jockeys = new ArrayList<>();
                System.out.println("選択した騎手の詳細:");
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    String name = rs.getString("name");
                    String jockeyDetail = "ID: " + id + ", 名前: " + name;
                    jockeyIDs.add(id);
                    jockeys.add(jockeyDetail);
                    System.out.println(jockeys.size() + ":  " + jockeyDetail);
                }
         
                rs.close();
                st.close();
        
                if (jockeys.isEmpty()) {
                    System.out.println(jockeyName + "という騎手は見つかりませんでした");
                    return null;
                }
        
                if (jockeys.size() == 1) {
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("これでよろしいですか？ (y/n) : ");
                    String answer = scanner.nextLine();
                    scanner.close();
                    if (answer.equals("y")) {
                        selectedJockeyID = jockeyIDs.get(0);
                        return String.valueOf(selectedJockeyID);
                    } else {
                        System.out.println("削除をキャンセルしました");
                        return null;
                    }
                    
                } else {
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("削除する騎手を選んでください (1-" + jockeys.size() + "): ");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // consume the newline
                    scanner.close();
                    if (choice < 1 || choice > jockeys.size()) {
                        System.out.println("無効な選択です。削除できませんでした。");
                        return null;
                    } else {
                        selectedJockeyDetail = jockeys.get(choice - 1);
                        System.out.println(choice + ":  " + selectedJockeyDetail);
                        System.out.print("これでよろしいですか？ (y/n) : ");
                        String answer = scanner.nextLine();
                        scanner.close();
                        if (answer.equals("y")) {
                            selectedJockeyID = jockeyIDs.get(choice - 1);
                            return String.valueOf(selectedJockeyID);
                        } else {
                            System.out.println("削除はキャンセルされました");
                            return null;
                        }
                    }
                }
            }
        }
    }
}
