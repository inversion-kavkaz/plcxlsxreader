-- Start of DDL Script for Table XXI.IK_PLC_XLS_ZP


CREATE TABLE ik_plc_xls_zp
    (id                             NUMBER NOT NULL,
    cusrlogname                    VARCHAR2(30 BYTE) NOT NULL,
    cfio                           VARCHAR2(255 BYTE) NOT NULL,
    cinn                           VARCHAR2(12 BYTE) NOT NULL,
    caccc                          VARCHAR2(20 BYTE) NOT NULL,
    msumm                          NUMBER(10,2) NOT NULL)
/

-- Grants for Table
GRANT DELETE ON ik_plc_xls_zp TO odb
/
GRANT INSERT ON ik_plc_xls_zp TO odb
/
GRANT UPDATE ON ik_plc_xls_zp TO odb
/




-- Indexes for IK_PLC_XLS_ZP

CREATE INDEX ik_plc_xls_zp_nuk ON ik_plc_xls_zp
  (
    cusrlogname                     ASC
  )
/



-- Constraints for IK_PLC_XLS_ZP

ALTER TABLE ik_plc_xls_zp
ADD CONSTRAINT ik_plc_xls_zp_pk PRIMARY KEY (id, cusrlogname)
/


-- Comments for IK_PLC_XLS_ZP

COMMENT ON TABLE ik_plc_xls_zp IS 'BVV 2020/09/11
����������� ����� �������� �� XLSX
'
/
COMMENT ON COLUMN ik_plc_xls_zp.caccc IS '���� ����������'
/
COMMENT ON COLUMN ik_plc_xls_zp.cfio IS '���'
/
COMMENT ON COLUMN ik_plc_xls_zp.cinn IS '���'
/
COMMENT ON COLUMN ik_plc_xls_zp.cusrlogname IS '������������'
/
COMMENT ON COLUMN ik_plc_xls_zp.id IS '��������� ����'
/
COMMENT ON COLUMN ik_plc_xls_zp.msumm IS '�����'
/
create public synonym ik_plc_xls_zp for ik_plc_xls_zp
/
-- End of DDL Script for Table XXI.IK_PLC_XLS_ZP

