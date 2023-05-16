package pe.com.ci.migration.migrationservice.entity;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import static com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import static pe.com.ci.migration.migrationservice.util.GenericConstant.FORMAT_YYYYMMDD;

import java.time.LocalDate;
import java.util.List;

@Container(containerName = "document")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Document {
    @Id
    private String id;
    private String nroEncuentro;
    private String pacienteApellidoPaterno;
    private String pacienteNombre;
    private String pacienteApellidoMaterno;
    private String pacienteTipoDocIdentId;
    private String pacienteNroDocIdent;
    private String pacienteTipoDocIdentDesc;

    @PartitionKey
    private String nroLote;

    private String facturaNro;
    private String garanteId;
    private String garanteDescripcion;
    private Long nroRemesa;
    private Double facturaImporte;
    private String facturaIdDocumento;

    @JsonFormat(shape = Shape.STRING, pattern = FORMAT_YYYYMMDD)
    private LocalDate fechaAtencion;

    private Long mecanismoFacturacionId;
    private Long modoFacturacionId;
    private String origenServicio;
    private String beneficio;
    private String beneficioDesc;
    private String sede;
    private String sedeDesc;
    private List<ArchivoInput> archivos;
    private Integer estado;
    private String sexoPaciente;

    @JsonFormat(shape = Shape.STRING, pattern = FORMAT_YYYYMMDD)
    private LocalDate fechaMensaje;

    private String codigoOrigenPaciente;
    private String codigoServicioOrigen;
    private String codigoCamaPaciente;
    private String codigoSede;
    private String codigoCMP;
    private String codigoServDestino;
    private String descripServDestino;
    private String codigoApp;
    private String peticionHisID;
    private String peticionLisID;

    @JsonFormat(shape = Shape.STRING, pattern = FORMAT_YYYYMMDD)
    private LocalDate fechaTransaccion;

    @JsonFormat(shape = Shape.STRING, pattern = FORMAT_YYYYMMDD)
    private LocalDate fechaEfectivaOrden;

    private String codigoServSolicita;
    private String descripcionServSolicita;
    private String fullName;
    private String transactionId;
    private String applicationId;
    private String userId;
    private String userName;
    private String origenDescripcion;
}