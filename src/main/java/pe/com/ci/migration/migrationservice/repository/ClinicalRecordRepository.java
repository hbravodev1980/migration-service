package pe.com.ci.migration.migrationservice.repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pe.com.ci.migration.migrationservice.entity.ClinicalRecord;

import java.util.Optional;

@Repository
public interface ClinicalRecordRepository extends CosmosRepository<ClinicalRecord, String> {
    Optional<ClinicalRecord> findOptionalByNroLoteAndFacturaNro(Integer nroLote, String facturaNro);

    Page<ClinicalRecord> findAllByEstado(String estado, Pageable pageable);
}