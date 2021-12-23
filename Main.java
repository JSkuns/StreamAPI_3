import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        // 1. Найти количество несовершеннолетних (т.е. людей младше 18 лет).
        long countMinor = persons.stream()
                .filter(age -> age.getAge() < 18)
                .count();
        System.out.println(countMinor + " <- это количество несовершеннолетних");


        // 2. Получить список фамилий призывников (т.е. мужчин от 18 и до 27 лет).
        List<String> listFamily = persons.stream()
                .filter(ageLow -> ageLow.getAge() > 18)
                .filter(ageHigh -> ageHigh.getAge() < 27)
                .map(Person::getFamily)
                .collect(Collectors.toList());
        System.out.println(listFamily + " <- это список фамилий призывников");


        // 3. Получить отсортированный по фамилии список потенциально работоспособных людей с высшим образованием
        //  в выборке (т.е. людей с высшим образованием от 18 до 60 лет для женщин и до 65 лет для мужчин)
        List<Person> listWorkable = persons.stream()
                .filter(highEd -> highEd.getEducation() == Education.HIGHER)
                .filter(ageLow -> ageLow.getAge() > 18)
                .filter(man -> (man.getSex() == Sex.WOMAN && man.getAge() < 60) || (man.getSex() == Sex.MAN && man.getAge() < 65))
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());
        System.out.println(listWorkable + " <- это список работоспособных людей");

    }

}
