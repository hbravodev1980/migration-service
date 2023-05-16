package pe.com.ci.migration.migrationservice.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemProcessor;
import pe.com.ci.migration.migrationservice.entity.ClinicalRecord;
import pe.com.ci.migration.migrationservice.entity.Document;
import pe.com.ci.migration.migrationservice.entity.Encuentro;
import pe.com.ci.migration.migrationservice.entity.Factura;
import pe.com.ci.migration.migrationservice.repository.DocumentRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class ClinicalRecordItemProcessor implements ItemProcessor<ClinicalRecord, Factura> {

    private final ModelMapper modelMapper;
    private final DocumentRepository documentRepository;

    @Override
    public Factura process(ClinicalRecord clinicalRecord) throws Exception {
        List<Document> documents = documentRepository
                .findAllByNroLoteAndFacturaNro(clinicalRecord.getNroLote(), clinicalRecord.getFacturaNro());

        if (documents.isEmpty()) {
            return null;
        }

        List<Encuentro> encuentros = documents.stream()
                .map(document -> modelMapper.map(document, Encuentro.class))
                .collect(Collectors.toList());

        Factura factura = modelMapper.map(clinicalRecord, Factura.class);
        factura.setEncuentros(encuentros);
        return factura;
    }
}