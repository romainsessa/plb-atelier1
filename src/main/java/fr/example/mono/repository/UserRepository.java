package fr.example.mono.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.example.mono.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    public User findByEmail(String email);

    public User findByFirstNameAndLastName(String firstName, String lastName);

    public User findByUid(Integer uid);
}
