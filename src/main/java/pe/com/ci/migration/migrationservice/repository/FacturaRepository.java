package pe.com.ci.migration.migrationservice.repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import org.springframework.stereotype.Repository;
import pe.com.ci.migration.migrationservice.entity.Factura;

@Repository
public interface FacturaRepository extends CosmosRepository<Factura, String> {
}