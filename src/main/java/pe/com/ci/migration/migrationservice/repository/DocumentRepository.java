package pe.com.ci.migration.migrationservice.repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import org.springframework.stereotype.Repository;
import pe.com.ci.migration.migrationservice.entity.Document;

import java.util.List;

@Repository
public interface DocumentRepository extends CosmosRepository<Document, String> {
    List<Document> findAllByNroLoteAndFacturaNro(Integer nroLote, String facturaNro);
}