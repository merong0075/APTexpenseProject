package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Model.Citizen;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

public class SignUpController implements Initializable {
	@FXML	private TextField sighTextAddr;
	@FXML	private TextField sighTextAddr1;
	@FXML	private PasswordField sighTextPw;
	@FXML	private PasswordField sighTextPw2;
	@FXML	private TextField sighTextName;
	@FXML	private ComboBox<String> sighCmbGender;
	@FXML	private TextField sighTextBirth;
	@FXML	private TextField sighTextEmail;
	@FXML	private TextField sighTextPhone;
	@FXML	private Button signBtnSave;
	@FXML	private Button signBtnClose;
	@FXML	private Button pwcheck;
	@FXML	private Button idcheck;
	ArrayList<Citizen> dbArrayList = new ArrayList<>();

	public Stage citizensignstage;
	ObservableList<String> signGenderList = FXCollections.observableArrayList();
	ObservableList<Citizen> citizenData = FXCollections.observableArrayList();

	public static String dongho;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		setComboBoxGender();

		setButtonTextFieldInitiate("처음");
		// 저장 버튼 클릭시 값을 저장시키고 로그인 화면으로 전환
		signBtnSave.setOnAction((e) -> {handlerBtnSaveAction();});

		// 닫기 버튼 클릭시 로그인 화면으로 전환
		signBtnClose.setOnAction((e) -> {handlerBtnCloseAction();});

		// 아이디 중복체크
		idcheck.setOnAction((e) -> {hadlejBtnIDcheckAction();});
		// 패스워드 확인
		pwcheck.setOnAction((e) -> {	handlerpwcheckAction();});

		// 핸드폰 제한
		inputDecimalFormatPhone(sighTextPhone);
		// 생년월일 제한
		inputDecimalFormatResidentt(sighTextBirth);
		// 세대 주소 제한(동)
		inputDecimalFormatAPT(sighTextAddr);
		// 세대주소 제한(호)
		inputDecimalFormatAPT(sighTextAddr1);

		// inputDecimalFormatEmail(sighTextEmail);
		// 이름(영어 한글 가능)
		sighTextName.textProperty().addListener(inputDecimalFormatName);
	}

	private void setButtonTextFieldInitiate(String message) {

		switch (message) {
		case "저장":
			sighTextPw.setDisable(true);
			sighTextPw2.setDisable(true);

			break;
		case "중복":
			sighTextAddr.setDisable(true);
			sighTextAddr1.setDisable(true);

			break;
		}
	}

	private void setComboBoxGender() {
		signGenderList.addAll("남", "여");
		sighCmbGender.setItems(signGenderList);

	}

	// 저장버튼 클릭시 저장 기능
	private void handlerBtnSaveAction() {

		if(idcheck.isDisable() && pwcheck.isDisable()) {
			
			try {
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/citizenlogin.fxml"));
				Parent root = loader.load();
				
				CitiLoginController CitiLoginController = loader.getController();
				CitiLoginController.primaryStage = primaryStage;
				
				if (sighTextAddr != null && sighTextAddr1 != null && sighTextPw != null && sighTextPw2 != null
						&& sighTextName != null && sighCmbGender != null && sighTextBirth != null && sighTextEmail != null
						&& sighTextPhone != null && pwcheck != null && idcheck != null) {
					dongho = sighTextAddr.getText() + sighTextAddr1.getText();
					Citizen citizen = new Citizen(dongho, 
							sighTextAddr.getText(), 
							sighTextAddr1.getText(),
							sighTextPw.getText(), 
							sighTextPw2.getText(), 
							sighTextName.getText(),
							sighCmbGender.getSelectionModel().getSelectedItem(), 
							sighTextBirth.getText(),
							sighTextEmail.getText(), 
							sighTextPhone.getText());
					citizenData.add(citizen);
					int count = SignUpDAO.insertAdminCostData(citizen);
					if (count != 0) {
						callAlert("입력성공 : 데이타베이스 입력이 성공되었습니다. ");
					}
					
				} else {
					Platform.exit();
					callAlert("빈칸 발생!!! : 빈칸을 모두 채워주세요");
				}
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				citizensignstage.close();
		
				
			} catch (IOException e) {
			}
			
		
		
		
		}else {
			callAlert("가입오류!: 중복확인은 하셨습니까?");
		}
		
		
		

	}

	// 닫기 버튼 클릭시 로그인 화면으로 전환
	private void handlerBtnCloseAction() {
		try {
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/citizenlogin.fxml"));
			Parent root = loader.load();

			CitiLoginController CitiLoginController = loader.getController();
			CitiLoginController.primaryStage = primaryStage;

			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			citizensignstage.close();
			primaryStage.show();// 새로운 창을 띄운다.
		} catch (IOException e1) {
		}

	}

	// 패스워드 확인
	private void handlerpwcheckAction() {
		if(sighTextPw.getText().isEmpty()&&sighTextPw2.getText().isEmpty()) {
			
			return;	
			}
		if (!(sighTextPw.getText().equals(sighTextPw2.getText()))) {
			callAlert("비밀번호가 동일하지 않습니다.: 비밀번호를 동일하게 넣어주세요");
			sighTextPw.clear();
			sighTextPw2.clear();
			return;
		}
		callAlert("비밀번호 확인완료 : 가입을 진행해주세요");
		pwcheck.setDisable(true);
		setButtonTextFieldInitiate("저장");

	}

	// 아이디 중복체크
	private void hadlejBtnIDcheckAction() {
		if(sighTextAddr.getText().isEmpty()||sighTextAddr1.getText().isEmpty()) {
			callAlert("빈칸 발생: 동수와 호수를 모두 입력해주세요");
			return;	
		}
		if (id(sighTextAddr.getText() + sighTextAddr1.getText()) == true) {
			callAlert("아이디 중복: 사용불가능한 아이디입니다. 다시 입력바랍니다.");
			sighTextAddr.clear();
			sighTextAddr1.clear();
			return;
		} else {
			setButtonTextFieldInitiate("중복");
			idcheck.setDisable(true);
			callAlert("아이디 사용가능: 사용가능한 아이디입니다.");
		}
		
	

	}

	// 아이디 중복확인에서 쓰이는 함수
	public boolean id(String dongho) {
		boolean result = false;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select dongho from citizen where dongho=? ";

		try {
			con = DBUtility.getConnection();
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, dongho);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = true;
			} else {
				result = false;
			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	// 기타 알림창
	public static void callAlert(String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("알림창");
		alert.setHeaderText(contentText.substring(0, contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":") + 1));
		alert.showAndWait();
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

	private void inputDecimalFormatEmail(TextField textField) {
		if (!(sighTextEmail.getText().contains("@"))) {
			callAlert("이메일 입력 오류: 이메일의 형식이 잘못되었습니다.");
			return;
		}

	}

	// 이름 제한(한글 가능,영어가능)
	ChangeListener<String> inputDecimalFormatName = (observable, oldValue, newValue) -> {

		if (newValue != null && !newValue.equals("")) {

			if (!newValue.matches("\\D*") || newValue.length() > 5) {

				((StringProperty) observable).setValue(oldValue);
			}
		}
	};

}
