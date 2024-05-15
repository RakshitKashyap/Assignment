package com.example.Employee.TestAssignment.entity;

import com.example.Employee.TestAssignment.models.entity.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AddressTest {
    private Address address;

    @BeforeEach
    public void setUp() {
        address = new Address();
        address.setId(1L);
        address.setTitle("Home");
        address.setAddress1("123 Main St");
        address.setCity("New York");
        address.setState("NY");
        address.setZipCode("10001");
        address.setStatus(true);
    }

    @Test
    public void testGettersAndSetters() {
        assertThat(address.getId()).isEqualTo(1L);
        assertThat(address.getTitle()).isEqualTo("Home");
        assertThat(address.getAddress1()).isEqualTo("123 Main St");
        assertThat(address.getCity()).isEqualTo("New York");
        assertThat(address.getState()).isEqualTo("NY");
        assertThat(address.getZipCode()).isEqualTo("10001");
        assertThat(address.isStatus()).isTrue();

        // Test setters
        address.setId(2L);
        address.setTitle("Work");
        address.setAddress1("456 Broadway");
        address.setCity("Los Angeles");
        address.setState("CA");
        address.setZipCode("90001");
        address.setStatus(false);

        assertThat(address.getId()).isEqualTo(2L);
        assertThat(address.getTitle()).isEqualTo("Work");
        assertThat(address.getAddress1()).isEqualTo("456 Broadway");
        assertThat(address.getCity()).isEqualTo("Los Angeles");
        assertThat(address.getState()).isEqualTo("CA");
        assertThat(address.getZipCode()).isEqualTo("90001");
        assertThat(address.isStatus()).isFalse();
    }

    @Test
    public void testNoArgsConstructor() {
        Address newAddress = new Address();
        assertThat(newAddress).isNotNull();
    }

    @Test
    public void testAllArgsConstructor() {
        Address newAddress = new Address(3L, "Office", "789 Park Ave", "Chicago", "IL", "60601", true);
        assertThat(newAddress).isNotNull();
        assertThat(newAddress.getId()).isEqualTo(3L);
        assertThat(newAddress.getTitle()).isEqualTo("Office");
        assertThat(newAddress.getAddress1()).isEqualTo("789 Park Ave");
        assertThat(newAddress.getCity()).isEqualTo("Chicago");
        assertThat(newAddress.getState()).isEqualTo("IL");
        assertThat(newAddress.getZipCode()).isEqualTo("60601");
        assertThat(newAddress.isStatus()).isTrue();
    }

    @Test
    public void testToString() {
        String expectedToString = "Address(id=1, title=Home, address1=123 Main St, city=New York, state=NY, zipCode=10001, status=true)";
        assertThat(address.toString()).isEqualTo(expectedToString);
    }
}
