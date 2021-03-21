package services;

import domain.Income;
import repo.FileRepo;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class IncomeService {

    private List<Income> incomesList;

    public Boolean insert(Income value) {
        return incomesList.add(value);
    }

    public List<Income> getData() {
        return incomesList;
    }

    public IncomeService(){
        FileRepo fileRepo = new FileRepo();
        incomesList = fileRepo.getIncomeList();
    }

    public int numberOfIncomes() {
        return incomesList.size();
    }

    private Float calculateAverage() {
        return incomesList.isEmpty() ? 0.0f : calculateSum(incomesList) / incomesList.size();
    }

    public Float calculateSum(List<Income> list) {
        float sum = 0.0f;
        for (Income element : list) {
            sum += element.getValue();
        }
        return sum;
    }

    public ArrayList<Income> getIncomesOfGivenDate(LocalDateTime someDate) {
        ArrayList<Income> filteredList = new ArrayList<>();
        for (Income income : incomesList) {
            if (income.getIncomeDate().getMonth().equals(someDate.getMonth())) {
                filteredList.add(income);
            }
        }
        return filteredList;
    }

    public void deleteIncomeGivenDaysOld(Long days) {
        List<Income> tempIncomesList = incomesList
                .stream()
                .peek(income -> {
                    System.out.println(income.getIncomeDate().minusDays(days));
                })
                .filter(income -> income.getIncomeDate().isBefore(LocalDateTime.now().minusDays(days)))
                .collect(Collectors.toList());

        setIncomesList(tempIncomesList);
    }

    @Override
    public String toString() {
        Comparator<Income> valueComparator = new Comparator<Income>() {
            @Override

            public int compare(Income o1, Income o2) {
                return o1.getValue().compareTo(o1.getValue());
            }
        };

        incomesList.sort(valueComparator);
        return "incomesList=" + incomesList + "\naverage of " + calculateAverage();
    }


    public void setIncomesList(List<Income> incomesList) {
        this.incomesList = incomesList;
    }
}
