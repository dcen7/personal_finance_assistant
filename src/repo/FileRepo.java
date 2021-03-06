package repo;

import domain.Expense;
import domain.Income;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
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
        income.setID(tokens[1]);
        income.setName(tokens[2]);
        income.setValue(Float.parseFloat(tokens[3]));
        income.setIncomeDate(LocalDateTime.parse(tokens[4]));

        return income;
    }

    public Expense parseExpense(String line){
        String[] tokens = line.split(";");
        Expense expense = new Expense();
        expense.setID(tokens[1]);
        expense.setExpenseName(tokens[2]);
        expense.setExpenseValue(Float.parseFloat(tokens[3]));
        expense.setExpenseDate(LocalDateTime.parse(tokens[4]));

        return expense;

    }

    public void writeEverythingToFile() throws IOException {
        StringBuilder content = new StringBuilder();

        for (Expense e: expenseList){
            content.append(e+"\n");
        }

        for (Income i: incomeList){
            content.append(i+"\n");
        }

        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(fileName))){
            bw.write(content.toString());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void writeData(String data){
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(fileName),StandardOpenOption.APPEND)){
            bw.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Income> getIncomeList() {
        return incomeList;
    }

    public List<Expense> getExpenseList() {
        return expenseList;
    }
}
