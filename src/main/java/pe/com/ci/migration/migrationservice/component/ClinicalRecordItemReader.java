package pe.com.ci.migration.migrationservice.component;

import com.azure.spring.data.cosmos.core.query.CosmosPageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import pe.com.ci.migration.migrationservice.entity.ClinicalRecord;
import pe.com.ci.migration.migrationservice.enums.StatusExpediente;
import pe.com.ci.migration.migrationservice.repository.ClinicalRecordRepository;

import javax.annotation.PostConstruct;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class ClinicalRecordItemReader implements ItemReader<ClinicalRecord> {

    private final ClinicalRecordRepository clinicalRecordRepository;
    private final int pageSize;
    private final int startPageNumber;
    private final int endPageNumber;

    private List<ClinicalRecord> clinicalRecords;
    private int pageNumber;
    private int index;
    private boolean paginationFlag;
    private Page<ClinicalRecord> clinicalRecordPage;

    @PostConstruct
    public void init() {
        pageNumber = startPageNumber - 1;

        clinicalRecordPage = clinicalRecordRepository
                .findAllByEstado(
                        StatusExpediente.EXPEDIENTE_PENDIENTE.name(),
                        new CosmosPageRequest(pageNumber, pageSize, null, Sort.by("id"))
                );

        log.info(
                "ClinicalRecordItemReader - Hilo: {}, Pagina Inicial: {}, Pagina Final: {}, Pagina Actual: {}, Registro(s): {}",
                Thread.currentThread().getName(),
                startPageNumber,
                endPageNumber,
                clinicalRecordPage.getNumber() + 1,
                clinicalRecordPage.getNumberOfElements()
        );

        if (clinicalRecordPage.hasContent()) {
            clinicalRecords = clinicalRecordPage.getContent();
            index = 0;
        }
        paginationFlag = false;
        pageNumber++;
    }

    @Override
    public ClinicalRecord read() throws Exception {
        ClinicalRecord clinicalRecord = null;

        if (paginationFlag) {
            if (pageNumber < endPageNumber) {
                clinicalRecordPage = clinicalRecordRepository
                        .findAllByEstado(
                                StatusExpediente.EXPEDIENTE_PENDIENTE.name(),
                                clinicalRecordPage.nextPageable()
                        );

                log.info(
                        "ClinicalRecordItemReader - Hilo: {}, Pagina Inicial: {}, Pagina Final: {}, Pagina Actual: {}, Registro(s): {}",
                        Thread.currentThread().getName(),
                        startPageNumber,
                        endPageNumber,
                        clinicalRecordPage.getNumber() + 1,
                        clinicalRecordPage.getNumberOfElements()
                );

                if (clinicalRecordPage.hasContent()) {
                    clinicalRecords = clinicalRecordPage.getContent();
                    index = 0;
                }
                paginationFlag = false;
                pageNumber++;
            }
            else {
                clinicalRecords = List.of();
            }
        }

        if (clinicalRecords != null && !clinicalRecords.isEmpty()) {
            if (index + 1 == clinicalRecords.size()) {
                paginationFlag = true;
            }
            clinicalRecord = clinicalRecords.get(index);
            index++;
        }
        return clinicalRecord;
    }
}