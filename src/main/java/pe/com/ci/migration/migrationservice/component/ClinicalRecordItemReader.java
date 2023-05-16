package pe.com.ci.migration.migrationservice.component;

import com.azure.spring.data.cosmos.core.query.CosmosPageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.ci.migration.migrationservice.entity.ClinicalRecord;
import pe.com.ci.migration.migrationservice.enums.StatusExpediente;
import pe.com.ci.migration.migrationservice.repository.ClinicalRecordRepository;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class ClinicalRecordItemReader implements ItemReader<ClinicalRecord> {

    private final ClinicalRecordRepository clinicalRecordRepository;
    private final int pageSize;
    private final int startPageNumber;
    private final int endPageNumber;

    private List<ClinicalRecord> clinicalRecords;
    private int pageNumber = 0;
    private int index = 0;
    private boolean paginationFlag = true;
    private Pageable pageable = null;
    private Page<ClinicalRecord> clinicalRecordPage = null;

    @Override
    public ClinicalRecord read()
            throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        ClinicalRecord clinicalRecord = null;

        if (pageNumber == 0) {
            pageNumber = startPageNumber - 1;
        }

        if (paginationFlag) {
            if (pageNumber < endPageNumber) {
                if (pageNumber == startPageNumber - 1) {
                    pageable = new CosmosPageRequest(pageNumber, pageSize, null);
                }
                else {
                    if (clinicalRecordPage != null && clinicalRecordPage.hasNext()) {
                        pageable = clinicalRecordPage.nextPageable();
                    }
                }

                clinicalRecordPage = clinicalRecordRepository
                        .findAllByEstado(StatusExpediente.EXPEDIENTE_PENDIENTE.name(),
                                pageable);

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

        if (!clinicalRecords.isEmpty()) {
            if (index + 1 == clinicalRecords.size()) {
                paginationFlag = true;
            }
            clinicalRecord = clinicalRecords.get(index);
            index++;
        }
        return clinicalRecord;
    }
}