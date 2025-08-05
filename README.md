# JavaFX with JDBC - Workshop Project

A comprehensive desktop application developed as part of Prof. Dr. Nelio Alves' **Java COMPLETO** course, demonstrating JavaFX integration with JDBC for database operations in a real-world department and seller management system.

## 📋 Project Overview

This project is a **workshop-style application** that teaches JavaFX development with JDBC integration, following industry best practices and design patterns. The system provides a complete solution for managing departments and sellers with full CRUD operations, form validation, and database persistence.

**Course**: Java COMPLETO - Programação Orientada a Objetos + Projetos  
**Instructor**: Prof. Dr. Nelio Alves  
**Platform**: educandoweb.com  
**Reference Repository**: https://github.com/acenelio/workshop-javafx-jdbc

## 🎯 Learning Objectives

- **JavaFX Fundamentals**: Master JavaFX application development with FXML
- **JDBC Integration**: Implement database operations using JDBC with MySQL
- **Design Patterns**: Apply MVC, DAO, Observer, and Factory patterns
- **Form Handling**: Create dynamic forms with validation and error handling
- **UI/UX Design**: Build professional desktop applications with responsive interfaces

## ✨ Key Features

### Department Management
- **Department Registration**: Add, edit, and remove departments
- **Interactive Table View**: Display departments with inline edit/remove buttons
- **Form Validation**: Real-time validation with error messaging
- **Database Persistence**: Full CRUD operations with MySQL database

### Seller Management  
- **Seller Registration**: Complete seller information management
- **Department Association**: Link sellers to departments via ComboBox
- **Date Formatting**: Professional date picker with custom formatting
- **Advanced TableView**: Display formatted dates, currency values, and emails

### Technical Features
- **Dynamic View Loading**: Seamless navigation between different views
- **Consumer Pattern**: Parameterized initialization for flexible view management
- **Observer Pattern**: Real-time table updates when data changes
- **Exception Handling**: Custom validation exceptions with user-friendly alerts
- **Database Integration**: Complete DAO layer with connection management

## 🏗️ Architecture & Design Patterns

### Model-View-Controller (MVC)
```
├── Model Layer
│   ├── entities/          # Domain entities (Department, Seller)
│   ├── services/          # Business logic services
│   └── exceptions/        # Custom exception classes
├── View Layer
│   ├── *.fxml            # FXML view definitions
│   └── application.css   # Styling and themes
└── Controller Layer
    ├── controllers/      # FXML controllers
    └── util/            # Utility classes (Alerts, Constraints, Utils)
```

### Data Access Object (DAO) Pattern
```
├── db/                   # Database connection management
├── model/dao/           # DAO interfaces
└── model/dao/impl/      # DAO implementations (JDBC)
```

### Design Patterns Implemented
- **MVC**: Separation of concerns across Model, View, Controller
- **DAO**: Data access abstraction layer
- **Factory**: DAO factory for dependency injection
- **Observer**: DataChangeListener for UI updates
- **Consumer**: Parameterized view initialization

## 🚀 Getting Started

### Prerequisites
- **Java 8+** with JAVA_HOME configured
- **JavaFX SDK** with PATH_TO_FX environment variable
- **MySQL Server** installed and running
- **IDE** with JavaFX support (Eclipse recommended)

### Project Setup

1. **Clone and Setup**:
   ```bash
   git init
   git remote add origin https://github.com/acenelio/workshop-javafx-jdbc.git
   git pull origin master
   ```

2. **IDE Configuration**:
   - **User Libraries**: Add JavaFX and MySQL Connector
   - **VM Arguments**: 
     ```
     --module-path C:\java-libs\javafx-sdk\lib --add-modules=javafx.fxml,javafx.controls
     ```

3. **Database Setup**:
   - Create MySQL database using provided `database.sql`
   - Configure `db.properties` with your database credentials

### Running the Application

**Development Mode**:
```bash
java --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.fxml application.Main
```

**Production JAR**:
```bash
java --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.fxml -cp myapp.jar application.Main
```

## 📁 Project Structure

```
javafx-jdbc/
├── src/
│   ├── application/
│   │   ├── Main.java                    # Application entry point
│   │   └── application.css              # Global styles
│   ├── gui/
│   │   ├── MainView.fxml               # Main window with menu
│   │   ├── MainViewController.java      # Main navigation controller
│   │   ├── About.fxml                  # About dialog
│   │   ├── DepartmentList.fxml         # Department table view
│   │   ├── DepartmentListController.java
│   │   ├── DepartmentForm.fxml         # Department form dialog
│   │   ├── DepartmentFormController.java
│   │   ├── SellerList.fxml             # Seller table view
│   │   ├── SellerListController.java
│   │   ├── SellerForm.fxml             # Seller form dialog
│   │   ├── SellerFormController.java
│   │   ├── listeners/
│   │   │   └── DataChangeListener.java  # Observer interface
│   │   └── util/
│   │       ├── Alerts.java             # Alert utilities
│   │       ├── Constraints.java        # Input constraints
│   │       └── Utils.java              # General utilities
│   ├── model/
│   │   ├── entities/
│   │   │   ├── Department.java         # Department entity
│   │   │   └── Seller.java             # Seller entity
│   │   ├── services/
│   │   │   ├── DepartmentService.java  # Department business logic
│   │   │   └── SellerService.java      # Seller business logic
│   │   ├── dao/
│   │   │   ├── DaoFactory.java         # DAO factory
│   │   │   ├── DepartmentDao.java      # Department DAO interface
│   │   │   ├── SellerDao.java          # Seller DAO interface
│   │   │   └── impl/
│   │   │       ├── DepartmentDaoJDBC.java
│   │   │       └── SellerDaoJDBC.java
│   │   └── exceptions/
│   │       └── ValidationException.java # Custom validation exception
│   └── db/
│       ├── DB.java                     # Database utilities
│       ├── DbException.java            # Database exceptions
│       └── DbIntegrityException.java   # Integrity constraint exceptions
├── db.properties                       # Database configuration
└── build.fxbuild                      # JavaFX build configuration
```

## 🛠️ Technical Implementation

### Key Technologies
- **JavaFX 8+**: Modern GUI framework with FXML support
- **JDBC**: Direct database connectivity with MySQL
- **MySQL**: Relational database for data persistence
- **Maven/Gradle**: Dependency management (optional)

### Advanced Features Implemented

#### Form Validation & Error Handling
```java
// Custom validation with user-friendly error messages
private void validateForm() throws ValidationException {
    ValidationException exception = new ValidationException("Validation error");
    
    if (txtName.getText() == null || txtName.getText().trim().equals("")) {
        exception.addError("name", "Field can't be empty");
    }
    
    if (exception.getErrors().size() > 0) {
        throw exception;
    }
}
```

#### Dynamic TableView with Action Buttons
```java
// Inline edit/remove buttons in table rows
private void initEditButtons() {
    tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
    tableColumnEDIT.setCellFactory(param -> new TableCell<Department, Department>() {
        private final Button button = new Button("edit");
        
        @Override
        protected void updateItem(Department obj, boolean empty) {
            super.updateItem(obj, empty);
            if (obj == null) {
                setGraphic(null);
                return;
            }
            setGraphic(button);
            button.setOnAction(event -> createDialogForm(obj, "/gui/DepartmentForm.fxml", Utils.currentStage(event)));
        }
    });
}
```

#### Observer Pattern for Real-time Updates
```java
public interface DataChangeListener {
    void onDataChanged();
}

// Notify observers when data changes
private void notifyDataChangeListeners() {
    for (DataChangeListener listener : dataChangeListeners) {
        listener.onDataChanged();
    }
}
```

## 🔧 Advanced Configurations

### Database Connection
```properties
# db.properties
user=root
password=yourpassword
dburl=jdbc:mysql://localhost:3306/coursejdbc
useSSL=false
```

### VM Arguments for JavaFX
```bash
--module-path C:\java-libs\javafx-sdk\lib 
--add-modules=javafx.fxml,javafx.controls
```

### JAR Distribution
- **Build**: Export as Runnable JAR file with library handling
- **Package**: Include JAR, db.properties, MySQL Connector, JavaFX SDK
- **Deploy**: Create batch files or shortcuts for easy execution

## 📚 Learning Progression

This project follows a structured learning path:

1. **Foundation**: JavaFX basics, FXML, Scene Builder
2. **Navigation**: Menu systems, dynamic view loading
3. **Data Display**: TableView, ObservableList, property binding
4. **Forms**: Dialog creation, validation, error handling
5. **Database**: JDBC integration, DAO pattern, transactions
6. **Advanced UI**: Custom table cells, formatters, ComboBox
7. **Patterns**: Observer, Factory, Consumer patterns
8. **Deployment**: JAR packaging, distribution, installation

## 🤝 Contributing

This is an educational project based on Prof. Dr. Nelio Alves' course. For learning purposes:

1. Follow the course structure and checklists
2. Complete each module before advancing
3. Experiment with additional features
4. Practice the implemented patterns

## 📝 Course Reference

**Original Course Project**: https://github.com/acenelio/workshop-javafx-jdbc  
**Course Platform**: https://educandoweb.com  
**Instructor**: Prof. Dr. Nelio Alves

## 🎓 Educational Value

This project demonstrates professional Java development practices including:
- **Enterprise Patterns**: MVC, DAO, Factory, Observer
- **Database Integration**: JDBC best practices, connection management
- **UI/UX Design**: Professional desktop application development
- **Error Handling**: Validation, exceptions, user feedback
- **Code Organization**: Package structure, separation of concerns

---

*This project serves as a comprehensive reference for JavaFX with JDBC development.
