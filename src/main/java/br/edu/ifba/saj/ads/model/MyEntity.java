package br.edu.ifba.saj.ads.model;

import java.util.List;

import io.quarkus.panache.common.Parameters;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;

/**
 * Example JPA entity defined as a Panache Entity.
 * An ID field of Long type is provided, if you want to define your own ID field
 * extends <code>PanacheEntityBase</code> instead.
 *
 * This uses the active record pattern, you can also use the repository pattern
 * instead:
 * .
 *
 * Usage (more example on the documentation)
 *
 * {@code
 *     public void doSomething() {
 *         MyEntity entity1 = new MyEntity();
 *         entity1.field = "field-1";
 *         entity1.persist();
 *
 *         List<MyEntity> entities = MyEntity.listAll();
 * }
 * }
 */
@Entity
@NamedQuery(name = "MyEntity.containsInField", query = "from MyEntity where field like CONCAT('%', CONCAT(:field, '%'))")
public class MyEntity extends AbstractModel {
    public String field;

    public static List<MyEntity> findByField(String field) {
        return find("#MyEntity.containsInField", Parameters.with("field", field)).list();
    }
}
