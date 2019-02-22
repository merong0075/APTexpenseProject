package Controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Model.AdminCost;
import Model.Employee;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class AdminCostController implements Initializable {
	@FXML	public TableView<AdminCost> costTableView;
	@FXML	private Button costBtnSearch;
	@FXML	private Button costBtnadd;
	@FXML	private Button costBtnEdit;
	@FXML	private Button costBtnClose;
	@FXML	private Button costBtnDel;
	@FXML	private Button costBtnChart;
	@FXML	private Button inputcost;
	@FXML	private Button costBtnClear;
	@FXML	private TextField costTextDong;
	@FXML	private TextField costTextHo;
	@FXML	private TextField costTextelec;
	@FXML	private TextField costTextWater;
	@FXML	private TextField costTextGas;
	@FXML	private TextField costTextHeat;
	@FXML	private TextField costTextJoint;
	@FXML	private TextField costTextTotal;
	@FXML	private TextField textSearch;
	@FXML	private ComboBox<String> costCmbRecive;
	@FXML	private DatePicker costDateRecive;

	public static String info=null;
	public static String dongho;
	
	public Stage admincoststage;
	public AdminCost selectCost;
	public int selectCostIndex;

	ObservableList<AdminCost> costListData = FXCollections.observableArrayList();
	ObservableList<String> listRecive = FXCollections.observableArrayList();

	ArrayList<AdminCost> dbArrayList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listRecive.addAll("완납", "미납");
		costCmbRecive.setItems(listRecive);

		// 테이블뷰 셋팅
		setcostTableView();
		// 찾기 기능
		costBtnSearch.setOnAction((e) -> {	handlerSearchAction();});
		// 삭제 기능
		costBtnDel.setOnAction((e) -> {	handlerDelAction();});
		// 한번 클릭시 텍스트에 값이 들어간다.
		costTableView.setOnMouseClicked((e) -> {handlecostTableviewAction(e);});
		// 수정버튼 클릭시 값이 수정된다.
		costBtnEdit.setOnAction((e) -> {	handleEditAction();});
		// 불러오기
		inputcost.setOnAction((e) -> {	handlerinputcostAction();});
		// 추가 버튼클릭시 화면전환
		costBtnadd.setOnAction((e) -> {	handlercostBtnaddAction();});
		// 초기화 버튼
		costBtnClear.setOnAction((e) -> {handleBtnClearAction();});
		// 닫기 버튼클릭시 화면 종료
		costBtnClose.setOnAction((e) -> {handlercostBtnCloseAction();});
		//관리비 숫자로 제한
		setCostLimit();
		
		
		

	}


	// 테이블 셋팅
	private void setcostTableView() {
		TableColumn tcdong = costTableView.getColumns().get(0);
		tcdong.setCellValueFactory(new PropertyValueFactory<>("dong"));
		tcdong.setStyle("-fx-alignment: CENTER");

		TableColumn tcho = costTableView.getColumns().get(1);
		tcho.setCellValueFactory(new PropertyValueFactory<>("ho"));
		tcho.setStyle("-fx-alignment: CENTER");

		TableColumn tcelectric = costTableView.getColumns().get(2);
		tcelectric.setCellValueFactory(new PropertyValueFactory<>("electric"));
		tcelectric.setStyle("-fx-alignment: CENTER");

		TableColumn tcwater = costTableView.getColumns().get(3);
		tcwater.setCellValueFactory(new PropertyValueFactory<>("water"));
		tcwater.setStyle("-fx-alignment: CENTER");

		TableColumn tcgas = costTableView.getColumns().get(4);
		tcgas.setCellValueFactory(new PropertyValueFactory<>("gas"));
		tcgas.setStyle("-fx-alignment: CENTER");

		TableColumn tcheat = costTableView.getColumns().get(5);
		tcheat.setCellValueFactory(new PropertyValueFactory<>("heat"));
		tcheat.setStyle("-fx-alignment: CENTER");

		TableColumn tcjoint = costTableView.getColumns().get(6);
		tcjoint.setCellValueFactory(new PropertyValueFactory<>("joint"));
		tcjoint.setStyle("-fx-alignment: CENTER");

		TableColumn tcreceive = costTableView.getColumns().get(7);
		tcreceive.setCellValueFactory(new PropertyValueFactory<>("receive"));
		tcreceive.setStyle("-fx-alignment: CENTER");

		TableColumn tctatalCost = costTableView.getColumns().get(8);
		tctatalCost.setCellValueFactory(new PropertyValueFactory<>("tatalCost"));
		tctatalCost.setStyle("-fx-alignment: CENTER");

		TableColumn tcreciveDay = costTableView.getColumns().get(9);
		tcreciveDay.setCellValueFactory(new PropertyValueFactory<>("reciveDay"));
		tcreciveDay.setStyle("-fx-alignment: CENTER");

		dbArrayList = AdminCostDAO.getAdminCostTotalData();
		for (AdminCost admincost : dbArrayList) {
			costListData.add(admincost);
		}
		costTableView.setItems(costListData);

	}

	// 한번 클릭시 텍스트에 값이 들어간다.
	private void handlecostTableviewAction(MouseEvent e) {
		selectCost = costTableView.getSelectionModel().getSelectedItem();
		selectCostIndex = costTableView.getSelectionModel().getSelectedIndex();
		costTextDong.setDisable(true);
		costTextHo.setDisable(true);

		if (e.getClickCount() == 1) {
			try {

				costTextDong.setText(selectCost.getDong());
				costTextHo.setText(selectCost.getHo());
				costTextelec.setText(selectCost.getElectric());
				costTextGas.setText(selectCost.getGas());
				costTextHeat.setText(selectCost.getHeat());
				costTextJoint.setText(selectCost.getJoint());
				costTextWater.setText(selectCost.getWater());
				costCmbRecive.getSelectionModel().select(selectCost.getReceive());
				costTextTotal.setText(selectCost.getTatalCost());
				costDateRecive.setValue(LocalDate.parse(selectCost.getReciveDay()));
			} catch (Exception e1) {
				callAlert("빈테이블 클릭 금지: 빈테이블을 클릭하지마세요");
			}
		} else if (e.getClickCount() == 2) {
			try {
				Stage bcStage = new Stage(StageStyle.UTILITY);
				bcStage.initModality(Modality.WINDOW_MODAL);
				bcStage.initOwner(admincoststage); 
				bcStage.setTitle("BarChart");

				FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/avgchart.fxml"));
				Parent root;

				root = loader.load();
				PieChart pieChart = (PieChart) root.lookup("#pieChart");
				Button charBtnclose = (Button) root.lookup("#charBtnclose");

				ObservableList costList = FXCollections.observableArrayList();
				costList.add(new PieChart.Data("전기세", Integer.parseInt(selectCost.getElectric())));
				costList.add(new PieChart.Data("가스비", Integer.parseInt(selectCost.getGas())));
				costList.add(new PieChart.Data("난방비", Integer.parseInt(selectCost.getHeat())));
				costList.add(new PieChart.Data("수도세", Integer.parseInt(selectCost.getWater())));

				pieChart.setData(costList);

				charBtnclose.setOnAction(e1 -> bcStage.close());

				Scene scene = new Scene(root);
				bcStage.setScene(scene);
				bcStage.show();

			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}

	}

	// 수정버튼 클릭시 값이 수정된다.
	private void handleEditAction() {
		String costtotal = "";

		costtotal = String.valueOf(Integer.parseInt(costTextelec.getText()) + Integer.parseInt(costTextGas.getText())
				+ Integer.parseInt(costTextHeat.getText()) + Integer.parseInt(costTextJoint.getText())
				+ Integer.parseInt(costTextWater.getText()));

		info = costTextDong.getText() + costTextHo.getText() + costDateRecive.getValue().toString();
		dongho = costTextDong.getText() + costTextHo.getText();
		AdminCost adminCost = new AdminCost(info, dongho, 
				costTextDong.getText(), 
				costTextHo.getText(),
				costTextelec.getText(), 
				costTextWater.getText(), 
				costTextGas.getText(), 
				costTextHeat.getText(),
				costTextJoint.getText(), 
				costCmbRecive.getSelectionModel().getSelectedItem(), 
				costtotal,
				costDateRecive.getValue().toString());
		int count = AdminCostDAO.updateAdminCostData(adminCost);
		if (count != 0) {
			costListData.remove(selectCostIndex);
			costListData.add(selectCostIndex, adminCost);
			int arrayin = dbArrayList.indexOf(selectCost);
			dbArrayList.set(arrayin, adminCost);

		}

		handleBtnClearAction();

	}

	// 삭제 기능
	private void handlerDelAction() {
		int count = AdminCostDAO.deleteAdminCostData(selectCost.getDong()+selectCost.getHo()+selectCost.getReciveDay());
		if (count != 0) {
			dbArrayList.clear();
			costListData.clear();
			dbArrayList=AdminCostDAO.getAdminCostTotalData();
			for(AdminCost ac:dbArrayList) {
				costListData  .add(ac);
			}
//			costListData.remove(selectCostIndex);
//			dbArrayList.remove(selectCost);
		} else {
			return;
		}

	}

	// 찾기 기능
	private void handlerSearchAction() {

		for (AdminCost adminCost : dbArrayList) {
			if (textSearch.getText().trim().equals(adminCost.getDong() + adminCost.getHo())) {
				costTableView.getSelectionModel().select(adminCost);
				costTableView.scrollTo(adminCost);

			}
		}

	}

	// 불러오기
	private void handlerinputcostAction() {
//		  FileChooser fileChooser=new FileChooser();
//	      fileChooser.getExtensionFilters().add(new ExtensionFilter("dd","*.xlsx"));
//
//	      File file=fileChooser.showSaveDialog(admincoststage); 
//	      
//	      try {
//	         FileOutputStream fos=new FileOutputStream(file);
//	         PrintWriter pw=new PrintWriter(fos,true);
//	         
//	         pw.print(costListData);
//	         pw.flush();
//	         System.out.println(costListData);
//	      } catch (FileNotFoundException e1) { }      
//	      if(file !=null) {
//	         System.out.println(file.getPath());
//	         
//	      }   
		
		
		try {
			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + "D:\\java\\cost.xlsx");
		} catch (IOException e) {

		}
	}

	// 추가 버튼클릭시 화면전환
	private void handlercostBtnaddAction() {

		if (costTextDong != null && costTextHo != null && costTextelec != null && costTextWater != null
				&& costTextGas != null && costTextHeat != null && costTextJoint != null) {
			String total = "";

			total = String.valueOf(Integer.parseInt(costTextelec.getText()) + Integer.parseInt(costTextWater.getText())
					+ Integer.parseInt(costTextGas.getText()) + Integer.parseInt(costTextHeat.getText())
					+ Integer.parseInt(costTextJoint.getText()));

			info = costTextDong.getText() + costTextHo.getText() + costDateRecive.getValue().toString();
			dongho = costTextDong.getText() + costTextHo.getText();
			AdminCost admincost = new AdminCost(info, 
					dongho, costTextDong.getText(), 
					costTextHo.getText(),
					costTextelec.getText(), 
					costTextWater.getText(), 
					costTextGas.getText(), 
					costTextHeat.getText(),
					costTextJoint.getText(), 
					costCmbRecive.getSelectionModel().getSelectedItem(), 
					total,
					costDateRecive.getValue().toString());

			costListData.add(admincost);
			int count = AdminCostDAO.insertAdminCostData(admincost);
			if (count != 0) {
				callAlert("저장 : 저장 완료");
		//		handleBtnClearAction();
			}
			for (AdminCost admincost1 : dbArrayList) {
				costListData.add(admincost1);
			} // for
			

		} else {
			callAlert("입력실패 : 빈칸이 있습니다. ");
		}
		costListData.clear();
		costTableView.setItems(costListData);
		dbArrayList = AdminCostDAO.getAdminCostTotalData();
		for (AdminCost admincost1 : dbArrayList) {
			costListData.add(admincost1);
	}
		handleBtnClearAction();
	}
	// 닫기 버튼클릭시 화면전환
	private void handlercostBtnCloseAction() {
		try {
			Stage adminliststage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/adlist.fxml"));
			Parent root = loader.load();

			AdminListController adminListController = loader.getController();
			adminListController.adminliststage = adminliststage;

			Scene scene = new Scene(root);
			adminliststage.setScene(scene);
			admincoststage.close();
			;
			adminliststage.show();// 새로운 창을 띄운다.
		} catch (IOException e1) {
		}

	}

	// 초기화 버튼클릭시 값초기화
	private void handleBtnClearAction() {

		costTextDong.clear();
		costTextHo.clear();
		costTextelec.clear();
		costTextWater.clear();
		costTextGas.clear();
		costTextHeat.clear();
		costTextTotal.clear();

		costTextDong.setDisable(false);
		costTextHo.setDisable(false);

	}
	
	//관리비 제한사항
	private void setCostLimit() {
		inputDecimalFormatResidentt(costTextelec);
		inputDecimalFormatResidentt(costTextHo);
		inputDecimalFormatResidentt(costTextDong);
		inputDecimalFormatResidentt(costTextWater);
		inputDecimalFormatResidentt(costTextGas);
		inputDecimalFormatResidentt(costTextHeat);
		inputDecimalFormatResidentt(costTextJoint);
		inputDecimalFormatResidentt(costTextGas);
		inputDecimalFormatResidentt(costTextTotal);
		inputDecimalFormatResidentt(textSearch);
		
	}
	
		
// 관리비 번호만 가능하도록 하는 함수
	private void inputDecimalFormatResidentt(TextField textField) {

		DecimalFormat format = new DecimalFormat("#,###");
		textField.setTextFormatter(new TextFormatter<>(event -> {
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(event.getControlNewText(), parsePosition);

			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()	|| event.getControlNewText().length() == 9 || event.getControlNewText()=="."
					||event.getControlNewText()=="-") {
				return null;
			} else {
				return event;
			}
		}));
		
	}



	
	
	// 기타 알림
	public static void callAlert(String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("알림창");
		alert.setHeaderText(contentText.substring(0, contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":") + 1));
		alert.showAndWait();
	}

}
