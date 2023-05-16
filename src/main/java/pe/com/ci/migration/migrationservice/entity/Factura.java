package pe.com.ci.migration.migrationservice.entity;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.List;

import static pe.com.ci.migration.migrationservice.util.GenericConstant.DEFAULT_LOCALE;
import static pe.com.ci.migration.migrationservice.util.GenericConstant.FORMATSLASH_DDMMYYYY;
import static pe.com.ci.migration.migrationservice.util.GenericConstant.FORMAT_YYYYMMDD;

@Container(containerName = "facturas")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Factura {
    private String archivoAnexoDet;
    private String archivoAnexoDetSas;
    private String beneficioId;
    private String beneficioDescripcion;
    private String coCentro;
    private String coEstru;
    private List<Encuentro> encuentros;
    private String estado;
    private String estadoFactura;
    private String facturaArchivo;
    private String facturaArchivoSas;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FORMATSLASH_DDMMYYYY, locale = DEFAULT_LOCALE)
    private LocalDate facturaFecha;

    private Double facturaImporte;
    private String facturaNro;
    private String facturaNroAfecta;
    private String facturaNroInafecta;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FORMAT_YYYYMMDD, locale = DEFAULT_LOCALE)
    private LocalDate fechaAtencion;

    private String garanteDescripcion;
    private String garanteId;
    private String mecanismoFacturacionDesc;
    private String mecanismoFacturacionId;
    private String modoFacturacion;
    private String modoFacturacionId;
    private String nroHistoriaClinica;

    @PartitionKey
    private String nroLote;

    private Long nroRemesa;
    private String origen;
    private String origenServicioDesc;
    private String origenServicioId;
    private String pacienteApellidoMaterno;
    private String pacienteApellidoPaterno;
    private String pacienteNombre;
    private String pacienteNroDocIdent;
    private String pacienteTipoDocIdentDesc;
    private String pacienteTipoDocIdentId;
    private String sedeRenipress;

    @Id
    private String id;
}