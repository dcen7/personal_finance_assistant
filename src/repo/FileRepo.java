package repo;

import domain.Expense;
import domain.Income;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileRepo {

    String fileName;
    List<Income> incomeList = new ArrayList<>();
    List<Expense> expenseList = new ArrayList<>();


    public FileRepo(){
        this.fileName = "/Users/mehmetdenizcengiz/Desktop/personal_finance_assistant/per_fin_assistant.txt";
        readAndparseData();
    }

    public void readAndparseData(){

        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.startsWith("I")){
                    incomeList.add(parseIncome(line));
                }
                if(line.startsWith("E")){
                    expenseList.add(parseExpense(line));
                }
            }
        } catch (NoSuchFileException ex ){
          System.out.println("I can not find the file : " + fileName
                  + " please contact administrator! ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Income parseIncome(String line){
        String[] tokens = line.split(";");
        Income income = new Income();
        income.setName(tokens[1]);
        income.setValue(Float.parseFloat(tokens[2]));
        income.setIncomeDate(LocalDateTime.parse(tokens[3]));

        return income;
    }

    public Expense parseExpense(String line){
        String[] tokens = line.split(";");
        Expense expense = new Expense();
        expense.setExpenseName(tokens[1]);
        expense.setExpenseValue(Float.parseFloat(tokens[2]));
        expense.setExpenseDate(LocalDateTime.parse(tokens[3]));

        return expense;

    }

    public List<Income> getIncomeList() {
        return incomeList;
    }

    public List<Expense> getExpenseList() {
        return expenseList;
    }
}
