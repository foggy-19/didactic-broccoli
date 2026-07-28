package com.panonit.service.adoptions;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "dogs")
record Dog(@Id int id, String name, String owner, String description) {

}
