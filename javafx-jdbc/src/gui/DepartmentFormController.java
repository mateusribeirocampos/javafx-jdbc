package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listener.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.exceptions.ValidationException;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable {

	private DepartmentService service;

	private Department entity;

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtName;

	@FXML
	private TextField txtQuantity;

	@FXML
	private TextField txtDescription;

	@FXML
	private Label labelErrorName;

	@FXML
	private Label labelErrorQuantity;

	@FXML
	private Label labelErrorDescription;

	@FXML
	private Button btSave;

	@FXML
	private Button btCancel;

	public void setDepartment(Department entity) {
		this.entity = entity;
	}

	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}

	// add ou subscribe in the list where the events will be run.
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyDataChangeListener();
			Utils.currentStage(event).close();
		} catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		} catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}

	// notify who is in the list.
	private void notifyDataChangeListener() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}

	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	private Department getFormData() {
		Department obj = new Department();
		ValidationException exception = new ValidationException("Validation error");
		obj.setId(Utils.tryParseToInt(txtId.getText()));

		if (txtName.getText() == null || txtName.getText().trim().equals("")) {
			exception.addError("name", "Field can't be empty");
		}
		obj.setName(txtName.getText());

		if (txtQuantity.getText() == null || txtQuantity.getText().trim().equals("")) {
			exception.addError("quantity", "Field can't be empty");
		} else {
			Integer quantity = Utils.tryParseToInt(txtQuantity.getText());
			if (quantity != null && quantity < 0) {
				exception.addError("quantity", "Quantity cannot be negative");
			}
		}
		obj.setQuantity(Utils.tryParseToInt(txtQuantity.getText()));

		if (txtDescription.getText() == null || txtDescription.getText().trim().equals("")) {
			exception.addError("description", "Field can't be empty");
		}
		obj.setDescription(txtDescription.getText());

		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		System.out.println("The object create: " + obj);
		return obj;
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);
		Constraints.setTextFieldInteger(txtQuantity);
		Constraints.setTextFieldMaxLength(txtDescription, 80);
	}

	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}

		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
		txtQuantity.setText(String.valueOf(entity.getQuantity()));
		txtDescription.setText(entity.getDescription());
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		if (fields.contains("name")) {
			labelErrorName.setText(errors.get("name"));
		}

		if (fields.contains("quantity")) {
			labelErrorQuantity.setText(errors.get("quantity"));
		}

		if (fields.contains("description")) {
			labelErrorDescription.setText(errors.get("description"));
		}
	}

}
