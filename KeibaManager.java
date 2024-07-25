/**
 * KeibaManager
 * 競馬管理アプリケーションの本体となるクラス
 * @author Temma Endo
 */


 import java.util.*;
 import java.sql.*;

 public class KeibaManager {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
    
		Search s111 = new SearchRaceName();
		Search s112 = new SearchRaceYear();

		Search s121 = new SearchHorseAge();
		Search s122 = new SearchHorseName();

		Search s13 = new SearchJockey();

		EditAdd a21 = new EditAddHorse();
		EditAdd a22 = new EditAddRace();
		EditAdd a23 = new EditAddTrainer();
		EditAdd a24 = new EditAddretired();
		EditAdd a25 = new EditAddJockey();

		EditDeletion d301 = new EditDeletionHorse(horseName);
		EditDeletion d302 = new EditDeletionJockey(jockeyName);
		EditDeletion d303 = new EditDeletionOwner(ownerName);
		EditDeletion d304 = new EditDeletionRace(raceName);
		EditDeletion d305 = new EditDeletionTrainer(trainerName);

		Change c401 = new ChangeAffiation();
		Change c402 = new ChangeFamily();
		Change c403 = new ChangeHeld();
		Change c404 = new ChangeHorse();
		Change c405 = new ChangeJockey();
		Change c406 = new ChangeOwner();
		Change c407 = new ChangeOwnership();
		Change c408 = new ChangePartification();
		Change c409 = new ChangeRace();
		Change c410 = new ChangeRetiredHorse();
		Change c411 = new ChangeTrainer();
		Change c412 = new ChangeTraining();


		System.out.println("=== 競馬データベース ===");
		System.out.println("メニューを選択してください: ");
		System.out.println("1: 検索");
		System.out.println("2: 追加");
		System.out.println("3: 削除");
		System.out.println("4: 変更");
		System.out.println("番号を入力してください: ");

		String lineSorE = scanner.nextLine();
		switch(lineSorE) {
			case "1":
				System.out.println("検索する項目を選択してください: ");
				System.out.println("1: レース");
				System.out.println("2: 馬");
				System.out.println("3: 騎手");
				System.out.println("番号を入力してください: ");
				
				String lineS = scanner.nextLine();
				switch(lineS){
					case "1":
						System.out.println("使用する項目を選択してください: ");
						System.out.println("1: 名前");
						System.out.println("2: 開催年");
						System.out.println("番号を入力してください: ");

						String lineSR = scanner.nextLine();
						
						switch(lineSR){
							case "1":
								s111.BDSearch();
								break;
							case "2":
								s112.BDSearch();
								break;
							default:
								System.out.println("Incorrect number");
						}
					case "2":
						System.out.println("使用する項目を選択してください: ");
						System.out.println("1: 年齢");
						System.out.println("2: 名前");
						System.out.println("番号を入力してください: ");

						String lineSH = scanner.nextLine();
						
						switch(lineSH){
							case "1":
								s121.BDSearch();
								break;
							case "2":
								s122.BDSearch();
								break;
							default:
								System.out.println("Incorrect number");
						}
					case "3":
						s13.DBSearch();
						break;
					default:
						System.out.println("Incorrect number");
					}
			case "2":
					System.out.println("追加するデータの項目を選択してください: ");
					System.out.println("1: 馬");
					System.out.println("2: レース");
					System.out.println("3: 調教師");
					System.out.println("4: 引退馬");
					System.out.println("5: 騎手");
					System.out.println("番号を入力してください: ");

					String lineAdd = scanner.nextLine();
					switch(lineAdd) {
						case "1":
							a21.DBAdd();
							break;
						case "2":
							a22.DBAdd();
							break;
						case "3":
							a23.DBAdd();
							break;
						case "4":
							a24.DBAdd();
							break;
						case "5":
							a25.DBAdd();
							break;
						default:
							System.out.println("Incorrect number");
					}
			case "3":
				System.out.println("削除するデータの項目を選択してください: ");
				System.out.println("01: 馬");
				System.out.println("02: 騎手");
				System.out.println("03: 馬主");
				System.out.println("04: レース");
				System.out.println("05: 調教師");
				System.out.println("番号を入力してください: ");
				
				String linedel = scanner.nextLine();
				switch(linedel) {
					case "01":
						Scanner scanner = new Scanner(System.in);
					        System.out.println("削除したい馬の名前を入力してください");
					        String horseName = scanner.nextLine();
					        d301.DBDel();
					        scanner.close();
						break;
					case "02":
						Scanner scanner = new Scanner(System.in);
					        System.out.println("削除したい騎手の名前を入力してください");
					        String jockeyName = scanner.nextLine();
							d302.DBDel();
					        scanner.close();
						break;
					case "03":
						Scanner scanner = new Scanner(System.in);
					        System.out.println("削除したい馬主の名前を入力してください");
					        String ownerName = scanner.nextLine();
					        d303.DBDel();
					        scanner.close();
						break;
					case "04":
						Scanner scanner = new Scanner(System.in);
					        System.out.println("削除したいレースの名前を入力してください");
					        String raceName = scanner.nextLine();
							d304.DBDel();
					        scanner.close();
						break;
					case "05":
						Scanner scanner = new Scanner(System.in);
					        System.out.println("削除したい調教師の名前を入力してください");
					        String trainerName = scanner.nextLine();
							d305.DBDel();
					        scanner.close();
						break;
					
					default:
						System.out.println("Incorrect number");
				}
			case "4":
				System.out.println("変更するデータの項目を選択してください: ");
				System.out.println("01: 所属");
				System.out.println("02: 親");
				System.out.println("03: 開催");
				System.out.println("04: 馬");
				System.out.println("05: 騎手");
				System.out.println("06: 馬主");
				System.out.println("07: 所有");
				System.out.println("08: 出走");
				System.out.println("09: レース");
				System.out.println("10: 引退馬");
				System.out.println("11: 調教師");
				System.out.println("12: 調教");
				System.out.println("番号を入力してください: ");
				
				String linecha = scanner.nextLine();
				switch(linecha) {
					case "01":
						c401.DBChange();
						break;
					case "02":
						c402.DBChange();
						break;
					case "03":
						c403.DBChange();
						break;
					case "04":
						c404.DBChange();
						break;
					case "05":
						c405.DBChange();
						break;
					case "06":
						c406.DBChange();
						break;
					case "07":
						c407.DBChange();
						break;
					case "08":
						c408.DBChange();
						break;
					case "09":
						c409.DBChange();
						break;
					case "10":
						c410.DBChange();
						break;
					case "11":
						c411.DBChange();
						break;
					case "12":
						c412.DBChange();
						break;
					
					default:
						System.out.println("Incorrect number");
				}
			default:
				System.out.println("Incorrect number");
		}
	}
}
