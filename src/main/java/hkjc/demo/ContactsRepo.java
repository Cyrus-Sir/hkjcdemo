package hkjc.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactsRepo extends JpaRepository<Contacts, Integer> {
}
