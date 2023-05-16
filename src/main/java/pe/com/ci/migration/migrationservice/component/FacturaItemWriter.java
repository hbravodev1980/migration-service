package pe.com.ci.migration.migrationservice.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import pe.com.ci.migration.migrationservice.entity.Factura;
import pe.com.ci.migration.migrationservice.repository.FacturaRepository;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class FacturaItemWriter implements ItemWriter<Factura> {

    private final FacturaRepository facturaRepository;

    @Override
    public void write(List<? extends Factura> facturas) throws Exception {
        log.info("FacturaItemWriter - Hilo: {}, Facturas a Grabar: {}",
                Thread.currentThread().getName(),
                facturas.size()
        );
        facturaRepository.saveAll(facturas);
    }
}