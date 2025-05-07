package br.com.cp2_java.repository;

import br.com.cp2_java.domainmodel.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
