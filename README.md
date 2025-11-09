# Banking System (Console) - Maven project

## Setup
1. Ensure you have MySQL running locally.
2. Update `src/main/resources/db.properties` if your DB credentials differ.
3. Run the `schema.sql` to create the DB and tables:
   - `mysql -u root -p < src/main/resources/schema.sql`
   - Or open it in MySQL workbench and run.
4. Import into IntelliJ: Open `pom.xml` or the project folder.
5. Build and run via Maven:
   - `mvn clean compile`
   - `mvn exec:java -Dexec.mainClass="org.example.bankingsystem.main.BankingApp"`

## Notes
- JDK 23 compatibility in `pom.xml`.
- Console-driven UI for customers, accounts, transactions.
