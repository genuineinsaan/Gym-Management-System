# Gym-Management-System
A pure Java console-based application for managing gym operations. Built from scratch using Core Java, JDBC, and MySQL without an IDE to demonstrate fundamental compilation and architecture concepts.

## 📸 Screenshots
![Admin](https://github.com/user-attachments/assets/80cb4cea-57ac-4e7c-ab27-cf04c8d2b1c0)
![manage_members](https://github.com/user-attachments/assets/9878ed6e-fd23-4464-bd9e-ae1839954e55)
![member_plan](https://github.com/user-attachments/assets/3fa631db-2398-442a-8649-b17f8638606c)
![Trainer](https://github.com/user-attachments/assets/4b3a299a-1622-47a3-b36b-78fa18e07a65)
![announcement](https://github.com/user-attachments/assets/406f85e0-2484-4b46-9021-48633dbae45c)
![login](https://github.com/user-attachments/assets/718b94a2-11ea-4490-a7db-2ea550f7db92)
![dashboard](https://github.com/user-attachments/assets/55a1d12d-9dfc-4921-9a4d-0ee325e65230)
![member_dashboard](https://github.com/user-attachments/assets/7cc3d97e-dad2-4cee-998e-9b6f77678b83)

## ✨ Features

### 👨‍💼 Admin Module
* **Dashboard Overview:** Live statistics on Total Members, Payments Due, and Total Trainers.
* **Member Management:**
    * Add, Edit, and Delete members.
    * **One-Click Actions:** Log Payments (Green Button) and Log Attendance (Cyan Button).
    * View payment due dates and active/inactive status.
* **Plan Management:** Create and modify membership plans (e.g., Weight Loss, Body Building) with specific prices and durations.
* **Trainer Management:** specific Database for trainer contact info and IDs.
* **Announcements:** Broadcast notices (e.g., Holiday closings) to all members.

### 👤 Member Module
* **Secure Login:** Authentication using Registered Phone Number and Date of Birth.
* **Profile View:** View personal details, join date, current plan, and next payment deadline.
* **My History:** * **Payment History:** Detailed table of past transactions.
    * **Attendance History:** Log of check-in times.
* **Gym Info:** View administrative announcements.

## 🛠️ Tech Stack

* **Language:** Java (JDK 17+)
* **GUI:** Java Swing (JFrame, JPanel, JTable)
* **Database:** MySQL
* **Connectivity:** JDBC (MySQL Connector/J)
* **IDE:** NetBeans / Eclipse / IntelliJ IDEA

## ⚙️ Database Schema

The system uses a relational database named `gym_db`. Key tables include:

1.  **Members:** Stores personal info, plan linkage, and status.
2.  **Plans:** Defines membership types (Duration, Price).
3.  **Payments:** Ledger of all financial transactions.
4.  **Attendance:** Timestamp logs for member check-ins.
5.  **Announcements:** General notifications.

## 🚀 How to Run

1.  **Clone the Repository**
    ```bash
    git clone [https://github.com/your-username/gym-management-system.git](https://github.com/your-username/gym-management-system.git)
    ```

2.  **Setup Database**
    * Open MySQL Workbench or your command line.
    * Run the script provided in `database/gym_db.sql` (or create the database manually).
    * Update the credentials in `DBConnection.java`:
        ```java
        DriverManager.getConnection("jdbc:mysql://localhost:3306/gym_db", "root", "your_password");
        ```

3.  **Add Dependencies**
    * Add `mysql-connector-j.jar` to your project libraries.

4.  **Run the Application**
    * Run `AdminDashboard.java` for the Admin view.
    * Run `MemberLogin.java` for the Member view.

## 🔮 Future Improvements
* Integration with a Payment Gateway (Stripe/PayPal).
* Chart visualizations for Monthly Revenue.
* Email notifications for payment reminders.

---
*Developed by Sanjana Barui & Shashank Srivastava*
