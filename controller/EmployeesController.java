package Controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Model.Employee;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.FileChooser.ExtensionFilter;

public class EmployeesController implements Initializable {
	public Stage employeestage;
	@FXML	private TextField empTextSearch;
	@FXML	private Button empSearch;
	@FXML	private Button empAdd;
	@FXML	private Button empEdit;
	@FXML	private Button empClose;
	@FXML	private Button empDel;
	@FXML	private TableView<Employee> t2TableView;

	boolean flag = false;

	ArrayList<Employee> dbArrayList;

	// 값을 저장하는 리스트
	ObservableList<Employee> t2Listdata = FXCollections.observableArrayList();

	private Employee selectEmployee; // 선택한 employee값
	private int selectEmployeeindex; // 인덱스값
	public static String number;

	public static String employeeNumber = null;

	// 이미지
	private File selectFile = null;
	private File imageDir = new File("C:/images");
	private String fileName = "";
	Image image = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 테이블뷰 셋팅
		setEmployeeTableView();

		// 추가버튼 클릭시 팝업
		empAdd.setOnAction((e) -> {	handlerBtnAddAction();	});
		// 찾기버튼
		empSearch.setOnAction((e) -> {	handlerBtnSearchAction();});
		// 수정버튼
		empEdit.setOnAction((e) -> {	handlerBtnEditAction();});
		// 닫기버튼
		empClose.setOnAction((e) -> {handlerBtnCloseAction();});
		// 삭제버튼
		empDel.setOnMousePressed((e) -> {handlerBtnDelAction();});

	}

	// 테이블뷰 셋팅
	private void setEmployeeTableView() {
		TableColumn tcnumber = t2TableView.getColumns().get(0);
		tcnumber.setCellValueFactory(new PropertyValueFactory<>("number"));
		tcnumber.setStyle("-fx-alignment: CENTER");
		TableColumn tcname = t2TableView.getColumns().get(1);
		tcname.setCellValueFactory(new PropertyValueFactory<>("name"));
		tcname.setStyle("-fx-alignment: CENTER");
		TableColumn tcage = t2TableView.getColumns().get(2);
		tcage.setCellValueFactory(new PropertyValueFactory<>("age"));
		tcage.setStyle("-fx-alignment: CENTER");
		TableColumn tcdepartment = t2TableView.getColumns().get(3);
		tcdepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
		tcdepartment.setStyle("-fx-alignment: CENTER");
		TableColumn tcEmploymentday = t2TableView.getColumns().get(4);
		tcEmploymentday.setCellValueFactory(new PropertyValueFactory<>("Employmentday"));
		tcEmploymentday.setStyle("-fx-alignment: CENTER");
		TableColumn tcposition = t2TableView.getColumns().get(5);
		tcposition.setCellValueFactory(new PropertyValueFactory<>("position"));
		tcposition.setStyle("-fx-alignment: CENTER");
		TableColumn tcaddress = t2TableView.getColumns().get(6);
		tcaddress.setCellValueFactory(new PropertyValueFactory<>("address"));
		tcaddress.setStyle("-fx-alignment: CENTER");
		TableColumn tcPhone = t2TableView.getColumns().get(7);
		tcPhone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
		tcPhone.setStyle("-fx-alignment: CENTER");
		TableColumn tcimage = t2TableView.getColumns().get(8);
		tcimage.setCellValueFactory(new PropertyValueFactory<>("image"));
		tcimage.setStyle("-fx-alignment: CENTER");

		t2TableView.setItems(t2Listdata);
		dbArrayList = EmployeeDAO.getEmployeeDataTotalData();
		for (Employee employee : dbArrayList) {
			t2Listdata.add(employee);

		}

	}

	// 찾기버튼
	private void handlerBtnSearchAction() {
		for (Employee employee : dbArrayList) {
			if (empTextSearch.getText().trim().equals(employee.getNumber())) {
				t2TableView.getSelectionModel().select(employee);
				t2TableView.scrollTo(employee);
			}
		}

	}

	// 추가버튼 클릭시 다이얼로그
	private void handlerBtnAddAction() {

		Parent root = null;
		try {
			Stage editStage = new Stage(StageStyle.UTILITY);
			editStage.initModality(Modality.WINDOW_MODAL);
			editStage.initOwner(employeestage);
			editStage.setTitle("직원을 추가창");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/employee.fxml"));
			root = loader.load();

			// employee값을 설정
			ImageView t2ImageView = (ImageView) root.lookup("#t2ImageView");
			Button t2BtnImage = (Button) root.lookup("#t2BtnImage");
			Button t2BtnInput = (Button) root.lookup("#t2BtnInput");
			Button t2BtnCancel = (Button) root.lookup("#t2BtnCancel");
			Button t2BtnClear = (Button) root.lookup("#t2BtnClear");
			TextField t2TextPhone = (TextField) root.lookup("#t2TextPhone");
			TextField t2TextAddress = (TextField) root.lookup("#t2TextAddress");
			TextField t2TextNumber = (TextField) root.lookup("#t2TextNumber");
			ComboBox<String> t2CmbPosition = (ComboBox) root.lookup("#t2CmbPosition");
			TextField t2TextAge = (TextField) root.lookup("#t2TextAge");
			TextField t2TextName = (TextField) root.lookup("#t2TextName");
			ComboBox<String> t2CmbDepart = (ComboBox) root.lookup("#t2CmbDepart");
			DatePicker t2dayEmp = (DatePicker) root.lookup("#t2dayEmp");

			// 제한사항 셋팅
			inputDecimalFormatnumber(t2TextNumber);
			t2TextName.textProperty().addListener(inputDecimalFormatName);
			inputDecimalFormatnumber(t2TextAge);
			t2TextAddress.textProperty().addListener(inputDecimalFormataddress);
			inputDecimalFormatPhone(t2TextPhone);

			// 콤보박스
			ObservableList<String> t2ListDepart = FXCollections.observableArrayList();
			ObservableList<String> t2ListPosition = FXCollections.observableArrayList();

			t2ListDepart.addAll("관리부", "기전실", "보안", "미화부");
			t2CmbDepart.setItems(t2ListDepart);
			t2ListPosition.addAll("소장", "과장", "반장", "대원", "미화원", "경리");
			t2CmbPosition.setItems(t2ListPosition);
			// -------------------------------------------------------------------------------------------------------------

			// 안되요...
			t2ImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {

					t2ImageView.setImage(new Image(getClass().getResource("../image/image.JPG").toString()));

				}
			});

			// 저장 버튼 입력시 작성한 정보가 tableview에 입력
			t2BtnInput.setOnAction((e) -> {
//			  String str =t2TextNumber.getText();
//			  
//			  for(Employee emp : dbArrayList) {
//				  if(emp.getNumber().equals(str)) {
//					  flag=true;
//				  }
//			  }
//			  
//			  if(flag==true) {
//				  callAlert("짜식아:노노");
//				  flag=false;
//			  }else if(flag=false){}

				imagesave();

				if (t2TextNumber != null && t2TextName != null && t2TextAge != null && t2CmbDepart != null
						&& t2dayEmp != null && t2CmbPosition != null && t2TextAddress != null && t2TextPhone != null
						&& fileName != null) {
					String number = t2TextNumber.getText();
					String name = t2TextName.getText();
					String age = t2TextAge.getText();
					String Depart = t2CmbDepart.getSelectionModel().getSelectedItem();
					String Employmentday = t2dayEmp.getValue().toString();
					String position = t2CmbPosition.getSelectionModel().getSelectedItem();
					String address = t2TextAddress.getText();
					String phone = t2TextPhone.getText();
					String image = fileName;
					System.out.println(fileName);

					Employee employee = new Employee(number, name, age, Depart, Employmentday, position, address, phone,
							image);

					int count = EmployeeDAO.insertEmployeeData(employee);
					if (count != 0) {
					}
					for (Employee em : dbArrayList) {
						t2Listdata.add(em);
					} // for
				} else {
				}

				t2Listdata.clear();
				t2TableView.setItems(t2Listdata);
				dbArrayList = EmployeeDAO.getEmployeeDataTotalData();
				for (Employee emplyee : dbArrayList) {
					t2Listdata.add(emplyee);
				}

				editStage.close();

			});

			// 이미지 정보 가져오기
			t2BtnImage.setOnAction((e1) -> {
				FileChooser fileChooser = new FileChooser();
				fileChooser.getExtensionFilters().add(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.gif"));
				selectFile = fileChooser.showOpenDialog(employeestage);
				String localURL = "";
				if (selectFile != null) {
					try {
						localURL = selectFile.toURI().toURL().toString();
					} catch (MalformedURLException e) {
					}
				}

				t2ImageView.setImage(new Image(localURL, false));
				fileName = selectFile.getName();
			});
			// 취소 버튼 클릭시 창 닫기
			t2BtnCancel.setOnAction((e) -> {
				editStage.close();
			});

			// 초기화 버튼 클릭시 입력정보 초기화
			t2BtnClear.setOnAction((e) -> {
				t2dayEmp.setValue(null);
				t2TextNumber.clear();
				t2TextName.clear();
				t2TextAge.clear();
				t2TextAddress.clear();
				t2TextPhone.clear();
				t2CmbDepart.getSelectionModel().clearSelection();
				t2CmbPosition.getSelectionModel().clearSelection();
				t2ImageView.setImage(new Image(getClass().getResource("../image/image.JPG").toString()));
				selectFile = null;
				fileName = "";
			});

			Scene scene = new Scene(root);
			editStage.setScene(scene);
			editStage.show();

		} catch (IOException e) {
		}

	}

	// 수정버튼클릭시
	private void handlerBtnEditAction() {

		if (fileName.equals("")) { // fileName.equals("") : 이미지를 안바꾼 상황임. 선택이 안된 경우이니까 // !( fileName.equals("")) :
									// 이미지를 바꾼상황
//			 imageDelete();
//			imagesave();
		}
		Parent root = null;
		try {
			Stage editStage = new Stage(StageStyle.UTILITY);
			editStage.initModality(Modality.WINDOW_MODAL);
			editStage.initOwner(employeestage);
			editStage.setTitle("직원정보 수정");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/employeee.fxml"));
			root = loader.load();

			// employee값을 설정
			ImageView t2ImageView = (ImageView) root.lookup("#t2ImageView");
			Button t2BtnImage = (Button) root.lookup("#t2BtnImage");
			Button t2BtnInput = (Button) root.lookup("#t2BtnInput");
			Button t2BtnCancel = (Button) root.lookup("#t2BtnCancel");
			Button t2BtnClear = (Button) root.lookup("#t2BtnClear");
			TextField t2TextPhone = (TextField) root.lookup("#t2TextPhone");
			TextField t2TextAddress = (TextField) root.lookup("#t2TextAddress");
			TextField t2TextNumber = (TextField) root.lookup("#t2TextNumber");
			ComboBox<String> t2CmbPosition = (ComboBox) root.lookup("#t2CmbPosition");
			TextField t2TextAge = (TextField) root.lookup("#t2TextAge");
			TextField t2TextName = (TextField) root.lookup("#t2TextName");
			ComboBox<String> t2CmbDepart = (ComboBox) root.lookup("#t2CmbDepart");
			DatePicker t2dayEmp = (DatePicker) root.lookup("#t2dayEmp");

			selectEmployee = t2TableView.getSelectionModel().getSelectedItem();
			selectEmployeeindex = t2TableView.getSelectionModel().getSelectedIndex();

			// 제한사항 셋팅
			inputDecimalFormatnumber(t2TextNumber);
			t2TextName.textProperty().addListener(inputDecimalFormatName);
			inputDecimalFormatnumber(t2TextAge);
			t2TextAddress.textProperty().addListener(inputDecimalFormataddress);
			inputDecimalFormatPhone(t2TextPhone);

			// 콤보박스
			ObservableList<String> t2ListDepart = FXCollections.observableArrayList();
			ObservableList<String> t2ListPosition = FXCollections.observableArrayList();

			t2ListDepart.addAll("관리부", "기전실", "보안", "미화부");
			t2CmbDepart.setItems(t2ListDepart);
			t2ListPosition.addAll("소장", "과장", "반장", "대원", "미화원", "경리");
			t2CmbPosition.setItems(t2ListPosition);

			// 값을 불러온다.
			t2CmbPosition.getSelectionModel().select(selectEmployee.getPosition());
			t2TextAge.setText(selectEmployee.getAge());
			t2TextName.setText(selectEmployee.getName());
			t2CmbDepart.getSelectionModel().select(selectEmployee.getDepartment());
			t2dayEmp.setValue(LocalDate.parse(selectEmployee.getEmploymentday()));
			t2TextPhone.setText(selectEmployee.getPhone());
			t2TextAddress.setText(selectEmployee.getAddress());

			System.out.println(imageDir.getAbsolutePath() + "/" + selectEmployee.getImage());

			Image image = new Image("file:///" + imageDir.getAbsolutePath() + "/" + selectEmployee.getImage());
			t2ImageView.setImage(image);

			t2TextNumber.setText(selectEmployee.getNumber());

			System.out.println(image + "이미지");

			// 저장 버튼 입력시 작성한 정보가 tableview에 입력
			t2BtnInput.setOnAction((e) -> {

				String name = t2TextName.getText();
				String age = t2TextAge.getText();
				String Depart = t2CmbDepart.getSelectionModel().getSelectedItem();
				String Employmentday = t2dayEmp.getValue().toString();
				String position = t2CmbPosition.getSelectionModel().getSelectedItem();
				String address = t2TextAddress.getText();
				String phone = t2TextPhone.getText();
				String image1 = fileName;
				String number = t2TextNumber.getText();

				System.out.println(fileName + "파일이름");
				System.out.println(image1 + "---image1");

				Employee employee = new Employee(number, name, age, Depart, Employmentday, position, address, phone,
						image1);

				int count = 0;
				count = EmployeeDAO.updateEmployeeData(employee);
				if (count != 0) {

					dbArrayList.clear();
					t2Listdata.clear();
					dbArrayList = EmployeeDAO.getEmployeeDataTotalData();
					for (Employee employee2 : dbArrayList) {
						t2Listdata.add(employee2);
					}

					callAlert("입력성공 : 데이타베이스 입력이 성공되었습니다. ");

					System.out.println(selectEmployeeindex + "aaaaa");
				} else {
					return;
				}

				editStage.close();

			});
			// 이미지 정보 가져오기
			t2BtnImage.setOnAction((e1) -> {
				FileChooser fileChooser = new FileChooser();
				fileChooser.getExtensionFilters().add(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.gif"));
				selectFile = fileChooser.showOpenDialog(employeestage);
				System.out.println("selectFile" + selectFile);
				String localURL = null;
				if (selectFile != null) {
					try {
						localURL = selectFile.toURI().toURL().toString();
					} catch (MalformedURLException e) {
					}
				}

				t2ImageView.setImage(new Image(localURL, false));
				System.out.println(t2ImageView);
				fileName = selectFile.getName();
				System.out.println(fileName);
			});

			// 취소 버튼 클릭시 창 닫기
			t2BtnCancel.setOnMouseClicked((e) -> {
				editStage.close();
			});
			// 초기화 버튼 클릭시 입력정보 초기화
			t2BtnClear.setOnAction((e) -> {
				t2dayEmp.setValue(null);
				t2TextNumber.clear();
				t2TextName.clear();
				t2TextAge.clear();
				t2TextAddress.clear();
				t2TextPhone.clear();
				t2CmbDepart.getSelectionModel().clearSelection();
				t2CmbPosition.getSelectionModel().clearSelection();
				t2ImageView.setImage(new Image(getClass().getResource("../images/image.jpg").toString()));
				fileName = "";
			});

			Scene scene = new Scene(root);
			editStage.setScene(scene);
			editStage.show();

		} catch (IOException e) {
			callAlert("경고창:빈칸을 누르지 마세요");
		}

	}

	// 닫기버튼
	private void handlerBtnCloseAction() {
		try {
			Stage adminliststage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/adlist.fxml"));
			Parent root = loader.load();

			AdminListController adminListController = loader.getController();
			adminListController.adminliststage = adminliststage;

			Scene scene = new Scene(root);
			adminliststage.setScene(scene);
			employeestage.close();
			adminliststage.show();// 새로운 창을 띄운다.
		} catch (IOException e1) {
		}
	}

	// 삭제버튼
	private void handlerBtnDelAction() {

		employeeNumber = t2TableView.getSelectionModel().getSelectedItem().getNumber();

		int count = EmployeeDAO.deleteEmployeeData(employeeNumber);

		if (count != 0) {
			// t2Listdata.remove(selectEmployeeindex);
			// dbArrayList.remove(selectEmployee);
			dbArrayList.clear();
			t2Listdata.clear();
			dbArrayList = EmployeeDAO.getEmployeeDataTotalData();
			for (Employee employee2 : dbArrayList) {
				t2Listdata.add(employee2);
			}
			// System.out.println(selectEmployeeindex+"a");
			// System.out.println(selectEmployee+"d");
			// imageDelete();
			// fileName="";

		} else {
			return;
		}

	}

	// 이미지 저장
	private void imagesave() {
		if (!imageDir.exists()) {
			imageDir.mkdir();

		}
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;

		try {
			fis = new FileInputStream(selectFile);
			bis = new BufferedInputStream(fis);
			fileName = "employee" + System.currentTimeMillis() + "_" + selectFile.getName();
			fos = new FileOutputStream(imageDir.getAbsolutePath() + "/" + fileName);
			bos = new BufferedOutputStream(fos);

			int data = -1;
			while ((data = bis.read()) != -1) {
				bos.write(data);
				bos.flush();

			}

		} catch (Exception e) {
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
				if (bis != null) {
					bis.close();
				}
				if (fos != null) {
					fos.close();
				}
				if (bos != null) {
					bos.close();
				}

			} catch (IOException e) {
			}
		} // end of finall

	}

	// 이미지 삭제
	private void imageDelete() {

		File imageFile = new File(imageDir.getAbsolutePath() + "/" + selectEmployee.getImage());
		boolean delFlag = false;

		if (imageFile.exists() && imageFile.isFile()) {
			delFlag = imageFile.delete();
			if (delFlag == false)
				callAlert("이미지제거 실패: 이미지 제거 실패 점검바람");
		}
	}

	// 생년월일 제한
	private void inputDecimalFormatResidentt(TextField textField) {

		DecimalFormat format = new DecimalFormat("##");
		textField.setTextFormatter(new TextFormatter<>(event -> {
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(event.getControlNewText(), parsePosition);

			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 9 || event.getControlNewText() == "."
					|| event.getControlNewText() == "-") {
				return null;
			} else {
				return event;
			}
		}));

	}

	// 사원번호,나이
	private void inputDecimalFormatnumber(TextField textField) {
		DecimalFormat format = new DecimalFormat("##");
		textField.setTextFormatter(new TextFormatter<>(event -> {

			if (event.getControlNewText().isEmpty()) {
				return event;
			}

			ParsePosition parsePosition = new ParsePosition(0);

			Object object = format.parse(event.getControlNewText(), parsePosition);

			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 3 || event.getControlNewText() == "."
					|| event.getControlNewText() == "-") {
				return null;
			} else {
				return event;
			}
		}));

	}

	// 핸드폰 제한
	private void inputDecimalFormatPhone(TextField textField) {

		DecimalFormat format = new DecimalFormat("##");

		textField.setTextFormatter(new TextFormatter<>(event -> {
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(event.getControlNewText(), parsePosition);

			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 12 || event.getControlNewText() == "-"
					|| event.getControlNewText() == "ㅁ") {
				return null;
			} else {
				return event;
			}
		}));

	}

	// 아파트 세대주소 입력 제한
	private void inputDecimalFormatAPT(TextField textField) {

		DecimalFormat format = new DecimalFormat("###");
		textField.setTextFormatter(new TextFormatter<>(event -> {
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(event.getControlNewText(), parsePosition);

			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 4 || event.getControlNewText() == "."
					|| event.getControlNewText() == "-") {
				return null;
			} else {
				return event;
			}
		}));

	}

	// 이름 제한(한글 가능,영어가능)
	ChangeListener<String> inputDecimalFormatName = (observable, oldValue, newValue) -> {

		if (newValue != null && !newValue.equals("")) {

			if (!newValue.matches("\\D*") || newValue.length() > 5) {

				((StringProperty) observable).setValue(oldValue);
			}
		}
	};
	// 주소 제한(한글, 영어가능)
	ChangeListener<String> inputDecimalFormataddress = (observable, oldValue, newValue) -> {

		if (newValue != null && !newValue.equals("")) {

			if (!newValue.matches("\\D*") || newValue.length() > 10) {

				((StringProperty) observable).setValue(oldValue);
			}
		}
	};

	// 알립창 생성
	private void callAlert(String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("경고!");
		alert.setHeaderText(contentText.substring(0, contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":") + 1));

		alert.showAndWait();
	}
}
