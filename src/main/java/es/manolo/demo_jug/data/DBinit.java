package es.manolo.demo_jug.data;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DBinit implements ApplicationRunner {

    private final ContactRepository contactRepository;

    public DBinit(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // create 20 random names in an array
        String[] names = {"Manolo", "Pepe", "Juan", "Pedro", "Luis", "Carlos", "Antonio", "Jose", "Javier", "Paco", "Raul", "Miguel", "Alberto", "David", "Jorge", "Fernando", "Sergio", "Ricardo", "Ruben", "Oscar"};
        // create 20 random surnames in an array
        String[] surnames = {"Carrasco", "Garcia", "Lopez", "Martinez", "Sanchez", "Perez", "Gonzalez", "Rodriguez", "Fernandez", "Gomez", "Diaz", "Alvarez", "Vazquez", "Jimenez", "Moreno", "Alonso", "Romero", "Navarro", "Torres", "Ramos"};
        // Insert 200 random contacts in the repository, with random phones formatted as +34 {6 random digits}, birthday in the past
        for (int i = 0; i < 200; i++) {
            Contact contact = new Contact();
            contact.setName(names[(int) (Math.random() * 20)]);
            contact.setSurname(surnames[(int) (Math.random() * 20)]);
            contact.setEmail(contact.getName().toLowerCase() + "." + contact.getSurname().toLowerCase() + "@example.com");
            contact.setPhone("+34 " + (int) (Math.random() * 1000000));
            contact.setBirthDay(java.time.LocalDate.of(1970 + (int) (Math.random() * 50), (int) (Math.random() * 12) + 1, (int) (Math.random() * 28) + 1));
            contactRepository.save(contact);
        }

    }
}
