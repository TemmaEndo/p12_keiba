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

		Add a21 = new AddHorse();
		Add a22 = new AddRace();
		Add a23 = new AddTrainer();
		Add a24 = new EditAddretired();
		Add a25 = new AddJockey();

		Delete d301 = new DeleteHorse();
		Delete d302 = new DeleteJockey();
		Delete d303 = new DeleteOwner();
		Delete d304 = new DeleteRace();
		Delete d305 = new DeleteTrainer();

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
								s111.DoSearch();
								break;
							case "2":
								s112.DoSearch();
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
								s121.DoSearch();
								break;
							case "2":
								s122.DoSearch();
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
							a21.DoAdd();
							break;
						case "2":
							a22.DoAdd();
							break;
						case "3":
							a23.DoAdd();
							break;
						case "4":
							a24.DoAdd();
							break;
						case "5":
							a25.DoAdd();
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
					        d301.DoDel();
						break;
					case "02":
						d302.DoDel();
						break;
					case "03":
					        d303.DoDel();
						break;
					case "04":
						d304.DoDel();
						break;
					case "05":
						d305.DoDel();
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
						c401.DoChange();
						break;
					case "02":
						c402.DoChange();
						break;
					case "03":
						c403.DoChange();
						break;
					case "04":
						c404.DoChange();
						break;
					case "05":
						c405.DoChange();
						break;
					case "06":
						c406.DoChange();
						break;
					case "07":
						c407.DoChange();
						break;
					case "08":
						c408.DoChange();
						break;
					case "09":
						c409.DoChange();
						break;
					case "10":
						c410.DoChange();
						break;
					case "11":
						c411.DoChange();
						break;
					case "12":
						c412.DoChange();
						break;
					
					default:
						System.out.println("Incorrect number");
				}
			default:
				System.out.println("Incorrect number");
		}
	}
}
