package com.panonit.service.adoptions;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface DogRepository extends ListCrudRepository<Dog, Integer> {
}
