-- Start of DDL Script for Table XXI.IK_PLC_XLS_PENS

CREATE TABLE ik_plc_xls_pens
    (id                             NUMBER NOT NULL,
    cusrlogname                    VARCHAR2(30 BYTE) NOT NULL,
    cfio                           VARCHAR2(255 BYTE) NOT NULL,
    cinn                           VARCHAR2(12 BYTE) NOT NULL,
    caccc                          VARCHAR2(20 BYTE) NOT NULL,
    msumm                          NUMBER(10,2) NOT NULL,
    ipaydate                       NUMBER NOT NULL,
    iotd                           NUMBER,
    ls                             NUMBER,
    passport                       VARCHAR2(32 BYTE) NOT NULL)
/

-- Grants for Table
GRANT DELETE ON ik_plc_xls_pens TO odb
/
GRANT INSERT ON ik_plc_xls_pens TO odb
/
GRANT UPDATE ON ik_plc_xls_pens TO odb
/




-- Indexes for IK_PLC_XLS_PENS

CREATE INDEX iik_plc_xls_pens_nuk ON ik_plc_xls_pens
  (
    cusrlogname                     ASC
  )
/



-- Constraints for IK_PLC_XLS_PENS

ALTER TABLE ik_plc_xls_pens
ADD CONSTRAINT ik_plc_xls_pens_pk PRIMARY KEY (id, cusrlogname)
/


-- Comments for IK_PLC_XLS_PENS

COMMENT ON TABLE ik_plc_xls_pens IS 'BVV 2020/09/11
����������� ����� �������� �� XLSX
'
/
COMMENT ON COLUMN ik_plc_xls_pens.caccc IS '���� ����������'
/
COMMENT ON COLUMN ik_plc_xls_pens.cfio IS '���'
/
COMMENT ON COLUMN ik_plc_xls_pens.cinn IS '���'
/
COMMENT ON COLUMN ik_plc_xls_pens.cusrlogname IS '������������'
/
COMMENT ON COLUMN ik_plc_xls_pens.id IS '��������� ����'
/
COMMENT ON COLUMN ik_plc_xls_pens.ipaydate IS '���� ������� (�����)'
/
COMMENT ON COLUMN ik_plc_xls_pens.msumm IS '�����'
/
create PUBLIC SYNONYM ik_plc_xls_pens for ik_plc_xls_pens
/

-- End of DDL Script for Table XXI.IK_PLC_XLS_PENS

