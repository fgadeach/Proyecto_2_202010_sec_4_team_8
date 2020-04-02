package model.logic;

public class Comparendos implements Comparable<Comparendos> {

	private String TYPE = "";
	private int OBJECTID = -1;
	private String FECHA_HORA = "";
	private String CLASE_VEHI = "";
	private String TIPO_SERVI = "";
	private String INFRACCION = "";
	private String DES_INFRAC = "";
	private String LOCALIDAD = "";
	private String TYPE_GEO = "";
	private String COORDINATES = "";


	public Comparendos( String tipo, int id, String fecha, String clase, String tipoS, String infraccion ,String descripcion, String localidad, String tipoGeo, String coordenadas)
	{
		TYPE = tipo;
		OBJECTID = id;
		FECHA_HORA = fecha;
		CLASE_VEHI = clase;
		TIPO_SERVI = tipoS;
		INFRACCION = infraccion;
		DES_INFRAC = descripcion;
		LOCALIDAD = localidad;
		TYPE_GEO = tipoGeo;
		COORDINATES = coordenadas;
	}

	public String getTYPE() {
		return TYPE;
	}

	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}

	public int getOBJECTID() {
		return OBJECTID;
	}

	public void setOBJECTID(int oBJECTID) {
		OBJECTID = oBJECTID;
	}

	public String getFECHA_HORA() {
		return FECHA_HORA;
	}

	public void setFECHA_HORA(String fECHA_HORA) {
		FECHA_HORA = fECHA_HORA;
	}

	public String getCLASE_VEHI() {
		return CLASE_VEHI;
	}

	public void setCLASE_VEHI(String cLASE_VEHI) {
		CLASE_VEHI = cLASE_VEHI;
	}

	public String getTIPO_SERVI() {
		return TIPO_SERVI;
	}

	public void setTIPO_SERVI(String tIPO_SERVI) {
		TIPO_SERVI = tIPO_SERVI;
	}

	public String getINFRACCION() {
		return INFRACCION;
	}

	public void setINFRACCION(String iNFRACCION) {
		INFRACCION = iNFRACCION;
	}

	public String getDES_INFRAC() {
		return DES_INFRAC;
	}

	public void setDES_INFRAC(String dES_INFRAC) {
		DES_INFRAC = dES_INFRAC;
	}

	public String getLOCALIDAD() {
		return LOCALIDAD;
	}

	public void setLOCALIDAD(String lOCALIDAD) {
		LOCALIDAD = lOCALIDAD;
	}

	public String getTYPE_GEO() {
		return TYPE_GEO;
	}

	public void setTYPE_GEO(String tYPE_GEO) {
		TYPE_GEO = tYPE_GEO;
	}

	public String getCOORDINATES() {
		return COORDINATES;
	}

	public void setCOORDINATES(String cOORDINATES) {
		COORDINATES = cOORDINATES;
	}

	@Override
	public int compareTo(Comparendos o) {
		// TODO Auto-generated method stub
		int resp = 0;


		if(this.getCOORDINATES().compareTo(o.getCOORDINATES()) > 1) {
			resp = 1;
		}
		else if(this.getCOORDINATES().compareTo(o.getCOORDINATES()) < 1) {
			resp = -1;
		}
		else if(this.getCOORDINATES().compareTo(o.getCOORDINATES()) == 0) 
		{
			if(this.getOBJECTID() > o.getOBJECTID()) {
				resp = 1;
			}
			else {
				resp = -1;
			}	
		}
		return resp;
	}

}
