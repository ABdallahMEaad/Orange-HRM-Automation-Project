<h1 align="center">🍊 OrangeHRM Automation Project 🤖</h1>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 21"/>
  <img src="https://img.shields.io/badge/Selenium-4.49-43B02A?style=for-the-badge&logo=selenium&logoColor=white" alt="Selenium"/>
  <img src="https://img.shields.io/badge/TestNG-7.11-E34F26?style=for-the-badge" alt="TestNG"/>
  <img src="https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" alt="Maven"/>
  <img src="https://img.shields.io/badge/Allure-Report-9B59B6?style=for-the-badge" alt="Allure"/>
  <img src="https://img.shields.io/badge/Pattern-Page%20Object%20Model-1F8ACB?style=for-the-badge" alt="POM"/>
</p>

<p align="center">
  <b>🤖 A robot that opens a browser and clicks through the OrangeHRM website, just like a real person.</b><br/>
  After every click it asks: <i>"Did the website do the right thing?"</i><br/>
  ✅ Yes = the test passes &nbsp;&nbsp;|&nbsp;&nbsp; ❌ No = the test fails
</p>

---

## 🧰 1. What you need on your computer

Install these first. They are all **free**.

| | Tool | What it is for | Check it in `cmd` |
|---|------|----------------|-------------------|
| ☕ | **Java 21 (JDK)** | Runs the code | `java -version` |
| 📦 | **Maven** | Downloads helpers and starts the tests | `mvn -version` |
| 🌐 | **Google Chrome** | The browser the robot uses | Open it once |
| 🔧 | **Git** | Downloads the project | `git --version` |
| 📊 | **Allure** *(optional)* | Shows the colorful report | `allure --version` |

> [!NOTE]
> If a command says **"not recognized"**, that tool is not installed yet, or it is not added to PATH. Install it and open a **new** `cmd`.

> [!IMPORTANT]
> You need **internet**, because the tests visit https://opensource-demo.orangehrmlive.com/

---

## 📥 2. Download the project (in `cmd`)

Open **cmd**: press the `Windows` key, type `cmd`, press `Enter`. Then type:

```bash
git clone https://github.com/ABdallahMEaad/Orange-HRM-Automation-Project.git
cd Orange-HRM-Automation-Project
```

> [!TIP]
> No Git? Download the project as a **ZIP**, unzip it, then go into the folder:
> ```bash
> cd path\to\Orange-HRM-Automation-Project
> ```

---

## ▶️ 3. Run the tests (in `cmd`)

Stay inside the project folder and type:

```bash
mvn clean test
```

Chrome opens **by itself** and starts working. Wait until `cmd` shows:

- 🟢 `BUILD SUCCESS` → everything passed
- 🔴 `BUILD FAILURE` → something failed

> [!WARNING]
> Do **not** close Chrome and do **not** touch the mouse while the robot works.

Run only one part:

```bash
mvn test -Dgroups=Reports
mvn test -Dgroups=Time
```

> [!TIP]
> The first time, run everything with `mvn clean test`. Some tests use the employee that earlier tests create.

---

## 📊 4. See the report

After the run, the results are saved in the `allure-results` folder. Open the report in your browser:

```bash
allure serve allure-results
```

📸 Failed tests also get a **screenshot** and a **log** inside the report.

---

## 🧪 5. What the tests do

The tests run in this order (it is written in `testng.xml`):

| # | Test | What happens |
|---|------|--------------|
| 1️⃣ | 🔑 **Login** | Sign in to the website |
| 2️⃣ | 👤 **Add Employee** | Create a new employee |
| 3️⃣ | ✏️ **Edit My Info** | The employee changes his personal info |
| 4️⃣ | 📞 **Contact Details** | Update address, phone and email |
| 5️⃣ | 🚨 **Add Emergency Contacts** | Add a person to call in an emergency |
| 6️⃣ | 🛠️ **Manage Emergency Contacts** | Change or remove emergency contacts |
| 7️⃣ | 👨‍👩‍👧 **Dependents** | Add family members |
| 8️⃣ | ⏱️ **Timesheet** | See below |
| 9️⃣ | 📈 **Reports** | See below |
| 🔟 | 🕵️ **Admin Verification** | The admin checks the employee data |

### ⏱️ Timesheet (Time module)

1. 🧑‍💼 The employee opens **Time → My Timesheet** and checks that the current week is shown.
2. ✍️ He clicks **Edit**, picks the project (*The Coca-Cola Company*), picks an activity, types hours for all 7 days and saves.
3. 📤 He presses **Submit** → status becomes `Submitted`.
4. 👔 The manager (the Admin user) opens **Time → Employee Timesheets**, searches for the employee, checks the project and activity, and presses **Approve**.
5. ✅ Status becomes `Approved`.

### 📈 Reports (PIM → Reports)

1. 🆕 **New report**: the robot creates a report with a unique name, adds fields, saves it, finds it in the list and opens it. Then it presses `Ctrl+B` to print it as a **PDF on your Desktop** and reads the PDF to make sure it is not empty.
2. 📋 **Employee Contact info report**: opens it and checks that employees are listed.
3. 📧 Checks that the **work email** and **mobile number** are shown.
4. 🔍 Applies the filters (**Job Title**, **Sub Unit**, **Employment Status**) and checks that the list got smaller or stayed the same.
5. 💾 Clicks **Export** if the website has an Export button *(if not, this test is skipped)*.

---

## 🗂️ 6. What is inside the folders

```
Orange-HRM-Automation-Project
├── pom.xml                       📦 Maven settings: which helpers to download
├── testng.xml                    📋 The order the tests run in
├── src/main/java/Page            📄 Pages: how the robot finds buttons and fields
├── src/main/resources            ⚙️ Settings and test data (config.properties, TestData.json ...)
├── src/test/java/TestCases       🧪 The tests themselves
├── src/test/java/DriverFactory   🚗 Opens and closes the browser
├── src/test/java/Utils           🧰 Small helpers (files, screenshots, Desktop, PDF reading)
├── allure-results                📊 Report data (created after a run)
└── logs                          📝 Log file (created after a run)
```

### ⚙️ Settings you can change

Open `src/main/resources/config.properties`:

| Setting | Meaning |
|---------|---------|
| `url` | The website address |
| `browserName` | `chrome` *(Edge and Firefox are also supported)* |
| `adminUsername` / `adminPassword` | Manager login |
| `employeeUsername` / `employeePassword` | Employee login |

---

## 🆘 7. If something goes wrong

| 😟 Problem | 💡 What to do |
|-----------|---------------|
| `'mvn' is not recognized` | Maven is not installed or not in PATH. Install it and open a new `cmd`. |
| `'java' is not recognized` or a Java version error | Install **JDK 21** and check with `java -version`. |
| `release version 21 not supported` | Your Java is too old. Install JDK 21. |
| The browser does not open | Update Google Chrome, then run again. |
| A test fails because of the website | The demo website is public and sometimes slow. Run again. |
| The PDF is not found | Make sure your Desktop folder exists and Chrome can write to it. |

---

## 👨‍💻 8. About the author

<table>
  <tr>
    <td>
      <h3>Abdallah Ahmed Meaad</h3>
      <b>QA / QC Engineer | Manual &amp; Automation Testing</b><br/>
      🏅 ISTQB CTFL Certified &nbsp;|&nbsp; 📍 Cairo, Egypt<br/><br/>
      📧 <a href="mailto:abdallahmead0@gmail.com">abdallahmead0@gmail.com</a><br/>
      🐙 <a href="https://github.com/ABdallahMEaad">GitHub</a><br/>
      🌍 <a href="https://abdallahmeaad.github.io/My_CV">Portfolio</a>
    </td>
  </tr>
</table>

<p align="center">
  <img src="https://img.shields.io/badge/Selenium-WebDriver-43B02A?style=flat-square&logo=selenium&logoColor=white"/>
  <img src="https://img.shields.io/badge/Java-TestNG-ED8B00?style=flat-square&logo=openjdk&logoColor=white"/>
  <img src="https://img.shields.io/badge/Postman-API-FF6C37?style=flat-square&logo=postman&logoColor=white"/>
  <img src="https://img.shields.io/badge/JMeter-Performance-D22128?style=flat-square&logo=apachejmeter&logoColor=white"/>
  <img src="https://img.shields.io/badge/SQL-Database-4479A1?style=flat-square&logo=mysql&logoColor=white"/>
  <img src="https://img.shields.io/badge/GitHub%20Actions-CI%2FCD-2088FF?style=flat-square&logo=githubactions&logoColor=white"/>
</p>

<p align="center"><i>⭐ If you like this project, give it a star!</i></p>