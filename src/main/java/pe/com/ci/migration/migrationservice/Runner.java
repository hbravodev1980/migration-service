package pe.com.ci.migration.migrationservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import pe.com.ci.migration.migrationservice.repository.FacturaRepository;

@Component
@Slf4j
public class Runner implements CommandLineRunner {
    @Autowired
    private FacturaRepository facturaRepository;

    @Override
    public void run(String... args) throws Exception {
        facturaRepository.deleteAll();
        log.info("Runner - Registros eliminados de Factura");
    }
}