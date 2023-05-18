package pe.com.ci.migration.migrationservice.entity;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import static com.fasterxml.jackson.annotation.JsonFormat.Shape;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import static pe.com.ci.migration.migrationservice.util.GenericConstant.DEFAULT_LOCALE;
import static pe.com.ci.migration.migrationservice.util.GenericConstant.FORMAT_YYYYMMDD;
import static pe.com.ci.migration.migrationservice.util.GenericConstant.FORMATSLASH_DDMMYYYY;

import java.time.LocalDate;
import java.util.List;

@Container(containerName = "clinicalrecordtest")
@Data
public class ClinicalRecord {
    @Id
    private String id;

    private String origen;

    @PartitionKey
    private Integer nroLote;

    private String mecanismoFacturacionId;
    private String mecanismoFacturacionDesc;
    private String modoFacturacion;
    private String modoFacturacionId;
    private List<String> nroEncuentro;
    private String pacienteTipoDocIdentDesc;
    private String pacienteTipoDocIdentId;
    private String pacienteNroDocIdent;
    private String pacienteNombre;
    private String pacienteApellidoPaterno;
    private String pacienteApellidoMaterno;
    private String garanteDescripcion;
    private String garanteId;
    private String facturaNro;
    private String facturaNroAfecta;
    private String facturaNroInafecta;

    @JsonFormat(shape = Shape.STRING, pattern = FORMATSLASH_DDMMYYYY, locale = DEFAULT_LOCALE)
    private LocalDate facturaFecha;

    private Double facturaImporte;
    private String archivoAnexoDet;
    private String archivoAnexoDetSas;
    private String facturaArchivo;
    private String facturaArchivoSas;
    private String nroHistoriaClinica;
    private String beneficioId;
    private String beneficioDescripcion;
    private String estado;
    private String estadoFactura;
    private String origenServicioId;
    private String origenServicioDesc;
    private Long nroRemesa;

    @JsonFormat(shape = Shape.STRING, pattern = FORMAT_YYYYMMDD, locale = DEFAULT_LOCALE)
    private LocalDate fechaAtencion;

    private String sedeRenipress;
    private String coEstru;
    private String coCentro;
}