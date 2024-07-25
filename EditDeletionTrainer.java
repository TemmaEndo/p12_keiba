/**
 * EditDeletionTrainer
 * 調教師要素の削除に責任を持つクラス
 * @author Shota Kitagawa
 */

 import java.sql.*;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Scanner;

public class EditDeletionTrainer extends EditDeletion {
    public String trainerName;
    private int selectedTrainerID;
    private String selectedTrainerDetail;


    public EditDeletionTrainer(String trainerName) {
        this.trainerName = trainerName;
    }

    @Override
    public String getSQLtemplate() {
        return "DELETE FROM trainer WHERE ID=?";
    }

    @Override
    public void setQuery(PreparedStatement st) throws SQLException {
        st.setInt(1, selectedTrainerID); // 選択したレースのIDを使用
    }

    // training,affiliationテーブルの trainerID を NULL にするメソッド
    @Override
    public void nullify(Connection conn) throws SQLException {
        String updateSQL1 = "UPDATE training SET trainerID = 0 WHERE trainerID = ?";
        String updateSQL2 = "UPDATE affiliation SET trainerID = 0 WHERE trainerID = ?";
        try(PreparedStatement st1 = conn.prepareStatement(updateSQL1);
            PreparedStatement st2 = conn.prepareStatement(updateSQL2)) {
            st1.setInt(1, selectedTrainerID);
            st2.setInt(1, selectedTrainerID);
            int rowsAffected1 = st1.executeUpdate();
            int rowsAffected2 = st2.executeUpdate();
            System.out.println("trainingテーブルを" + rowsAffected1 + "列更新しました");
            System.out.println("affiliationテーブルを" + rowsAffected2 + "列更新しました");
            st1.close();
            st2.close();
        }
    }

    // trainerテーブルから該当するレコードを表示するメソッド
    @Override
    public String showDetails(Connection conn) throws SQLException {
        String querySQL = "SELECT * FROM trainer WHERE name = ?";
        try(PreparedStatement st = conn.prepareStatement(querySQL)) {
            st.setString(1, trainerName);
            try(ResultSet rs = st.executeQuery()) {
                List<Integer> trainerIDs = new ArrayList<>();
                List<String> trainers = new ArrayList<>();
                System.out.println("選択した調教師の詳細:");
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    String name = rs.getString("name");
                    String trainerDetail = "ID: " + id + ", 名前: " + name;
                    trainerIDs.add(id);
                    trainers.add(trainerDetail);
                    System.out.println(trainers.size() + ":  " + trainerDetail);
                }
         
                rs.close();
                st.close();
 
                if (trainers.isEmpty()) {
                    System.out.println(trainerName + "という調教師は見つかりませんでした");
                    return null;
                }
        
                if (trainers.size() == 1) {
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("これでよろしいですか？ (y/n) : ");
                    String answer = scanner.nextLine();
                    scanner.close();
                    if (answer.equals("y")) {
                        selectedTrainerID = trainerIDs.get(0);
                        return String.valueOf(selectedTrainerID);
                    } else {
                        System.out.println("削除をキャンセルしました");
                        return null;
                    }
                    
                } else {
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("削除する調教師を選んでください (1-" + trainers.size() + "): ");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // consume the newline
                    scanner.close();
                    if (choice < 1 || choice > trainers.size()) {
                        System.out.println("無効な選択です。削除できませんでした。");
                        return null;
                    } else {
                        selectedTrainerDetail = trainers.get(choice - 1);
                        System.out.println(choice + ":  " + selectedTrainerDetail);
                        System.out.print("これでよろしいですか？ (y/n) : ");
                        String answer = scanner.nextLine();
                        scanner.close();
                        if (answer.equals("y")) {
                            selectedTrainerID = trainerIDs.get(choice - 1);
                            return String.valueOf(selectedTrainerID);
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
