package pe.com.ci.migration.migrationservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

import static pe.com.ci.migration.migrationservice.util.GenericConstant.FORMAT_YYYYMMDD;

@Data
public class Encuentro {
    private String applicationId;
    private List<ArchivoOutput> archivos;
    private String beneficio;
    private String beneficioDesc;
    private String codigoApp;
    private String codigoCamaPaciente;
    private String codigoCMP;
    private String codigoOrigenPaciente;
    private String codigoSede;
    private String codigoServDestino;
    private String codigoServicioOrigen;
    private String codigoServSolicita;
    private String descripcionServSolicita;
    private String descripServDestino;
    private Integer estado;
    private String facturaIdDocumento;
    private Double facturaImporte;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FORMAT_YYYYMMDD)
    private LocalDate fechaAtencion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FORMAT_YYYYMMDD)
    private LocalDate fechaEfectivaOrden;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FORMAT_YYYYMMDD)
    private LocalDate fechaMensaje;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FORMAT_YYYYMMDD)
    private LocalDate fechaTransaccion;

    private String fullName;
    private String nroEncuentro;
    private Long nroRemesa;
    private String origenDescripcion;
    private String origenServicio;
    private String pacienteApellidoMaterno;
    private String pacienteApellidoPaterno;
    private String pacienteNombre;
    private String pacienteNroDocIdent;
    private String pacienteTipoDocIdentDesc;
    private String pacienteTipoDocIdentId;
    private String peticionHisID;
    private String peticionLisID;
    private String sede;
    private String sedeDesc;
    private String sexoPaciente;
    private String transactionId;
    private String userId;
    private String userName;
}